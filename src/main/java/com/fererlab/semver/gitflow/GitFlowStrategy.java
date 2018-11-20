package com.fererlab.semver.gitflow;

import com.fererlab.semver.flow.FlowException;
import com.fererlab.semver.flow.FlowStrategy;
import com.fererlab.semver.http.HttpClient;
import com.fererlab.semver.model.SemverModel;
import com.fererlab.semver.model.SemverModelFactory;
import com.fererlab.semver.Version;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

/**
 * Git flow repository operations.
 */
public class GitFlowStrategy implements FlowStrategy {

    private final String versionXmlNodeName = "version";
    private final Map<String, String> parameters;
    private final SemverModelFactory semverModelFactory;
    private final HttpClient httpClient;

    /**
     * Constructor with parameters.
     *
     * @param params  validation parameters
     * @param factory semantic versioning model factory
     * @param client http client
     */
    public GitFlowStrategy(final Map<String, String> params,
                           final SemverModelFactory factory,
                           final HttpClient client) {
        this.parameters = Collections.unmodifiableMap(params);
        this.semverModelFactory = factory;
        this.httpClient = client;
    }

    /**
     * Validates versioning against semantic versioning according to git-flow.
     *
     * @throws FlowException if versioning doesn't align with semantic versioning
     */
    @Override
    public final void validate() throws FlowException {
        val semverModel = semverModelFactory.create(parameters);
        validateModel(semverModel);
        validate(semverModel);
    }

    private void validateModel(final SemverModel semverModel) throws FlowException {
        String current = semverModel.getCurrent();
        if (current == null || current.trim().isEmpty()) {
            throw new FlowException("Current branch name cannot be empty");
        }
        if (StringUtils.equalsIgnoreCase("master", current)) {
            val error = String.format("Current branch name cannot be 'master', found: %s", current);
            throw new FlowException(error);
        }
    }

    private void validate(final SemverModel model) throws FlowException {
        // https://raw.githubusercontent.com/$user/$project/$target/$type_file
        // type_file can be pom.xml or build.gradle etc
        val url = model.getUrl()
            .replace("$user", model.getUser())
            .replace("$project", model.getProject())
            .replace("$target", model.getTarget())
            .replace("$type_file", model.getType());
        httpClient.setUrl(url);
        val response = httpClient.getResponseBody();
        try {
            val remoteVersion = getModelVersionFromXml(response);
            val branchVersion = getVersionFromFile(model);
            compareCurrentAndRemoteBranch(remoteVersion, branchVersion);
        } catch (IOException | SAXException | ParserConfigurationException e) {
            val error = String.format("Got a parsing exception, error: %s", e.getMessage());
            throw new FlowException(error, e);
        }
    }

    private void compareCurrentAndRemoteBranch(final Version remoteVersion, final Version branchVersion)
        throws FlowException {
        if (branchVersion.compareTo(remoteVersion) != -1) {
            val error = String.format("branch version %s is not greater then remote version %s",
                branchVersion.get(),
                remoteVersion.get());
            throw new FlowException(error);
        }
    }

    private Version getVersionFromFile(final SemverModel model)
        throws FlowException, ParserConfigurationException, SAXException, IOException {
        Version branchVersion;
        String pathToTypeFile = String.format("%s%s%s",
            model.getDirectory(),
            File.separator,
            model.getType());
        final File file = new File(pathToTypeFile);
        if (!file.exists()) {
            final String error = String.format("file does not exist, file path: %s", pathToTypeFile);
            throw new FlowException(error);
        }
        final FileInputStream inputStream = new FileInputStream(file);
        branchVersion = getModelVersionFromXml(inputStream);
        return branchVersion;
    }

    final Version getModelVersionFromXml(final InputStream inputStream)
        throws ParserConfigurationException, SAXException, IOException, FlowException {
        val document = getDocumentFromInputStream(inputStream);
        final Optional<Node> versionNodeOptional = getVersionNode(document);
        if (!versionNodeOptional.isPresent()) {
            throw new FlowException("There is no 'modelVersion' node found in the XML.");
        }
        val node = versionNodeOptional.get();
        val versionNumber = node.getFirstChild().getNodeValue();
        return new Version(versionNumber);
    }

    final Document getDocumentFromInputStream(final InputStream inputStream)
        throws ParserConfigurationException, SAXException, IOException {
        val documentBuilderFactory = DocumentBuilderFactory.newInstance();
        val documentBuilder = documentBuilderFactory.newDocumentBuilder();
        return documentBuilder.parse(inputStream);
    }

    final Optional<Node> getVersionNode(final Document document) {
        for (int ni = 0; ni < document.getFirstChild().getChildNodes().getLength(); ni++) {
            val node = document.getFirstChild().getChildNodes().item(ni);
            if (versionXmlNodeName.equals(node.getNodeName())) {
                return Optional.of(node);
            }
        }
        return Optional.empty();
    }

}
