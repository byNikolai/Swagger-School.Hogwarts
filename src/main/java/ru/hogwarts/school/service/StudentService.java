package ru.hogwarts.school.service;

import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;

public interface StudentService {
    Student add(Student student);

    Student remove(Long id);
    Student update(Student student);

    Collection<Student> getAll();

    Student get(Long id);
    Collection<Student> getByAge(Integer lowestAge, Integer highestAge);

    Integer getStudentsCount();

    Float getStudentsAverageAge();

    List<Student> getLastFiveStudents();
    List<String> getAllStudentsWhichNameStartsWithA();
    Double getStudentsAverageAgeViaStream();

    void printStudents();
    void printStudentsSync();

}
