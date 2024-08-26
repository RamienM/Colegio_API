package org.prueba.calificacionesh2.controller;


import jakarta.validation.Valid;
import org.prueba.calificacionesh2.business.dto.AlumnoDTO;
import org.prueba.calificacionesh2.business.exception.AlumnoNotFoundException;
import org.prueba.calificacionesh2.business.service.AlumnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AlumnoController {
    @Autowired
    private AlumnoService alumnoService;

    @GetMapping("/alumnos")
    @ResponseBody
    public List<AlumnoDTO> getAllAlumnos() {
        return alumnoService.getAll();
    }

    @GetMapping("/alumnos/{id}")
    @ResponseBody
    public ResponseEntity<AlumnoDTO> getAlumno(@PathVariable Integer id) {
        try {
            return new ResponseEntity<>(alumnoService.getById(id), HttpStatus.OK);
        }catch(AlumnoNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/alumnos")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ResponseBody
    public ResponseEntity<AlumnoDTO> addAlumno(@Valid @RequestBody AlumnoDTO alumnoPostDTO) {
        return new ResponseEntity<>(alumnoService.add(alumnoPostDTO),HttpStatus.CREATED);
    }

    @PatchMapping("/alumnos/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ResponseBody
    public ResponseEntity<AlumnoDTO> updateAlumno(@PathVariable Integer id, @RequestBody AlumnoDTO alumnoPostDTO) {
        try {
            return new ResponseEntity<>(alumnoService.update(alumnoPostDTO, id), HttpStatus.OK);
        }catch (AlumnoNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping("/alumnos/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ResponseBody
    public ResponseEntity<Void> deleteAlumno(@PathVariable Integer id) {
        try {
            alumnoService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (AlumnoNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}