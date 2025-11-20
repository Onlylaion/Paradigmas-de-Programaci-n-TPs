package Persistencia;

import Repositorio.RecitalRepository;

import Dominio.Recital;
import Dominio.Rol;
import Dominio.Cancion;

import java.util.List;

import Dominio.Artista;

public class ControllerRecital {
	private RecitalRepository recitalRepository;

	public ControllerRecital() {
		this.recitalRepository = new RecitalRepository();
	}

	public void agregarRecital(Recital recital) {
		recitalRepository.addRecital(recital);
	}

	public void agregarArtistaBase(int recitalId, Artista artista) {
		recitalRepository.addArtistaBase(recitalId, artista);
	}

	public void agregarArtistaContratado(int recitalId, Artista artista) {
		recitalRepository.addArtistaContratado(recitalId, artista);
	}

	public void agregarCancion(int recitalId, Cancion cancion) {
		recitalRepository.addCancion(recitalId, cancion);
	}

	// Parte extra
	// solo 1 vale el id, porque es un solo recital
	public void entrenarArtista(String nombreArtista, Rol rolNuevo) throws Exception {
		int id = 1;
		try {
			Recital recital = recitalRepository.findById(id);
			recital.entrenarArtista(nombreArtista, rolNuevo);
		} catch (Exception e) {
			throw new Exception("El recital no ha sido encontrado en el repositorio");
		}
	}

	public void listarArtistasContratados() throws Exception {
		int id = 1;
		try {
			Recital recital = recitalRepository.findById(id);
			recital.listarArtistasContratados();
		} catch (Exception e) {
			throw new Exception("El recital no ha sido encontrado en el repositorio");
		}
	}

	public List<Artista> getArtistasContratados() throws Exception {
		List<Artista> artistas = null;
		try {
			Recital recital = recitalRepository.findById(1);
			artistas = recital.getArtistasContratados();
		} catch (Exception e) {
			throw new Exception("El recital no ha sido encontrado en el repositorio");
		}

		if(artistas.isEmpty()) {
			throw new Exception("No hay artistas contratados");
		}
		return artistas;
	}

	public Recital buscarXId(int id) throws Exception {
		try {
			Recital recital = this.recitalRepository.findById(id);
			return recital;
		} catch (Exception e) {
			throw new Exception("El recital no ha sido encontrado en el repositorio");
		}
	}

	public void verEstadoRecital() throws Exception {
		int id = 1;
		try {
			Recital recital = recitalRepository.findById(id);
			recital.verEstadoRecital();
		} catch (Exception e) {
			throw new Exception("El recital no ha sido encontrado en el repositorio");
		}
	}

	public List<Recital> getRecitales() {
		return recitalRepository.getListaRecitales();
	}
}