package Repositorio;

import java.util.ArrayList;
import java.util.List;

import Dominio.Cancion;

public class CancionRepository {
    private int id = 0;
    private List<Cancion> canciones;

    public CancionRepository() {
        this.canciones = new ArrayList<Cancion>();
    }

    public void addCancion(Cancion cancion) {
        id++;
        cancion.setIdCancion(id);
        this.canciones.add(cancion);
    }

    public void removeCancion(Cancion cancion) {
        this.canciones.remove(cancion);
    }

    public List<Cancion> getAllCanciones() {
        return this.canciones;
    }

    public void clearRepository() {
        this.canciones.clear();
        this.id = 0;
    }

    public Cancion findById(int id) throws IndexOutOfBoundsException {
        if (id > this.canciones.size() || id < 1) {
            throw new IndexOutOfBoundsException("Indice incorrecto");
        }
        return this.canciones.get(id - 1);
    }
}
