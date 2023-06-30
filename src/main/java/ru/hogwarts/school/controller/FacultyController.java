package ru.hogwarts.school.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;

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
        @DeleteMapping({"id"})
        @Operation(summary = "Faculty remove")
        public ResponseEntity<Faculty> remove(@PathVariable Long id) {
            Faculty removedFaculty = service.remove(id);
            return ResponseEntity.ok(removedFaculty);
        }
        @GetMapping({"id"})
        @Operation(summary = "Get faculty")
        public ResponseEntity<Faculty> get(@PathVariable Long id) {
            Faculty faculty = service.get(id);
            return ResponseEntity.ok(faculty);
        }
        @GetMapping({"all"})
        @Operation(summary = "Get all faculties")
        public ResponseEntity<Collection<Faculty>> getAll() {
            Collection<Faculty> faculties = service.getAll();
            return ResponseEntity.ok(faculties);
        }
        @GetMapping({"color"})
        @Operation(summary = "Get all Faculty by color")
        public ResponseEntity<Collection<Faculty>> getByColor(@RequestParam String color) {
            Collection<Faculty> faculties = service.getByColor(color);
            return ResponseEntity.ok(faculties);
        }

    }
