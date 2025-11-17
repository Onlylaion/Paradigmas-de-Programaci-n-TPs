package Dominio;

import java.util.ArrayList;
import java.util.List;

public class Contrato {
	private long idContrato;
	private Recital recital;
	private Cancion cancion;
	List<Artista> artistasAsignados;
	private double costoTotal;

	// ============ CONSTRUCTOR ============
	public Contrato(Recital recital, Cancion cancion) {
		this.recital = recital;
		this.cancion = cancion;
		this.artistasAsignados = new ArrayList<>();
		this.costoTotal = 0;
	}

	// ============ GETTERS ============

	public long getIdContrato() {
		return this.idContrato;
	}

	public void setIdContrato(long idContrato) {
		this.idContrato = idContrato;
	}

	public Cancion getCancion() {
		return this.cancion;
	}

	public Recital getRecital() {
		return this.recital;
	}

	public List<Artista> getArtistasAsignados() {
		return this.artistasAsignados;
	}

	public double getCostoTotal() {
		return this.costoTotal;
	}

	// ============ MÉTODOS DE CONTRATO ============

	// Contratar para una canción específica
	public boolean contratoPorCancion(List<Artista> artistasCandidatos) {
		List<Rol> rolesFaltantes = cancion.consultarRolesFaltantes();

		for (Rol rol : rolesFaltantes) {
			boolean asignado = false;
			List<Artista> candidatosRestantes = new ArrayList<>(artistasCandidatos);
			List<Artista> artistasNoPosibles = new ArrayList<>();

			while(!asignado && !candidatosRestantes.isEmpty()) {
				Artista artistaSeleccionado = buscarMejorArtistaParaRol(rol, candidatosRestantes, artistasNoPosibles);

				if (artistaSeleccionado != null) {
					try {
						if (cancion.ocuparRol(artistaSeleccionado)) {
							double costoFinal = calcularCostoConDescuentos(artistaSeleccionado);
							costoTotal += costoFinal;
							artistasAsignados.add(artistaSeleccionado);
							artistaSeleccionado.asignarACancion(this.cancion.getDuracion());

							if (!recital.getArtistasContratados().contains(artistaSeleccionado)) {
								recital.agregarArtistaContrato(artistaSeleccionado);
							}
							asignado = true;
						}
					} catch (Exception e) {
						System.out.println("Error al contratar a " + artistaSeleccionado.getNombre() + " en " + rol + " para la cancion " + cancion.getNombreCancion() + ": " + e.getMessage());
						candidatosRestantes.remove(artistaSeleccionado);
						artistasNoPosibles.add(artistaSeleccionado);
					}
				} else {
					System.out.println("No hay artista disponible para el rol: " + rol);
					return false;
				}
			}
		}

		if (!artistasAsignados.isEmpty()) {
			return true;
		}

		return false;
	}

	// // Contratar para todas las canciones
	// public double contratoTodasCanciones(List<Artista> artistasCandidatos) {
	// 	for (Cancion cancion : recital.getListaCanciones()) {
	// 		if (!cancion.puestosCubiertos()) {
	// 			contratoPorCancion(artistasCandidatos);
	// 		}
	// 	}
	// 	return costoTotal;
	// }

	// Desasignar un artista del contrato
	public void desasignarContrato(Artista artista) throws Exception {
		boolean encontrado = false;

		if (this.artistasAsignados.contains(artista)) {
			try {
				cancion.descuparRol(artista);
				artista.desasignarDeCancion();
				this.artistasAsignados.remove(artista);
				encontrado = true;
			} catch (Exception e) {
				System.out.println("Error al desasignar: " + e.getMessage());
			}
		}
		

		if (!encontrado) {
			throw new Exception("El artista " + artista.getNombre() + " no está en este contrato");
		}

		recital.getArtistasContratados().remove(artista);
	}

	// ============ MÉTODOS PRIVADOS ============

	private Artista buscarMejorArtistaParaRol(Rol rol, List<Artista> artistasCandidatos, List<Artista> artistasNoPosibles) {
		// Primero buscar en artistas BASE
		for (Artista artista : recital.getArtistasBase()) {
			if (artista.estaCalificadoParaLaCancion(rol) && !artistasNoPosibles.contains(artista) 
				&& artista.tieneDisponibilidadHoraria(this.cancion.getDuracion())) {
				
				return artista;
			}
		}

		if(artistasCandidatos.isEmpty()) {
			return null;
		}
		// Luego en candidatos - elegir el más barato
		Artista mejorArtista = null;
		double menorCosto = Double.MAX_VALUE;

		for (Artista artista : artistasCandidatos) {
			if (artista.estaCalificadoParaLaCancion(rol) && verificarDisponibilidad(artista, 1) 
				&& !artistasNoPosibles.contains(artista) && artista.tieneDisponibilidadHoraria(this.cancion.getDuracion())) {
					
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

		System.out.println("Canción: " + cancion.getNombreCancion());
		for (Artista artista : artistasAsignados) {
			System.out.println("  - " + artista.getNombre() + " | $" + String.format("%.2f", artista.getCosto()));
		}
		System.out.println();

		System.out.println("COSTO TOTAL DEL CONTRATO: $" + String.format("%.2f", costoTotal));
	}

	@Override
	public String toString() {
		return "Contrato{" + "id=" + idContrato + ", canciones=" + cancion.getNombreCancion()
				+ ", artistas contratados=" + artistasAsignados.size() + ", costo=$"
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
