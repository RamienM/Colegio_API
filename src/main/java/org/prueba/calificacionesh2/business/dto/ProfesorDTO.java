package org.prueba.calificacionesh2.business.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.prueba.calificacionesh2.persistence.entity.Profesor;

@Data
public class ProfesorDTO {

    private Integer id;

    @NotBlank
    @NotNull
    private String nombre;

    @NotBlank
    @NotNull
    private String apellido;

    @NotBlank
    @NotNull
    private String correo;

    @NotBlank
    @NotNull
    private String telefono;

    public ProfesorDTO() {}
    public ProfesorDTO(Profesor profesor) {
        this.id = profesor.getId();
        this.nombre = profesor.getNombre();
        this.apellido = profesor.getApellido();
        this.correo = profesor.getCorreo();
        this.telefono = profesor.getTelefono();
    }
}
