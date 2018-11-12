package com.fererlab.semver;

import java.util.Objects;

/**
 * External implementation from "alex", please see https://stackoverflow.com/a/11024200.
 */
public class Version implements Comparable<Version> {

    private String version;

    /**
     * String representation of the version.
     *
     * @return version as String
     */
    public final String get() {
        return this.version;
    }

    /**
     * Version constructor.
     *
     * @param versionNumber version number
     */
    public Version(final String versionNumber) {
        if (versionNumber == null) {
            throw new IllegalArgumentException("Version can not be null");
        }
        if (!versionNumber.matches("[0-9]+(\\.[0-9]+)*")) {
            throw new IllegalArgumentException("Invalid version format");
        }
        this.version = versionNumber;
    }

    /**
     * Comparable's compareTo.
     *
     * @param that compared version
     * @return
     */
    @Override
    public final int compareTo(final Version that) {
        if (that == null) {
            return 1;
        }
        String[] thisParts = this.get().split("\\.");
        String[] thatParts = that.get().split("\\.");
        int length = Math.max(thisParts.length, thatParts.length);
        for (int i = 0; i < length; i++) {
            int thisPart = i < thisParts.length
                ? Integer.parseInt(thisParts[i]) : 0;
            int thatPart = i < thatParts.length
                ? Integer.parseInt(thatParts[i]) : 0;
            if (thisPart < thatPart) {
                return -1;
            }
            if (thisPart > thatPart) {
                return 1;
            }
        }
        return 0;
    }

    /**
     * Overridden equals method necessary for collection operations.
     *
     * @param that compared version
     * @return true if references are equal or compareTo is 0
     */
    @Override
    public final boolean equals(final Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (this.getClass() != that.getClass()) {
            return false;
        }
        return this.compareTo((Version) that) == 0;
    }

    /**
     * Compares against version number.
     * @return hash code.
     */
    @Override
    public final int hashCode() {
        return Objects.hash(version);
    }

}
