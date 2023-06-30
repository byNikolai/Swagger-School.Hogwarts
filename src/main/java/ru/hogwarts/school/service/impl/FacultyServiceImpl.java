package ru.hogwarts.school.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.hogwarts.school.exceptions.EntityNotFoundException;
import ru.hogwarts.school.exceptions.IncorrectArgumentException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FacultyServiceImpl implements FacultyService {
    private final Map<Long, Faculty> facultyMap = new HashMap<>();
    private static Long idCounter = 1L;
    @Override
    public Faculty add(Faculty faculty) {
        facultyMap.put(idCounter++, faculty);
        return faculty;
    }

    @Override
    public Faculty remove(Long id) {
        if (facultyMap.containsKey(id)) {
            return facultyMap.remove(id);
        }
        throw new EntityNotFoundException();
    }

    @Override
    public Faculty update(Faculty faculty) {
        if (facultyMap.containsKey(faculty.getId())) {
            return facultyMap.put(faculty.getId(), faculty);
        }
        throw new EntityNotFoundException();    }

    @Override
    public Collection<Faculty> getAll() {
        return facultyMap.values();
    }

    @Override
    public Faculty get(Long id) {
        if (facultyMap.containsKey(id)) {
            return facultyMap.get(id);
        }
        throw new EntityNotFoundException();
    }

    @Override
    public List<Faculty> getByColor(String color) {
        if (StringUtils.hasText(color)) {
            throw new IncorrectArgumentException("Color is incorrect");
        }
        return facultyMap.values().stream()
                .filter(e -> Objects.equals(e.getColor(), color))
                .collect(Collectors.toList());
    }}
