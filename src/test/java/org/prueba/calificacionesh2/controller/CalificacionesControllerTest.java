package org.prueba.calificacionesh2.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prueba.calificacionesh2.business.dto.AsignaturaDTO;
import org.prueba.calificacionesh2.business.dto.CalificacionDTO;
import org.prueba.calificacionesh2.business.dto.NotasEstudianteProfesorDTO;
import org.prueba.calificacionesh2.persistence.entity.Usuario;
import org.prueba.calificacionesh2.business.exception.AlumnoNotFoundException;
import org.prueba.calificacionesh2.business.exception.AsignaturaNotFoundException;
import org.prueba.calificacionesh2.business.exception.CalificacionNotFoundException;
import org.prueba.calificacionesh2.security.JwtProvider;
import org.prueba.calificacionesh2.business.service.CalificacionesService;
import org.prueba.calificacionesh2.business.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.any;


@ExtendWith(MockitoExtension.class)
@WebMvcTest(CalificacionesController.class)
@AutoConfigureMockMvc(addFilters = false)  // Desactiva los filtros de seguridad
public class CalificacionesControllerTest {
    @MockBean
    private CalificacionesService calificacionesService;
    @MockBean
    private JwtProvider jwtProvider;
    @MockBean
    private UsuarioService usuarioService;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper jacksonObjectMapper;

    @BeforeEach
    public void setUp() {
        when(jwtProvider.generateToken(any())).thenReturn("token");
        when(jwtProvider.isTokenValid(anyString(),any())).thenReturn(true);

        Usuario usuario = new Usuario();
        when(usuarioService.loadUserByUsername(anyString())).thenReturn(usuario);
    }

    @Test
    void addCalificacionTest() throws Exception {
        //Arrange
        int id = 1;

        var calificacion = new CalificacionDTO();
        calificacion.setIdAlumno(id);
        calificacion.setMark(7.0f);
        calificacion.setIdAsignatura(id);

        when(calificacionesService.add(any(CalificacionDTO.class))).thenReturn(calificacion);

        //Act
        var result = mockMvc.perform(post("/calificaciones")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jacksonObjectMapper.writeValueAsString(calificacion)));

        //Assert
        result.andExpect(status().isCreated())
                .andExpect(jsonPath("$.idAlumno").value(calificacion.getIdAlumno()))
                .andExpect(jsonPath("$.mark").value(calificacion.getMark()))
                .andExpect(jsonPath("$.idAsignatura").value(calificacion.getIdAsignatura()));
        verify(calificacionesService,times(1)).add(any(CalificacionDTO.class));
    }

    @Test
    void addCalificacionAsingaturaNotFoundException() throws Exception {
        //Arrange
        int id = 1;

        var calificacion = new CalificacionDTO();
        calificacion.setIdAlumno(id);
        calificacion.setMark(7.0f);
        calificacion.setIdAsignatura(id);

        when(calificacionesService.add(any(CalificacionDTO.class))).thenThrow(AsignaturaNotFoundException.class);

        //Act
        var result = mockMvc.perform(post("/calificaciones")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jacksonObjectMapper.writeValueAsString(calificacion)));

        //Assert
        result.andExpect(status().isNotFound());
        verify(calificacionesService,times(1)).add(any(CalificacionDTO.class));
    }

    @Test
    void addCalificacionAlumnoNotFoundException() throws Exception {
        //Arrange
        int id = 1;

        var calificacion = new CalificacionDTO();
        calificacion.setIdAlumno(id);
        calificacion.setMark(7.0f);
        calificacion.setIdAsignatura(id);

        when(calificacionesService.add(any(CalificacionDTO.class))).thenThrow(AlumnoNotFoundException.class);

        //Act
        var result = mockMvc.perform(post("/calificaciones")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jacksonObjectMapper.writeValueAsString(calificacion)));

        //Assert
        result.andExpect(status().isNotFound());
        verify(calificacionesService,times(1)).add(any(CalificacionDTO.class));
    }

    @Test
    void getAllCalificacionesTest() throws Exception {
        //Arrange
        var calificaciones = new ArrayList<CalificacionDTO>();

        when(calificacionesService.getAll()).thenReturn(calificaciones);

        //Act
        var result = mockMvc.perform(get("/calificaciones"));

        //Assert
        result.andExpect(status().isOk())
                .andExpect(content().json("[]"));
        verify(calificacionesService,times(1)).getAll();
    }

    @Test
    void getCalificacionByIdTest() throws Exception {
        //Arrange
        int id = 1;

        var calificacion = new CalificacionDTO();
        calificacion.setIdAlumno(id);
        calificacion.setMark(7.0f);
        calificacion.setIdAsignatura(id);

        when(calificacionesService.getById(id)).thenReturn(calificacion);

        //Act
        var result = mockMvc.perform(get("/calificaciones/" + id));

        //Assert
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.idAlumno").value(calificacion.getIdAlumno()))
                .andExpect(jsonPath("$.mark").value(calificacion.getMark()))
                .andExpect(jsonPath("$.idAsignatura").value(calificacion.getIdAsignatura()));
        verify(calificacionesService,times(1)).getById(id);
    }

    @Test
    void getCalificacionByIdNotFoundException() throws Exception {
        //Arrange
        int id = 1;

        when(calificacionesService.getById(any(Integer.class))).thenThrow(CalificacionNotFoundException.class);

        //Act
        var result = mockMvc.perform(get("/calificaciones/" + id));

        //Assert
        result.andExpect(status().isNotFound());
        verify(calificacionesService,times(1)).getById(id);
    }

    @Test
    void updateCalificacionTest() throws Exception {
        //Arrange
        int id = 1;

        var calificacion = new CalificacionDTO();
        calificacion.setIdAlumno(id);
        calificacion.setMark(7.0f);
        calificacion.setIdAsignatura(id);

        when(calificacionesService.update(any(CalificacionDTO.class),any(Integer.class))).thenReturn(calificacion);

        //Act
        var result = mockMvc.perform(patch("/calificaciones/"+id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jacksonObjectMapper.writeValueAsString(calificacion)));

        //Assert
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.idAlumno").value(calificacion.getIdAlumno()))
                .andExpect(jsonPath("$.mark").value(calificacion.getMark()))
                .andExpect(jsonPath("$.idAsignatura").value(calificacion.getIdAsignatura()));
        verify(calificacionesService,times(1)).update(any(CalificacionDTO.class),any(Integer.class));
    }

    @Test
    void updateCalificacionNotFoundException() throws Exception {
        //Arrange
        int id = 1;

        var calificacion = new CalificacionDTO();
        calificacion.setIdAlumno(id);
        calificacion.setMark(7.0f);
        calificacion.setIdAsignatura(id);

        when(calificacionesService.update(any(CalificacionDTO.class),any(Integer.class))).thenThrow(CalificacionNotFoundException.class);

        //Act
        var result = mockMvc.perform(patch("/calificaciones/"+id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jacksonObjectMapper.writeValueAsString(calificacion)));

        //Assert
        result.andExpect(status().isNotFound());
        verify(calificacionesService,times(1)).update(any(CalificacionDTO.class),any(Integer.class));
    }

    @Test
    void updateCalificacionAsignaturaNotFoundException() throws Exception {
        //Arrange
        int id = 1;

        var calificacion = new CalificacionDTO();
        calificacion.setIdAlumno(id);
        calificacion.setMark(7.0f);
        calificacion.setIdAsignatura(id);

        when(calificacionesService.update(any(CalificacionDTO.class),any(Integer.class))).thenThrow(AsignaturaNotFoundException.class);

        //Act
        var result = mockMvc.perform(patch("/calificaciones/"+id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jacksonObjectMapper.writeValueAsString(calificacion)));

        //Assert
        result.andExpect(status().isNotFound());
        verify(calificacionesService,times(1)).update(any(CalificacionDTO.class),any(Integer.class));
    }

    @Test
    void updateCalificacionAlumnoNotFoundException() throws Exception {
        //Arrange
        int id = 1;

        var calificacion = new CalificacionDTO();
        calificacion.setIdAlumno(id);
        calificacion.setMark(7.0f);
        calificacion.setIdAsignatura(id);

        when(calificacionesService.update(any(CalificacionDTO.class),any(Integer.class))).thenThrow(AlumnoNotFoundException.class);

        //Act
        var result = mockMvc.perform(patch("/calificaciones/"+id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jacksonObjectMapper.writeValueAsString(calificacion)));

        //Assert
        result.andExpect(status().isNotFound());
        verify(calificacionesService,times(1)).update(any(CalificacionDTO.class),any(Integer.class));
    }

    @Test
    void deleteCalificacionTest() throws Exception {
        //Arrange
        int id = 1;
        doNothing().when(calificacionesService).delete(id);

        //Act
        var result = mockMvc.perform(delete("/calificaciones/"+id));

        //Assert
        result.andExpect(status().isOk());
        verify(calificacionesService,times(1)).delete(id);
    }

    @Test
    void deleteCalificacionNotFoundException() throws Exception {
        //Arrange
        int id = 1;
        doThrow(CalificacionNotFoundException.class).when(calificacionesService).delete(id);

        //Act
        var result = mockMvc.perform(delete("/calificaciones/"+id));

        //Assert
        result.andExpect(status().isNotFound());
        verify(calificacionesService,times(1)).delete(id);
    }

    @Test
    void getCalificacionesEstudiantesAndAsignaturasByIdProfesorTest() throws Exception {
        //Arrange
        int id = 1;
        var notas = new ArrayList<NotasEstudianteProfesorDTO>();

        when(calificacionesService.getCalificacionesEstudiantesAndAsignaturasByIdProfesor(id)).thenReturn(notas);

        //Act
        var result = mockMvc.perform(get("/calificaciones/profesor/" + id));

        //Assert
        result.andExpect(status().isOk())
                .andExpect(content().json("[]"));
        verify(calificacionesService, times(1)).getCalificacionesEstudiantesAndAsignaturasByIdProfesor(id);
    }

    @Test
    void getCalificacionesAsignaturasByIdAlumnoTest() throws Exception{
        //Arrange
        int id = 1;
        var notas = new ArrayList<NotasEstudianteProfesorDTO>();

        when(calificacionesService.getCalificacionesAndAsignaturasByIdAlumno(id)).thenReturn(notas);

        //Act
        var result = mockMvc.perform(get("/calificaciones/alumno/" + id));

        //Assert
        result.andExpect(status().isOk())
                .andExpect(content().json("[]"));
        verify(calificacionesService, times(1)).getCalificacionesAndAsignaturasByIdAlumno(id);
    }
}
