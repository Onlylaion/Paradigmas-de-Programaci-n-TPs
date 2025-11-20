package Repositorio;

import java.util.ArrayList;
import java.util.List;

import Dominio.Recital;
import Dominio.Artista;
import Dominio.Cancion;

public class RecitalRepository {
    private int nextId;
    private List<Recital> recitales;

    public RecitalRepository() {
        this.nextId = 1;
        this.recitales = new ArrayList<>();
    }

    public void addRecital(Recital recital) {
        recital.setId(nextId);
        nextId++;
        recitales.add(recital);
    }

    public void eliminarRecital(Recital recital) {
        recitales.remove(recital);
    }

    public void addArtistaBase(int recitalId, Artista artista) {
        Recital recital = findById(recitalId);
        if (recital != null) {
            recital.agregarArtistaBase(artista);
        }
    }

    public void addArtistaContratado(int recitalId, Artista artista) {
        Recital recital = findById(recitalId);
        if (recital != null) {
            recital.agregarArtistaContrato(artista);
        }
    }

    public void addCancion(int recitalId, Cancion cancion) {
        Recital recital = findById(recitalId);
        if (recital != null) {
            recital.agregarCancion(cancion);
        }
    }

    public Recital findById(int id) throws IndexOutOfBoundsException {
        if (id > this.recitales.size() || id < 1) {
            throw new IndexOutOfBoundsException("Indice incorrecto");
        }
        return this.recitales.get(id - 1);
    }

    public List<Recital> getListaRecitales() {
        return this.recitales;
    }
}