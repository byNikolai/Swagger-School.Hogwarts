package ru.hogwarts.school.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exceptions.EntityNotFoundException;
import ru.hogwarts.school.exceptions.IncorrectArgumentException;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StudentService;

import java.util.*;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository repository;
    private final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);


    public StudentServiceImpl(StudentRepository repository) {
        this.repository = repository;
    }

    @Override
    public Student add(Student student) {
        logger.info("Was invoked method for add student");


        return repository.save(student);
    }
    @Override
    public Student remove(Long id) {
        logger.info("Was invoked method for remove student");

        Student student = get(id);
        repository.deleteById(id);
        return student;
    }

    @Override
    public Student update(Student student) {
        logger.info("Was invoked method for update student");

        Student presentStudent = get(student.getId());
        return repository.save(student);
    }
    @Override
    public Collection<Student> getAll() {
        logger.info("Was invoked method for get all students");

        return repository.findAll();
    }

    @Override
    public Student get(Long id) {
        logger.info("Was invoked method for get student");

        Optional<Student> student = repository.findById(id);

        if (student.isPresent()) {
            return student.get();
        } else {
            logger.error("There is not student with id = " + id);

            throw new EntityNotFoundException();
        }
    }

    @Override
    public Collection<Student> getByAge(Integer lowestAge, Integer highestAge) {
        logger.info("Was invoked method for get student by age");
        logger.debug("getByAge, lowestAge=" + lowestAge + "; highestAge=" + highestAge + ";" );


        ageCheck(lowestAge);
        ageCheck(highestAge);
        return repository.findStudentByAgeBetween(lowestAge, highestAge);
    }

    @Override
    public Integer getStudentsCount() {
        logger.info("Was invoked method for get students count");

        return repository.getCount();
    }

    @Override
    public Float getStudentsAverageAge() {
        logger.info("Was invoked method for get students average age");

        return repository.getAverageAge();
    }

    @Override
    public List<Student> getLastFiveStudents() {
        logger.info("Was invoked method for get last five students");

        return repository.getLastFive();
    }

    private void ageCheck(Integer age) {
        if (age <= 10 || age >= 100) {
            logger.error("Incorrect age input");
            logger.warn("If student is genius, he may get age permission! Please talk to Hagrid.");

            throw new IncorrectArgumentException("Student's age is incorrect");
        }
    }
}
