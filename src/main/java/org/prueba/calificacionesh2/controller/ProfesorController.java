package org.prueba.calificacionesh2.controller;

import org.prueba.calificacionesh2.business.dto.ProfesorDTO;
import org.prueba.calificacionesh2.business.exception.ProfesorNotFoundException;
import org.prueba.calificacionesh2.business.service.ProfesorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProfesorController {
    @Autowired
    private ProfesorService profesorService;

    @GetMapping("/profesores")
    @ResponseBody
    public List<ProfesorDTO> getAllProfesores() {
        return profesorService.getAll();
    }

    @GetMapping("/profesores/{id}")
    @ResponseBody
    public ResponseEntity<ProfesorDTO> getProfesor(@PathVariable Integer id) {
        try {
            return new ResponseEntity<>(profesorService.getById(id), HttpStatus.OK);
        }catch (ProfesorNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/profesores")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ResponseBody
    public ResponseEntity<ProfesorDTO> addProfesor(@RequestBody ProfesorDTO profesorDTO) {
        return new ResponseEntity<>(profesorService.add(profesorDTO),HttpStatus.CREATED);
    }

    @PatchMapping("/profesores/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ResponseBody
    public ResponseEntity<ProfesorDTO> updateProfesor(@PathVariable Integer id, @RequestBody ProfesorDTO profesorDTO) {
        try {
            return new ResponseEntity<>(profesorService.update(profesorDTO, id), HttpStatus.OK);
        }catch (ProfesorNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/profesores/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ResponseBody
    public ResponseEntity<Void> deleteProfesor(@PathVariable Integer id) {
        try {
            profesorService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (ProfesorNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
