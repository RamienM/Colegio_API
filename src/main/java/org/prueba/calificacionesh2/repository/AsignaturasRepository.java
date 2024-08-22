package org.prueba.calificacionesh2.repository;

import org.prueba.calificacionesh2.entity.Asignatura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AsignaturasRepository extends JpaRepository<Asignatura, Integer> {
}
