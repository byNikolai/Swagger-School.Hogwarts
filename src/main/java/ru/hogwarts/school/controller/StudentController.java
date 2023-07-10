package ru.hogwarts.school.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;

@RestController
@RequestMapping("student")
@Tag(name = "API for students")
public class StudentController {
    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Student creation")
    public ResponseEntity<Student> create(@RequestBody Student student) {
        Student addedStudent = service.add(student);
        return ResponseEntity.ok(addedStudent);
    }

    @PutMapping
    @Operation(summary = "Student update")
    public ResponseEntity<Student> update(@RequestBody Student student) {
        Student updatedStudent = service.update(student);
        return ResponseEntity.ok(updatedStudent);
    }
    @DeleteMapping("{id}")
    @Operation(summary = "Student remove")
    public ResponseEntity<Student> remove(@PathVariable Long id) {
        Student removedStudent = service.remove(id);
        return ResponseEntity.ok(removedStudent);
    }
    @GetMapping("{id}")
    @Operation(summary = "Get student")
    public ResponseEntity<Student> get(@PathVariable Long id) {
        Student student = service.get(id);
        return ResponseEntity.ok(student);
    }
    @GetMapping("all")
    @Operation(summary = "Get all Students")
    public ResponseEntity<Collection<Student>> getAll() {
        Collection<Student> students = service.getAll();
        return ResponseEntity.ok(students);
    }
    @GetMapping("age")
    @Operation(summary = "Get all Student by age")
    public ResponseEntity<Collection<Student>> getByAge(@RequestParam Integer lowestAge,
                                                        @RequestParam Integer highestAge) {
        Collection<Student> students = service.getByAge(lowestAge, highestAge);
        return ResponseEntity.ok(students);
    }

    @GetMapping("faculty/{studentId}")
    @Operation(summary = "Get students faculty")
    public ResponseEntity<Faculty> getFaculty(@PathVariable Long studentId) {
        Faculty faculty = service.get(studentId).getFaculty();
        return ResponseEntity.ok(faculty);
    }

    @GetMapping("count")
    @Operation(summary = "Get students quantity")
    public ResponseEntity<Integer> getStudentsCount() {
        Integer count = service.getStudentsCount();
        return ResponseEntity.ok(count);
    }

    @GetMapping("age/average")
    @Operation(summary = "Get average quantity of students")
    public ResponseEntity<Float> getStudentsAverageAge() {
        Float averageAge = service.getStudentsAverageAge();
        return ResponseEntity.ok(averageAge);
    }
    @GetMapping("last")
    @Operation(summary = "Get 5 last students")
    public ResponseEntity<Collection<Student>> getLastFiveStudents() {
        Collection<Student> students = service.getLastFiveStudents();
        return ResponseEntity.ok(students);
    }

}

