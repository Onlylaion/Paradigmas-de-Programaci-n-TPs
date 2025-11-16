package Persistencia;

import Repositorio.ContratoRepository;

import java.util.List;

import Dominio.Contrato;
import Dominio.Cancion;
import Dominio.Artista;
import Dominio.Recital;

public class ControllerContrato {
    private ContratoRepository contratoRepository;

    public ControllerContrato() {
        this.contratoRepository = new ContratoRepository();
    }

    public void agregarContrato(Contrato contrato) {
        contratoRepository.agregarContrato(contrato);
    } 

    public void eliminarContrato(Contrato contrato) {
        contratoRepository.eliminarContrato(contrato);
    }

    public List<Artista> obtenerArtistasCandidatos() throws Exception {
        Contrato contrato = contratoRepository.findById(1);
        if(contrato == null) {
            throw new Exception("Contrato no encontrado.");
        }
        return contrato.getArtistasCandidatos();
    }

    public Recital obtenerRecital() throws Exception {
        Contrato contrato = contratoRepository.findById(1);
        if(contrato == null) {
            throw new Exception("Contrato no encontrado.");
        }
        return contrato.getRecital();
    }

    public double contratarPorCancion(Cancion cancion) throws Exception {
        Contrato nuevoContrato = new Contrato(obtenerRecital(), obtenerArtistasCandidatos());
        if(nuevoContrato.contratoPorCancion(cancion)) {
            return nuevoContrato.getCostoTotal();
        } else {
            throw new Exception("No se pudo concretar el contrato para la cancion seleccionada.");
        }
    }
}
