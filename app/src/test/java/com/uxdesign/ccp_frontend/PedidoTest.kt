package com.uxdesign.ccp_frontend

import org.junit.Assert.*
import org.junit.Test

class PedidoTest {

    @Test
    fun crearPedidoCorrectamente() {
        val pedido = Pedido(
            idCliente = "cl001",
            fechaEntrega = "2025-06-01",
            estadoPedido = "Pendiente",
            valorTotal = 150.75,
            idVendedor = "v001",
            comentarios = "Entrega rápida, por favor",
            idMoneda = 1
        )

        assertEquals("cl001", pedido.idCliente)
        assertEquals("2025-06-01", pedido.fechaEntrega)
        assertEquals("Pendiente", pedido.estadoPedido)
        assertEquals(150.75, pedido.valorTotal, 0.0)
        assertEquals("v001", pedido.idVendedor)
        assertEquals("Entrega rápida, por favor", pedido.comentarios)
        assertEquals(1, pedido.idMoneda)
    }

    @Test
    fun crearPedidoConIdVendedorNull() {
        val pedido = Pedido(
            idCliente = "cl002",
            fechaEntrega = "2025-06-15",
            estadoPedido = "Confirmado",
            valorTotal = 200.0,
            idVendedor = null,
            comentarios = "Sin comentarios",
            idMoneda = 2
        )

        assertNull(pedido.idVendedor)
        assertEquals("cl002", pedido.idCliente)
    }

    @Test
    fun pedidosConMismosValoresSonIguales() {
        val p1 = Pedido(
            "cl003", "2025-07-01", "Entregado", 300.50,
            "v002", "Todo OK", 1
        )
        val p2 = Pedido(
            "cl003", "2025-07-01", "Entregado", 300.50,
            "v002", "Todo OK", 1
        )

        assertEquals(p1, p2)
    }

    @Test
    fun copyModificaEstadoPedido() {
        val original = Pedido(
            "cl004", "2025-08-01", "Pendiente", 120.0,
            "v003", "Sin prisa", 1
        )
        val copia = original.copy(estadoPedido = "Cancelado")

        assertEquals("Cancelado", copia.estadoPedido)
        assertEquals(original.idCliente, copia.idCliente)
        assertEquals(original.valorTotal, copia.valorTotal, 0.0)
    }

    @Test
    fun toStringContieneValoresEsperados() {
        val pedido = Pedido(
            "cl005", "2025-09-01", "Procesando", 450.0,
            "v004", "Urgente", 1
        )
        val str = pedido.toString()

        assertTrue(str.contains("idCliente=cl005"))
        assertTrue(str.contains("fechaEntrega=2025-09-01"))
        assertTrue(str.contains("estadoPedido=Procesando"))
        assertTrue(str.contains("valorTotal=450.0"))
        assertTrue(str.contains("idVendedor=v004"))
        assertTrue(str.contains("comentarios=Urgente"))
        assertTrue(str.contains("idMoneda=1"))
    }
}
