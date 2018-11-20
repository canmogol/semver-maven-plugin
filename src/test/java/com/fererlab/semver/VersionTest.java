package com.fererlab.semver;

import lombok.val;
import org.junit.Before;
import org.junit.Test;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Version tests.
 */
public class VersionTest {

    private static final String VERSION_NUMBER_001 = "0.0.1";
    private static final String VERSION_NUMBER_002 = "0.0.2";
    private static final String VERSION_NUMBER_010 = "0.1.0";
    private static final String VERSION_NUMBER_020 = "0.2.0";
    private static final String VERSION_NUMBER_100 = "0.2.0";
    private static final String VERSION_NUMBER_200 = "2.0.0";
    private static final String VERSION_NUMBER_123 = "1.2.3";
    private static final String VERSION_NUMBER_124 = "1.2.4";

    private Version version_0_0_1;
    private Version version_0_0_2;

    private Version version_0_1_0;
    private Version version_0_2_0;

    private Version version_1_0_0;
    private Version version_2_0_0;

    private Version version_1_2_3;
    private Version version_1_2_4;

    @Before
    public void setup() {
        version_0_0_1 = new Version(VERSION_NUMBER_001);
        version_0_0_2 = new Version(VERSION_NUMBER_002);

        version_0_1_0 = new Version(VERSION_NUMBER_010);
        version_0_2_0 = new Version(VERSION_NUMBER_020);

        version_1_0_0 = new Version(VERSION_NUMBER_100);
        version_2_0_0 = new Version(VERSION_NUMBER_200);

        version_1_2_3 = new Version(VERSION_NUMBER_123);
        version_1_2_4 = new Version(VERSION_NUMBER_124);
    }

    @Test
    public void testGetShouldReturnSameValue() throws Exception {
        assertThat(version_0_0_1.get(), is(VERSION_NUMBER_001));
        assertThat(version_0_0_2.get(), is(VERSION_NUMBER_002));
        assertThat(version_0_1_0.get(), is(VERSION_NUMBER_010));
        assertThat(version_0_2_0.get(), is(VERSION_NUMBER_020));
        assertThat(version_1_0_0.get(), is(VERSION_NUMBER_100));
        assertThat(version_2_0_0.get(), is(VERSION_NUMBER_200));
        assertThat(version_1_2_3.get(), is(VERSION_NUMBER_123));
        assertThat(version_1_2_4.get(), is(VERSION_NUMBER_124));
    }

    /**
     * Tests the equals and compareTo methods.
     *
     * @throws Exception
     */
    @Test
    public void testGetShouldBeEqualToSameVersion() throws Exception {
        assertThat(version_0_0_1, is(new Version(VERSION_NUMBER_001)));
        assertThat(version_0_0_2, is(new Version(VERSION_NUMBER_002)));
        assertThat(version_0_1_0, is(new Version(VERSION_NUMBER_010)));
        assertThat(version_0_2_0, is(new Version(VERSION_NUMBER_020)));
        assertThat(version_1_0_0, is(new Version(VERSION_NUMBER_100)));
        assertThat(version_2_0_0, is(new Version(VERSION_NUMBER_200)));
        assertThat(version_1_2_3, is(new Version(VERSION_NUMBER_123)));
        assertThat(version_1_2_4, is(new Version(VERSION_NUMBER_124)));
    }

    @Test
    public void test200ShouldBeTheGreatestOfThem() throws Exception {
        val lessThan200 = Stream.of(
            version_0_0_1,
            version_0_0_2,
            version_0_1_0,
            version_0_2_0,
            version_1_0_0,
            version_1_2_3,
            version_1_2_4
        ).collect(Collectors.toList());
        for (Version less : lessThan200) {
            shouldBeGreaterThen(version_2_0_0, less);
        }
    }

    @Test
    public void test123ShouldBeTheGreatestOfThem() throws Exception {
        val lessThan200 = Stream.of(
            version_0_0_1,
            version_0_0_2,
            version_0_1_0,
            version_0_2_0,
            version_1_0_0
        ).collect(Collectors.toList());
        for (Version less : lessThan200) {
            shouldBeGreaterThen(version_2_0_0, less);
        }
    }

    @Test
    public void test010ShouldBeTheGreatestOfThem() throws Exception {
        val lessThan200 = Stream.of(
            version_0_0_1,
            version_0_0_2
        ).collect(Collectors.toList());
        for (Version less : lessThan200) {
            shouldBeGreaterThen(version_2_0_0, less);
        }
    }

    private void shouldBeGreaterThen(Version greater, Version less) {
        val error = String.format("%s is not greater then %s", greater.get(), less.get());
        assertThat(error, firstGreaterThenSecond(greater, less));
    }

    private boolean firstGreaterThenSecond(Version greater, Version less) {
        return greater.compareTo(less) == 1;
    }

    @Test
    public void test001ShouldBeTheLeastOfThem() throws Exception {
        val greaterThan001 = Stream.of(
            version_0_0_2,
            version_0_1_0,
            version_0_2_0,
            version_1_0_0,
            version_2_0_0,
            version_1_2_3,
            version_1_2_3
        ).collect(Collectors.toList());
        for (Version greater : greaterThan001) {
            shouldBeLessThen(version_0_0_1, greater);
        }
    }

    @Test
    public void test010ShouldBeTheLeastOfThem() throws Exception {
        val greaterThan001 = Stream.of(
            version_0_2_0,
            version_1_0_0,
            version_2_0_0,
            version_1_2_3,
            version_1_2_3
        ).collect(Collectors.toList());
        for (Version greater : greaterThan001) {
            shouldBeLessThen(version_0_0_1, greater);
        }
    }

    @Test
    public void test100ShouldBeTheLeastOfThem() throws Exception {
        val greaterThan001 = Stream.of(
            version_2_0_0,
            version_1_2_3,
            version_1_2_3
        ).collect(Collectors.toList());
        for (Version greater : greaterThan001) {
            shouldBeLessThen(version_0_0_1, greater);
        }
    }

    private void shouldBeLessThen(final Version lesser, Version greater) {
        val error = String.format("%s is not less then %s", lesser.get(), greater.get());
        assertThat(error, firstLessThenSecond(lesser, greater));
    }

    private boolean firstLessThenSecond(final Version first, final Version second) {
        return first.compareTo(second) == -1;
    }

}
