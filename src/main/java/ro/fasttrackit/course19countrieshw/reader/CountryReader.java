package ro.fasttrackit.course19countrieshw.reader;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ro.fasttrackit.course19countrieshw.model.Continents;
import ro.fasttrackit.course19countrieshw.model.Country;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Getter
@Component
public class CountryReader {
    public static int ID = 1;
    private final String inputFile;

    public CountryReader(@Value("${countries.fileLocation}") String inputFile) {
        this.inputFile = inputFile;
    }

    public List<Country> readCountries() {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            return reader.lines()
                    .map(this::parseCountry)
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private Country parseCountry(String line) {
        String[] tokens = line.split("[|]");
        return Country.builder()
                .id(ID++)
                .name(tokens[0])
                .capital(tokens[1])
                .population(Long.parseLong(tokens[2]))
                .area(Long.parseLong(tokens[3]))
                .continent(Continents.of(tokens[4]))
                .neighbours(tokens.length == 6 ? neighbourList(tokens[5]) : List.of())
                .build();
    }

    private List<String> neighbourList(String neighboursString) {
        String[] tokens = neighboursString.split("~");
        return Arrays.asList(tokens);
    }
}
