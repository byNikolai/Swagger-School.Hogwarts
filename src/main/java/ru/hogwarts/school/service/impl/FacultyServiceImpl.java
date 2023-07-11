package ru.hogwarts.school.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final Logger logger = LoggerFactory.getLogger(FacultyServiceImpl.class);


    public FacultyServiceImpl(FacultyRepository repository) {
        this.repository = repository;
    }

    @Override
    public Faculty add(Faculty faculty) {
        logger.info("Was invoked method for add faculty");

        return repository.save(faculty);
    }
    @Override
    public Faculty remove(Long id) {
        logger.info("Was invoked method for remove faculty");

        Faculty faculty = get(id);
        repository.deleteById(id);
        return faculty;
    }

    @Override
    public Faculty update(Faculty faculty) {
        logger.info("Was invoked method for update faculty");

        Faculty presentStudent = get(faculty.getId());
        return repository.save(faculty);
    }
    @Override
    public Collection<Faculty> getAll() {
        logger.info("Was invoked method for get all faculties");

        return repository.findAll();
    }

    @Override
    public Faculty get(Long id) {
        logger.info("Was invoked method for get faculty");

        Optional<Faculty> faculty = repository.findById(id);

        if (faculty.isPresent()) {
            return faculty.get();
        } else {
            logger.error("There is no faculty with id = " + id);

            throw new EntityNotFoundException();
        }
    }
    @Override
    public Collection<Faculty> getByNameOrColor(String name, String color) {
        logger.info("Was invoked method for get faculty by name or color");
        logger.debug("getByNameOrColor, name=" + name + "; color=" + color + ";" );
        logger.warn("You can find info about faculties in library");



        if (!StringUtils.hasText(name) || !StringUtils.hasText(color)) {
            logger.error("There is no faculty with such name or color = " + name + " or " + color);

            throw new IncorrectArgumentException("Color or Name is incorrect");
        }
        return repository.findFacultiesByNameIgnoreCaseOrColorIgnoreCase(name, color);
    }
}
