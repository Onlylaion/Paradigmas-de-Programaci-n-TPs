package Persistencia;

import Repositorio.ArtistaRepository;

import Dominio.Artista;


public class ControllerArtista {
    private ArtistaRepository artistaRepository;

    public ControllerArtista() {
        this.artistaRepository = new ArtistaRepository();
    }

    public void agregarArtista(Artista artista) {
        artistaRepository.agregarArtista(artista);
    }

    public Artista findById(int id) throws Exception{
    	Artista artist = artistaRepository.findById(id);
    	if(artist == null) {
    		throw new Exception("No existe tal artista");
    	}else {
    		return artist;
    	}
    }
}