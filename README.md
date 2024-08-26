Proyecto de estancia de prácticas cuyo objetivo es aprender a desarrollar una API haciendo uso de Spring Boot, Spring Security y JWT. Lo que se pide realizar es lo siguiente:
#  Enunciado
Se presenta el día de las calificaciones para los estudiantes de un colegio. Para ello el director solicita al equipo de IT una funcionalidad que consiste en mostrar las calificaciones obtenidas en las distintas asignaturas por los estudiantes. Se debe tener en cuenta que cada asignatura es dada por un único profesor, pero un profesor puede estar a cargo de más de una asignatura.
Los requerimientos que debe abarcar este problema son los siguientes:
•	Que un profesor pueda obtener de su asignatura/s las calificaciones por estudiante.
•	Que un estudiante pueda obtener todas las calificaciones de sus distintas asignaturas.

#  Analisis
##  Analisis de Requisitos
###  Requisitos de Usuario
1.	Requisitos Básicos
a.	Crear/Consultar/Actualizar/Eliminar un alumno.
b.	Crear/Consultar/Actualizar/Eliminar un profesor.
c.	Crear/Consultar/Actualizar/Eliminar una asignatura.
d.	Crear/Consultar/Actualizar/Eliminar una calificación
2.	Primer apartado: “Que un profesor pueda obtener de su asignatura/s las calificaciones por estudiante”
a.	Un profesor debe poder consultar todas sus asignaturas
b.	Una asignatura debe poder consultar todas sus calificaciones.
3.	Segundo apartado: “Que un estudiante pueda obtener todas las calificaciones de sus distintas asignaturas”
a.	Un estudiante debe poder consultar todas sus calificaciones.
b.	Una calificación debe poder conocer a que asignatura pertenece.

### Analisis de restricción
1.	Una asignatura solo puede ser impartida por un único profesor.

### Suposiciones
1.	Un alumno solamente tiene una calificación de cada asignatura.

### Analisis técnico
####  UML
![image](https://github.com/user-attachments/assets/28444a3e-cc4f-4646-9364-d385c6b60508)

#### Arquitectura
Como arquitectura principal se ha usado una de 3 capas:

•	Capa de presentación -> Controlador donde se controlan las peticiones externas.

•	Capa de negocio -> Servicio donde se procesan las peticiones que le manda el controlador.

•	Capa de persistencia -> Repositorio donde se realizan las operaciones en la base de datos que son pedidas por la capa de negocio.

La razón por lo que se ha optado por esta arquitectura es por su fácil mantenimiento y ordenación. Además, asegura una alta cohesión y bajo acoplamiento.

#  Tecnologías Usadas
| Tecnología         | Descripción                                                                              |
|--------------------|------------------------------------------------------------------------------------------|
| JAVA               | V .17                                                                                    |
| Intellij IDEA      | Entorno de desarrollo                                                                    |
| Spring Boot        | V.3.3.2                                                                                  |
| H2                 | Base de datos                                                                            |
| Spring Security    | Para proteger las peticiones y el acceso a URL no deseadas                               |
| JWT                | JSON Web Token permite la autenticación de los usuarios dentro de la API                 |
| Junit y Mockito    | Para la realización de los tests                                                         |
| GitHub             | Repositorio del código y gestión de versiones                                            |
| Lombok             | Permite el uso de anotaciones permitiendo generar código más limpio                      |
| Ajax Validation    | Permite validar los datos que recibimos                                                  |
| Postman            | Permite probar el correcto funcionamiento de la API                                      |


# Peticiones API
Mostaré los links haciendo uso del puerto 8080.

## Registrarse
(POST) http://localhost:8080/auth/register

Es necesario proporcionar un JSON con los siguiente datos:
{
    "username": "Nombre_Usuario",
    "password": "Contrasña",
    "nombre": "Nombre",
    "apellido":"Apellido",
    "correo": "Correo",
    "telefono": "Telefono",
    "rol": ("ROLE_ADMIN" | "ROLE_USER") 
}

Devuelve un JSON con el usuario registrado

## Inicio Sesion
(POST) http://localhost:8080/auth/login

Es necesario proporcionar un JSON con los siguientes datos:
{
    "username": "Nombre_Usuario",
    "password": "Contraseña"
}

Devuelve un JSON con el token


## Alumnos
### Obtener todos los alumnos 
(GET) http://localhost:8080/alumnos

Devuelve un JSON con todos los alumnos

### Obtener Alumno
(GET) http://localhost:8080/alumnos/{id}

Devuelve un JSON con el alumno

### Añadir Alumno
(POST) http://localhost:8080/alumnos

Es necesario proporcionar un JSON con los siguientes datos:
{
    "nombre": "Nombre",
    "apellido":"Apellido",
    "correo":"Coreo",
    "telefono":"Telefono"
}

Devuelve un JSON con el alumno

### Editar Alumno
(PATCH) http://localhost:8080/alumnos/{id}

Es ncesario proporcionar un JSON con los siguiente datos (no es necesario añadir todos los campos):
{
    "nombre": "Nombre",
    "apellido":"Apellido",
    "correo":"Coreo",
    "telefono":"Telefono"
}

Devuelve un JSON con el alumno

### Eliminar Alumno
(DELETE) http://localhost:8080/alumnos/{id}

No devuelve nada

## Profesores
### Obtener todos los profesores 
(GET) http://localhost:8080/profesores

Devuelve un JSON con todos los profesores

### Obtener Profesor
(GET) http://localhost:8080/profesores/{id}

Devuelve un JSON con el profesor

### Añadir Profesor
(POST) http://localhost:8080/profesores

Es necesario proporcionar un JSON con los siguientes datos:
{
    "nombre": "Nombre",
    "apellido":"Apellido",
    "correo":"Coreo",
    "telefono":"Telefono"
}

Devuelve un JSON con el profesor

### Editar Profesor
(PATCH) http://localhost:8080/profesores/{id}

Es ncesario proporcionar un JSON con los siguiente datos (no es necesario añadir todos los campos):
{
    "nombre": "Nombre",
    "apellido":"Apellido",
    "correo":"Coreo",
    "telefono":"Telefono"
}

Devuelve un JSON con el profesor

### Eliminar Profesor
(DELETE) http://localhost:8080/profesores/{id}

No devuelve nada

## Asignaturas
### Obtener todas las asignaturas 
(GET) http://localhost:8080/asignaturas

Devuelve un JSON con todas las asignaturas

### Obtener Asigatura
(GET) http://localhost:8080/asignaturas/{id}

Devuelve un JSON con la asignatura

### Añadir Asignatura
(POST) http://localhost:8080/asignaturas

Es necesario proporcionar un JSON con los siguientes datos:
{
    "nombreAsignatura": "Nombre_Asignatura",
    "idProfesor": (INTEGER)
}

Devuelve un JSON con la asignatura

### Editar Asignatura
(PATCH) http://localhost:8080/asignaturas/{id}

Es ncesario proporcionar un JSON con los siguiente datos (no es necesario añadir todos los campos):
{
    "nombreAsignatura": "Nombre_Asignatura",
    "idProfesor": (INTEGER)
}

Devuelve un JSON con la asignatura

### Eliminar Asignatura
(DELETE) http://localhost:8080/asignaturas/{id}

No devuelve nada

## Calificaciones
### Obtener todas los calificaciones 
(GET) http://localhost:8080/calificaciones

Devuelve un JSON con todas las calificaciones

### Obtener Calificacion
(GET) http://localhost:8080/calificaciones/{id}

Devuelve un JSON con la calificacion

### Obtener todas las calificaciones de cada asginatura de un profesor
(GET) http://localhost:8080/calificaciones/profesor/{id}

Devuelve un JSON con las calificaciones

### Obtener todas las calificaciones de las asignaturas de un alumno
(GET) http://localhost:8080/calificaciones/alumno/{id}

Devuelve un JSON con las calificaciones

### Añadir Calificacion
(POST) http://localhost:8080/calificaciones

Es necesario proporcionar un JSON con los siguientes datos:
{
    "idAlumno": (INTEGER),
    "idAsignatura": (INTEGER),
    "mark" : (FLOAT)
}

Devuelve un JSON con la calificacion

### Editar Calificacion
(PATCH) http://localhost:8080/calificaciones/{id}

Es ncesario proporcionar un JSON con los siguiente datos (no es necesario añadir todos los campos):
{
    "idAlumno": (INTEGER),
    "idAsignatura": (INTEGER),
    "mark" : (FLOAT)
}

Devuelve un JSON con el profesor

### Eliminar Calificacion
(DELETE) http://localhost:8080/calificaciones/{id}

No devuelve nada




