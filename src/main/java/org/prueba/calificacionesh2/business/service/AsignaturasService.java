package org.prueba.calificacionesh2.business.service;

import org.prueba.calificacionesh2.business.dto.AsignaturaDTO;
import org.prueba.calificacionesh2.business.exception.AsignaturaNotFoundException;
import org.prueba.calificacionesh2.business.exception.ProfesorNotFoundException;
import org.prueba.calificacionesh2.business.service.interfaces.ServiceInterface;
import org.prueba.calificacionesh2.persistence.entity.Asignatura;
import org.prueba.calificacionesh2.persistence.repository.AsignaturasRepository;
import org.prueba.calificacionesh2.persistence.repository.ProfesorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AsignaturasService implements ServiceInterface<AsignaturaDTO> {
    @Autowired
    private AsignaturasRepository asignaturasRepository;
    @Autowired
    private ProfesorRepository profesorRepository;

    @Override
    public List<AsignaturaDTO> getAll() {
        var asignaturasDTO = new ArrayList<AsignaturaDTO>();
        for (Asignatura asignatura : asignaturasRepository.findAll()) {
            asignaturasDTO.add(new AsignaturaDTO(asignatura));
        }
        return asignaturasDTO;
    }

    @Override
    public AsignaturaDTO getById(Integer id) throws AsignaturaNotFoundException {
        var optionalAsignatura = asignaturasRepository.findById(id);
        if (optionalAsignatura.isPresent()) { //En caso contrario devolver exception
            return new AsignaturaDTO(optionalAsignatura.get());
        }
        throw new AsignaturaNotFoundException("No se ha podido encontrar la asignatura con el id: " + id);
    }

    @Override
    public AsignaturaDTO add(AsignaturaDTO asignaturaDTO) throws ProfesorNotFoundException {
        var optinalProfesor = profesorRepository.findById(asignaturaDTO.getIdProfesor());
        if (optinalProfesor.isPresent()) { //En caso contrarios devolver exception
            var asignatura = new Asignatura();
            asignatura.setName(asignaturaDTO.getNombreAsignatura());
            asignatura.setIdProfesor(optinalProfesor.get());
            return new AsignaturaDTO(asignaturasRepository.save(asignatura));
        }
        throw new ProfesorNotFoundException("No se ha podido encontrar el profesor con id: "+ asignaturaDTO.getIdProfesor());
    }

    @Override
    public AsignaturaDTO update(AsignaturaDTO asignaturaDTO, Integer id) throws AsignaturaNotFoundException, ProfesorNotFoundException {
        Optional<Asignatura> exitsAsignatura = asignaturasRepository.findById(id);
        if (exitsAsignatura.isPresent()) {
            Asignatura updatedAsignatura = exitsAsignatura.get();
            updatedAsignatura.setName(asignaturaDTO.getNombreAsignatura() == null ? updatedAsignatura.getName() : asignaturaDTO.getNombreAsignatura());
            if(asignaturaDTO.getIdProfesor() != null) {
                var profesor = profesorRepository.findById(asignaturaDTO.getIdProfesor());
                if (profesor.isPresent()) {
                    updatedAsignatura.setIdProfesor(profesor.get());
                }else {
                    throw new ProfesorNotFoundException("No se ha podido encontrar el profesor con id: "+ asignaturaDTO.getIdProfesor());
                }
            }

            return new AsignaturaDTO(asignaturasRepository.save(updatedAsignatura));
        }
        throw new AsignaturaNotFoundException("No se ha podido encontrar la asignatura con el id: " + id);
    }

    @Override
    public void delete(Integer id) throws AsignaturaNotFoundException {
        var asignatura = asignaturasRepository.findById(id);
        if (asignatura.isPresent()){
            asignaturasRepository.delete(asignatura.get());
            return;
        }
        throw new AsignaturaNotFoundException("No se ha podido encontrar la asignatura con el id: " + id);
    }

}
