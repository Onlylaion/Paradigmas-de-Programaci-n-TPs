package Dominio;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Artista implements Comparable<Artista>{
	private int id;
	private String nombreArtista;
	private List<Rol> RolesHistoricos; // Puedo querer agregarle Roles por si lo entrené
	private List<Banda> bandasHistoricas; // Puedo querer agregarle a futuro una banda
	private double costoCancionBase;
	private double costoCancionDesc;
	private int cancionesAsignadas;
	private int maxCanciones;
	private boolean descuento;
	private double recargo;
	private Set<Rol> rolesEntrenados;
	private double disponibilidadHoraria;

	public Artista(String nombreArtista, List<Rol> rolesHistoricos, List<Banda> bandasHistoricas,
			double costoCancionBase, int maxCanciones, double disponibilidadHoraria) {
		this.nombreArtista = nombreArtista;
		this.RolesHistoricos = rolesHistoricos;
		this.bandasHistoricas = bandasHistoricas;
		this.costoCancionBase = costoCancionBase;
		this.costoCancionDesc = costoCancionBase * 0.5;
		this.cancionesAsignadas = 0;
		this.maxCanciones = maxCanciones;
		this.descuento = false;
		this.recargo = 0;
		this.rolesEntrenados = new HashSet<>();
		this.disponibilidadHoraria = disponibilidadHoraria;
	}

	// GETTERS
	public int getId() {
		return id;
	}

	public String getNombre() {
		return nombreArtista;
	}

	public List<Rol> getRolesHistoricos() {
		return this.RolesHistoricos;
	}

	public List<Banda> getBandasHistoricas() {
		return this.bandasHistoricas;
	}

	public double getCostoCancionBase() {
		return this.costoCancionBase;
	}

	public double getCostoCancionDesc() {
		return this.costoCancionDesc;
	}

	public int getCancionesAsignadas() {
		return this.cancionesAsignadas;
	}

	public int getMaxCanciones() {
		return this.maxCanciones;
	}

	public boolean isDescuento() {
		return this.descuento;
	}

	public double getRecargo() {
		return this.recargo;
	}

	public Set<Rol> getRolesEntrenados() {
		return this.rolesEntrenados;
	}

	public void setId(int id) {
		this.id = id;
	}

	// ============ MÉTODOS DEL DIAGRAMA ============

	public boolean compartioBandaCon(Artista otroArtista) {
		if (otroArtista == null)
			return false;
		for (Banda banda : this.bandasHistoricas) {
			for (Banda otraBanda : otroArtista.bandasHistoricas) {
				if (banda.getNombreBanda().equals(otraBanda.getNombreBanda())) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean estaCalificadoParaLaCancion(Rol rol) {
		return RolesHistoricos.contains(rol) || rolesEntrenados.contains(rol);
	}

	// Aca te tiro una excepcion si quieren entrenar alguien que ya tiene cierta
	// habilidad
	public void agregarRol(Rol rol) throws Exception {
		if (estaCalificadoParaLaCancion(rol)) {
			throw new Exception("No se puede entrenar una habilidad a alguien que ya la tiene");
		} else {
			rolesEntrenados.add(rol);
			recargo += costoCancionBase * 0.5;
		}
	}

	public boolean esBase() {
		return costoCancionBase == 0;
	}

	/// El precio que vemos para contratar
	public double getCostoCancionDescuento() {
		return this.costoCancionDesc;
	}

	// Calcula el costo final (base o desc + recargo)
	public double getCosto() {
		double costoFinal = this.descuento ? this.costoCancionDesc : this.costoCancionBase;
		return costoFinal + this.recargo;
	}

	/// Maximo de canciones que va a tener disponibles el artista esa ocasión
	public int getMaxCancionesRecital() {
		return this.maxCanciones;
	}

	public boolean tieneDisponibilidadHoraria(double duracionNuevaCancion) {
		return (this.disponibilidadHoraria - duracionNuevaCancion) >= 0;
	}

	// Entrenar implica 50% extra no acumulativo
	/**
	 * Entrena al artista en nuevo rol (+50% recargo no acumulativo)
	 */
	public void entrenar(Rol rol) throws Exception {
		if (cancionesAsignadas > 0) {
			throw new IllegalArgumentException(
					"No se puede entrenar a " + nombreArtista + " porque ya está contratado");
		}
		agregarRol(rol);
	}

	public void aplicarDescuento() throws Exception {
		if (this.descuento == true) {
			throw new Exception("El artista ya tuvo el descuento aplicado");
		}
		this.descuento = true;
	}

	/**
	 * Asigna a una canción más
	 */
	public void asignarACancion(double duracionNuevaCancion) throws Exception {
		if (cancionesAsignadas >= maxCanciones) {
			throw new IllegalArgumentException("El artista " + nombreArtista + " alcanzó el máximo de canciones");
		}
		if ((this.disponibilidadHoraria - duracionNuevaCancion) < 0) {
			throw new IllegalArgumentException(
					"El artista " + nombreArtista + " no tiene disponibilidad horaria para la canción");
		}
		this.disponibilidadHoraria -= duracionNuevaCancion;
		cancionesAsignadas++;
	}

	// quitar una canción
	public void desasignarDeCancion() {
		if (cancionesAsignadas > 0) {
			cancionesAsignadas--;
		}
	}

	// Es la cantidad de veces que aprendio un nuevo rol, o los roles entrenados
	public int cantidadEntrenamientos() {
		return rolesEntrenados.size();
	}

	@Override
	public String toString() {
		String cad = "Artista{" + "nombre='" + nombreArtista + '\'' + ", costo=$" + String.format("%.2f", getCosto())
				+ ", canciones=" + cancionesAsignadas + "/" + maxCanciones + '}';
		for (Rol rol : RolesHistoricos) {
			cad += "\n  - Rol historico: " + rol;
		}
		return cad;
	}

	@Override
	public int compareTo(Artista o) {
		if(this.nombreArtista.equals(o.nombreArtista))
			return 0;
		else if(this.nombreArtista.compareTo(o.nombreArtista) > 1)
			return 1;
		
		return -1;
	}
}
