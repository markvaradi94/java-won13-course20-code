package ro.fasttrackit.course19countrieshw.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ro.fasttrackit.course19countrieshw.model.Country;
import ro.fasttrackit.course19countrieshw.model.CountryUpdate;
import ro.fasttrackit.course19countrieshw.service.CountryService;

import java.util.List;

@RestController
@RequestMapping("countries")
@RequiredArgsConstructor
public class CountryController {
    private final CountryService service;

    @GetMapping
    List<Country> getAll(@RequestParam(required = false) String continent) {
        return service.listCountries(continent);
    }

    @GetMapping("{id}")
    Country getById(@PathVariable int id) {
        return service.findById(id)
                .orElseThrow(() -> new RuntimeException("Could not find country with id %s".formatted(id)));
    }

    // extreme example, do not reproduce
//    @GetMapping("{id}/neighbours/{index}")
//    String neighbour(@PathVariable int id, @PathVariable int index) {
//        return service.findById(id)
//                .map(country -> country.neighbours().get(index))
//                .orElseThrow();
//    }

    @PostMapping
    Country addCountry(@RequestBody Country newCountry) {
        return service.add(newCountry);
    }

    @PatchMapping("{id}")
    Country patchCountry(@PathVariable int id, @RequestBody CountryUpdate update) {
        return service.update(id, update);
    }

    @GetMapping("names")
    List<String> countryNames() {
        return service.listAllCountryNames();
    }

    @DeleteMapping("{id}")
    Country deleteById(@PathVariable int id) {
        return service.delete(id)
                .orElseThrow(() -> new RuntimeException("Could not find country with id %s".formatted(id)));
    }
}
