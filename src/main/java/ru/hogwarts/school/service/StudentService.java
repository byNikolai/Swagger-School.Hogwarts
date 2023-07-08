package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;

public interface StudentService {
    Student add(Student student);

    Student remove(Long id);
    Student update(Student student);

    Collection<Student> getAll();

    Student get(Long id);
    List<Student> getByAge(Integer age);
}
