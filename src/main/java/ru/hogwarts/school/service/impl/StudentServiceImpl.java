package ru.hogwarts.school.service.impl;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exceptions.EntityNotFoundException;
import ru.hogwarts.school.exceptions.IncorrectArgumentException;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {
    private final Map<Long, Student> studentMap = new HashMap<>();
    private static Long idCounter = 1L;
    @Override
    public Student add(Student student) {
        studentMap.put(idCounter++, student);
        return student;
    }

    @Override
    public Student remove(Long id) {
        if (studentMap.containsKey(id)) {
            return studentMap.remove(id);
        }
        throw new EntityNotFoundException();
    }

    @Override
    public Student update(Student student) {
        if (studentMap.containsKey(student.getId())) {
            return studentMap.put(student.getId(), student);
        }
        throw new EntityNotFoundException();    }

    @Override
    public Collection<Student> getAll() {
        return studentMap.values();
    }

    @Override
    public Student get(Long id) {
        if (studentMap.containsKey(id)) {
            return studentMap.get(id);
        }
        throw new EntityNotFoundException();
    }

    @Override
    public List<Student> getByAge(Integer age) {
        if (age <= 10 || age >= 100) {
            throw new IncorrectArgumentException("Student's age is incorrect");
        }
        return studentMap.values().stream()
                .filter(e -> Objects.equals(e.getAge(), age))
                .collect(Collectors.toList());
    }
}
