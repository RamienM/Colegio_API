package org.prueba.calificacionesh2.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "profesor")
public class Profesor extends Persona{
}
