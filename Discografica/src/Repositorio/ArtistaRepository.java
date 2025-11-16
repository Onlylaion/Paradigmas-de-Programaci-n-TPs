package Repositorio;

import java.util.Set;
import java.util.HashSet;

import Dominio.Artista;

public class ArtistaRepository {
    private int nextId = 1;
    private Set<Artista> artistas;

    public ArtistaRepository() {
        this.artistas = new HashSet<>();
    }

    public void agregarArtista(Artista artista) {
        artista.setId(nextId++);
        artistas.add(artista);
    }

    public Set<Artista> getArtistas() {
        return artistas;
    }

    public Artista findById(int id) {
        for (Artista artista : artistas) {
            if (artista.getId() == id) {
                return artista;
            }
        }
        return null;
    }
    
}
