package com.fererlab.semver;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DefaultParameters {

    private String type;

    public String get(String parameter) {
        switch (parameter) {
            case "type":
                return type;
            default:
                return null;
        }
    }
}
