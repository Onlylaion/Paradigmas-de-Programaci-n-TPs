package Servicio;


import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Scanner;

//import Persistencia.ControllerArchivoInicial;
//import Persistencia.ControllerArtista;
import Persistencia.ControllerCancion;
//import Persistencia.ControllerRecital;
import Persistencia.ControllerContrato;
import Dominio.Artista;
import Dominio.Cancion;
import Dominio.Rol;

public class InterfazUsuarioConsola {
    //private ControllerArchivoInicial controladorArchivos;
    private ControllerCancion controladorCancion;
    private ControllerContrato controladorContrato;
	//private ControllerRecital controladorRecital;
	//private ControllerArtista controladorArtista;
    private Scanner scanner;
    private LoteDePrueba loteDePrueba = new LoteDePrueba();

    public InterfazUsuarioConsola() {
        //this.controladorArchivos = new ControllerArchivoInicial();
        //this.controladorCancion = new ControllerCancion();
        //this.controladorRecital = new ControllerRecital();
        this.scanner = new Scanner(System.in);
    }

    public void inicializar() {
        loteDePrueba.cargarLoteDePrueba();
        this.controladorCancion = loteDePrueba.getControllerCancion();
        this.controladorContrato = loteDePrueba.getControllerContrato();
    }

    public int obtenerComando() {
        //scanner.nextLine(); // Limpiar el buffer
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
        Set <Cancion> canciones = null;
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

            for(Cancion c : canciones) {
                System.out.println("\nAsignaciones para la cancion: " + c.getNombreCancion());
                System.out.println("--------------------------------");
                List<Artista> artistasAsignados = controladorContrato.obtenerArtistasContratadosPorCancion(c);
                for(Artista a : artistasAsignados) {
                    System.out.println(" - " + a.getNombre());
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }
	}
}
