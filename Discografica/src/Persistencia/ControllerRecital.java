package Persistencia;

import Repositorio.RecitalRepository;

import Dominio.Recital;
import Dominio.Cancion;

import Dominio.Artista;

public class ControllerRecital {
    private RecitalRepository recitalRepository;
    
    public ControllerRecital() {
        this.recitalRepository = new RecitalRepository();
    }

    public void agregarRecital(Recital recital) {
        recitalRepository.addRecital(recital);
    }

    public void agregarArtistaBase(int recitalId, Artista artista) {
        recitalRepository.addArtistaBase(recitalId, artista);
    }

    public void agregarArtistaContratado(int recitalId, Artista artista) {
        recitalRepository.addArtistaContratado(recitalId, artista);
    }

    public void agregarCancion(int recitalId, Cancion cancion) {
        recitalRepository.addCancion(recitalId, cancion);
    }
}
