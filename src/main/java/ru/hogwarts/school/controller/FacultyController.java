package ru.hogwarts.school.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;
import java.util.List;

@RestController
    @RequestMapping("faculty")
    @Tag(name = "API for faculties")
    public class FacultyController {
        private final FacultyService service;

        public FacultyController(FacultyService service) {
            this.service = service;
        }

        @PostMapping
        @Operation(summary = "Faculty creation")
        public ResponseEntity<Faculty> create(@RequestBody Faculty faculty) {
            Faculty addedFaculty = service.add(faculty);
            return ResponseEntity.ok(addedFaculty);
        }

        @PutMapping
        @Operation(summary = "Faculty update")
        public ResponseEntity<Faculty> update(@RequestBody Faculty faculty) {
            Faculty updatedFaculty = service.update(faculty);
            return ResponseEntity.ok(updatedFaculty);
        }
        @DeleteMapping("{id}")
        @Operation(summary = "Faculty remove")
        public ResponseEntity<Faculty> remove(@PathVariable Long id) {
            Faculty removedFaculty = service.remove(id);
            return ResponseEntity.ok(removedFaculty);
        }
        @GetMapping("{id}")
        @Operation(summary = "Get faculty")
        public ResponseEntity<Faculty> get(@PathVariable Long id) {
            Faculty faculty = service.get(id);
            return ResponseEntity.ok(faculty);
        }
        @GetMapping("all")
        @Operation(summary = "Get all faculties")
        public ResponseEntity<Collection<Faculty>> getAll() {
            Collection<Faculty> faculties = service.getAll();
            return ResponseEntity.ok(faculties);
        }
        @GetMapping("filter")
        @Operation(summary = "Get all Faculty by color or name")
        public ResponseEntity<Collection<Faculty>> getByNameOrColor(@RequestParam String name,
                                                                    @RequestParam String color) {
            Collection<Faculty> faculties = service.getByNameOrColor(name, color);
            return ResponseEntity.ok(faculties);
        }
    @GetMapping("students/{facultyId}")
    @Operation(summary = "Get students by faculty")
    public ResponseEntity<List<Student>> getStudents(@PathVariable Long facultyId) {
        List<Student> students = service.get(facultyId).getStudents();
        return ResponseEntity.ok(students);
    }

    @GetMapping("longest-name")
    @Operation(summary = "Get the longest faculty name")
    public ResponseEntity<String> getTheLongestFacultyName() {
            String longestFacultyName = service.getTheLongestFacultyName();
        return ResponseEntity.ok(longestFacultyName);
    }


}
