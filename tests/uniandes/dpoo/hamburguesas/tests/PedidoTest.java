package uniandes.dpoo.hamburguesas.tests;

import uniandes.dpoo.hamburguesas.mundo.Pedido;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.mundo.Combo;
import uniandes.dpoo.hamburguesas.mundo.Ingrediente;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

import java.io.File;
import java.io.FileNotFoundException;

public class PedidoTest {
	
	private Pedido pedido1;

    @BeforeEach
    void setUp() {
        pedido1 = new Pedido("Juan Pérez", "Calle 123");
    }

    @AfterEach
	void tearDown() throws Exception {
    }
    

    
    @Test
    void getIdPedido() {
        int idEsperado = 1;  
        assertEquals(idEsperado, pedido1.getIdPedido(), "El Id del pedido no es el esperado.");
    }

    @Test
    void getNombreCliente() {
        String nombreEsperado = "Juan Pérez";  
        assertEquals(nombreEsperado, pedido1.getNombreCliente(), "El nombre del cliente no es el esperado.");
    }

    @Test
    void testAgregarProducto() {
        ProductoMenu producto1 = new ProductoMenu("Hamburguesa", 12000);
        pedido1.agregarProducto(producto1);
        
        assertEquals(1, pedido1.getProductos().size(), "El producto no se agregó correctamente al pedido.");
    }

    @Test
    void testGetPrecioNetoPedido() {
        ProductoMenu producto1 = new ProductoMenu("Hamburguesa", 12000);
        ProductoMenu producto2 = new ProductoMenu("Papas Fritas", 5000);
        
        pedido1.agregarProducto(producto1);
        pedido1.agregarProducto(producto2);
        
        int precioNetoEsperado = 17000;
        assertEquals(precioNetoEsperado, pedido1.getPrecioNetoPedido(), "El precio neto del pedido no es el esperado.");
    }

    @Test
    void testGetPrecioIVAPedido() {
        ProductoMenu producto1 = new ProductoMenu("Hamburguesa", 12000);
        ProductoMenu producto2 = new ProductoMenu("Papas Fritas", 5000);
        
        pedido1.agregarProducto(producto1);
        pedido1.agregarProducto(producto2);
        
        int precioIVAEsperado = (int) (17000 * 0.19);
        assertEquals(precioIVAEsperado, pedido1.getPrecioIVAPedido(), "El IVA del pedido no es el esperado.");
    }

    @Test
    void testGetPrecioTotalPedido() {
        ProductoMenu producto1 = new ProductoMenu("Hamburguesa", 12000);
        ProductoMenu producto2 = new ProductoMenu("Papas Fritas", 5000);
        
        pedido1.agregarProducto(producto1);
        pedido1.agregarProducto(producto2);
        
        int precioTotalEsperado = 17000 + (int) (17000 * 0.19);
        assertEquals(precioTotalEsperado, pedido1.getPrecioTotalPedido(), "El precio total del pedido no es el esperado.");
    }

    @Test
    void testGenerarTextoFactura() {
        ProductoMenu producto1 = new ProductoMenu("Hamburguesa", 12000);
        ProductoMenu producto2 = new ProductoMenu("Papas Fritas", 5000);
        
        pedido1.agregarProducto(producto1);
        pedido1.agregarProducto(producto2);
        
        String facturaEsperada = "Cliente: Juan Pérez\n" +
                                 "Dirección: Calle 123\n" +
                                 "----------------\n" +
                                 producto1.generarTextoFactura() +
                                 producto2.generarTextoFactura() +
                                 "----------------\n" +
                                 "Precio Neto:  17000\n" +
                                 "IVA:          " + (int) (17000 * 0.19) + "\n" +
                                 "Precio Total: " + (17000 + (int) (17000 * 0.19)) + "\n";
        
        assertEquals(facturaEsperada, pedido1.generarTextoFactura(), "El texto de la factura no es el esperado.");
    }

    @Test
    void testGuardarFactura() {
        ProductoMenu producto1 = new ProductoMenu("Hamburguesa", 12000);
        ProductoMenu producto2 = new ProductoMenu("Papas Fritas", 5000);
        
        pedido1.agregarProducto(producto1);
        pedido1.agregarProducto(producto2);

        File archivoFactura = new File("facturaTest.txt");
        try {
            pedido1.guardarFactura(archivoFactura);
            assertTrue(archivoFactura.exists(), "El archivo de factura no se creó correctamente.");
        } catch (FileNotFoundException e) {
            fail("No se pudo guardar la factura: " + e.getMessage());
        } finally {
            archivoFactura.delete(); // Limpia el archivo después de la prueba
        }
    }
	


}
