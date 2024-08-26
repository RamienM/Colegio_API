package org.prueba.calificacionesh2.controller;

import org.prueba.calificacionesh2.business.dto.AsignaturaDTO;
import org.prueba.calificacionesh2.business.exception.AsignaturaNotFoundException;
import org.prueba.calificacionesh2.business.exception.ProfesorNotFoundException;

import org.prueba.calificacionesh2.business.service.AsignaturasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AsignaturasController {
    @Autowired
    private AsignaturasService asignaturaService;

    @GetMapping("/asignaturas")
    @ResponseBody
    public List<AsignaturaDTO> getAllAsignaturas() {
        return asignaturaService.getAll();
    }

    @GetMapping("/asignaturas/{id}")
    @ResponseBody
    public ResponseEntity<AsignaturaDTO> getAsignatura(@PathVariable Integer id) {
        try {
            return new ResponseEntity<>(asignaturaService.getById(id), HttpStatus.OK);
        }catch (AsignaturaNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/asignaturas")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ResponseBody
    public ResponseEntity<AsignaturaDTO> addAsignatura(@RequestBody AsignaturaDTO asignaturaDTO) {
        try {
            return new ResponseEntity<>(asignaturaService.add(asignaturaDTO),HttpStatus.CREATED);
        }catch (ProfesorNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/asignaturas/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ResponseBody
    public ResponseEntity<AsignaturaDTO> updateAsignatura(@PathVariable Integer id, @RequestBody AsignaturaDTO asignaturaDTO) {
        try {
            return new ResponseEntity<>(asignaturaService.update(asignaturaDTO, id),HttpStatus.OK);
        }catch (AsignaturaNotFoundException | ProfesorNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping("/asignaturas/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ResponseBody
    public ResponseEntity<Void> deleteAsignatura(@PathVariable Integer id) {
        try {
            asignaturaService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (AsignaturaNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
}
