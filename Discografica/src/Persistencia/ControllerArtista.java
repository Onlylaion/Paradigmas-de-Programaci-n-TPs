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

    public Artista findById(int id) {
        return artistaRepository.findById(id);
    }
}