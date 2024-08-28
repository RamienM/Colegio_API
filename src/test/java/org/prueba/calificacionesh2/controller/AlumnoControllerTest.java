package org.prueba.calificacionesh2.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prueba.calificacionesh2.business.dto.AlumnoDTO;
import org.prueba.calificacionesh2.persistence.entity.Usuario;
import org.prueba.calificacionesh2.business.exception.AlumnoNotFoundException;
import org.prueba.calificacionesh2.security.JwtProvider;
import org.prueba.calificacionesh2.business.service.AlumnoService;
import org.prueba.calificacionesh2.business.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = AlumnoController.class)
@AutoConfigureMockMvc(addFilters = false)  // Desactiva los filtros de seguridad
public class AlumnoControllerTest {

    @MockBean
    private AlumnoService alumnoService;
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
     void addAlumnoTest() throws Exception {
        //Arrange
        var alumno = new AlumnoDTO();
        alumno.setNombre("Ruben");
        alumno.setApellido("Ramis");
        alumno.setTelefono("123456789");
        alumno.setCorreo("ruben.ramis@patterson.agency");

        when(alumnoService.add(any(AlumnoDTO.class))).thenReturn(alumno);

        //Act
        ResultActions result = mockMvc.perform(post("/alumnos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jacksonObjectMapper.writeValueAsString(alumno)));

        //Assert
        result.andExpect(status().isCreated()).
                andExpect(jsonPath("$.nombre").value(alumno.getNombre()))
                .andExpect(jsonPath("$.apellido").value(alumno.getApellido()))
                .andExpect(jsonPath("$.telefono").value(alumno.getTelefono()))
                .andExpect(jsonPath("$.correo").value(alumno.getCorreo()));
        verify(alumnoService, times(1)).add(any(AlumnoDTO.class));
    }

    @Test
     void getAllAlumnosTest() throws Exception {
        //Arrange
        var alumnos = new ArrayList<AlumnoDTO>();

        when(alumnoService.getAll()).thenReturn(alumnos);

        //Act
        var result = mockMvc.perform(get("/alumnos"));


        //Assert
        result.andExpect(status().isOk())
                .andExpect(content().json("[]"));
        verify(alumnoService,times(1)).getAll();
    }

    @Test
     void getAlumnoByIdTest() throws Exception {
        //Arrange
        int id = 1;

        var alumno = new AlumnoDTO();
        alumno.setNombre("Ruben");
        alumno.setApellido("Ramis");
        alumno.setTelefono("123456789");
        alumno.setCorreo("ruben.ramis@patterson.agency");

        when(alumnoService.getById(id)).thenReturn(alumno);

        //Act
        ResultActions result = mockMvc.perform(get("/alumnos/"+id));

        //Assert
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value(alumno.getNombre()))
                .andExpect(jsonPath("$.apellido").value(alumno.getApellido()))
                .andExpect(jsonPath("$.telefono").value(alumno.getTelefono()))
                .andExpect(jsonPath("$.correo").value(alumno.getCorreo()));
        verify(alumnoService, times(1)).getById(id);
    }

    @Test
    void getAlumnoByIdNotFoundTest() throws Exception {
        //Arrange
        int id =1;

        when(alumnoService.getById(any(Integer.class))).thenThrow(AlumnoNotFoundException.class);

        //Act
        var result = mockMvc.perform(get("/alumnos/"+id));

        //Assert
        result.andExpect(status().isNotFound());
        verify(alumnoService,times(1)).getById(id);
    }

    @Test
    void updateAlumnoTest() throws Exception {
        //Arrange
        int id = 1;

        var alumno = new AlumnoDTO();
        alumno.setNombre("Ruben");
        alumno.setApellido("Ramis");
        alumno.setTelefono("123456789");
        alumno.setCorreo("ruben.ramis@patterson.agency");

        when(alumnoService.update(any(AlumnoDTO.class),any(Integer.class))).thenReturn(alumno);

        //Act
        ResultActions result = mockMvc.perform(patch("/alumnos/"+id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jacksonObjectMapper.writeValueAsString(alumno)));

        //Assert
        result.andExpect(status().isOk()).
                andExpect(jsonPath("$.nombre").value(alumno.getNombre()))
                .andExpect(jsonPath("$.apellido").value(alumno.getApellido()))
                .andExpect(jsonPath("$.telefono").value(alumno.getTelefono()))
                .andExpect(jsonPath("$.correo").value(alumno.getCorreo()));
        verify(alumnoService, times(1)).update(any(AlumnoDTO.class),any(Integer.class));
    }

    @Test
    void updateAlumnoNotFoundTest() throws Exception {
        //Arrange
        int id =1;
        var alumno = new AlumnoDTO();
        alumno.setNombre("Ruben");
        alumno.setApellido("Ramis");
        alumno.setTelefono("123456789");
        alumno.setCorreo("ruben.ramis@patterson.agency");

        when(alumnoService.update(any(AlumnoDTO.class),any(Integer.class))).thenThrow(AlumnoNotFoundException.class);

        //Act
        var result = mockMvc.perform(patch("/alumnos/"+id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jacksonObjectMapper.writeValueAsString(alumno)));

        //Assert
        result.andExpect(status().isNotFound());
        verify(alumnoService,times(1)).update(any(AlumnoDTO.class),any(Integer.class));
    }

    @Test
    void deleteAlumnoTest() throws Exception {
        //Arrange
        int id = 1;

        doNothing().when(alumnoService).delete(id);

        //Act
        var result = mockMvc.perform(delete("/alumnos/"+id));

        //Assert
        result.andExpect(status().isOk());
        verify(alumnoService,times(1)).delete(id);
    }

    @Test
    void deleteAlumnoNotFoundTest() throws Exception {
        //Arrange
        int id =1;

        Mockito.doThrow(AlumnoNotFoundException.class).when(alumnoService).delete(id);

        //Act
        var result = mockMvc.perform(delete("/alumnos/"+id));

        //Assert
        result.andExpect(status().isNotFound());
        verify(alumnoService,times(1)).delete(id);
    }
}
