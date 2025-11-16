package Persistencia;

import Repositorio.ArtistaRepository;

import java.util.ArrayList;
import java.util.List;

import Dominio.Artista;
import Dominio.Banda;
import Dominio.Rol;

public class ControllerArtista {
    private ArtistaRepository artistaRepository;

    public ControllerArtista() {
        this.artistaRepository = new ArtistaRepository();
    }

    public void agregarArtista(Artista artista) {
        artistaRepository.agregarArtista(artista);
    }

    public Artista findById(int id) {
        return artistaRepository.findById(id);
    }

    public void loteDePrueba() {
        // Crear artistas de la banda base (Queen)
		List<Rol> rolesBrian = new ArrayList<>();
		rolesBrian.add(Rol.Guitarrista);
		rolesBrian.add(Rol.VozPrincipal);
		List<Banda> bandasBrian = new ArrayList<>();
		bandasBrian.add(new Banda("Queen", new ArrayList<>()));
		Artista brian = new Artista("Brian May", rolesBrian, bandasBrian, 0, 100);

		List<Rol> rolesRoger = new ArrayList<>();
		rolesRoger.add(Rol.Baterista);
		List<Banda> bandasRoger = new ArrayList<>();
		bandasRoger.add(new Banda("Queen", new ArrayList<>()));
		Artista roger = new Artista("Roger Taylor", rolesRoger, bandasRoger, 0, 100);

		List<Rol> rolesJohn = new ArrayList<>();
		rolesJohn.add(Rol.Bajista);
		List<Banda> bandasJohn = new ArrayList<>();
		bandasJohn.add(new Banda("Queen", new ArrayList<>()));
		Artista john = new Artista("John Deacon", rolesJohn, bandasJohn, 0, 100);

		artistaRepository.agregarArtista(brian);
		artistaRepository.agregarArtista(roger);
		artistaRepository.agregarArtista(john);
    
        // Crear artistas candidatos para contratar
		List<Rol> rolesDavid = new ArrayList<>();
		rolesDavid.add(Rol.VozPrincipal);
		List<Banda> bandasDavid = new ArrayList<>();
		bandasDavid.add(new Banda("David Bowie", new ArrayList<>()));
		Artista david = new Artista("David Bowie", rolesDavid, bandasDavid, 1500, 2);

		List<Rol> rolesElton = new ArrayList<>();
		rolesElton.add(Rol.VozPrincipal);
		rolesElton.add(Rol.Tecladista);
		List<Banda> bandasElton = new ArrayList<>();
		bandasElton.add(new Banda("Elton John", new ArrayList<>()));
		Artista elton = new Artista("Elton John", rolesElton, bandasElton, 1200, 2);

		List<Rol> rolesAnnie = new ArrayList<>();
		rolesAnnie.add(Rol.VozPrincipal);
		List<Banda> bandasAnnie = new ArrayList<>();
		bandasAnnie.add(new Banda("Eurythmics", new ArrayList<>()));
		Artista annie = new Artista("Annie Lennox", rolesAnnie, bandasAnnie, 900, 2);

		artistaRepository.agregarArtista(david);
		artistaRepository.agregarArtista(elton);
		artistaRepository.agregarArtista(annie);

    }
}