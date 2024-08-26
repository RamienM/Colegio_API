package org.prueba.calificacionesh2.business.service;

import org.prueba.calificacionesh2.business.dto.CalificacionDTO;
import org.prueba.calificacionesh2.business.dto.NotasEstudianteProfesorODT;
import org.prueba.calificacionesh2.business.exception.AlumnoNotFoundException;
import org.prueba.calificacionesh2.business.exception.AsignaturaNotFoundException;
import org.prueba.calificacionesh2.business.exception.CalificacionNotFoundException;
import org.prueba.calificacionesh2.business.service.interfaces.ServiceInterface;
import org.prueba.calificacionesh2.persistence.entity.Calificacion;
import org.prueba.calificacionesh2.persistence.repository.AlumnoRepository;
import org.prueba.calificacionesh2.persistence.repository.AsignaturasRepository;
import org.prueba.calificacionesh2.persistence.repository.CalificacionesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CalificacionesService implements ServiceInterface<CalificacionDTO> {
    @Autowired
    private CalificacionesRepository calificacionesRepository;
    @Autowired
    private AlumnoRepository alumnoRepository;
    @Autowired
    private AsignaturasRepository asignaturasRepository;

    @Override
    public List<CalificacionDTO> getAll() {
        var calificaciones = new ArrayList<CalificacionDTO>();
        for(var calificacion : calificacionesRepository.findAll()){
            calificaciones.add(new CalificacionDTO(calificacion));
        }
        return calificaciones;
    }

    @Override
    public CalificacionDTO getById(Integer id) throws CalificacionNotFoundException {
        var calificacion = calificacionesRepository.findById(id);
        if(calificacion.isPresent()){
            return new CalificacionDTO(calificacion.get());
        }
        throw new CalificacionNotFoundException("No se ha podido encontrar la calificacion con el id: " + id);
    }

    @Override
    public CalificacionDTO add(CalificacionDTO calificacionDTO) throws AlumnoNotFoundException, AsignaturaNotFoundException {
        var alumno = alumnoRepository.findById(calificacionDTO.getIdAlumno());
        var asignatura = asignaturasRepository.findById(calificacionDTO.getIdAsignatura());
        if(alumno.isPresent()){
            if(asignatura.isPresent()){
                var calificacion = new Calificacion();
                calificacion.setMark(calificacionDTO.getMark());
                calificacion.setIdAlumno(alumno.get());
                calificacion.setIdAsignatura(asignatura.get());
                return new CalificacionDTO(calificacionesRepository.save(calificacion));
            }
            throw new AsignaturaNotFoundException("No se ha podido encontrar la asignatura con el id: " + calificacionDTO.getIdAsignatura());
        }
        throw new AlumnoNotFoundException("No se ha encontrado el alumno con id: " + calificacionDTO.getIdAlumno());
    }

    @Override
    public CalificacionDTO update(CalificacionDTO calificacionDTO, Integer id) throws CalificacionNotFoundException, AsignaturaNotFoundException, AlumnoNotFoundException {
        var calificacion = calificacionesRepository.findById(id);
        if(calificacion.isPresent()){
            Calificacion updateCalificacion = calificacion.get();
            updateCalificacion.setMark(calificacionDTO.getMark() == null ? updateCalificacion.getMark() : calificacionDTO.getMark());

            if(calificacionDTO.getIdAsignatura() != null){
                var asignatura = asignaturasRepository.findById(calificacionDTO.getIdAsignatura());
                if(asignatura.isPresent()){
                    updateCalificacion.setIdAsignatura(asignatura.get());
                }else{
                    throw new AsignaturaNotFoundException("No se ha podido encontrar la asignatura con el id: " + calificacionDTO.getIdAsignatura());
                }
            }

            if(calificacionDTO.getIdAlumno() != null){
                var alumno = alumnoRepository.findById(calificacionDTO.getIdAlumno());
                if(alumno.isPresent()){
                    updateCalificacion.setIdAlumno(alumno.get());
                }else{
                    throw new AlumnoNotFoundException("No se ha encontrado el alumno con id: " + calificacionDTO.getIdAlumno());
                }
            }

            return new CalificacionDTO(calificacionesRepository.save(updateCalificacion));
        }
        throw new CalificacionNotFoundException("No se ha podido encontrar la calificacion con el id: " + id);
    }

    @Override
    public void delete(Integer id) throws CalificacionNotFoundException {
        var calificacion = calificacionesRepository.findById(id);
        if(calificacion.isPresent()){
            calificacionesRepository.delete(calificacion.get());
            return;
        }
        throw new CalificacionNotFoundException("No se ha podido encontrar la calificacion con el id: " + id);
    }


    public List<NotasEstudianteProfesorODT> getCalificacionesEstudiantesAndAsignaturasByIdProfesor(Integer idProfesor){
        var calificaciones = calificacionesRepository.findCalificacionesByProfesorId(idProfesor);
        if(calificaciones.isEmpty()) return new ArrayList<>();

        var notasEstudiantes = new ArrayList<NotasEstudianteProfesorODT>();
        NotasEstudianteProfesorODT notaEstudiante;

        for(var calificacion : calificaciones){
            notaEstudiante = new NotasEstudianteProfesorODT(calificacion);

            notasEstudiantes.add(notaEstudiante);
        }

        return notasEstudiantes;
    }

    public List<NotasEstudianteProfesorODT> getCalificacionesAndAsignaturasByIdAlumno(Integer idAlumno){
        var calificaciones = calificacionesRepository.findCalificacionByAlumnoId(idAlumno);
        if(calificaciones.isEmpty()) return new ArrayList<>();

        var notasEstudiantes = new ArrayList<NotasEstudianteProfesorODT>();
        NotasEstudianteProfesorODT notaEstudiante;

        for(var calificacion : calificaciones){
            notaEstudiante = new NotasEstudianteProfesorODT(calificacion);

            notasEstudiantes.add(notaEstudiante);
        }

        return notasEstudiantes;
    }
}
