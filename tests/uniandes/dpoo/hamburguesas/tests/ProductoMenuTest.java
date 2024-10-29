package uniandes.dpoo.hamburguesas.tests;

import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.mundo.Ingrediente;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

public class ProductoMenuTest {

    private ProductoMenu producto;

    @BeforeEach
    public void setUp() {
        // Inicializa el producto con un nombre y un precio base
        producto = new ProductoMenu("Hamburguesa Clásica", 12000);
    }

    @Test
    public void testGetNombre() {
        // Verifica que el nombre del producto sea el esperado
        assertEquals("Hamburguesa Clásica", producto.getNombre(), "El nombre del producto no es el esperado.");
    }

    @Test
    public void testGetPrecio() {
        // Verifica que el precio del producto sea el esperado
        assertEquals(12000, producto.getPrecio(), "El precio del producto no es el esperado.");
    }

    @Test
    public void testGenerarTextoFactura() {
        // Genera el texto de la factura y verifica que sea el esperado
        String textoFactura = producto.generarTextoFactura();
        
        String textoEsperado = "Hamburguesa Clásica\n"
                             + "            12000\n";
        
        assertEquals(textoEsperado, textoFactura, "El texto de la factura no es el esperado.");
    }
}
