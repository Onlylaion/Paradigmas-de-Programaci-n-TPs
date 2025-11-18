package Dominio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Contrato {
	private long idContrato;
	private Recital recital;
	private Cancion cancion;
	Map<Artista, Rol> artistasAsignados;
	private double costoTotal;

	// ============ CONSTRUCTOR ============
	public Contrato(Recital recital, Cancion cancion) {
		this.recital = recital;
		this.cancion = cancion;
		this.artistasAsignados = new HashMap<>();
		this.costoTotal = 0;
	}

	// ============ GETTERS ============

	public long getIdContrato() {
		return this.idContrato;
	}

	public Cancion getCancion() {
		return this.cancion;
	}

	public Recital getRecital() {
		return this.recital;
	}

	public Map<Artista, Rol> getArtistasAsignados() {
		return this.artistasAsignados;
	}

	public double getCostoTotal() {
		return this.costoTotal;
	}

	
	public void setIdContrato(long idContrato) {
		this.idContrato = idContrato;
	}
	public void setArtistasAsignados(Map<Artista,Rol> artistas)
	{
		this.artistasAsignados = artistas;
	}
	
	// ============ MÉTODOS DE CONTRATO ============

	// Contratar para una canción específica
	public boolean contratoPorCancion(Set<Artista> artistasCandidatos) {//----------------------------------------MODIFICADO
		List<Rol> rolesFaltantes = cancion.consultarRolesFaltantes();

		for (Rol rol : rolesFaltantes) {
			boolean asignado = false;
			List<Artista> candidatosRestantes = new ArrayList<>(artistasCandidatos);
			List<Artista> artistasNoPosibles = new ArrayList<>();

			while(!asignado && !candidatosRestantes.isEmpty()) {
				Artista artistaSeleccionado = buscarMejorArtistaParaRol(rol, candidatosRestantes, artistasNoPosibles);

				if (artistaSeleccionado != null) {
					try {
						if (cancion.ocuparRol(artistaSeleccionado, rol)) {
							double costoFinal = calcularCostoConDescuentos(artistaSeleccionado);
							costoTotal += costoFinal;
							artistasAsignados.put(artistaSeleccionado, rol);
							artistaSeleccionado.asignarACancion(this.cancion.getDuracion());
							System.out.println("Artista " + artistaSeleccionado.getNombre() + " asignado al rol " + rol + " para la cancion " + cancion.getNombreCancion() + " con un costo de $" + String.format("%.2f", costoFinal));

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
					break;
				}
			}
		}

		if (!artistasAsignados.isEmpty()) {
			return true;
		}

		return false;
	}



	// Desasignar un artista del contrato
	public void desasignarContrato(Artista artista) throws Exception {
		boolean encontrado = false;

		if (this.artistasAsignados.containsKey(artista)) {
			try {
				Rol rol = this.artistasAsignados.getOrDefault(artista, null);
				if(rol == null) {
					throw new Exception("Rol no encontrado para el artista en este contrato");
				}
				cancion.desocuparRol(artista, rol);
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
		// Luego en candidatos -> elegir el más barato
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

		if(!artistaCandidato.isDescuento()) {
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
		for (Map.Entry<Artista, Rol> entry : artistasAsignados.entrySet()) {
			Artista artista = entry.getKey();
			Rol rol = entry.getValue();
			System.out.println("  - " + artista.getNombre() + " (" + rol + ") | $" + String.format("%.2f", artista.getCosto()));
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

