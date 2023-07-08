package ru.hogwarts.school.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.hogwarts.school.exceptions.EntityNotFoundException;
import ru.hogwarts.school.exceptions.IncorrectArgumentException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.FacultyService;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FacultyServiceImpl implements FacultyService {
    private final FacultyRepository repository;

    public FacultyServiceImpl(FacultyRepository repository) {
        this.repository = repository;
    }

    @Override
    public Faculty add(Faculty faculty) {
        return repository.save(faculty);
    }
    @Override
    public Faculty remove(Long id) {
        Faculty faculty = get(id);
        repository.deleteById(id);
        return faculty;
    }

    @Override
    public Faculty update(Faculty faculty) {
        Faculty presentStudent = get(faculty.getId());
        return repository.save(faculty);
    }
    @Override
    public Collection<Faculty> getAll() {
        return repository.findAll();
    }

    @Override
    public Faculty get(Long id) {
        Optional<Faculty> faculty = repository.findById(id);

        if (faculty.isPresent()) {
            return faculty.get();
        } else {
            throw new EntityNotFoundException();
        }
    }
    @Override
    public Collection<Faculty> getByNameOrColor(String name, String color) {
        if (!StringUtils.hasText(color) || !StringUtils.hasText(name)) {
            throw new IncorrectArgumentException("Color or Name is incorrect");
        }
        return repository.findFacultiesByNameIgnoreCaseOrColorIgnoreCase(name, color);
    }
}
