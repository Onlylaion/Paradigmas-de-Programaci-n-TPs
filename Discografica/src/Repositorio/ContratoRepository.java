package Repositorio;

import java.util.ArrayList;
import java.util.List;

import Dominio.Contrato;

public class ContratoRepository {
    private int idContrato = 1;
    private List<Contrato> contratos;

    public ContratoRepository() {
        this.contratos = new ArrayList<Contrato>();
    }

    public void agregarContrato(Contrato contrato) {
        contrato.setIdContrato(idContrato++);
        this.contratos.add(contrato);
    }

    public void eliminarContrato(Contrato contrato) {
        contratos.remove(contrato);
    }

    public List<Contrato> obtenerContratos() {
        return contratos;
    }

    public Contrato findById(int idContrato) throws IndexOutOfBoundsException {
        if (idContrato > this.contratos.size() || idContrato < 1) {
            throw new IndexOutOfBoundsException("Indice incorrecto");
        }
        return this.contratos.get(idContrato - 1);
    }

    public Contrato findByCancionId(long idCancion) {
        for (Contrato contratoActual : contratos) {
            if (contratoActual.getCancion().getId() == idCancion) {
                return contratoActual;
            }
        }
        return null;
    }
}
