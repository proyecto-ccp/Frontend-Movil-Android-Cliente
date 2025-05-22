package com.uxdesign.ccp_frontend

import org.junit.Assert.*
import org.junit.Test

class ProductoTest {

    @Test
    fun crearProductoCorrectamente() {
        val producto = Producto(
            id = 101,
            nombre = "Camiseta",
            descripcion = "Camiseta 100% algodón",
            idProveedor = "prov123",
            precioUnitario = 19.99,
            cantidad = 50,
            idMedida = 1,
            idCategoria = 2,
            idMarca = 3,
            idColor = 4,
            idModelo = 5,
            idMaterial = 6,
            urlFoto1 = "http://foto1.com/camiseta.jpg",
            urlFoto2 = "http://foto2.com/camiseta.jpg"
        )

        assertEquals(101, producto.id)
        assertEquals("Camiseta", producto.nombre)
        assertEquals("Camiseta 100% algodón", producto.descripcion)
        assertEquals("prov123", producto.idProveedor)
        assertEquals(19.99, producto.precioUnitario, 0.0)
        assertEquals(50, producto.cantidad)
        assertEquals(1, producto.idMedida)
        assertEquals(2, producto.idCategoria)
        assertEquals(3, producto.idMarca)
        assertEquals(4, producto.idColor)
        assertEquals(5, producto.idModelo)
        assertEquals(6, producto.idMaterial)
        assertEquals("http://foto1.com/camiseta.jpg", producto.urlFoto1)
        assertEquals("http://foto2.com/camiseta.jpg", producto.urlFoto2)
    }

    @Test
    fun productosConMismosValoresSonIguales() {
        val p1 = Producto(
            102, "Pantalón", "Pantalón jeans",
            "prov456", 39.99, 20, 2, 3, 4, 5, 6, 7,
            "http://foto1.com/pantalon.jpg",
            "http://foto2.com/pantalon.jpg"
        )
        val p2 = Producto(
            102, "Pantalón", "Pantalón jeans",
            "prov456", 39.99, 20, 2, 3, 4, 5, 6, 7,
            "http://foto1.com/pantalon.jpg",
            "http://foto2.com/pantalon.jpg"
        )

        assertEquals(p1, p2)
    }

    @Test
    fun copyModificaPrecioUnitario() {
        val original = Producto(
            103, "Zapatos", "Zapatos deportivos",
            "prov789", 59.99, 15, 3, 4, 5, 6, 7, 8,
            "http://foto1.com/zapatos.jpg",
            "http://foto2.com/zapatos.jpg"
        )
        val copia = original.copy(precioUnitario = 49.99)

        assertEquals(49.99, copia.precioUnitario, 0.0)
        assertEquals(original.id, copia.id)
        assertEquals(original.nombre, copia.nombre)
    }

    @Test
    fun toStringContieneValoresEsperados() {
        val producto = Producto(
            104, "Gorra", "Gorra deportiva",
            "prov000", 9.99, 100, 4, 5, 6, 7, 8, 9,
            "http://foto1.com/gorra.jpg",
            "http://foto2.com/gorra.jpg"
        )
        val str = producto.toString()

        assertTrue(str.contains("id=104"))
        assertTrue(str.contains("nombre=Gorra"))
        assertTrue(str.contains("descripcion=Gorra deportiva"))
        assertTrue(str.contains("idProveedor=prov000"))
        assertTrue(str.contains("precioUnitario=9.99"))
        assertTrue(str.contains("cantidad=100"))
        assertTrue(str.contains("idMedida=4"))
        assertTrue(str.contains("idCategoria=5"))
        assertTrue(str.contains("idMarca=6"))
        assertTrue(str.contains("idColor=7"))
        assertTrue(str.contains("idModelo=8"))
        assertTrue(str.contains("idMaterial=9"))
        assertTrue(str.contains("urlFoto1=http://foto1.com/gorra.jpg"))
        assertTrue(str.contains("urlFoto2=http://foto2.com/gorra.jpg"))
    }
}
