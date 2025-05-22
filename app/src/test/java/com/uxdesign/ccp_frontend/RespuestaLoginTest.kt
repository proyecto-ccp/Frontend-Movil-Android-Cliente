package com.uxdesign.ccp_frontend

import org.junit.Assert.*
import org.junit.Test

class RespuestaLoginTest {

    @Test
    fun crearRespuestaLoginCorrectamente() {
        val respuesta = RespuestaLogin(
            resultado = 1,
            mensaje = "Login exitoso",
            status = 200,
            token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9",
            menu = "menu_principal",
            idusuario = "user123"
        )

        assertEquals(1, respuesta.resultado)
        assertEquals("Login exitoso", respuesta.mensaje)
        assertEquals(200, respuesta.status)
        assertEquals("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9", respuesta.token)
        assertEquals("menu_principal", respuesta.menu)
        assertEquals("user123", respuesta.idusuario)
    }

    @Test
    fun respuestasConMismosValoresSonIguales() {
        val r1 = RespuestaLogin(
            0, "OK", 100, "token123", "menuA", "userA"
        )
        val r2 = RespuestaLogin(
            0, "OK", 100, "token123", "menuA", "userA"
        )

        assertEquals(r1, r2)
    }

    @Test
    fun copiaModificaMensajeYToken() {
        val original = RespuestaLogin(
            resultado = 1,
            mensaje = "Mensaje original",
            status = 200,
            token = "originalToken",
            menu = "menuA",
            idusuario = "user001"
        )

        val copia = original.copy(
            mensaje = "Nuevo mensaje",
            token = "nuevoToken"
        )

        assertEquals("Nuevo mensaje", copia.mensaje)
        assertEquals("nuevoToken", copia.token)
        assertEquals(original.status, copia.status)
        assertEquals(original.menu, copia.menu)
        assertEquals(original.idusuario, copia.idusuario)
    }

    @Test
    fun toStringContieneCamposEsperados() {
        val respuesta = RespuestaLogin(
            1,
            "Autenticación OK",
            200,
            "jwtTokenABC",
            "menu_usuario",
            "usuarioX"
        )

        val str = respuesta.toString()
        assertTrue(str.contains("resultado=1"))
        assertTrue(str.contains("mensaje=Autenticación OK"))
        assertTrue(str.contains("status=200"))
        assertTrue(str.contains("token=jwtTokenABC"))
        assertTrue(str.contains("menu=menu_usuario"))
        assertTrue(str.contains("idusuario=usuarioX"))
    }
}
