package Dominio;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class Recital {
	private int idRecital;
	private Set<Cancion> listaCanciones;
	private Set<Artista> artistasBase;
	private Set<Artista> artistasContratados;
	private LocalDate fecha;

	// ============ CONSTRUCTOR ============

	public Recital(Set<Cancion> listaCanciones, Set<Artista> artistasBase, Set<Artista> artistasContratados,
			LocalDate fecha) {
		this.listaCanciones = listaCanciones;
		this.artistasBase = artistasBase;
		this.artistasContratados = artistasContratados;
		this.fecha = fecha;
	}

	// ============ GETTERS ============
	public int getId() {
		return this.idRecital;
	}

	public Set<Cancion> getListaCanciones() {
		return this.listaCanciones;
	}

	public Set<Artista> getArtistasBase() {
		return this.artistasBase;
	}

	public Set<Artista> getArtistasContratados() {
		return this.artistasContratados;
	}

	public LocalDate getFecha() {
		return this.fecha;
	}

	// ============ AGREGAR ELEMENTOS ============
	public void setId(int id) {
		this.idRecital = id;
	}

	public void setArtistasBase(Set<Artista> artistasBase) {
		this.artistasBase = artistasBase;
	}

	public void agregarCancion(Cancion otra) {
		this.listaCanciones.add(otra);
	}

	public void agregarArtistaBase(Artista otro) {
		this.artistasBase.add(otro);
	}

	public void agregarArtistaContrato(Artista otro) {
		this.artistasContratados.add(otro);
	}

	// ============ MÉTODOS AUXILIARES ============

	public Artista buscarArtistaPorNombre(String nombre) {
		for (Artista artista : artistasBase) {
			if (artista.getNombre().equalsIgnoreCase(nombre)) {
				return artista;
			}
		}

		for (Artista artista : artistasContratados) {
			if (artista.getNombre().equalsIgnoreCase(nombre)) {
				return artista;
			}
		}

		return null;
	}

	// ============ MÉTODOS DE UTILIDAD ============

	public void entrenarArtista(String nombreArtista, Rol rol) throws Exception {
		Artista artista = buscarArtistaPorNombre(nombreArtista);

		if (artista == null) {
			throw new Exception("Artista no encontrado: " + nombreArtista);
		}

		artista.entrenar(rol);
	}

	public boolean verificarBandaCompartida(Artista artistaCandidato) {
		for (Artista base : artistasBase) {
			if (base.compartioBandaCon(artistaCandidato)) {
				return true;
			}
		}
		return false;
	}

	public boolean validarArtistaParaEntrenamiento(Artista artista) {
		return artista.getCancionesAsignadas() == 0;
	}

	// ============ MÉTODOS DE CONSULTA ============

	// Obtener roles faltantes para una canción específica
	public java.util.Map<Rol, Integer> getRolesFaltantesCancion(Cancion cancion) throws Exception {
		if(!listaCanciones.contains(cancion))
			throw new Exception("La cancion " + cancion.getNombreCancion() + " no está en este recital");
		else
			return cancion.getMapRoles();
	}

	// Obtener roles faltantes para TODAS las canciones
	public java.util.Map<Rol, Integer> getRolesFaltantesTotal() {
		java.util.Map<Rol, Integer> rolesFaltantes = new java.util.HashMap<>();

		for (Cancion cancion : listaCanciones) {
			java.util.List<Rol> rolesFaltantesCancion = cancion.consultarRolesFaltantes();
			for (Rol rol : rolesFaltantesCancion) {
				rolesFaltantes.put(rol, rolesFaltantes.getOrDefault(rol, 0) + 1);
			}
		}

		return rolesFaltantes;
	}

	// Listar artistas contratados con información
	public void listarArtistasContratados() {
		System.out.println("\n=== ARTISTAS CONTRATADOS ===\n");

		if (artistasContratados.isEmpty()) {
			System.out.println("No hay artistas contratados aun.\n");
			return;
		}

		double costoTotal = 0;
		for (Artista artista : artistasContratados) {
			double costo = artista.getCosto();
			costoTotal += costo;
			System.out.println("Nombre: " + artista.getNombre());
			System.out.println("  Costo: $" + String.format("%.2f", costo));
			System.out.println("  Canciones asignadas: " + artista.getCancionesAsignadas() + "/" 
					+ artista.getMaxCanciones());
			System.out.println("  Entrenamientos: " + artista.cantidadEntrenamientos());
			System.out.println();
		}

		System.out.println("COSTO TOTAL: $" + String.format("%.2f", costoTotal) + "\n");
	}

	// Listar canciones con estado
	public void listarCancionesConEstado(List<Contrato> contratos) {
		System.out.println("\n=== ESTADO DE CANCIONES ===\n");

		for (Cancion cancion : listaCanciones) {
			System.out.println("Cancion: " + cancion.getNombreCancion());
			System.out.println("  Duracion: " + cancion.getDuracion() + " minutos");

			java.util.List<Rol> rolesFaltantes = cancion.consultarRolesFaltantes();
			boolean completa = cancion.puestosCubiertos();

			if (completa) {
				System.out.println("  Estado: COMPLETA");
			} else {
				System.out.println("  Estado: INCOMPLETA");
				System.out.println("  Roles faltantes: " + rolesFaltantes.size());
				for (Rol rol : rolesFaltantes) {
					System.out.println("    - " + rol);
				}
			}

			System.out.println("  Artistas asignados: " + cancion.getListArtAsignados().size());
			for(Contrato contrato : contratos) {
				if(contrato.getCancion().getId() == cancion.getId()) {
					System.out.println("  Contrato asociado:");
					System.out.println("    Artistas contratados:");
					for(Artista artista : contrato.getArtistasAsignados().keySet()) {
						Rol rol = contrato.getArtistasAsignados().get(artista);
						System.out.println("      - " + artista.getNombre() + " (" + rol + ")");
					}
					System.out.println("    Costo total: $" + String.format("%.2f", contrato.getCostoTotal()));
				}
			}
			System.out.println();
		}
	}

	public void verEstadoRecital() {
		System.out.println("\n=== INFORMACION DEL RECITAL ===\n");
		System.out.println("Fecha del recital: " + fecha);
		System.out.println("\nCanciones:");
		for (Cancion cancion : listaCanciones) {
			System.out.println("- Cancion: " + cancion.getNombreCancion() + " | Duracion: " + cancion.getDuracion() + " minutos");
			
		}
		System.out.println("Cantidad de canciones: " + listaCanciones.size());
		System.out.println("\nArtistas Base:");
		for (Artista artista : artistasBase) {
			System.out.println("- Artista base: " + artista.getNombre());
		}
		System.out.println("Cantidad de artistas base: " + artistasBase.size());
		System.out.println("\nArtistas Contratados:");
		for (Artista artista : artistasContratados) {
			System.out.println("- Artista contratado: " + artista.getNombre());
		}
		System.out.println("Cantidad de artistas contratados: " + artistasContratados.size() + "\n");
	}

	@Override
	public String toString() {
		return "Recital{" + "fecha=" + fecha + ", canciones=" + listaCanciones.size()
				+ ", artistas base=" + artistasBase.size() + ", artistas contratados=" + artistasContratados.size()
				+ '}';
	}
}