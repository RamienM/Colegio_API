package org.prueba.calificacionesh2.business.dto;

import lombok.Data;
import org.prueba.calificacionesh2.persistence.entity.Calificacion;

@Data
public class NotasEstudianteProfesorDTO {
    private String nombreEstudiante;
    private String nombreAsignatura;
    private Float nota;

    public NotasEstudianteProfesorDTO(Calificacion calificacion){
        this.nota = calificacion.getMark();
        this.nombreEstudiante = calificacion.getIdAlumno().getNombre();
        this.nombreAsignatura = calificacion.getIdAsignatura().getName();
    }
}
