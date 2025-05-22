package com.uxdesign.ccp_frontend

import org.junit.Assert.*
import org.junit.Test

class RespuestaProductoTest {

    @Test
    fun crearRespuestaProductoCorrectamente() {
        val producto1 = Producto(
            id = 1,
            nombre = "Laptop",
            descripcion = "Ultrabook 14 pulgadas",
            idProveedor = "prov001",
            precioUnitario = 899.99,
            cantidad = 10,
            idMedida = 1,
            idCategoria = 2,
            idMarca = 3,
            idColor = 4,
            idModelo = 5,
            idMaterial = 6,
            urlFoto1 = "http://example.com/foto1.jpg",
            urlFoto2 = "http://example.com/foto2.jpg"
        )

        val producto2 = Producto(
            id = 2,
            nombre = "Mouse",
            descripcion = "Mouse inalámbrico",
            idProveedor = "prov002",
            precioUnitario = 29.99,
            cantidad = 50,
            idMedida = 1,
            idCategoria = 2,
            idMarca = 3,
            idColor = 4,
            idModelo = 5,
            idMaterial = 6,
            urlFoto1 = "http://example.com/mouse1.jpg",
            urlFoto2 = "http://example.com/mouse2.jpg"
        )

        val respuesta = RespuestaProducto(
            resultado = 1,
            mensaje = "Productos obtenidos correctamente",
            status = 200,
            productos = listOf(producto1, producto2)
        )

        assertEquals(1, respuesta.resultado)
        assertEquals("Productos obtenidos correctamente", respuesta.mensaje)
        assertEquals(200, respuesta.status)
        assertEquals(2, respuesta.productos.size)

        val p1 = respuesta.productos[0]
        assertEquals("Laptop", p1.nombre)
        assertEquals(899.99, p1.precioUnitario, 0.0)

        val p2 = respuesta.productos[1]
        assertEquals("Mouse", p2.nombre)
        assertEquals(29.99, p2.precioUnitario, 0.0)
    }

    @Test
    fun respuestasConMismosProductosSonIguales() {
        val productos = listOf(
            Producto(10, "Teclado", "Mecánico", "provA", 49.99, 15, 1, 1, 1, 1, 1, 1, "url1", "url2"),
            Producto(11, "Monitor", "24 pulgadas", "provB", 149.99, 5, 1, 1, 1, 1, 1, 1, "url3", "url4")
        )

        val r1 = RespuestaProducto(1, "OK", 200, productos)
        val r2 = RespuestaProducto(1, "OK", 200, productos)

        assertEquals(r1, r2)
    }

    @Test
    fun copiaModificaMensaje() {
        val original = RespuestaProducto(
            resultado = 0,
            mensaje = "Error al obtener productos",
            status = 500,
            productos = emptyList()
        )

        val copia = original.copy(mensaje = "Intento de nuevo")

        assertEquals("Intento de nuevo", copia.mensaje)
        assertEquals(original.resultado, copia.resultado)
        assertEquals(original.status, copia.status)
        assertEquals(original.productos, copia.productos)
    }

    @Test
    fun toStringIncluyeDatosEsperados() {
        val respuesta = RespuestaProducto(
            resultado = 1,
            mensaje = "Cargado",
            status = 200,
            productos = listOf(
                Producto(1, "Producto1", "Desc", "prov", 10.0, 5, 1, 1, 1, 1, 1, 1, "foto1", "foto2")
            )
        )

        val str = respuesta.toString()
        assertTrue(str.contains("resultado=1"))
        assertTrue(str.contains("mensaje=Cargado"))
        assertTrue(str.contains("status=200"))
        assertTrue(str.contains("productos=[Producto"))
    }
}
