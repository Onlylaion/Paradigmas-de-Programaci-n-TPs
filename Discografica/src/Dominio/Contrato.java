package Dominio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Contrato {
	private long idContrato;
	private Recital recital;
	private List<Artista> artistasCandidatos;
	private Map<Cancion, List<Artista>> asignacionesPorCancion;
	private double costoTotal;

	// ============ CONSTRUCTOR ============

	public Contrato(Recital recital, List<Artista> artistasCandidatos) {
		this.recital = recital;
		this.artistasCandidatos = new ArrayList<>(artistasCandidatos);
		this.asignacionesPorCancion = new HashMap<>();
		this.costoTotal = 0;
	}

	// ============ GETTERS ============

	public long getIdContrato() {
		return this.idContrato;
	}

	public void setIdContrato(long idContrato) {
		this.idContrato = idContrato;
	}

	public Recital getRecital() {
		return this.recital;
	}

	public List<Artista> getArtistasCandidatos() {
		return this.artistasCandidatos;
	}

	public Map<Cancion, List<Artista>> getAsignacionesPorCancion() {
		return this.asignacionesPorCancion;
	}

	public double getCostoTotal() {
		return this.costoTotal;
	}

	// ============ MÉTODOS DE CONTRATO ============

	// Contratar para una canción específica
	public boolean contratoPorCancion(Cancion cancion) {
		List<Artista> artistasAsignados = new ArrayList<>();
		List<Rol> rolesFaltantes = cancion.consultarRolesFaltantes();
		double costoCancion = 0;

		for (Rol rol : rolesFaltantes) {
			Artista artistoSeleccionado = buscarMejorArtistaParaRol(rol);

			if (artistoSeleccionado != null) {
				try {
					if (cancion.ocuparRol(artistoSeleccionado)) {
						double costoFinal = calcularCostoConDescuentos(artistoSeleccionado);
						costoCancion += costoFinal;
						artistasAsignados.add(artistoSeleccionado);
						artistoSeleccionado.asignarACancion();

						if (!recital.getArtistasContratados().contains(artistoSeleccionado)) {
							recital.agregarArtistaContrato(artistoSeleccionado);
						}
					}
				} catch (Exception e) {
					System.out.println("Error al contratar a " + artistoSeleccionado + " en " + rol + " para la cancion " + cancion.getNombreCancion() + ": " + e.getMessage());
				}
			} else {
				System.out.println("No hay artista disponible para el rol: " + rol);
				return false;
			}
		}

		if (!artistasAsignados.isEmpty()) {
			asignacionesPorCancion.put(cancion, artistasAsignados);
			costoTotal += costoCancion;
			return true;
		}

		return false;
	}

	// Contratar para todas las canciones
	public double contratoTodasCanciones() {
		for (Cancion cancion : recital.getListaCanciones()) {
			if (!cancion.puestosCubiertos()) {
				contratoPorCancion(cancion);
			}
		}
		return costoTotal;
	}

	// Desasignar un artista del contrato
	public void desasignarContrato(Artista artista) throws Exception {
		boolean encontrado = false;

		for (Cancion cancion : asignacionesPorCancion.keySet()) {
			List<Artista> artistas = asignacionesPorCancion.get(cancion);
			if (artistas.contains(artista)) {
				try {
					cancion.descuparRol(artista);
					artista.desasignarDeCancion();
					artistas.remove(artista);
					encontrado = true;
				} catch (Exception e) {
					System.out.println("Error al desasignar: " + e.getMessage());
				}
			}
		}

		if (!encontrado) {
			throw new Exception("El artista " + artista.getNombre() + " no está en este contrato");
		}

		recital.getArtistasContratados().remove(artista);
	}

	// ============ MÉTODOS PRIVADOS ============

	private Artista buscarMejorArtistaParaRol(Rol rol) {
		// Primero buscar en artistas BASE
		for (Artista artista : recital.getArtistasBase()) {
			if (artista.estaCalificadoParaLaCancion(rol)) {
				return artista;
			}
		}

		// Luego en candidatos - elegir el más barato
		Artista mejorArtista = null;
		double menorCosto = Double.MAX_VALUE;

		for (Artista artista : artistasCandidatos) {
			if (artista.estaCalificadoParaLaCancion(rol) && verificarDisponibilidad(artista, 1)) {
				double costoConDescuento = calcularCostoConDescuentos(artista);
				if (costoConDescuento < menorCosto) {
					menorCosto = costoConDescuento;
					mejorArtista = artista;
				}
			}
		}

		return mejorArtista;
	}

	private double calcularCostoConDescuentos(Artista artistaCandidato) {
		double costo = artistaCandidato.getCosto();

		for (Artista base : recital.getArtistasBase()) {
			if (base.compartioBandaCon(artistaCandidato)) {
				try {
					artistaCandidato.aplicarDescuento();
					costo = artistaCandidato.getCosto();
				} catch (Exception e) {
					// Ya tiene descuento
				}
				break;
			}
		}

		return costo;
	}

	private boolean verificarDisponibilidad(Artista artista, int cancionesRequeridas) {
		int espaciosDisponibles = artista.getMaxCanciones() - artista.getCancionesAsignadas();
		return espaciosDisponibles >= cancionesRequeridas;
	}

	// ============ INFORMACIÓN ============

	public void listarAsignaciones() {
		System.out.println("\n═══ ASIGNACIONES POR CANCIÓN ═══\n");

		for (Cancion cancion : asignacionesPorCancion.keySet()) {
			System.out.println("Canción: " + cancion.getNombreCancion());
			List<Artista> artistas = asignacionesPorCancion.get(cancion);
			for (Artista artista : artistas) {
				System.out.println("  - " + artista.getNombre() + " | $" + String.format("%.2f", artista.getCosto()));
			}
			System.out.println();
		}

		System.out.println("COSTO TOTAL DEL CONTRATO: $" + String.format("%.2f", costoTotal));
	}

	@Override
	public String toString() {
		return "Contrato{" + "id=" + idContrato + ", canciones=" + asignacionesPorCancion.size()
				+ ", artistas contratados=" + recital.getArtistasContratados().size() + ", costo=$"
				+ String.format("%.2f", costoTotal) + '}';
	}
}
//package concierto;
//
//public class Contrato {
//	private Artista artistaInteroretante;
//	private Rol rolInterpretar;
//	private  double costoRequerido;
//	
//	public Contrato(Artista artistaInteroretante, Rol rolInterpretar, double costoRequerido) {
//		this.artistaInteroretante = artistaInteroretante;
//		this.rolInterpretar = rolInterpretar;
//		this.costoRequerido = costoRequerido;
//	}
//
//	public Artista getArtistaInterpretante() {
//		return this.artistaInteroretante;
//	}
//	
//	public Rol getRolInterpretar() {
//		return this.rolInterpretar;
//	}
//	
//	public double getCostoDeContratancion() {
//		return this.costoRequerido;
//	}
//}
