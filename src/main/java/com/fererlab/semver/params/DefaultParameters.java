package com.fererlab.semver.params;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Default parameters.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement
public class DefaultParameters {

    private String type;

    /**
     * Matches the given parameter with the parameter name.
     * @param parameter given parameter
     * @return parameter name
     */
    public final String get(final String parameter) {
        switch (parameter) {
            case "type":
                return type;
            default:
                return null;
        }
    }
}
