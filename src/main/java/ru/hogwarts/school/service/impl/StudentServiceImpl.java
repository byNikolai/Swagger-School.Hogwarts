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
import java.util.stream.Collectors;

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

    @Override
    public List<String> getAllStudentsWhichNameStartsWithA() {
        logger.info("Was invoked method for get all students which name starts with letter A");

        return repository.findAll().stream()
                .filter(s -> s.getName().startsWith("A"))
                .map(s -> s.getName().toUpperCase())
                .sorted()
                .collect(Collectors.toList());
    }

    @Override
    public Double getStudentsAverageAgeViaStream() {
        logger.info("Was invoked method for get students average age via stream");

        return repository.findAll().stream()
                .mapToDouble(Student::getAge)
                .average()
                .orElse(0.00);
    }

    @Override
    public void printStudents() {
        List<Student> students = repository.findAll();
        if (students.size() >= 6) {
            students.subList(0,2).forEach(this::studentIdAndNameLog);
            printStudentsInThread(students.subList(2,4));
            printStudentsInThread(students.subList(4,6));
        }
    }
    @Override
    public void printStudentsSync() {
        List<Student> students = repository.findAll();
        if (students.size() >= 6) {
            students.subList(0,2).forEach(this::studentIdAndNameLog);
            printStudentsInThreadSync(students.subList(2,4));
            printStudentsInThreadSync(students.subList(4,6));
        }
    }

    public void studentIdAndNameLog(Student student) {
        logger.info("Students id= " + student.getId() + " and name= " + student.getName());
    }

    private void printStudentsInThread(List<Student> students) {
        new Thread(() -> {
            students.forEach(this::studentIdAndNameLog);
        }).start();
    }
    public synchronized void studentIdAndNameLogSync(Student student) {
        logger.info("Students id= " + student.getId() + " and name= " + student.getName());
    }
    private void printStudentsInThreadSync(List<Student> students) {
        new Thread(() -> {
            students.forEach(this::studentIdAndNameLogSync);
        }).start();
    }

    private void ageCheck(Integer age) {
        if (age <= 10 || age >= 100) {
            logger.error("Incorrect age input");
            logger.warn("If student is genius, he may get age permission! Please talk to Hagrid.");

            throw new IncorrectArgumentException("Student's age is incorrect");
        }
    }
}
