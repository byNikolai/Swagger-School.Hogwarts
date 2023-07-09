package ru.hogwarts.school.service.impl;

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

    public StudentServiceImpl(StudentRepository repository) {
        this.repository = repository;
    }

    @Override
    public Student add(Student student) {
        return repository.save(student);
    }
    @Override
    public Student remove(Long id) {
        Student student = get(id);
        repository.deleteById(id);
        return student;
    }

    @Override
    public Student update(Student student) {
        Student presentStudent = get(student.getId());
        return repository.save(student);
    }
    @Override
    public Collection<Student> getAll() {
        return repository.findAll();
    }

    @Override
    public Student get(Long id) {
        Optional<Student> student = repository.findById(id);

        if (student.isPresent()) {
            return student.get();
        } else {
            throw new EntityNotFoundException();
        }
    }

    @Override
    public Collection<Student> getByAge(Integer lowestAAge, Integer highestAge) {
        ageCheck(lowestAAge);
        ageCheck(highestAge);
        return repository.findStudentByAgeBetween(lowestAAge, highestAge);
    }

    private void ageCheck(Integer age) {
        if (age <= 10 || age >= 100) {
            throw new IncorrectArgumentException("Student's age is incorrect");
        }
    }
}
