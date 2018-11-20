package com.fererlab.semver.gitflow;

import com.fererlab.semver.flow.FlowException;
import com.fererlab.semver.http.HttpClient;
import com.fererlab.semver.http.HttpClientDelegate;
import com.fererlab.semver.http.HttpRequest;
import com.fererlab.semver.http.HttpResponseBody;
import com.fererlab.semver.model.SemverModelFactory;
import com.fererlab.semver.params.DefaultParameterFactory;
import com.fererlab.semver.params.Params;
import lombok.val;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.util.reflection.Whitebox;

import javax.xml.parsers.DocumentBuilderFactory;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Git Flow semantic versioning tests.
 */
public class GitFlowStrategyTest {

    private static final java.lang.String DEFAULT_CURRENT = "test-branch";
    private static final java.lang.String DEFAULT_TARGET = "test-target";
    private static final java.lang.String DEFAULT_TYPE = "test-type";
    private static final java.lang.String DEFAULT_DIRECTORY = "test-directory";
    private static final java.lang.String DEFAULT_USER = "test-user";
    private static final java.lang.String DEFAULT_PROJECT = "test-project";
    private static final java.lang.String DEFAULT_URL = "test-url";

    private static final String POM_XML = "pom.xml";
    private static final String POM_NO_VERSION_TAG_XML = "pom.no-version-tag.xml";

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    private GitFlowStrategy gitFlowStrategy;

    @Mock
    private SemverModelFactory semverModelFactory;

    @Mock
    private DefaultParameterFactory defaultParameterFactory;

    @Mock
    private HttpClient httpClient;

    @Mock
    private HttpClientDelegate httpClientDelegate;

    @Mock
    private HttpRequest httpRequest;

    @Mock
    private HttpResponseBody httpResponseBody;

    private Map<String, String> parameters = Collections.unmodifiableMap(new HashMap<String, String>() {
        private static final int serialVersionUID = 1;
        {
        put(Params.CURRENT.getName(), DEFAULT_CURRENT);
        put(Params.TARGET.getName(), DEFAULT_TARGET);
        put(Params.TYPE.getName(), DEFAULT_TYPE);
        put(Params.DIRECTORY.getName(), DEFAULT_DIRECTORY);
        put(Params.USER.getName(), DEFAULT_USER);
        put(Params.PROJECT.getName(), DEFAULT_PROJECT);
        put(Params.URL.getName(), DEFAULT_URL);
    }});

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldFailMissingParameterCurrent() throws FlowException {
        failOnMissingParameter(Params.CURRENT.getName());
    }

    @Test
    public void shouldFailMissingParameterDirectory() throws FlowException {
        failOnMissingParameter(Params.DIRECTORY.getName());
    }

    @Test
    public void shouldFailMissingParameterProject() throws FlowException {
        failOnMissingParameter(Params.PROJECT.getName());
    }

    @Test
    public void shouldFailMissingParameterTarget() throws FlowException {
        failOnMissingParameter(Params.TARGET.getName());
    }

    @Test
    public void shouldFailMissingParameterType() throws FlowException {
        failOnMissingParameter(Params.TYPE.getName());
    }

    @Test
    public void shouldFailMissingParameterUrl() throws FlowException {
        failOnMissingParameter(Params.URL.getName());
    }

    @Test
    public void shouldFailMissingParameterUser() throws FlowException {
        failOnMissingParameter(Params.USER.getName());
    }

    private void failOnMissingParameter(String parameter) throws FlowException {
        val params = new HashMap<String, String>(parameters);
        params.remove(parameter);
        Whitebox.setInternalState(gitFlowStrategy, "parameters", params);
        Whitebox.setInternalState(gitFlowStrategy, "semverModelFactory", semverModelFactory);

        expectedException.expect(FlowException.class);
        val error = String.format("%s mandatory parameter is missing", parameter);
        expectedException.expectMessage(error);

        gitFlowStrategy.validate();
    }

    @Test
    public void shouldFailWhenParameterCurrentIsMaster() throws FlowException {
        val params = new HashMap<String, String>(parameters);
        val masterBranchName = "master";
        params.put(Params.CURRENT.getName(), masterBranchName);

        Whitebox.setInternalState(gitFlowStrategy, "parameters", params);
        Whitebox.setInternalState(gitFlowStrategy, "semverModelFactory", semverModelFactory);

        expectedException.expect(FlowException.class);
        val errorMessage = String.format("Current branch name cannot be 'master', found: %s", masterBranchName);
        expectedException.expectMessage(errorMessage);

        gitFlowStrategy.validate();
    }

    @Test
    public void shouldFailWhenParameterCurrentIsEmpty() throws FlowException {
        val params = new HashMap<String, String>(parameters);
        params.put(Params.CURRENT.getName(), "");

        Whitebox.setInternalState(gitFlowStrategy, "parameters", params);
        Whitebox.setInternalState(gitFlowStrategy, "semverModelFactory", semverModelFactory);

        expectedException.expect(FlowException.class);
        expectedException.expectMessage("Current branch name cannot be empty");

        gitFlowStrategy.validate();
    }

    @Test
    public void shouldFailOnEmptyParameters() throws Exception {
        Whitebox.setInternalState(gitFlowStrategy, "parameters", new HashMap<>());
        Whitebox.setInternalState(gitFlowStrategy, "semverModelFactory", semverModelFactory);
        expectedException.expect(FlowException.class);
        expectedException.expectMessage("current mandatory parameter is missing");
        gitFlowStrategy.validate();
    }

    @Test
    public void shouldFailOnEmptyResponseBody() throws Exception {
        val params = new HashMap<String, String>(parameters);
        params.put(Params.URL.getName(), "http://null");
        Whitebox.setInternalState(gitFlowStrategy, "parameters", params);
        Whitebox.setInternalState(gitFlowStrategy, "semverModelFactory", semverModelFactory);
        Whitebox.setInternalState(gitFlowStrategy, "httpClient", httpClient);
        Whitebox.setInternalState(httpClient, "httpClientDelegate", httpClientDelegate);
        Whitebox.setInternalState(httpClient, "httpRequest", httpRequest);
        Whitebox.setInternalState(httpClient, "url", params.get(Params.URL.getName()));
        Mockito.when(httpClient.getResponseBody()).thenCallRealMethod();
        Mockito.when(httpClientDelegate.getResponseBody(httpRequest)).thenReturn(httpResponseBody);
        Mockito.when(httpResponseBody.getResponseBody()).thenReturn(null);

        expectedException.expect(FlowException.class);
        String error = String.format("could not get body for url %s", params.get(Params.URL.getName()));
        expectedException.expectMessage(error);

        gitFlowStrategy.validate();
    }

    @Test
    public void test_getModelVersionFromXml_shouldFailOnNoModelVersion() throws Exception {
        expectedException.expect(FlowException.class);
        expectedException.expectMessage("There is no 'modelVersion' node found in the XML.");

        val documentBuilderFactory = DocumentBuilderFactory.newInstance();
        val documentBuilder = documentBuilderFactory.newDocumentBuilder();
        val document = documentBuilder.newDocument();
        val inputStream = getClass().getClassLoader().getResourceAsStream(POM_NO_VERSION_TAG_XML);

        Mockito.when(gitFlowStrategy.getDocumentFromInputStream(inputStream)).thenReturn(document);
        Mockito.when(gitFlowStrategy.getVersionNode(document)).thenReturn(Optional.empty());
        Mockito.when(gitFlowStrategy.getModelVersionFromXml(inputStream)).thenCallRealMethod();

        gitFlowStrategy.getModelVersionFromXml(inputStream);
    }

    @Test
    public void test_getModelVersionFromXml_shouldReturnVersion() throws Exception {

        val documentBuilderFactory = DocumentBuilderFactory.newInstance();
        val documentBuilder = documentBuilderFactory.newDocumentBuilder();
        val document = documentBuilder.newDocument();
        val inputStream = getClass().getClassLoader().getResourceAsStream(POM_NO_VERSION_TAG_XML);

        Mockito.when(gitFlowStrategy.getDocumentFromInputStream(inputStream)).thenReturn(document);
        Mockito.when(gitFlowStrategy.getVersionNode(document)).thenReturn(Optional.empty());
        Mockito.when(gitFlowStrategy.getModelVersionFromXml(inputStream)).thenCallRealMethod();

        gitFlowStrategy.getModelVersionFromXml(inputStream);
    }

}
