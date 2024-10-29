package uniandes.dpoo.hamburguesas.tests;

import uniandes.dpoo.hamburguesas.mundo.Restaurante;
import uniandes.dpoo.hamburguesas.mundo.Pedido;
import uniandes.dpoo.hamburguesas.mundo.Combo;
import uniandes.dpoo.hamburguesas.mundo.Ingrediente;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import uniandes.dpoo.hamburguesas.excepciones.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Restaurantetest {

    private Restaurante restaurante;

    @BeforeEach
    void setUp( ) throws Exception{
        restaurante = new Restaurante();
    }

    @Test
    public void testIniciarPedido() throws YaHayUnPedidoEnCursoException {
        restaurante.iniciarPedido("Juan Pérez", "Calle 123");

        Pedido pedidoEnCurso = restaurante.getPedidoEnCurso();
        assertNotNull(pedidoEnCurso, "No se ha iniciado el pedido correctamente.");
        assertEquals("Juan Pérez", pedidoEnCurso.getNombreCliente(), "El nombre del cliente no coincide.");
    }

    @Test
    public void testIniciarPedidoConPedidoEnCurso() {
        assertThrows(YaHayUnPedidoEnCursoException.class, () -> {
            restaurante.iniciarPedido("Cliente1", "Dirección1"); 
            restaurante.iniciarPedido("Cliente2", "Dirección2"); 
        });
    }

    @Test
    public void testCerrarYGuardarPedidoSinPedidoEnCurso() {
        assertThrows(NoHayPedidoEnCursoException.class, () -> {
            restaurante.cerrarYGuardarPedido(); // Debe fallar porque no hay pedido en curso
        });
    }

    @Test
    public void testCerrarYGuardarPedido(@TempDir File tempDir) throws YaHayUnPedidoEnCursoException, NoHayPedidoEnCursoException, IOException {
        restaurante.iniciarPedido("Juan Pérez", "Calle 123");

        File facturaDir = new File(tempDir, "facturas");
        if (!facturaDir.exists()) {
            facturaDir.mkdir();
        }

        System.setProperty("user.dir", tempDir.getAbsolutePath());
        restaurante.cerrarYGuardarPedido();

        assertNull(restaurante.getPedidoEnCurso(), "El pedido en curso no se ha cerrado.");
    }

    @Test
    public void testGetIngredientes() {
        ArrayList<Ingrediente> ingredientes = restaurante.getIngredientes();
        assertNotNull(ingredientes, "La lista de ingredientes debería inicializarse vacía.");
        assertTrue(ingredientes.isEmpty(), "La lista de ingredientes debería estar vacía al inicio.");
    }

    @Test
    public void testGetMenuBase() {
        ArrayList<ProductoMenu> menuBase = restaurante.getMenuBase();
        assertNotNull(menuBase, "La lista de productos del menú debería inicializarse vacía.");
        assertTrue(menuBase.isEmpty(), "La lista de productos del menú debería estar vacía al inicio.");
    }

    @Test
    public void testGetMenuCombos() {
        ArrayList<Combo> menuCombos = restaurante.getMenuCombos();
        assertNotNull(menuCombos, "La lista de combos debería inicializarse vacía.");
        assertTrue(menuCombos.isEmpty(), "La lista de combos debería estar vacía al inicio.");
    }

    @Test
    public void testCargarInformacionRestaurante(@TempDir File tempDir) throws IOException, HamburguesaException {
        // Crea archivos temporales para ingredientes, menú y combos
        File archivoIngredientes = new File(tempDir, "ingredientes.txt");
        FileWriter writerIngredientes = new FileWriter(archivoIngredientes);
        writerIngredientes.write("Lechuga;500\nTomate;300\n");
        writerIngredientes.close();

        File archivoMenu = new File(tempDir, "menu.txt");
        FileWriter writerMenu = new FileWriter(archivoMenu);
        writerMenu.write("Hamburguesa;10000\n");
        writerMenu.close();

        File archivoCombos = new File(tempDir, "combos.txt");
        FileWriter writerCombos = new FileWriter(archivoCombos);
        writerCombos.write("Combo1;10%;Hamburguesa\n");
        writerCombos.close();

        restaurante.cargarInformacionRestaurante(archivoIngredientes, archivoMenu, archivoCombos);

        assertEquals(2, restaurante.getIngredientes().size(), "No se cargaron los ingredientes correctamente.");
        assertEquals(1, restaurante.getMenuBase().size(), "No se cargó el menú correctamente.");
        assertEquals(1, restaurante.getMenuCombos().size(), "No se cargaron los combos correctamente.");
    }

    @Test
    public void testCargarIngredientesConIngredienteRepetido(@TempDir File tempDir) throws IOException {
        File archivoIngredientes = new File(tempDir, "ingredientes.txt");
        FileWriter writer = new FileWriter(archivoIngredientes);
        writer.write("Lechuga;500\nLechuga;500\n");
        writer.close();

        assertThrows(IngredienteRepetidoException.class, () -> {
            restaurante.cargarInformacionRestaurante(archivoIngredientes, new File(""), new File(""));
        });
    }

    @Test
    public void testCargarMenuConProductoRepetido(@TempDir File tempDir) throws IOException {
        File archivoMenu = new File(tempDir, "menu.txt");
        FileWriter writer = new FileWriter(archivoMenu);
        writer.write("Hamburguesa;10000\nHamburguesa;10000\n");
        writer.close();

        assertThrows(ProductoRepetidoException.class, () -> {
            restaurante.cargarInformacionRestaurante(new File(""), archivoMenu, new File(""));
        });
    }

    @Test
    public void testCargarCombosConProductoFaltante(@TempDir File tempDir) throws IOException {
        File archivoCombos = new File(tempDir, "combos.txt");
        FileWriter writer = new FileWriter(archivoCombos);
        writer.write("Combo1;10%;HamburguesaInexistente\n");
        writer.close();

        assertThrows(ProductoFaltanteException.class, () -> {
            restaurante.cargarInformacionRestaurante(new File(""), new File(""), archivoCombos);
        });
    }
}
