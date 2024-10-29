package uniandes.dpoo.hamburguesas.tests;

import uniandes.dpoo.hamburguesas.mundo.ProductoAjustado;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.mundo.Combo;
import uniandes.dpoo.hamburguesas.mundo.Ingrediente;
import uniandes.dpoo.hamburguesas.mundo.Pedido;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

import java.io.File;
import java.io.FileNotFoundException;

public class ProductoAjustadoTest {

    private ProductoAjustado productoAjustado;
    private ProductoMenu productoBase;
    private Ingrediente queso;
    private Ingrediente tocineta;
    private Ingrediente lechuga;

    @BeforeEach
    void setUp() {
        // Configura el producto base
        productoBase = new ProductoMenu("Hamburguesa Simple", 15000);

        // Crea el producto ajustado basado en el producto base
        productoAjustado = new ProductoAjustado(productoBase);

        // Crea ingredientes adicionales y eliminados
        queso = new Ingrediente("Queso", 1000);
        tocineta = new Ingrediente("Tocineta", 2000);
        lechuga = new Ingrediente("Lechuga", 500);

        // Aquí suponemos que productoAjustado ya está configurado con estos ingredientes
    }

    @Test
    void testGetNombre() {
        assertEquals("Hamburguesa Simple", productoAjustado.getNombre(), "El nombre del producto ajustado no es el esperado.");
    }

    @Test
    void testGetPrecio() {
        // Calcula el precio esperado sumando el precio base y los costos de los ingredientes agregados
        int precioEsperado = productoBase.getPrecio() + queso.getCostoAdicional() + tocineta.getCostoAdicional();

        // Verifica el precio esperado
        assertEquals(precioEsperado, productoAjustado.getPrecio(), "El precio del producto ajustado no es el esperado.");
    }

    @Test
    void testGenerarTextoFactura() {
        // Construye el texto esperado manualmente
        StringBuilder textoEsperado = new StringBuilder();
        textoEsperado.append("Hamburguesa Simple\n");
        textoEsperado.append("    +Queso                1000\n");
        textoEsperado.append("    +Tocineta             2000\n");
        textoEsperado.append("    -Lechuga\n");
        textoEsperado.append("            " + productoAjustado.getPrecio() + "\n");

        // Verifica el texto generado por la factura
        assertEquals(textoEsperado.toString(), productoAjustado.generarTextoFactura(), "El texto de la factura no es el esperado.");
    }
}