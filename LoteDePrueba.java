package Servicio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import Dominio.Artista;
import Dominio.Banda;
import Dominio.Cancion;
import Dominio.Recital;
import Dominio.Contrato;
import Dominio.Rol;
import Persistencia.ControllerArtista;
import Persistencia.ControllerCancion;
import Persistencia.ControllerRecital;
import Persistencia.ControllerContrato;
import ResourceJSON.ProcesadorJSON;

public class LoteDePrueba {
	private ControllerArtista controllerArtista;
	private ControllerCancion controllerCancion;
	private ControllerRecital controllerRecital;
	private ControllerContrato controllerContrato;

	public LoteDePrueba() {
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
		Set<Artista> artistas = ProcesadorJSON.cargarArtistas("src/ResourceJSON/artistas.json");
		Set<Artista> artistasBases = ProcesadorJSON.cargarArtistasBase("src/ResourceJSON/artistas-incluidos.json");
		Recital recitalp = ProcesadorJSON.cargarRecital("src/ResourceJSON/recital.json");

		// GUARDAR EN CONTROLLLERS
		System.out.println(recitalp);
		System.out.println("\nArtistas:\n");
		for (Artista a : artistas) {
			controllerArtista.agregarArtista(a);
			System.out.println(a);
		}
		controllerRecital.agregarRecital(recitalp);
		Set<Artista> artistasCandidatos = new HashSet<>(artistas);
		artistasCandidatos.removeAll(artistasBases);
		this.controllerContrato = new ControllerContrato(recitalp, artistasCandidatos);
		
		
		System.out.println("\n");
		for (Cancion c : recitalp.getListaCanciones()) {
			controllerCancion.agregarCancion(c);
			if (!c.puestosCubiertos()) {
				try {
					this.controllerContrato.contratarPorCancion(c);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}

				System.out.println(c);
				System.out.println("\n");
			}
			else
			{
				try {
					Set<Artista> artistasAContratar = new HashSet<>(c.getListArtAsignados());
					controllerContrato.contratarEstosArtistasParaCancion(artistasAContratar, c);
				}
				catch(Exception e)
				{
					System.out.println(e.getMessage());
				}
			}
		}
		for (Contrato c : this.controllerContrato.obtenerContratos()) {
			System.out.println(c);
		}
	}
}
