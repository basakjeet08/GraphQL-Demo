package dev.anirban.graphqldemo.service;


import dev.anirban.graphqldemo.entity.Faculty;
import dev.anirban.graphqldemo.exception.FacultyNotFound;
import dev.anirban.graphqldemo.repository.FacultyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class FacultyService {

    private final FacultyRepository facultyRepo;

    public Faculty createFaculty(String name, Double experience, String photoUrl) {

        Faculty newFaculty = Faculty
                .builder()
                .name(name)
                .experience(experience)
                .photoUrl(photoUrl)
                .avgRating(0.0)
                .totalRating(0)
                .build();

        return facultyRepo.save(newFaculty);
    }

    public List<Faculty> findAllFaculty() {
        return facultyRepo.findAll();
    }

    public Faculty findFacultyById(String id) {
        return facultyRepo
                .findById(id)
                .orElseThrow(() -> new FacultyNotFound(id));
    }

    public List<Faculty> findFacultyByName(String name) {
        return facultyRepo.findByNameContaining(name);
    }

    public void deleteFacultyById(String id) {
        if (!facultyRepo.existsById(id))
            throw new FacultyNotFound(id);

        facultyRepo.deleteById(id);
    }
}
