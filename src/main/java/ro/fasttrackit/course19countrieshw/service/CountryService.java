package ro.fasttrackit.course19countrieshw.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.fasttrackit.course19countrieshw.model.Continents;
import ro.fasttrackit.course19countrieshw.model.Country;
import ro.fasttrackit.course19countrieshw.model.CountryUpdate;
import ro.fasttrackit.course19countrieshw.reader.CountryReader;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CountryService {
    private final CountryReader reader;
    private final List<Country> countries;

//    public CountryService(CountryReader reader) {
//        this.reader = reader;
//        this.countries = reader.readCountries();
//    }

    @PostConstruct
    void init() {
        countries.addAll(reader.readCountries());
    }

    public List<Country> listCountries(String continent) {
        return continent == null ? listAllCountries() : listCountriesByContinent(continent);
    }

    public List<Country> listAllCountries() {
        return countries;
    }

    public List<Country> listCountriesByContinent(String continent) {
        Continents cntnt = Continents.of(continent);
        return countries.stream()
                .filter(country -> country.continent() == cntnt)
                .toList();
    }

    public List<String> listAllCountryNames() {
        return countries.stream()
                .map(Country::name)
                .toList();
    }

    public Optional<Country> delete(int id) {
        Optional<Country> countryOptional = findById(id);
        countryOptional.ifPresent(countries::remove);
        return countryOptional;
    }

    public Country update(int id, CountryUpdate countryUpdate) {
        Country country = findById(id)
                .orElseThrow(() -> new RuntimeException("Could not find country with id %s".formatted(id)));
        Country updatedCountry = country
                .withCapital(countryUpdate.capital() == null ? country.capital() : countryUpdate.capital())
                .withArea(countryUpdate.area() == null ? country.area() : countryUpdate.area())
                .withPopulation(countryUpdate.population() == null ? country.population() : countryUpdate.population())
                .withId(countries.size() + 1);
        delete(country.id());
        countries.add(updatedCountry);
        return updatedCountry;
    }

    public Country add(Country newCountry) {
        Country country = newCountry.withId(countries.size() + 1);
        countries.add(country);
        return country;
    }

    public Optional<Country> findById(int id) {
        return countries.stream()
                .filter(country -> country.id() == id)
                .findFirst();
    }

    public String getCapitalByCountry(String countryName) {
        return countries.stream().
                filter(country -> country.name().equalsIgnoreCase(countryName))
                .findFirst()
                .map(Country::capital)
                .orElse("No country like this");

    }

    public Long getPopulationOfACountry(String countryName) {
        return countries.stream().
                filter(country -> country.name().equalsIgnoreCase(countryName))
                .findFirst()
                .map(Country::population)
                .orElse(-1l);
    }

    public List<Country> getCountriesByContinent(String continents) {
        return countries.stream()
                .filter(country -> country.continent().equals(Continents.of(continents)))
                .toList();
    }

    public List<String> getNeighbourByCountry(String country) {
        return countries.stream()
                .filter(country1 -> country1.name().equalsIgnoreCase(country))
                .map(Country::neighbours)
                .findAny()
                .orElseThrow();
    }

    public List<Country> countriesInContinentWithMinPopulation(Continents continent, Long minPopulation) {
        return countries.stream()
                .filter(country -> country.continent() == continent)
                .filter(country -> country.population() >= minPopulation)
                .toList();
    }

    public List<Country> getByNeighbours(String include, String exclude) {
        return countries.stream()
                .filter(country -> checkNeighbours(include, exclude, country))
                .toList();
    }

    private boolean checkNeighbours(String include, String exclude, Country country) {
        return country.neighbours().contains(include.toUpperCase()) && !country.neighbours().contains(exclude.toUpperCase());
    }
}
