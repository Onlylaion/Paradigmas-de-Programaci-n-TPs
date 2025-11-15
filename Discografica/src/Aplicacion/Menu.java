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

public class Menu {
	private Recital recital;
	private Contrato contrato;
	private Scanner scanner;
	private List<Artista> artistasCandidatos;

	public Menu() {
		this.scanner = new Scanner(System.in);
		this.artistasCandidatos = new ArrayList<>();
		inicializarDatos();
	}

	private void inicializarDatos() {
		// Crear artistas base (Queen)
		Set<Artista> artistasBase = new HashSet<>();

		List<Rol> rolesBrian = new ArrayList<>();
		rolesBrian.add(Rol.Guitarrista);
		rolesBrian.add(Rol.VozPrincipal);
		List<Banda> bandasBrian = new ArrayList<>();
		bandasBrian.add(new Banda("Queen", new ArrayList<>()));
		Artista brian = new Artista("Brian May", rolesBrian, bandasBrian, 0, 100);

		List<Rol> rolesRoger = new ArrayList<>();
		rolesRoger.add(Rol.Baterista);
		List<Banda> bandasRoger = new ArrayList<>();
		bandasRoger.add(new Banda("Queen", new ArrayList<>()));
		Artista roger = new Artista("Roger Taylor", rolesRoger, bandasRoger, 0, 100);

		List<Rol> rolesJohn = new ArrayList<>();
		rolesJohn.add(Rol.Bajista);
		List<Banda> bandasJohn = new ArrayList<>();
		bandasJohn.add(new Banda("Queen", new ArrayList<>()));
		Artista john = new Artista("John Deacon", rolesJohn, bandasJohn, 0, 100);

		artistasBase.add(brian);
		artistasBase.add(roger);
		artistasBase.add(john);

		// Crear artistas candidatos para contratar
		List<Rol> rolesDavid = new ArrayList<>();
		rolesDavid.add(Rol.VozPrincipal);
		List<Banda> bandasDavid = new ArrayList<>();
		bandasDavid.add(new Banda("David Bowie", new ArrayList<>()));
		Artista david = new Artista("David Bowie", rolesDavid, bandasDavid, 1500, 2);

		List<Rol> rolesElton = new ArrayList<>();
		rolesElton.add(Rol.VozPrincipal);
		rolesElton.add(Rol.Tecladista);
		List<Banda> bandasElton = new ArrayList<>();
		bandasElton.add(new Banda("Elton John", new ArrayList<>()));
		Artista elton = new Artista("Elton John", rolesElton, bandasElton, 1200, 2);

		List<Rol> rolesAnnie = new ArrayList<>();
		rolesAnnie.add(Rol.VozPrincipal);
		List<Banda> bandasAnnie = new ArrayList<>();
		bandasAnnie.add(new Banda("Eurythmics", new ArrayList<>()));
		Artista annie = new Artista("Annie Lennox", rolesAnnie, bandasAnnie, 900, 2);

		artistasCandidatos.add(david);
		artistasCandidatos.add(elton);
		artistasCandidatos.add(annie);

		// Crear canciones
		Set<Cancion> canciones = new HashSet<>();

		Map<Rol, Integer> rolesWillRock = new HashMap<>();
		rolesWillRock.put(Rol.VozPrincipal, 1);
		rolesWillRock.put(Rol.Guitarrista, 1);
		rolesWillRock.put(Rol.Bajista, 1);
		rolesWillRock.put(Rol.Baterista, 1);
		Cancion willRock = new Cancion("We Will Rock You", 3.5, rolesWillRock);

		Map<Rol, Integer> rolesSomeone = new HashMap<>();
		rolesSomeone.put(Rol.VozPrincipal, 1);
		rolesSomeone.put(Rol.Guitarrista, 1);
		rolesSomeone.put(Rol.Bajista, 1);
		rolesSomeone.put(Rol.Baterista, 1);
		rolesSomeone.put(Rol.Tecladista, 1);
		Cancion someone = new Cancion("Somebody to Love", 4.2, rolesSomeone);

		Map<Rol, Integer> rolesDays = new HashMap<>();
		rolesDays.put(Rol.VozPrincipal, 2);
		rolesDays.put(Rol.Guitarrista, 1);
		rolesDays.put(Rol.Bajista, 1);
		rolesDays.put(Rol.Baterista, 1);
		Cancion days = new Cancion("These Are the Days of Our Lives", 5.0, rolesDays);

		canciones.add(willRock);
		canciones.add(someone);
		canciones.add(days);

		// Crear recital
		this.recital = new Recital(canciones, artistasBase, new HashSet<>(), new Date());
		this.contrato = new Contrato(1, recital, artistasCandidatos);
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

			System.out.print("Selecciona una opcion: ");
			int opcion = scanner.nextInt();
			scanner.nextLine();

			switch (opcion) {
			case 1:
				rolesFaltantesCancion();
				break;
			case 2:
				rolesFaltantesTotal();
				break;
			case 3:
				contratarUnaCancion();
				break;
			case 4:
				contratarTodasCanciones();
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

	private void rolesFaltantesCancion() {
		System.out.println("\n=== ROLES FALTANTES POR CANCION ===\n");

		int indice = 1;
		Map<Integer, Cancion> canciones = new HashMap<>();

		for (Cancion cancion : recital.getListaCanciones()) {
			canciones.put(indice, cancion);
			System.out.println(indice + ". " + cancion.getNombreCancion());
			indice++;
		}

		System.out.print("\nSelecciona una cancion (numero): ");
		int seleccion = scanner.nextInt();
		scanner.nextLine();

		Cancion seleccionada = canciones.get(seleccion);

		if (seleccionada == null) {
			System.out.println("Cancion no encontrada.");
			return;
		}

		Map<Rol, Integer> rolesFaltantes = recital.getRolesFaltantesCancion(seleccionada);

		System.out.println("\nRoles faltantes para: " + seleccionada.getNombreCancion());
		System.out.println("--------------------------------");

		if (rolesFaltantes.isEmpty()) {
			System.out.println("Todos los roles estan cubiertos!");
		} else {
			for (Map.Entry<Rol, Integer> entry : rolesFaltantes.entrySet()) {
				System.out.println("  " + entry.getKey() + ": " + entry.getValue());
			}
		}
	}

	private void rolesFaltantesTotal() {
		System.out.println("\n=== ROLES FALTANTES TOTAL ===\n");

		Map<Rol, Integer> rolesFaltantes = recital.getRolesFaltantesTotal();

		if (rolesFaltantes.isEmpty()) {
			System.out.println("Todos los roles estan cubiertos!");
		} else {
			System.out.println("Roles faltantes para el recital completo:");
			System.out.println("--------------------------------");
			for (Map.Entry<Rol, Integer> entry : rolesFaltantes.entrySet()) {
				System.out.println("  " + entry.getKey() + ": " + entry.getValue());
			}
		}
	}

	private void contratarUnaCancion() {
		System.out.println("\n=== CONTRATAR PARA UNA CANCION ===\n");

		int indice = 1;
		Map<Integer, Cancion> canciones = new HashMap<>();

		for (Cancion cancion : recital.getListaCanciones()) {
			if (!cancion.puestosCubiertos()) {
				canciones.put(indice, cancion);
				System.out.println(indice + ". " + cancion.getNombreCancion());
				indice++;
			}
		}

		if (canciones.isEmpty()) {
			System.out.println("Todas las canciones ya estan completas!");
			return;
		}

		System.out.print("\nSelecciona una cancion (numero): ");
		int seleccion = scanner.nextInt();
		scanner.nextLine();

		Cancion seleccionada = canciones.get(seleccion);

		if (seleccionada == null) {
			System.out.println("Cancion no encontrada.");
			return;
		}

		boolean resultado = contrato.contratoPorCancion(seleccionada);

		if (resultado) {
			System.out.println("\nContratacion exitosa!");
			System.out.println("Costo total del contrato: $" + String.format("%.2f", contrato.getCostoTotal()));
		} else {
			System.out.println("\nNo se pudo completar la contratacion.");
		}
	}

	private void contratarTodasCanciones() {
		System.out.println("\n=== CONTRATAR TODAS LAS CANCIONES ===\n");

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
