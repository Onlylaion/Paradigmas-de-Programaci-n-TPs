package Persistencia;

import Repositorio.ContratoRepository;

import java.util.List;
import java.util.Map;
import java.util.Set;

import java.util.HashMap;

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

    public List<Contrato> obtenerContratos() {
        return contratoRepository.obtenerContratos();
    }

    public double contratarPorCancion(Cancion cancion) throws Exception {
        Contrato nuevoContrato = new Contrato(obtenerRecital(), obtenerArtistasCandidatos());
        if(nuevoContrato.contratoPorCancion(cancion)) {
            return nuevoContrato.getCostoTotal();
        } else {
            throw new Exception("No se pudo concretar el contrato para la cancion seleccionada.");
        }
    }

    public double contratoTodasCanciones(Set<Cancion> canciones) throws Exception {
        double costoFinal = 0.0;
        for(Cancion c : canciones) {
            costoFinal += this.contratarPorCancion(c);
        }
        return costoFinal;
    }

    public Map<Cancion, List<Artista>> obtenerArtistasContratadosPorCancion(Cancion cancion) throws Exception {
        List<Contrato> contratos = contratoRepository.obtenerContratos();
        if(contratos == null || contratos.isEmpty()) {
            throw new Exception("Contratos no realizados.");
        }

        for (Contrato contrato : contratos) {
            for (Map.Entry<Cancion, List<Artista>> entry : contrato.getAsignacionesPorCancion().entrySet()) {
                if (entry.getKey().getId() == cancion.getId()) {
                    return Map.of(entry.getKey(), entry.getValue());
                }
            }
        }
        throw new Exception("No se encontraron artistas contratados para la cancion con ID: " + cancion.getId());
    }

    public Map<Cancion, List<Artista>> obtenerTodosArtistasContratadosPorCancion() throws Exception {
        List<Contrato> contratos = contratoRepository.obtenerContratos();
        if(contratos == null || contratos.isEmpty()) {
            throw new Exception("Contratos no realizados.");
        }

        Map<Cancion, List<Artista>> asignacionesPorCancion = new HashMap<>();
        for (Contrato contrato : contratos) {
            asignacionesPorCancion.putAll(contrato.getAsignacionesPorCancion());
        }
        return asignacionesPorCancion;
    }
}
