package ro.fasttrackit.course19countrieshw;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ro.fasttrackit.course19countrieshw.service.CountryService;

@SpringBootApplication
public class Course19CountriesHwApplication implements ApplicationRunner {
    @Autowired
    private CountryService service;

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Course19CountriesHwApplication.class, args);
        context.getBeanDefinitionNames();
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        service.getByNeighbours("rou", "hun")
                .forEach(System.out::println);
    }
}
