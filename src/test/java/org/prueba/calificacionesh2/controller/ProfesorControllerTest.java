package org.prueba.calificacionesh2.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prueba.calificacionesh2.business.dto.ProfesorDTO;
import org.prueba.calificacionesh2.persistence.entity.Usuario;
import org.prueba.calificacionesh2.business.exception.ProfesorNotFoundException;
import org.prueba.calificacionesh2.security.JwtProvider;
import org.prueba.calificacionesh2.business.service.ProfesorService;
import org.prueba.calificacionesh2.business.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(ProfesorController.class)
@AutoConfigureMockMvc(addFilters = false)  // Desactiva los filtros de seguridad
public class ProfesorControllerTest {

    @MockBean
    private ProfesorService profesorService;
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
    void addProfesorTest() throws Exception {
        //Arrange
        var profesor = new ProfesorDTO();
        profesor.setNombre("Ruben");
        profesor.setApellido("Ramis");
        profesor.setTelefono("123456789");
        profesor.setCorreo("ruben.ramis@patterson.agency");

        when(profesorService.add(any(ProfesorDTO.class))).thenReturn(profesor);

        //Act
        var result = mockMvc.perform(post("/profesores").contentType(MediaType.APPLICATION_JSON)
                .content(jacksonObjectMapper.writeValueAsString(profesor)));

        //Assert
        result.andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value(profesor.getNombre()))
                .andExpect(jsonPath("$.apellido").value(profesor.getApellido()))
                .andExpect(jsonPath("$.telefono").value(profesor.getTelefono()))
                .andExpect(jsonPath("$.correo").value(profesor.getCorreo()));
        verify(profesorService,times(1)).add(any(ProfesorDTO.class));
    }

    @Test
    void getAllProfesoresTest() throws Exception {
        //Arrange
        var profesores = new ArrayList<ProfesorDTO>();

        when(profesorService.getAll()).thenReturn(profesores);

        //Act
        var result = mockMvc.perform(get("/profesores"));

        //Assert
        result.andExpect(status().isOk())
                .andExpect(content().json("[]"));
        verify(profesorService,times(1)).getAll();
    }

    @Test
    void getProfesorByIdTest() throws Exception {
        //Arrange
        int id = 1;

        var profesor = new ProfesorDTO();
        profesor.setNombre("Ruben");
        profesor.setApellido("Ramis");
        profesor.setTelefono("123456789");
        profesor.setCorreo("ruben.ramis@patterson.agency");

        when(profesorService.getById(id)).thenReturn(profesor);

        //Act
        var result = mockMvc.perform(get("/profesores/" + id));

        //Assert
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value(profesor.getNombre()))
                .andExpect(jsonPath("$.apellido").value(profesor.getApellido()))
                .andExpect(jsonPath("$.telefono").value(profesor.getTelefono()))
                .andExpect(jsonPath("$.correo").value(profesor.getCorreo()));
        verify(profesorService,times(1)).getById(id);
    }

    @Test
    void getProfesorByIdNotFoundTest() throws Exception {
        //Arrange
        int id = 1;

        when(profesorService.getById(id)).thenThrow(ProfesorNotFoundException.class);

        //Act
        var result = mockMvc.perform(get("/profesores/" + id));

        //Assert
        result.andExpect(status().isNotFound());
        verify(profesorService,times(1)).getById(id);
    }

    @Test
    void updateProfesorTest() throws Exception{
        //Arrange
        int id = 1;

        var profesor = new ProfesorDTO();
        profesor.setNombre("Ruben");
        profesor.setApellido("Ramis");
        profesor.setTelefono("123456789");
        profesor.setCorreo("ruben.ramis@patterson.agency");

        when(profesorService.update(any(ProfesorDTO.class),any(Integer.class))).thenReturn(profesor);

        //Act
        var result = mockMvc.perform(patch("/profesores/"+id).contentType(MediaType.APPLICATION_JSON)
                .content(jacksonObjectMapper.writeValueAsString(profesor)));

        //Assert
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value(profesor.getNombre()))
                .andExpect(jsonPath("$.apellido").value(profesor.getApellido()))
                .andExpect(jsonPath("$.telefono").value(profesor.getTelefono()))
                .andExpect(jsonPath("$.correo").value(profesor.getCorreo()));
        verify(profesorService,times(1)).update(any(ProfesorDTO.class),any(Integer.class));
    }

    @Test
    void updateProfesorNotFoundTest() throws Exception {
        //Arrange
        int id = 1;

        var profesor = new ProfesorDTO();
        profesor.setNombre("Ruben");
        profesor.setApellido("Ramis");
        profesor.setTelefono("123456789");
        profesor.setCorreo("ruben.ramis@patterson.agency");

        when(profesorService.update(any(ProfesorDTO.class),any(Integer.class))).thenThrow(ProfesorNotFoundException.class);

        //Act
        var result = mockMvc.perform(patch("/profesores/"+id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jacksonObjectMapper.writeValueAsString(profesor)));

        //Assert
        result.andExpect(status().isNotFound());
        verify(profesorService, times(1)).update(any(ProfesorDTO.class),any(Integer.class));
    }

    @Test
    void deleteProfesorTest() throws Exception {
        //Arrange
        int id = 1;

        doNothing().when(profesorService).delete(id);
        //Act
        var result = mockMvc.perform(delete("/profesores/"+id));

        //Assert
        result.andExpect(status().isOk());
        verify(profesorService,times(1)).delete(id);
    }

    @Test
    void deleteProfesorNotFoundTest() throws Exception {
        //Arrange
        int id = 1;

        doThrow(ProfesorNotFoundException.class).when(profesorService).delete(id);

        //Act
        var result = mockMvc.perform(delete("/profesores/"+id));

        //Assert
        result.andExpect(status().isNotFound());
        verify(profesorService,times(1)).delete(id);
    }
}
