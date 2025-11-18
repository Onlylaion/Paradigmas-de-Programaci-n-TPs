package Persistencia;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

import Repositorio.CancionRepository;

import Dominio.Cancion;
import Dominio.Rol;

public class ControllerCancion {
    CancionRepository cancionRepo;

    public ControllerCancion() {
        this.cancionRepo = new CancionRepository();
    }

    public void agregarCancion(Cancion cancion) {
        cancionRepo.addCancion(cancion);
    }

    public void eliminarCancion(Cancion cancion) {
        cancionRepo.removeCancion(cancion);
    }

    public Cancion findById(int id) {
        return cancionRepo.findById(id);
    }

    public Set<Cancion> obtenerCanciones() {
        return cancionRepo.getAllCanciones();
    }

    public Set<Cancion> obtenerCancionesConPuestosFaltantes() throws Exception {
        Set<Cancion> cancionesConPuestosFaltantes = new HashSet<Cancion>();

        for (Cancion cancion : cancionRepo.getAllCanciones()) {
			if (!cancion.puestosCubiertos()) {
				cancionesConPuestosFaltantes.add(cancion);
			}
		}

        if (cancionesConPuestosFaltantes.isEmpty()) {
            throw new Exception("Todas las canciones tienen los puestos cubiertos!");
        }

        return cancionesConPuestosFaltantes;
    }

    public Map<Rol, Integer> getRolesFaltantes(Cancion cancion) throws Exception {
        if (cancion.puestosCubiertos()) {
            throw new Exception("Todos los roles estan cubiertos!");
        } else {
            Map<Rol, Integer> rolesFaltantes = new HashMap<Rol, Integer>();
            for (Map.Entry<Rol, Integer> entry : cancion.getMapRoles().entrySet()) {
                if (entry.getValue() > 0) {
                    rolesFaltantes.put(entry.getKey(), entry.getValue());
                }
            }
            return rolesFaltantes;
        }
    }

    public Map<Rol, Integer> getRolesFaltantesTotal() throws Exception {
        Map<Rol, Integer> rolesFaltantesTotal = new HashMap<Rol, Integer>();

        for (Cancion cancionActual : cancionRepo.getAllCanciones()) {
            if(cancionActual.puestosCubiertos()) {
                continue;
            } else {
                for (Map.Entry<Rol, Integer> entry : cancionActual.getMapRoles().entrySet()) {
                    if (entry.getValue() > 0) {
                        rolesFaltantesTotal.put(entry.getKey(), rolesFaltantesTotal.getOrDefault(entry.getKey(), 0) + entry.getValue());
                    }
                }
            }
        }

        if(rolesFaltantesTotal.isEmpty()) {
            throw new Exception("Todos los roles estan cubiertos!");
        }

        return rolesFaltantesTotal;
    }
}
