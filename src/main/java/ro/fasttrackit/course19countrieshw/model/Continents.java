package ro.fasttrackit.course19countrieshw.model;

import java.util.Arrays;

public enum Continents {
    ASIA("asia"),
    AFRICA("africa"),
    AMERICAS("americas"),
    EUROPE("europe"),
    OCEANIA("oceania");

    private final String code;

    Continents(String code) {
        this.code = code;
    }

    public static Continents of(String value) {
        return Arrays.stream(Continents.values())
                .filter(continents -> continents.code.equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Value is not good"));
    }
}
