package com.uxdesign.ccp_frontend

import org.junit.Assert.*
import org.junit.Test

class RespuestaPedidoTest {

    @Test
    fun crearRespuestaPedidoCorrectamente() {
        val pedido1 = Pedido(
            idCliente = "cliente001",
            fechaEntrega = "2025-05-22",
            estadoPedido = "Pendiente",
            valorTotal = 150.0,
            idVendedor = "vendedor001",
            comentarios = "Entrega urgente",
            idMoneda = 1
        )

        val pedido2 = Pedido(
            idCliente = "cliente002",
            fechaEntrega = "2025-05-23",
            estadoPedido = "Completado",
            valorTotal = 200.0,
            idVendedor = "vendedor002",
            comentarios = "Sin observaciones",
            idMoneda = 1
        )

        val respuesta = RespuestaPedido(
            pedidos = listOf(pedido1, pedido2),
            resultado = 1,
            mensaje = "Pedidos obtenidos",
            status = 200
        )

        assertEquals(1, respuesta.resultado)
        assertEquals("Pedidos obtenidos", respuesta.mensaje)
        assertEquals(200, respuesta.status)
        assertEquals(2, respuesta.pedidos.size)

        val p1 = respuesta.pedidos[0]
        assertEquals("cliente001", p1.idCliente)
        assertEquals("Pendiente", p1.estadoPedido)
        assertEquals(150.0, p1.valorTotal, 0.0)

        val p2 = respuesta.pedidos[1]
        assertEquals("cliente002", p2.idCliente)
        assertEquals("Completado", p2.estadoPedido)
        assertEquals(200.0, p2.valorTotal, 0.0)
    }

    @Test
    fun respuestasConMismosDatosSonIguales() {
        val pedidos = listOf(
            Pedido("cliente001", "2025-05-22", "Pendiente", 100.0, "vend001", "Obs1", 1),
            Pedido("cliente002", "2025-05-23", "En proceso", 200.0, "vend002", "Obs2", 1)
        )

        val r1 = RespuestaPedido(pedidos, 1, "OK", 200)
        val r2 = RespuestaPedido(pedidos, 1, "OK", 200)

        assertEquals(r1, r2)
    }

    @Test
    fun copiaModificaMensaje() {
        val original = RespuestaPedido(emptyList(), 0, "Mensaje original", 400)
        val copia = original.copy(mensaje = "Mensaje modificado")

        assertEquals("Mensaje modificado", copia.mensaje)
        assertEquals(original.resultado, copia.resultado)
        assertEquals(original.status, copia.status)
        assertEquals(original.pedidos, copia.pedidos)
    }

    @Test
    fun toStringIncluyeCamposEsperados() {
        val respuesta = RespuestaPedido(
            pedidos = listOf(
                Pedido("cliX", "2025-05-25", "Nuevo", 99.99, null, "Ninguno", 2)
            ),
            resultado = 1,
            mensaje = "Exitoso",
            status = 200
        )

        val str = respuesta.toString()
        assertTrue(str.contains("resultado=1"))
        assertTrue(str.contains("mensaje=Exitoso"))
        assertTrue(str.contains("status=200"))
        assertTrue(str.contains("pedidos=[Pedido"))
    }
}
