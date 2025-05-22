package com.uxdesign.ccp_frontend

import org.junit.Assert.*
import org.junit.Test

class LoginRequestTest {

    @Test
    fun crearLoginRequestCorrectamente() {
        val loginRequest = LoginRequest(
            username = "usuario123",
            contrasena = "password123",
            aplicacion = 5
        )

        assertEquals("usuario123", loginRequest.username)
        assertEquals("password123", loginRequest.contrasena)
        assertEquals(5, loginRequest.aplicacion)
    }

    @Test
    fun loginRequestsConMismosValoresSonIguales() {
        val lr1 = LoginRequest("user1", "pass1", 1)
        val lr2 = LoginRequest("user1", "pass1", 1)

        assertEquals(lr1, lr2)
    }

    @Test
    fun copyModificaUsername() {
        val original = LoginRequest("user2", "pass2", 2)
        val copia = original.copy(username = "userModificado")

        assertEquals("userModificado", copia.username)
        assertEquals(original.contrasena, copia.contrasena)
        assertEquals(original.aplicacion, copia.aplicacion)
    }

    @Test
    fun toStringContieneValoresEsperados() {
        val loginRequest = LoginRequest("usuarioX", "claveX", 3)
        val str = loginRequest.toString()

        assertTrue(str.contains("username=usuarioX"))
        assertTrue(str.contains("contrasena=claveX"))
        assertTrue(str.contains("aplicacion=3"))
    }
}
