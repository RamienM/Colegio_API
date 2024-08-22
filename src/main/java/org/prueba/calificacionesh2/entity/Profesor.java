package org.prueba.calificacionesh2.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "profesor")
public class Profesor extends Persona{
}
