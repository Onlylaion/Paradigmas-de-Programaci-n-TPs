package Servicio;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import Dominio.Artista;
import Dominio.Banda;
import Dominio.Cancion;
import Dominio.Recital;
import Dominio.Rol;
import Persistencia.ControllerArtista;
import Persistencia.ControllerCancion;
import Persistencia.ControllerRecital;
import Persistencia.ControllerContrato;

public class LoteDePrueba {
    private ControllerArtista controllerArtista;
    private ControllerCancion controllerCancion;
    private ControllerRecital controllerRecital;
    private ControllerContrato controllerContrato;

    public LoteDePrueba() {
        this.controllerArtista = new ControllerArtista();
        this.controllerCancion = new ControllerCancion();
        this.controllerRecital = new ControllerRecital();
		
        //this.controllerContrato = new ControllerContrato();
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
        Set<Artista> artistasBase = new HashSet<>();

		List<Rol> rolesBrian = new ArrayList<>();
		rolesBrian.add(Rol.Guitarrista);
		rolesBrian.add(Rol.VozPrincipal);
		List<Banda> bandasBrian = new ArrayList<>();
		bandasBrian.add(new Banda("Queen"));
		Artista brian = new Artista("Brian May", rolesBrian, bandasBrian, 0, 100, 120);

		List<Rol> rolesRoger = new ArrayList<>();
		rolesRoger.add(Rol.Baterista);
		List<Banda> bandasRoger = new ArrayList<>();
		bandasRoger.add(new Banda("Queen"));
		Artista roger = new Artista("Roger Taylor", rolesRoger, bandasRoger, 0, 100, 100);

		List<Rol> rolesJohn = new ArrayList<>();
		rolesJohn.add(Rol.Bajista);
		List<Banda> bandasJohn = new ArrayList<>();
		bandasJohn.add(new Banda("Queen"));
		Artista john = new Artista("John Deacon", rolesJohn, bandasJohn, 0, 100, 60);

		artistasBase.add(brian);
		artistasBase.add(roger);
		artistasBase.add(john);

		controllerArtista.agregarArtista(brian);
		controllerArtista.agregarArtista(roger);
		controllerArtista.agregarArtista(john);
    
        // Crear artistas candidatos para contratar
        List<Artista> artistasCandidatos = new ArrayList<>();

        List<Rol> rolesDavid = new ArrayList<>();
		rolesDavid.add(Rol.VozPrincipal);
		List<Banda> bandasDavid = new ArrayList<>();
		bandasDavid.add(new Banda("David Bowie"));
		Artista david = new Artista("David Bowie", rolesDavid, bandasDavid, 1500, 2, 30);

		List<Rol> rolesElton = new ArrayList<>();
		rolesElton.add(Rol.VozPrincipal);
		rolesElton.add(Rol.Tecladista);
		List<Banda> bandasElton = new ArrayList<>();
		bandasElton.add(new Banda("Elton John"));
		Artista elton = new Artista("Elton John", rolesElton, bandasElton, 1200, 2, 60);

		List<Rol> rolesAnnie = new ArrayList<>();
		rolesAnnie.add(Rol.VozPrincipal);
		List<Banda> bandasAnnie = new ArrayList<>();
		bandasAnnie.add(new Banda("Eurythmics"));
		Artista annie = new Artista("Annie Lennox", rolesAnnie, bandasAnnie, 900, 2, 20);

		artistasCandidatos.add(david);
		artistasCandidatos.add(elton);
		artistasCandidatos.add(annie);

		controllerArtista.agregarArtista(david);
		controllerArtista.agregarArtista(elton);
		controllerArtista.agregarArtista(annie);

        Set<Cancion> canciones = new HashSet<>();

		Map<Rol, Integer> rolesWillRock = new HashMap<>();
		rolesWillRock.put(Rol.VozPrincipal, 1);
		rolesWillRock.put(Rol.Guitarrista, 1);
		rolesWillRock.put(Rol.Bajista, 1);
		rolesWillRock.put(Rol.Baterista, 1);
		Cancion willRock = new Cancion("We Will Rock You", 3.5, rolesWillRock);

		Map<Rol, Integer> rolesSomeone = new HashMap<>();
		rolesSomeone.put(Rol.VozPrincipal, 1);
		rolesSomeone.put(Rol.Guitarrista, 1);
		rolesSomeone.put(Rol.Bajista, 1);
		rolesSomeone.put(Rol.Baterista, 1);
		rolesSomeone.put(Rol.Tecladista, 1);
		Cancion someone = new Cancion("Somebody to Love", 4.2, rolesSomeone);

		Map<Rol, Integer> rolesDays = new HashMap<>();
		rolesDays.put(Rol.VozPrincipal, 2);
		rolesDays.put(Rol.Guitarrista, 1);
		rolesDays.put(Rol.Bajista, 1);
		rolesDays.put(Rol.Baterista, 1);
		Cancion days = new Cancion("These Are the Days of Our Lives", 5.0, rolesDays);

		canciones.add(willRock);
		canciones.add(someone);
		canciones.add(days);

        controllerCancion.agregarCancion(willRock);
        controllerCancion.agregarCancion(someone);
        controllerCancion.agregarCancion(days);

		// Crear recital
		Recital recital = new Recital(canciones, artistasBase, new HashSet<>(), new Date());
        controllerRecital.agregarRecital(recital);

        this.controllerContrato = new ControllerContrato(recital, artistasCandidatos);
		/*Contrato contrato = new Contrato(recital, artistasCandidatos);
        controllerContrato.agregarContrato(contrato);*/
    }
}
