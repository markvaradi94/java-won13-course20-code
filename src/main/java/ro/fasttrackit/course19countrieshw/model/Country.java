package ro.fasttrackit.course19countrieshw.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.With;

import java.util.List;

@With
@Builder
public record Country(
        Integer id,
        String name,
        String capital,
        Long population,
        Long area,
        Continents continent,
        List<String> neighbours
) {
}
