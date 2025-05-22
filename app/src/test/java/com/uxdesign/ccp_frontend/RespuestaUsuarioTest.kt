package com.uxdesign.ccp_frontend

import org.junit.Assert.*
import org.junit.Test

class RespuestaUsuarioTest {

    @Test
    fun crearRespuestaUsuarioCorrectamente() {
        val respuesta = RespuestaUsuario(
            resultado = 1,
            mensaje = "Usuario autenticado",
            status = 200,
            id = "u123",
            username = "jdoe",
            nombres = "John",
            apellidos = "Doe",
            correo = "john.doe@example.com",
            telefono = "987654321",
            idRol = "r01",
            rol = "Administrador",
            idPerfil = "p01",
            fechaRegistro = "2025-01-01T10:00:00Z",
            fechaActualizacion = "2025-05-20T14:00:00Z"
        )

        assertEquals(1, respuesta.resultado)
        assertEquals("Usuario autenticado", respuesta.mensaje)
        assertEquals(200, respuesta.status)
        assertEquals("u123", respuesta.id)
        assertEquals("jdoe", respuesta.username)
        assertEquals("John", respuesta.nombres)
        assertEquals("Doe", respuesta.apellidos)
        assertEquals("john.doe@example.com", respuesta.correo)
        assertEquals("987654321", respuesta.telefono)
        assertEquals("r01", respuesta.idRol)
        assertEquals("Administrador", respuesta.rol)
        assertEquals("p01", respuesta.idPerfil)
        assertEquals("2025-01-01T10:00:00Z", respuesta.fechaRegistro)
        assertEquals("2025-05-20T14:00:00Z", respuesta.fechaActualizacion)
    }

    @Test
    fun respuestasUsuarioConMismosDatosSonIguales() {
        val r1 = RespuestaUsuario(
            resultado = 1,
            mensaje = "OK",
            status = 200,
            id = "u123",
            username = "admin",
            nombres = "Ana",
            apellidos = "Pérez",
            correo = "ana.perez@example.com",
            telefono = "999999999",
            idRol = "r02",
            rol = "Supervisor",
            idPerfil = "p02",
            fechaRegistro = "2025-01-02T09:00:00Z",
            fechaActualizacion = "2025-05-22T11:30:00Z"
        )

        val r2 = r1.copy()

        assertEquals(r1, r2)
    }

    @Test
    fun copiaModificaCorreoYTelefono() {
        val original = RespuestaUsuario(
            resultado = 1,
            mensaje = "OK",
            status = 200,
            id = "u001",
            username = "testuser",
            nombres = "Test",
            apellidos = "User",
            correo = "test@original.com",
            telefono = "000000000",
            idRol = "role1",
            rol = "User",
            idPerfil = "perfil1",
            fechaRegistro = "2025-01-10T08:00:00Z",
            fechaActualizacion = "2025-05-01T12:00:00Z"
        )

        val copia = original.copy(
            correo = "test@nuevo.com",
            telefono = "111111111"
        )

        assertEquals("test@nuevo.com", copia.correo)
        assertEquals("111111111", copia.telefono)
        assertEquals(original.id, copia.id)
        assertEquals(original.username, copia.username)
    }

    @Test
    fun toStringIncluyeDatosClave() {
        val usuario = RespuestaUsuario(
            resultado = 1,
            mensaje = "Correcto",
            status = 200,
            id = "abc123",
            username = "usr",
            nombres = "Nombre",
            apellidos = "Apellido",
            correo = "mail@mail.com",
            telefono = "123456789",
            idRol = "R001",
            rol = "Operador",
            idPerfil = "P001",
            fechaRegistro = "2025-01-01",
            fechaActualizacion = "2025-05-01"
        )

        val str = usuario.toString()
        assertTrue(str.contains("resultado=1"))
        assertTrue(str.contains("username=usr"))
        assertTrue(str.contains("correo=mail@mail.com"))
        assertTrue(str.contains("rol=Operador"))
    }
}
