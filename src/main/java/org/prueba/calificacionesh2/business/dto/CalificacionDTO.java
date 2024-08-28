package org.prueba.calificacionesh2.business.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.prueba.calificacionesh2.persistence.entity.Calificacion;

@Data
public class CalificacionDTO {
    private Integer id;

    @NotNull
    private Integer idAlumno;

    private String nombreAlumno;

    @NotNull
    private Float mark;

    @NotNull
    private Integer idAsignatura;

    private String nombreAsignatura;

    public CalificacionDTO() {}
    public CalificacionDTO(Calificacion cal) {
        this.id = cal.getId();
        if (cal.getIdAlumno() != null) {
            this.setIdAlumno(cal.getIdAlumno().getId());
            this.nombreAlumno = cal.getIdAlumno().getNombre();
        }
        this.setMark(cal.getMark());
        if (cal.getIdAsignatura() != null) {
            this.setIdAsignatura(cal.getIdAsignatura().getId());
            this.nombreAsignatura = cal.getIdAsignatura().getName();
        }
    }
}
