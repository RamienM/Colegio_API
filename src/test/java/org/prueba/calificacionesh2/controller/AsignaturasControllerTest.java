package org.prueba.calificacionesh2.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prueba.calificacionesh2.business.dto.AsignaturaDTO;
import org.prueba.calificacionesh2.persistence.entity.Usuario;
import org.prueba.calificacionesh2.business.exception.AsignaturaNotFoundException;
import org.prueba.calificacionesh2.business.exception.ProfesorNotFoundException;
import org.prueba.calificacionesh2.security.JwtProvider;
import org.prueba.calificacionesh2.business.service.AsignaturasService;
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
@WebMvcTest(AsignaturasController.class)
@AutoConfigureMockMvc(addFilters = false)  // Desactiva los filtros de seguridad
public class AsignaturasControllerTest {

    @MockBean
    private AsignaturasService asignaturasService;
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
    void addAsignaturasTest() throws Exception {
        //Arrange
        int id = 1;

        var asignatura = new AsignaturaDTO();
        asignatura.setIdProfesor(id);
        asignatura.setNombreAsignatura("Spring");

        when(asignaturasService.add(any(AsignaturaDTO.class))).thenReturn(asignatura);

        //Act
        var result = mockMvc.perform(post("/asignaturas").contentType(MediaType.APPLICATION_JSON).content(jacksonObjectMapper.writeValueAsString(asignatura)));

        //Assert
        result.andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombreAsignatura").value(asignatura.getNombreAsignatura()))
                .andExpect(jsonPath("$.idProfesor").value(asignatura.getIdProfesor()));
        verify(asignaturasService,times(1)).add(any(AsignaturaDTO.class));
    }

    @Test
    void addAsignaturaProfesorNotFoundTest() throws Exception {
        //Arrange
        var asignatura = new AsignaturaDTO();
        asignatura.setNombreAsignatura("Spring");

        when(asignaturasService.add(any(AsignaturaDTO.class))).thenThrow(ProfesorNotFoundException.class);

        //Act
        var result = mockMvc.perform(post("/asignaturas").contentType(MediaType.APPLICATION_JSON).content(jacksonObjectMapper.writeValueAsString(asignatura)));

        //Assert
        result.andExpect(status().isNotFound());
        verify(asignaturasService,times(1)).add(any(AsignaturaDTO.class));
    }

    @Test
    void getAllAsignaturas() throws Exception{
        //Arrange
        var asignaturas = new ArrayList<AsignaturaDTO>();

        when(asignaturasService.getAll()).thenReturn(asignaturas);

        //Act
        var result = mockMvc.perform(get("/asignaturas"));

        //Assertç
        result.andExpect(status().isOk())
                .andExpect(content().json("[]"));
        verify(asignaturasService,times(1)).getAll();

    }

    @Test
    void getAsignaturaByIdTest() throws Exception {
        //Arrange
        int id = 1;

        var asignatura = new AsignaturaDTO();
        asignatura.setIdProfesor(1);
        asignatura.setNombreAsignatura("Spring");

        when(asignaturasService.getById(id)).thenReturn(asignatura);

        //Act
        var result = mockMvc.perform(get("/asignaturas/" + id));

        //Assert
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreAsignatura").value(asignatura.getNombreAsignatura()))
                .andExpect(jsonPath("$.idProfesor").value(asignatura.getIdProfesor()));
        verify(asignaturasService,times(1)).getById(id);
    }

    @Test
    void getAsignaturaByIdNotFoundTest() throws Exception {
        //Arrange
        int id = 1;

        when(asignaturasService.getById(any(Integer.class))).thenThrow(AsignaturaNotFoundException.class);

        //Act
        var result = mockMvc.perform(get("/asignaturas/"+id));

        //Assert
        result.andExpect(status().isNotFound());
        verify(asignaturasService,times(1)).getById(id);
    }

    @Test
    void updateAsignaturaTest() throws Exception {
        //Arrange
        int id = 1;

        var asignatura = new AsignaturaDTO();
        asignatura.setIdProfesor(id);
        asignatura.setNombreAsignatura("Spring");

        when(asignaturasService.update(any(AsignaturaDTO.class),any(Integer.class))).thenReturn(asignatura);

        //Act
        var result = mockMvc.perform(patch("/asignaturas/"+id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jacksonObjectMapper.writeValueAsString(asignatura)));

        //Assert
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreAsignatura").value(asignatura.getNombreAsignatura()))
                .andExpect(jsonPath("$.idProfesor").value(asignatura.getIdProfesor()));
        verify(asignaturasService,times(1)).update(any(AsignaturaDTO.class),any(Integer.class));
    }

    @Test
    void updateAsignaturaNotFoundTest() throws Exception {
        //Arrange
        int id = 1;

        var asignatura = new AsignaturaDTO();
        asignatura.setIdProfesor(id);
        asignatura.setNombreAsignatura("Spring");

        when(asignaturasService.update(any(AsignaturaDTO.class),any(Integer.class))).thenThrow(AsignaturaNotFoundException.class);

        //Act
        var result = mockMvc.perform(patch("/asignaturas/"+id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jacksonObjectMapper.writeValueAsString(asignatura)));

        //Assert
        result.andExpect(status().isNotFound());
        verify(asignaturasService,times(1)).update(any(AsignaturaDTO.class),any(Integer.class));
    }
    @Test
    void updateAsignaturaProfesorNotFoundTest() throws Exception {
        //Arrange
        int id = 1;

        var asignatura = new AsignaturaDTO();
        asignatura.setIdProfesor(id);
        asignatura.setNombreAsignatura("Spring");

        when(asignaturasService.update(any(AsignaturaDTO.class),any(Integer.class))).thenThrow(ProfesorNotFoundException.class);

        //Act
        var result = mockMvc.perform(patch("/asignaturas/"+id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jacksonObjectMapper.writeValueAsString(asignatura)));

        //Assert
        result.andExpect(status().isNotFound());
        verify(asignaturasService,times(1)).update(any(AsignaturaDTO.class),any(Integer.class));
    }

    @Test
    void deleteAsignaturaTest() throws Exception {
        //Arrange
        int id = 1;

        doNothing().when(asignaturasService).delete(id);

        //Act
        var result = mockMvc.perform(delete("/asignaturas/"+id));

        //Assert
        result.andExpect(status().isOk());
        verify(asignaturasService,times(1)).delete(id);
    }

    @Test
    void deleteAsignaturaNotFoundTest() throws Exception {
        //Arrange
        int id = 1;

        doThrow(AsignaturaNotFoundException.class).when(asignaturasService).delete(id);

        //Act
        var result = mockMvc.perform(delete("/asignaturas/"+id));

        //Assert
        result.andExpect(status().isNotFound());
        verify(asignaturasService,times(1)).delete(id);
    }

}
