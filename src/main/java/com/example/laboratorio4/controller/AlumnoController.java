package com.example.laboratorio4.controller;

import com.example.laboratorio4.model.Alumno;
import com.example.laboratorio4.service.AlumnoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alumnos")
public class AlumnoController {

    private final AlumnoService alumnoService;

    public AlumnoController(AlumnoService alumnoService) {
        this.alumnoService = alumnoService;
    }

    // 200 OK - Lista de alumnos
    @GetMapping
    public List<Alumno> listar() {
        return alumnoService.listarTodos();
    }

    // 200 OK o 404 Not Found - Alumno por ID
    @GetMapping("/{id}")
    public ResponseEntity<Alumno> buscarPorId(@PathVariable Long id) {
        return alumnoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 201 Created - Registrar alumno
    @PostMapping
    public ResponseEntity<Alumno> registrar(@RequestBody Alumno alumno) {
        Alumno guardado = alumnoService.registrar(alumno);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }

    // 200 OK o 404 Not Found - Actualizar alumno
    @PutMapping("/{id}")
    public ResponseEntity<Alumno> actualizar(
            @PathVariable Long id,
            @RequestBody Alumno alumno) {

        return alumnoService.actualizar(id, alumno)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 204 No Content o 404 Not Found - Eliminar alumno
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!alumnoService.eliminar(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}