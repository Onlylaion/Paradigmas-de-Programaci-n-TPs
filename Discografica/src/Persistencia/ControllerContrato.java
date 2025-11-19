package Persistencia;

import Repositorio.ContratoRepository;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import java.util.HashMap;
import java.util.LinkedList;

import Dominio.Contrato;
import Dominio.Cancion;
import Dominio.Artista;
import Dominio.Recital;
import Dominio.Rol;

public class ControllerContrato {
    private ContratoRepository contratoRepository;
    private Recital recital;
    private List<Artista> artistasCandidatos;

    public ControllerContrato(Recital recital, List<Artista> artistasCandidatos) {
        this.contratoRepository = new ContratoRepository();
        this.recital = recital;
        this.artistasCandidatos = artistasCandidatos;
    }

    public void agregarContrato(Contrato contrato) {
        contratoRepository.agregarContrato(contrato);
    }

    public void eliminarContrato(Contrato contrato) {
        contratoRepository.eliminarContrato(contrato);
    }

    public List<Contrato> obtenerContratos() {
        return contratoRepository.obtenerContratos();
    }

    public double contratarPorCancion(Cancion cancion) throws Exception {
        Contrato nuevoContrato = contratoRepository.findByCancionId(cancion.getId());
        if (nuevoContrato == null) {
            nuevoContrato = new Contrato(recital, cancion);
            if (nuevoContrato.contratoPorCancion(artistasCandidatos)) {
                contratoRepository.agregarContrato(nuevoContrato);
                return nuevoContrato.getCostoTotal();
            } else {
                throw new Exception("No se pudo concretar el contrato para la cancion seleccionada.");
            }
        } else if (!cancion.puestosCubiertos()) {
            if (nuevoContrato.contratoPorCancion(artistasCandidatos)) {
                return nuevoContrato.getCostoTotal();
            } else {
                throw new Exception("No se pudo concretar el contrato para la cancion seleccionada.");
            }
        } else {
            throw new Exception("Ya existe un contrato para la cancion seleccionada.");
        }

    }

    public double contratoTodasCanciones(List<Cancion> canciones) {
        double costoFinal = 0.0;
        for (Cancion c : canciones) {
            try {
                costoFinal += this.contratarPorCancion(c);
            } catch (Exception e) {
                System.out.println("No se pudo concretar el contrato para todas las canciones: " + e.getMessage());
            }
        }
        return costoFinal;
    }

    public List<Artista> obtenerArtistasContratadosPorCancion(Cancion cancion) throws Exception {
        List<Contrato> contratos = contratoRepository.obtenerContratos();
        if (contratos == null || contratos.isEmpty()) {
            throw new Exception("Contratos no realizados.");
        }

        for (Contrato contrato : contratos) {
            if (contrato.getCancion().getId() == cancion.getId()) {
                List<Artista> artistasAsignados = new ArrayList<>(contrato.getArtistasAsignados().keySet());
                return artistasAsignados;
            }
        }
        throw new Exception("No se encontraron artistas contratados para la cancion con ID: " + cancion.getId());
    }

    public Map<Artista, Rol> obtenerArtistasYRolContratadosPorCancion(Cancion cancion) throws Exception {
        List<Contrato> contratos = contratoRepository.obtenerContratos();
        if (contratos == null || contratos.isEmpty()) {
            throw new Exception("Contratos no realizados.");
        }

        for (Contrato contrato : contratos) {
            if (contrato.getCancion().getId() == cancion.getId()) {
                return contrato.getArtistasAsignados();
            }
        }
        throw new Exception("No se encontraron artistas contratados para la cancion con ID: " + cancion.getId());
    }

    public Map<Cancion, List<Artista>> obtenerTodosArtistasContratadosPorCancion() throws Exception {
        List<Contrato> contratos = contratoRepository.obtenerContratos();
        if (contratos == null || contratos.isEmpty()) {
            throw new Exception("Contratos no realizados.");
        }

        Map<Cancion, List<Artista>> asignacionesPorCancion = new HashMap<>();
        for (Contrato contrato : contratos) {
            List<Artista> artistasAsignados = new ArrayList<>(contrato.getArtistasAsignados().keySet());
            asignacionesPorCancion.put(contrato.getCancion(), artistasAsignados);
        }
        return asignacionesPorCancion;
    }

    public List<Artista> obtenerTodosArtistasNoContratados() {
        List<Artista> artistasNoContratados = new LinkedList<>(artistasCandidatos);
        artistasNoContratados.removeAll(recital.getArtistasContratados());
        return artistasNoContratados;
    }

    public void desasignarContrato(Artista artista) {
        List<Contrato> contratos = null;
        try {
            contratos = this.contratoRepository.obtenerContratos();
        } catch (Exception e) {
            System.out.println("No hay contratos realizados.");
            return;
        }

        for (Contrato contrato : contratos) {
            try {
                contrato.desasignarContrato(artista);

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void listarContratoYCancionesConEstado() {
        List<Contrato> contratos = this.contratoRepository.obtenerContratos();
        recital.listarCancionesConEstado(contratos);
    }
}
