package Persistencia;

import Repositorio.RecitalRepository;

import Dominio.Recital;
import Dominio.Rol;
import Dominio.Cancion;

import java.util.Set;

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
		// Traer del repositorio el recital, y entrenarlo
		int id = 1;
		Recital recital = recitalRepository.findById(id);
		if (recital == null) {
			throw new Exception("El recital no ha sido encontrado en el repositorio");
		} else {
			recital.entrenarArtista(nombreArtista, rolNuevo);
		}

	}

	public void listarArtistasContratados() throws Exception {
		int id = 1;
		Recital recital = recitalRepository.findById(id);
		if (recital == null) {
			throw new Exception("El recital no ha sido encontrado en el repositorio");
		} else {
			recital.listarArtistasContratados();
		}
	}

	public void listarCancionesConEstado() throws Exception {
		int id = 1;
		Recital recital = recitalRepository.findById(id);
		if (recital == null) {
			throw new Exception("El recital no ha sido encontrado en el repositorio");
		} else {
			recital.listarCancionesConEstado();
		}
	}

	public Set<Artista> getArtistasContratados() throws Exception {
		int id = 1;
		Recital recital = recitalRepository.findById(id);
		if (recital == null) {
			throw new Exception("El recital no ha sido encontrado en el repositorio");
		} else {
			return recital.getArtistasContratados();
		}
	}
	
	public Recital buscarXId(int id)throws Exception{
		Recital recital = this.recitalRepository.findById(id);
		if (recital == null) {
			throw new Exception("El recital no ha sido encontrado en el repositorio");
		} else {
			return recital;
		}
	}

}
