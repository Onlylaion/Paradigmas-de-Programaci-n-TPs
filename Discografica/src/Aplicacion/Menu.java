package Aplicacion;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import Dominio.Artista;
import Dominio.Banda;
import Dominio.Cancion;
import Dominio.Contrato;
import Dominio.Recital;
import Dominio.Rol;
import Servicio.InterfazUsuarioConsola;

public class Menu {
	private InterfazUsuarioConsola interfazUsuario;
	private Recital recital;
	private Contrato contrato;
	private Scanner scanner;
	//private List<Artista> artistasCandidatos;

	public Menu() {
		this.scanner = new Scanner(System.in);
		//this.artistasCandidatos = new ArrayList<>();
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
				entrenarArtista();
				break;
			case 6:
				recital.listarArtistasContratados();
				break;
			case 7:
				recital.listarCancionesConEstado();
				break;
			case 8:
				desasignarArtista();
				break;
			case 9:
				verInformacionRecital();
				break;
			case 10:
				System.out.println("\nSaliendo del sistema. Hasta luego!");
				salir = true;
				break;
			default:
				System.out.println("Opcion invalida. Intenta nuevamente.");
			}
		}

		scanner.close();
	}

	private void contratarTodasCanciones() {

		double costo = contrato.contratoTodasCanciones();

		System.out.println("Contratacion finalizada!");
		System.out.println("Costo total: $" + String.format("%.2f", costo));

		contrato.listarAsignaciones();
	}

	private void entrenarArtista() {
		System.out.println("\n=== ENTRENAR ARTISTA ===\n");

		System.out.print("Nombre del artista a entrenar: ");
		String nombre = scanner.nextLine();

		System.out.println("\nRoles disponibles:");
		Rol[] roles = Rol.values();
		for (int i = 0; i < roles.length; i++) {
			System.out.println((i + 1) + ". " + roles[i]);
		}

		System.out.print("\nSelecciona un rol (numero): ");
		int seleccion = scanner.nextInt();
		scanner.nextLine();

		if (seleccion < 1 || seleccion > roles.length) {
			System.out.println("Rol invalido.");
			return;
		}

		Rol rolSeleccionado = roles[seleccion - 1];

		try {
			recital.entrenarArtista(nombre, rolSeleccionado);
			System.out.println("\nArtista " + nombre + " entrenado en " + rolSeleccionado + "!");
		} catch (Exception e) {
			System.out.println("\nError: " + e.getMessage());
		}
	}

	private void desasignarArtista() {
		System.out.println("\n=== DESASIGNAR ARTISTA ===\n");

		if (recital.getArtistasContratados().isEmpty()) {
			System.out.println("No hay artistas contratados.");
			return;
		}

		int indice = 1;
		Map<Integer, Artista> artistas = new HashMap<>();

		for (Artista artista : recital.getArtistasContratados()) {
			artistas.put(indice, artista);
			System.out.println(indice + ". " + artista.getNombre());
			indice++;
		}

		System.out.print("\nSelecciona un artista (numero): ");
		int seleccion = scanner.nextInt();
		scanner.nextLine();

		Artista seleccionado = artistas.get(seleccion);

		if (seleccionado == null) {
			System.out.println("Artista no encontrado.");
			return;
		}

		try {
			contrato.desasignarContrato(seleccionado);
			System.out.println("\nArtista " + seleccionado.getNombre() + " desasignado exitosamente.");
		} catch (Exception e) {
			System.out.println("\nError: " + e.getMessage());
		}
	}

	private void verInformacionRecital() {
		System.out.println("\n=== INFORMACION DEL RECITAL ===\n");
		System.out.println(recital);
		System.out.println("\nArtistas base: " + recital.getArtistasBase().size());
		System.out.println("Artistas contratados: " + recital.getArtistasContratados().size());
		System.out.println("Total de canciones: " + recital.getListaCanciones().size());
	}

	public static void main(String[] args) {
		Menu menu = new Menu();
		menu.mostrarMenu();
	}
} 
