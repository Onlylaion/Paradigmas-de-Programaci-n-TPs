package Dominio;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;

public class Artista implements Comparable<Artista> {
	private int id;
	private String nombreArtista;
	private List<Rol> RolesHistoricos; // Puedo querer agregarle Roles por si lo entrené
	private List<Banda> bandasHistoricas; // Puedo querer agregarle a futuro una banda
	private double costoCancionBase;
	private double costoCancionDesc;
	private int cancionesAsignadas;
	private int maxCanciones;
	private Map<Cancion, Boolean> descuento = new HashMap<>(); // Cancion y si tiene descuento o no
	private double recargo;
	private Set<Rol> rolesEntrenados = new HashSet<>();
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
		this.recargo = 0;
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

	public boolean isDescuento(Cancion cancion) {
		return this.descuento.getOrDefault(cancion, false);
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
		if (otroArtista == null) {
			return false;
		}
		
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
	public double getCosto(Cancion cancion) {
		double costoFinal = this.descuento.getOrDefault(cancion, false) ? this.costoCancionDesc : this.costoCancionBase;
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

	public void aplicarDescuento(Cancion cancion) throws Exception {
		if (this.descuento.getOrDefault(cancion, false)) {
			throw new Exception("El artista ya tuvo el descuento aplicado");
		}
		this.costoCancionDesc = this.costoCancionBase * 0.5;
		this.descuento.put(cancion, true);
	}

	public void quitarDescuento(Cancion cancion) throws Exception {
		if (!this.descuento.getOrDefault(cancion, false)) {
			throw new Exception("El artista ya tuvo el descuento aplicado");
		}
		this.descuento.put(cancion, false);
		this.costoCancionDesc = this.costoCancionBase;
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
		String cad = "Artista{" + "nombre='" + nombreArtista + '\'' + ", costo base=$" + String.format("%.2f", getCostoCancionBase())
				+ ", canciones=" + cancionesAsignadas + "/" + maxCanciones + '}';
		for (Rol rol : RolesHistoricos) {
			cad += "\n  - Rol historico: " + rol;
		}
		
		if(rolesEntrenados == null || rolesEntrenados.isEmpty()) {
			cad += "\n  - Sin roles entrenados";
		}else {
			for (Rol rol : rolesEntrenados) {
				cad += "\n  - Rol entrenado: " + rol;
			}
		}

		if (descuento == null || descuento.isEmpty()) {
			cad += "\n  - Sin descuentos aplicados";
		}else {
			for (Map.Entry<Cancion, Boolean> entry : descuento.entrySet()) {
				Cancion cancion = entry.getKey();
				cad += "\n  - Descuento en cancion '" + cancion.getNombreCancion() + "': " + String.format("%.2f", getCosto(cancion));
			}
		}
		return cad;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Artista artista = (Artista) o;
		return id == artista.id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public int compareTo(Artista o) {
		if (this.id == o.id)
			return 0;
		else if (this.id > o.id)
			return 1;

		return -1;
	}

}
