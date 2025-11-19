package Repositorio;

import java.util.List;
import java.util.ArrayList;

import Dominio.Artista;

public class ArtistaRepository {
    private int nextId = 1;
    private List<Artista> artistas;

    public ArtistaRepository() {
        this.artistas = new ArrayList<>();
    }

    public void agregarArtista(Artista artista) {
        artista.setId(nextId++);
        artistas.add(artista);
    }

    public List<Artista> getArtistas() {
        return artistas;
    }

    public Artista findById(int id) throws IndexOutOfBoundsException {
        if (id > this.artistas.size() || id < 1) {
            throw new IndexOutOfBoundsException("Indice incorrecto");
        }
        return this.artistas.get(id - 1);
    }

    public Artista findByNombre(String nombre) {
        for (Artista artista : artistas) {
            if (artista.getNombre().equalsIgnoreCase(nombre)) {
                return artista;
            }
        }
        return null;
    }
}
