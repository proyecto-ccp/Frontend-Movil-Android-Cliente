package com.uxdesign.ccp_frontend

import org.junit.Assert.*
import org.junit.Test

class RespuestaTiposDocsTest {

    @Test
    fun crearRespuestaTiposDocsCorrectamente() {
        val tipo1 = TipoDocumento(
            id = 1,
            nombre = "DNI",
            codigo = "DNI"
        )

        val tipo2 = TipoDocumento(
            id = 2,
            nombre = "Pasaporte",
            codigo = "PASS"
        )

        val respuesta = RespuestaTiposDocs(
            resultado = 1,
            mensaje = "Tipos de documentos cargados",
            status = 200,
            tiposDocumentos = listOf(tipo1, tipo2)
        )

        assertEquals(1, respuesta.resultado)
        assertEquals("Tipos de documentos cargados", respuesta.mensaje)
        assertEquals(200, respuesta.status)
        assertEquals(2, respuesta.tiposDocumentos.size)

        val t1 = respuesta.tiposDocumentos[0]
        assertEquals("DNI", t1.nombre)
        assertEquals("DNI", t1.codigo)

        val t2 = respuesta.tiposDocumentos[1]
        assertEquals("Pasaporte", t2.nombre)
        assertEquals("PASS", t2.codigo)
    }

    @Test
    fun respuestasIgualesConMismosDatos() {
        val docs = listOf(
            TipoDocumento(1, "DNI", "DNI"),
            TipoDocumento(2, "CE", "CE")
        )

        val r1 = RespuestaTiposDocs(1, "OK", 200, docs)
        val r2 = RespuestaTiposDocs(1, "OK", 200, docs)

        assertEquals(r1, r2)
    }

    @Test
    fun copiaModificaMensaje() {
        val original = RespuestaTiposDocs(
            resultado = 0,
            mensaje = "Error",
            status = 500,
            tiposDocumentos = emptyList()
        )

        val copia = original.copy(mensaje = "Reintentar")

        assertEquals("Reintentar", copia.mensaje)
        assertEquals(original.resultado, copia.resultado)
        assertEquals(original.status, copia.status)
        assertEquals(original.tiposDocumentos, copia.tiposDocumentos)
    }

    @Test
    fun toStringIncluyeCampos() {
        val respuesta = RespuestaTiposDocs(
            resultado = 1,
            mensaje = "Datos cargados",
            status = 200,
            tiposDocumentos = listOf(TipoDocumento(1, "RUC", "RUC"))
        )

        val str = respuesta.toString()

        assertTrue(str.contains("resultado=1"))
        assertTrue(str.contains("mensaje=Datos cargados"))
        assertTrue(str.contains("status=200"))
        assertTrue(str.contains("tiposDocumentos=[TipoDocumento"))
    }
}
