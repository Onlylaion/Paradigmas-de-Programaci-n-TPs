package Persistencia;

import Repositorio.ArtistaRepository;

import java.util.List;

import Dominio.Artista;
import Dominio.Rol;

public class ControllerArtista {
    private ArtistaRepository artistaRepository;

    public ControllerArtista() {
        this.artistaRepository = new ArtistaRepository();
    }

    public void agregarArtista(Artista artista) {
        artistaRepository.agregarArtista(artista);
    }

    public Artista findById(int id) throws Exception {
        try {
            Artista artist = artistaRepository.findById(id);
            return artist;
        } catch (Exception e) {
            throw new Exception("No existe tal artista");
        }
    }

    public List<Artista> obtenerTodosArtistas() {
        return artistaRepository.getArtistas();
    }

    public void entrenarArtista(String nombre, Rol rol) throws Exception {
        Artista artista = artistaRepository.findByNombre(nombre);
        if (artista == null) {
            throw new Exception("Artista no encontrado: " + nombre);
        }
        artista.agregarRol(rol);
    }
}