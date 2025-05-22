package com.uxdesign.ccp_frontend

import org.junit.Assert.*
import org.junit.Test

class RespuestaPedidoProcesadoTest {

    @Test
    fun crearRespuestaPedidoProcesadoCorrectamente() {
        val pedido1 = PedidoProcesado(
            id = "pedido001",
            idCliente = "cliente001",
            fechaRealizado = "2025-05-20",
            fechaEntrega = "2025-05-25",
            estadoPedido = "Procesado",
            valorTotal = 300.0,
            idVendedor = "vendedor001",
            comentarios = "Entregar rápido",
            idMoneda = 1
        )

        val pedido2 = PedidoProcesado(
            id = "pedido002",
            idCliente = "cliente002",
            fechaRealizado = "2025-05-21",
            fechaEntrega = "2025-05-26",
            estadoPedido = "Enviado",
            valorTotal = 500.5,
            idVendedor = "vendedor002",
            comentarios = "Sin observaciones",
            idMoneda = 1
        )

        val respuesta = RespuestaPedidoProcesado(
            pedidos = listOf(pedido1, pedido2),
            resultado = 1,
            mensaje = "Pedidos procesados correctamente",
            status = 200
        )

        assertEquals(1, respuesta.resultado)
        assertEquals("Pedidos procesados correctamente", respuesta.mensaje)
        assertEquals(200, respuesta.status)
        assertEquals(2, respuesta.pedidos.size)

        val p1 = respuesta.pedidos[0]
        assertEquals("pedido001", p1.id)
        assertEquals("cliente001", p1.idCliente)
        assertEquals("Procesado", p1.estadoPedido)
        assertEquals(300.0, p1.valorTotal, 0.0)

        val p2 = respuesta.pedidos[1]
        assertEquals("pedido002", p2.id)
        assertEquals("cliente002", p2.idCliente)
        assertEquals("Enviado", p2.estadoPedido)
        assertEquals(500.5, p2.valorTotal, 0.0)
    }

    @Test
    fun respuestasConMismosPedidosSonIguales() {
        val pedidos = listOf(
            PedidoProcesado("p003", "cliA", "2025-05-20", "2025-05-27", "Completado", 100.0, "vendA", "Listo", 1),
            PedidoProcesado("p004", "cliB", "2025-05-21", "2025-05-28", "Entregado", 200.0, "vendB", "Rápido", 1)
        )

        val r1 = RespuestaPedidoProcesado(pedidos, 1, "OK", 200)
        val r2 = RespuestaPedidoProcesado(pedidos, 1, "OK", 200)

        assertEquals(r1, r2)
    }

    @Test
    fun copiaModificaMensaje() {
        val original = RespuestaPedidoProcesado(
            pedidos = emptyList(),
            resultado = 0,
            mensaje = "Mensaje original",
            status = 500
        )

        val copia = original.copy(mensaje = "Mensaje modificado")

        assertEquals("Mensaje modificado", copia.mensaje)
        assertEquals(original.resultado, copia.resultado)
        assertEquals(original.status, copia.status)
        assertEquals(original.pedidos, copia.pedidos)
    }

    @Test
    fun toStringIncluyeCamposEsperados() {
        val respuesta = RespuestaPedidoProcesado(
            pedidos = listOf(
                PedidoProcesado("p005", "cliX", "2025-05-22", "2025-05-30", "Confirmado", 400.0, "vendX", "Nada", 1)
            ),
            resultado = 1,
            mensaje = "Consulta OK",
            status = 200
        )

        val str = respuesta.toString()
        assertTrue(str.contains("resultado=1"))
        assertTrue(str.contains("mensaje=Consulta OK"))
        assertTrue(str.contains("status=200"))
        assertTrue(str.contains("pedidos=[PedidoProcesado"))
    }
}
