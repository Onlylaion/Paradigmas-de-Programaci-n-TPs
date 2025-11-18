package Servicio;

import java.util.Map;
import java.util.Set;
import java.util.Scanner;

//import Persistencia.ControllerArchivoInicial;
import Persistencia.ControllerArtista;
import Persistencia.ControllerCancion;
import Persistencia.ControllerRecital;
import Persistencia.ControllerContrato;
import Dominio.Artista;
import Dominio.Cancion;
import Dominio.Recital;
import Dominio.Rol;
import ResourceProlog.ConsultaProlog;

public class InterfazUsuarioConsola {
	private ControllerCancion controladorCancion;
	private ControllerContrato controladorContrato;
	private ControllerRecital controladorRecital;
	private ControllerArtista controladorArtista;
	private Scanner scanner;
	private LoteDePrueba loteDePrueba = new LoteDePrueba();
	private ConsultaProlog PrologQuery = new ConsultaProlog("src/ResourceProlog/Entrenamiento.pl");

	public InterfazUsuarioConsola() {
		this.scanner = new Scanner(System.in);
	}

	/// lote de prueba para el constructor de nuestro controller
	public void inicializar() {
		loteDePrueba.cargarLoteDePrueba();
		this.controladorCancion = loteDePrueba.getControllerCancion();
		this.controladorContrato = loteDePrueba.getControllerContrato();
		this.controladorRecital = loteDePrueba.getControllerRecital();
		this.controladorArtista = loteDePrueba.getControllerArtista();
	}

	public int obtenerComando() {
		// scanner.nextLine(); // Limpiar el buffer
		System.out.print("Selecciona una opcion: ");
		return scanner.nextInt();
	}

	public void rolesFaltantesCancion() {
		Set<Cancion> canciones = controladorCancion.obtenerCanciones();
		for (Cancion cancionActual : canciones) {
			System.out.println(cancionActual.getId() + " - " + cancionActual.getNombreCancion());
		}

		System.out.print("\nSelecciona una cancion (numero): ");
		int seleccion = scanner.nextInt();
		scanner.nextLine();

		Cancion seleccionada = null;

		for (Cancion cancionActual : canciones) {
			if (cancionActual.getId() == seleccion) {
				seleccionada = cancionActual;
				break;
			}
		}

		if (seleccionada == null) {
			System.out.println("Cancion no encontrada.");
			return;
		}

		System.out.println("\nRoles faltantes para: " + seleccionada.getNombreCancion());
		System.out.println("--------------------------------");

		try {
			Map<Rol, Integer> rolesFaltantes = controladorCancion.getRolesFaltantes(seleccionada);
			for (Map.Entry<Rol, Integer> entry : rolesFaltantes.entrySet()) {
				System.out.println("  " + entry.getKey() + ": " + entry.getValue());
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return;
		}
	}

	public void rolesFaltantesTotal() {
		try {
			Map<Rol, Integer> rolesFaltantes = controladorCancion.getRolesFaltantesTotal();
			System.out.println("Roles faltantes para el recital completo:");
			System.out.println("--------------------------------");
			for (Map.Entry<Rol, Integer> entry : rolesFaltantes.entrySet()) {
				System.out.println("  " + entry.getKey() + ": " + entry.getValue());
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return;
		}
	}

	public void contratarUnaCancion() {
		Set<Cancion> canciones = null;
		try {
			canciones = controladorCancion.obtenerCancionesConPuestosFaltantes();

			for (Cancion cancionActual : canciones) {
				System.out.println(cancionActual.getId() + " - " + cancionActual.getNombreCancion());
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return;
		}

		System.out.print("\nSelecciona una cancion (numero): ");
		int seleccion = scanner.nextInt();
		scanner.nextLine();

		Cancion seleccionada = null;
		for (Cancion cancionActual : canciones) {
			if (cancionActual.getId() == seleccion) {
				seleccionada = cancionActual;
				break;
			}
		}

		if (seleccionada == null) {
			System.out.println("Cancion no encontrada.");
			return;
		}

		try {
			double costoTotal = controladorContrato.contratarPorCancion(seleccionada);
			System.out.println("\nContratacion exitosa!");
			System.out.println("Costo total del contrato: $" + String.format("%.2f", costoTotal));

			System.out.println("\nAsignaciones para la cancion: " + seleccionada.getNombreCancion());
			System.out.println("--------------------------------");
			Map<Artista, Rol> artistasAsignados = controladorContrato.obtenerArtistasYRolContratadosPorCancion(seleccionada);
			for (Map.Entry<Artista, Rol> entry : artistasAsignados.entrySet()) {
				Artista a = entry.getKey();
				Rol rol = entry.getValue();
				System.out.println(" - " + a.getNombre() + " (" + rol + ")");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return;
		}
	}

	public void contratarTodasCanciones() {
		Set<Cancion> canciones = null;
		try {
			canciones = controladorCancion.obtenerCancionesConPuestosFaltantes();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return;
		}

		try {
			double costoTotalContrataciones = controladorContrato.contratoTodasCanciones(canciones);
			System.out.println("Contratacion finalizada!");
			System.out.println("Costo total: $" + String.format("%.2f", costoTotalContrataciones));

			for (Cancion c : canciones) {
				System.out.println("\nAsignaciones para la cancion: " + c.getNombreCancion());
				System.out.println("--------------------------------");
				Map<Artista, Rol> artistasAsignados = controladorContrato.obtenerArtistasYRolContratadosPorCancion(c);
				for (Map.Entry<Artista, Rol> entry : artistasAsignados.entrySet()) {
					Artista a = entry.getKey();
					Rol rol = entry.getValue();
					System.out.println(" - " + a.getNombre() + " (" + rol + ")");
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return;
		}
	}

	public void entrenarArtista() {
		Set<Artista> artistas = controladorContrato.obtenerTodosArtistasNoContratados();
		System.out.println("Artistas disponibles para entrenar:");
		for (Artista artistaActual : artistas) {
			System.out.println(artistaActual.getNombre());
		}

		System.out.print("Nombre del artista a entrenar: ");
		scanner.nextLine(); // Limpiar buffer
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
			this.controladorArtista.entrenarArtista(nombre, rolSeleccionado);
			System.out.println("\nArtista " + nombre + " entrenado en " + rolSeleccionado + "!");
		} catch (Exception e) {
			System.out.println("\nError: " + e.getMessage());
		}
	}

	public void listarArtistasContratados() {
		try {
			this.controladorRecital.listarArtistasContratados();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void listarCancionesConEstado() {
		try {
			this.controladorContrato.listarContratoYCancionesConEstado();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void desasignarArtista(){
		try {
			//Listar artistas
			for (Artista artista : controladorRecital.getArtistasContratados()) {
				System.out.println(artista.getId()+ " " + artista.getNombre() );
			}
		}catch(Exception e) {
			System.out.println("No hay artistas contratados");
		}

		//Seleccionar artista a borrar
		System.out.print("\nSelecciona un artista (numero): ");
		int seleccion = scanner.nextInt();
		scanner.nextLine();


		try {
			Artista seleccionado = controladorArtista.findById(seleccion);
			controladorContrato.desasignarContrato(seleccionado);
			System.out.println("\nArtista " + seleccionado.getNombre() + " desasignado exitosamente.");
		} catch (Exception e) {
			System.out.println("\nError: " + e.getMessage());
		}
	}
	
	public void verInformacionRecital() {
		try {
			System.out.println(this.controladorRecital.buscarXId(1));
			this.controladorRecital.verEstadoRecital();
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}

	}
	
	public void realizarConsultaMinimosProlog()
	{
		try {
			for(Recital r: controladorRecital.getRecitales() )
			{
				PrologQuery.ConsultarEntrenamientosMinimos(r);
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
			
	}

}
