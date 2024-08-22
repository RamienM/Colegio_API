package org.prueba.calificacionesh2.controller;

import org.prueba.calificacionesh2.dto.CalificacionDTO;
import org.prueba.calificacionesh2.dto.NotasEstudianteProfesorODT;
import org.prueba.calificacionesh2.exception.AlumnoNotFoundException;
import org.prueba.calificacionesh2.exception.AsignaturaNotFoundException;
import org.prueba.calificacionesh2.exception.CalificacionNotFoundException;
import org.prueba.calificacionesh2.service.CalificacionesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CalificacionesController {
    @Autowired
    private CalificacionesService calificacionesService;
    @GetMapping("/calificaciones")
    public List<CalificacionDTO> getAllCalificaciones(){ return calificacionesService.getAllCalificaciones();}
    @GetMapping("/calificaciones/{id}")
    public ResponseEntity<CalificacionDTO> getCalificacionesById(@PathVariable int id) {
        try {
            return new ResponseEntity<>(calificacionesService.getCalificacionesById(id), HttpStatus.OK);
        }catch (CalificacionNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/calificaciones")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<CalificacionDTO> addCalificaciones(@RequestBody CalificacionDTO calificacionesDTO) {
        try {
            return new ResponseEntity<>(calificacionesService.addCalificaciones(calificacionesDTO), HttpStatus.CREATED);
        }catch (AsignaturaNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (AlumnoNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
    @PatchMapping("/calificaciones/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<CalificacionDTO> updateCalificaciones(@PathVariable int id, @RequestBody CalificacionDTO calificacionesDTO) {
        try {
            return new ResponseEntity<>(calificacionesService.updateCalificaciones(id, calificacionesDTO), HttpStatus.OK);
        }catch (CalificacionNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (AsignaturaNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (AlumnoNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
    @DeleteMapping("/calificaciones/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity deleteCalificaciones(@PathVariable int id) {
        try {
            calificacionesService.deleteCalificaciones(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (CalificacionNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/calificaciones/profesor/{id}")
    public List<NotasEstudianteProfesorODT> getCalificacionesEstudiantesAndAsignaturasByProfesor(@PathVariable Integer id) {
        return calificacionesService.getCalificacionesEstudiantesAndAsignaturasByIdProfesor(id);
    }

    @GetMapping("/calificaciones/alumno/{id}")
    public List<NotasEstudianteProfesorODT> getCalificacionesAndAsignaturasByAlumno(@PathVariable Integer id) {
        return calificacionesService.getCalificacionesAndAsignaturasByIdAlumno(id);
    }
}