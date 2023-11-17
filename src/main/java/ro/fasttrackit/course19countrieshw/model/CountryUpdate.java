package ro.fasttrackit.course19countrieshw.model;

import lombok.Builder;

@Builder
public record CountryUpdate(
        String capital,
        Long area,
        Long population
) {
}
