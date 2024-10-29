package uniandes.dpoo.hamburguesas.tests;

import uniandes.dpoo.hamburguesas.mundo.Combo;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.mundo.Ingrediente;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

public class ComboTest {
	
	private Combo combo1;

	@BeforeEach
    void setUp( ) throws Exception{
    	

    	ArrayList<ProductoMenu> items = new ArrayList<>(); // Crea la lista de productos

    	items.add(new ProductoMenu("corral queso",16000));
    	items.add(new ProductoMenu("papas medianas", 5500));
    	items.add(new ProductoMenu("gaseosa", 5000));

    	
    	
    	combo1 = new Combo("combo corral queso",10,items);
    }

	@AfterEach
	void tearDown() throws Exception {
		
	}
    @Test
    //Verificamos si el nombre del combo es el correcto
    void testGetNombre( )
    {
        assertEquals( "combo corral queso", combo1.getNombre( ), "El nombre del combo no es el esperado." );
    }

    @Test
    //Verificamos si el precio del combo es el correcto

    void testGetPrecio( )
    {
        assertEquals( 23850, combo1.getPrecio( ), "El precio del combo no es el esperado." );
    }

    @Test
    void testGenerarTextoFactura( ) 
    {
    	 // Configuración de valores esperados
        String nombreCombo = "combo corral queso";
        double descuento = 0.10;
        int precioEsperado = 23850; // Este valor se calculó previamente en la prueba anterior
        String textoEsperado = "Combo " + nombreCombo + "\n" +
                               " Descuento: " + descuento + "\n" +
                               "            " + precioEsperado + "\n";

        // Ejecutar el método que genera la factura
        String facturaGenerada = combo1.generarTextoFactura();

        // Comprobación
        assertEquals(textoEsperado, facturaGenerada, "La factura generada no es la esperada.");
    }
}



