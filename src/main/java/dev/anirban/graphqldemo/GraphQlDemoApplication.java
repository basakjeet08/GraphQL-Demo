package dev.anirban.graphqldemo;

import dev.anirban.graphqldemo.service.FacultyService;
import dev.anirban.graphqldemo.service.RandomNameGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@RequiredArgsConstructor
public class GraphQlDemoApplication {

    private final FacultyService service;
    private final RandomNameGenerator randomNameGenerator;

    public static void main(String[] args) {
        SpringApplication.run(GraphQlDemoApplication.class, args);
    }


    @Bean
    public CommandLineRunner commandLineRunner() {
        return _ -> {
            for (int i = 0; i < 50; i++) {
                service.createFaculty(
                        randomNameGenerator.generateRandomName(),
                        0.0,
                        ""
                );
            }
        };
    }
}