package Aplicacion;
import Servicio.InterfazUsuarioConsola;


public class Menu {
	private InterfazUsuarioConsola interfazUsuario;

	public Menu() {
		this.interfazUsuario = new InterfazUsuarioConsola();
		inicializarDatos();
	}

	private void inicializarDatos() {
		interfazUsuario.inicializar();
	}

	public void mostrarMenu() {
		boolean salir = false;
        
		while (!salir) {
			System.out.println("\n========================================");
			System.out.println("    SISTEMA DE CONTRATACION DE ARTISTAS");
			System.out.println("========================================\n");

			System.out.println("1. Ver roles faltantes por cancion");
			System.out.println("2. Ver roles faltantes TOTALES");
			System.out.println("3. Contratar artistas para UNA cancion");
			System.out.println("4. Contratar artistas para TODAS las canciones");
			System.out.println("5. Entrenar artista");
			System.out.println("6. Listar artistas contratados");
			System.out.println("7. Listar canciones con estado");
			System.out.println("8. Desasignar artista");
			System.out.println("9. Ver informacion del recital");
			System.out.println("10. Salir\n");

			int opcion = interfazUsuario.obtenerComando();

			switch (opcion) {
			case 1:
				System.out.println("\n=== ROLES FALTANTES POR CANCION ===\n");
				interfazUsuario.rolesFaltantesCancion();
				break;
			case 2:
				System.out.println("\n=== ROLES FALTANTES TOTAL ===\n");
				interfazUsuario.rolesFaltantesTotal();
				break;
			case 3:
				System.out.println("\n=== CONTRATAR PARA UNA CANCION ===\n");
				interfazUsuario.contratarUnaCancion();
				break;
			case 4:
				System.out.println("\n=== CONTRATAR TODAS LAS CANCIONES ===\n");
				interfazUsuario.contratarTodasCanciones();
				break;
			case 5:
				System.out.println("\n=== ENTRENAR A UN ARTISTA ===\n");
				interfazUsuario.entrenarArtista();
				break;
			case 6:
				interfazUsuario.listarArtistasContratados();
				break;
			case 7:
				interfazUsuario.listarCancionesConEstado();
				break;
			case 8:
				System.out.println("\n=== DESASIGNAR ARTISTA ===\n");
				interfazUsuario.desasignarArtista();
				break;
			case 9:
				System.out.println("\n=== INFORMACION DEL RECITAL ===\n");
				interfazUsuario.verInformacionRecital();
				break;
			case 10:
				System.out.println("\nSaliendo del sistema. Hasta luego!");
				salir = true;
				break;
			default:
				System.out.println("Opcion invalida. Intenta nuevamente.");
			}
		}
	}

	public static void main(String[] args) {
		Menu menu = new Menu();
		menu.mostrarMenu();
	}
	
} 


