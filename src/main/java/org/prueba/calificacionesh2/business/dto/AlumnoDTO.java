package org.prueba.calificacionesh2.business.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.prueba.calificacionesh2.persistence.entity.Alumno;


@Data
@AllArgsConstructor
public class AlumnoDTO {
    private Integer id;

    @NotNull
    @NotBlank
    private String nombre;
    @NotNull
    @NotBlank
    private String apellido;
    @NotNull
    @NotBlank
    private String correo;
    @NotNull
    @NotBlank
    private String telefono;

    public AlumnoDTO() {}
    public AlumnoDTO(Alumno alumno){
        this.id = alumno.getId();
        this.nombre = alumno.getNombre();
        this.apellido = alumno.getApellido();
        this.correo = alumno.getCorreo();
        this.telefono = alumno.getTelefono();
    }

    @Override
    public String toString() {
        return "Alumno:\n\tID: "+id+" Nombre: " + nombre + "\n\tApellido: " + apellido + "\n\tCorreo: " + correo + "\n\tTelefono: " + telefono;
    }
}
