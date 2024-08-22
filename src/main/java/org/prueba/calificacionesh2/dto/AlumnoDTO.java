package org.prueba.calificacionesh2.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.prueba.calificacionesh2.entity.Alumno;


@Data
@AllArgsConstructor
public class AlumnoDTO {
    private Integer id;

    @NotBlank
    private String nombre;

    private String apellido;
    private String correo;
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
