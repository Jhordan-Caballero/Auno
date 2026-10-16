# Sistema de Gestión Médica - Clínica Auno

Plataforma web integral desarrollada para la gestión eficiente de citas médicas, historiales clínicos y administración de personal de salud. El sistema permite una interacción fluida entre pacientes y administradores, garantizando la seguridad de los datos mediante un control de acceso basado en roles.

## Características Principales

El sistema está dividido en dos flujos de trabajo principales, además de un portal público de aterrizaje (Landing Page) para la captación de nuevos pacientes.

### Módulo de Pacientes
* **Gestión de Cuentas:** Registro de nuevos usuarios y autenticación segura.
* **Reserva de Citas:** Motor de búsqueda de médicos filtrado por especialidad y selección de horarios disponibles.
* **Historial Médico:** Panel de control para visualizar citas pendientes, atendidas y canceladas.
* **Cancelación Autónoma:** Capacidad para cancelar citas programadas con liberación automática del horario.
* **Resultados Médicos:** Acceso digital de solo lectura a los diagnósticos, recetas y recomendaciones emitidas por los médicos tras cada consulta.

### Módulo de Administración
* **Control de Citas:** Panel general de monitoreo de todas las citas del sistema y sus estados actuales.
* **Registro de Atenciones:** Interfaz para documentar el motivo de consulta, diagnóstico clínico y tratamiento/receta del paciente.
* **Gestión de Personal:** Registro y habilitación de nuevos médicos en el sistema, incluyendo sus credenciales (CMP) e información de contacto.
* **Gestión de Especialidades:** Creación y mantenimiento de las áreas médicas disponibles en la clínica.
* **Apertura de Horarios:** Asignación de bloques de disponibilidad (turnos) para cada médico en fechas específicas.

## Tecnologías Utilizadas

### Backend
* **Java 17+**
* **Spring Boot 3.x** (Core, Web)
* **Spring Security:** Autenticación, encriptación de contraseñas y autorización estricta basada en roles (ROLE_ADMIN, ROLE_PACIENTE).
* **Spring Data JPA:** Mapeo objeto-relacional (ORM) e interacción con la base de datos.
* **PostgreSQL:** Motor de base de datos relacional.

### Frontend
* **Thymeleaf:** Motor de plantillas del lado del servidor para renderizado dinámico.
* **HTML5 & CSS3:** Arquitectura de estilos centralizada y diseño basado en tarjetas (Cards UI) sin dependencias de frameworks externos pesados.
* **Inter Font:** Tipografía moderna y altamente legible optimizada para interfaces de usuario.

## Requisitos del Sistema

Para ejecutar este proyecto en un entorno local, es necesario contar con:
* Java Development Kit (JDK) 17 o superior.
* Apache Maven.
* Servidor PostgreSQL instalado y en ejecución.

## Instalación y Configuración

1. **Clonar el repositorio:**
   ```bash
   git clone [https://github.com/tu-usuario/clinica-auno.git](https://github.com/tu-usuario/clinica-auno.git)
   cd clinica-auno
