package org.prueba.calificacionesh2.business.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.prueba.calificacionesh2.persistence.entity.Asignatura;

@Data
public class AsignaturaDTO {

    private Integer id;

    @NotBlank
    @NotNull
    private String nombreAsignatura;

    @NotNull
    private Integer idProfesor;

    private String nombreProfesor;

    public AsignaturaDTO() {}
    public AsignaturaDTO(Asignatura asignatura) {
        this.id = asignatura.getId();
        this.nombreAsignatura = asignatura.getName();
        if (asignatura.getIdProfesor() != null) {
            this.idProfesor = asignatura.getIdProfesor().getId();
            this.nombreProfesor = asignatura.getIdProfesor().getNombre();
        }
    }
}
