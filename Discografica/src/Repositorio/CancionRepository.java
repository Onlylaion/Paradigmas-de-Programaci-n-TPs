package Repositorio;

import java.util.HashSet;
import java.util.Set;

import Dominio.Cancion;

public class CancionRepository {
    private int id = 0;
    private Set<Cancion> canciones;

    public CancionRepository() {
        this.canciones = new HashSet<Cancion>();
    }

    public void addCancion(Cancion cancion) {
        id++;
        cancion.setIdCancion(id);
        this.canciones.add(cancion);
    }

    public void removeCancion(Cancion cancion) {
        this.canciones.remove(cancion);
    }

    public Set<Cancion> getAllCanciones() {
        return this.canciones;
    }

    public void clearRepository() {
        this.canciones.clear();
        this.id = 0;
    }

    public Cancion findById(int id) {
        for (Cancion cancionActual : this.canciones) {
            if (cancionActual.getId() == id) {
                return cancionActual;
            }
        }
        return null;
    }
}
