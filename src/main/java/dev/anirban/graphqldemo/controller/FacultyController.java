package dev.anirban.graphqldemo.controller;


import dev.anirban.graphqldemo.entity.Faculty;
import dev.anirban.graphqldemo.service.FacultyService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class FacultyController {

    private final FacultyService service;


    @MutationMapping
    public Faculty createFaculty(
            @Argument String name,
            @Argument Float experience,
            @Argument String photoUrl
    ) {
        return service.createFaculty(name, experience.doubleValue(), photoUrl);
    }

    @QueryMapping
    public List<Faculty> findAllFaculties() {
        return service.findAllFaculty();
    }

    @QueryMapping
    public List<Faculty> findAllFacultiesByName(@Argument String name) {
        return service.findFacultyByName(name);
    }

    @QueryMapping
    public Faculty findFacultyById(@Argument String id) {
        return service.findFacultyById(id);
    }

    @MutationMapping
    public String deleteFaculty(@Argument String id) {
        service.deleteFacultyById(id);
        return "Successfully Deleted !!";
    }
}