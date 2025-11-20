package Servicio;

import java.util.LinkedList;
import java.util.List;

import Dominio.Artista;
import Dominio.Cancion;
import Dominio.Recital;
import Persistencia.ControllerArtista;
import Persistencia.ControllerCancion;
import Persistencia.ControllerRecital;
import Persistencia.ControllerContrato;
import ResourceJSON.ProcesadorJSON;

public class FileReader {
	private ControllerArtista controllerArtista;
	private ControllerCancion controllerCancion;
	private ControllerRecital controllerRecital;
	private ControllerContrato controllerContrato;

	public FileReader() {
		this.controllerArtista = new ControllerArtista();
		this.controllerCancion = new ControllerCancion();
		this.controllerRecital = new ControllerRecital();

		// this.controllerContrato = new ControllerContrato();
	}

	public ControllerArtista getControllerArtista() {
		return controllerArtista;
	}

	public ControllerCancion getControllerCancion() {
		return controllerCancion;
	}

	public ControllerRecital getControllerRecital() {
		return controllerRecital;
	}

	public ControllerContrato getControllerContrato() {
		return controllerContrato;
	}

	public void cargarLoteDePrueba() {

		// CARGA DE DATOS DE ARCHIVOS JSON
		List<Artista> artistas = ProcesadorJSON.cargarArtistas("src/ResourceJSON/artistas.json");
		List<Artista> artistasBases = ProcesadorJSON.cargarArtistasBase("src/ResourceJSON/artistas-incluidos.json");
		Recital recitalp = ProcesadorJSON.cargarRecital("src/ResourceJSON/recital.json");

		// GUARDAR EN CONTROLLLERS
		System.out.println(recitalp);
		System.out.println("\nArtistas:\n");
		for (Artista a : artistas) {
			this.controllerArtista.agregarArtista(a);
			System.out.println(a);
		}
		recitalp.setArtistasBase(artistasBases);
		this.controllerRecital.agregarRecital(recitalp);

		List<Artista> artistasCandidatos = new LinkedList<>(artistas);
		artistasCandidatos.removeAll(artistasBases);
		this.controllerContrato = new ControllerContrato(recitalp, artistasCandidatos);

		System.out.println("\nCanciones:\n");
		for (Cancion c : recitalp.getListaCanciones()) {
			System.out.println(c);
			this.controllerCancion.agregarCancion(c);
		}
	}
}
