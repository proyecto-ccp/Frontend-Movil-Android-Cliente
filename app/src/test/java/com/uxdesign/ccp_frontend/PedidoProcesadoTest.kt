package com.uxdesign.ccp_frontend

import org.junit.Assert.*
import org.junit.Test

class PedidoProcesadoTest {

    @Test
    fun crearPedidoProcesadoCorrectamente() {
        val pedido = PedidoProcesado(
            id = "p001",
            idCliente = "c123",
            fechaRealizado = "2025-05-20",
            fechaEntrega = "2025-05-25",
            estadoPedido = "Procesado",
            valorTotal = 299.99,
            idVendedor = "v456",
            comentarios = "Entregar por la mañana",
            idMoneda = 1
        )

        assertEquals("p001", pedido.id)
        assertEquals("c123", pedido.idCliente)
        assertEquals("2025-05-20", pedido.fechaRealizado)
        assertEquals("2025-05-25", pedido.fechaEntrega)
        assertEquals("Procesado", pedido.estadoPedido)
        assertEquals(299.99, pedido.valorTotal, 0.0)
        assertEquals("v456", pedido.idVendedor)
        assertEquals("Entregar por la mañana", pedido.comentarios)
        assertEquals(1, pedido.idMoneda)
    }

    @Test
    fun pedidosProcesadosIguales() {
        val p1 = PedidoProcesado(
            "p002", "c789", "2025-05-21", "2025-05-28", "Entregado", 500.0, "v123", "Todo bien", 2
        )
        val p2 = PedidoProcesado(
            "p002", "c789", "2025-05-21", "2025-05-28", "Entregado", 500.0, "v123", "Todo bien", 2
        )

        assertEquals(p1, p2)
    }

    @Test
    fun copiaCambiaEstadoYComentarios() {
        val original = PedidoProcesado(
            "p003", "c456", "2025-05-22", "2025-05-30", "Pendiente", 100.0, "v789", "Ninguno", 1
        )
        val modificado = original.copy(
            estadoPedido = "Cancelado",
            comentarios = "Cliente canceló el pedido"
        )

        assertEquals("Cancelado", modificado.estadoPedido)
        assertEquals("Cliente canceló el pedido", modificado.comentarios)
        assertEquals(original.id, modificado.id)
        assertEquals(original.valorTotal, modificado.valorTotal, 0.0)
    }

    @Test
    fun toStringIncluyeDatos() {
        val pedido = PedidoProcesado(
            "p004", "c321", "2025-05-23", "2025-05-29", "En camino", 750.0, "v654", "Entrega programada", 3
        )

        val str = pedido.toString()
        assertTrue(str.contains("p004"))
        assertTrue(str.contains("En camino"))
        assertTrue(str.contains("750.0"))
        assertTrue(str.contains("Entrega programada"))
    }
}
