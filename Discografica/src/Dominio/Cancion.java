package Dominio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Cancion implements Comparable<Cancion>{
	private int idCancion;
	private String nombreCancion;
	private double duracion;
	private HashMap<Rol, Integer> mapRoles; // Es entero por la cantidad de roles repetibles
	private List<Artista> artistasAsignados;

	public Cancion(String nombreCancion, double duracion, Map<Rol, Integer> rolesRequeridos) {
		this.nombreCancion = nombreCancion;
		this.duracion = duracion;
		this.mapRoles = new HashMap<>(rolesRequeridos);
		this.artistasAsignados = new ArrayList<>();
	}

	public int getId() {
		return this.idCancion;
	}

	public String getNombreCancion() {
		return this.nombreCancion;
	}

	public double getDuracion() {
		return this.duracion;
	}

	public List<Artista> getListArtAsignados() {
		return this.artistasAsignados;
	}

	public HashMap<Rol, Integer> getMapRoles() {
		return this.mapRoles;
	}

	public void setIdCancion(int id) {
		this.idCancion = id;
	}

	/**
	 * Verifica si la canción está completa (todos los roles cubiertos)
	 */
	public boolean puestosCubiertos() {
		for (Entry<Rol, Integer> entry : mapRoles.entrySet()) {
			if (entry.getValue() != 0) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Ocupa un rol con un artista
	 * Busca un rol compatible entre los que tiene el artista y los que necesita la canción
	 * @return true si se asignó exitosamente, false si no fue posible
	 */
	public boolean ocuparRol(Artista otro, Rol rol) throws Exception {
		if (this.artistasAsignados.contains(otro)) {
			throw new Exception("El artista ya está asignado en un rol para la canción");
		}

		// Buscar en roles históricos
		for (Rol rolArtista : otro.getRolesHistoricos()) {
			if (rolArtista.equals(rol) && mapRoles.containsKey(rolArtista) && mapRoles.get(rolArtista) > 0) {
				// Le bajo 1 a la cantidad de artista de ese rol
				mapRoles.put(rolArtista, mapRoles.get(rolArtista) - 1);
				// Agregamos al artista nuevo
				artistasAsignados.add(otro);
				return true;
			}
		}

		// Si no encontró en históricos, buscar en roles entrenados
		for (Rol rolEntrenado : otro.getRolesEntrenados()) {
			if (mapRoles.containsKey(rolEntrenado) && mapRoles.get(rolEntrenado) > 0) {
				// Decrementar el rol disponible
				mapRoles.put(rolEntrenado, mapRoles.get(rolEntrenado) - 1);
				// Agregar el artista
				artistasAsignados.add(otro);
				return true;
			}
		}

		return false;
	}

	/**
	 * Desocupa un rol (quita la asignación de un artista)
	 */
	public void desocuparRol(Artista artista, Rol rol) throws Exception {
		// Remover artista
		artistasAsignados.remove(artista);

		// Incrementar el rol disponible
		mapRoles.put(rol, mapRoles.get(rol) + 1);
	}

	/**
	 * Retorna los roles faltantes
	 */
	public List<Rol> consultarRolesFaltantes() {
		List<Rol> rolesFaltantes = new ArrayList<>();
		for (Entry<Rol, Integer> entry : mapRoles.entrySet()) {
			for (int i = 0; i < entry.getValue(); i++) {
				rolesFaltantes.add(entry.getKey());
			}
		}
		return rolesFaltantes;
	}

	@Override
	public String toString() {
		return "Cancion{" + "nombre='" + nombreCancion + '\'' + ", duracion=" + duracion + ", artistas asignados="
				+ artistasAsignados.size() + ", roles faltantes=" + consultarRolesFaltantes().size() + '}';
	}

	@Override
	public int compareTo(Cancion o) {
		if(this.nombreCancion.equals(o.nombreCancion))
			return 0;
		else if(this.nombreCancion.compareTo(o.nombreCancion) > 1)
			return 1;

		return -1;
	}
}