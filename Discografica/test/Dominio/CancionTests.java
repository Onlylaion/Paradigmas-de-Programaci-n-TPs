package Dominio;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CancionTests {

    private Cancion c1;
    private Artista a1;

    @BeforeEach
    void setUp() {
        HashMap<Rol, Integer> rolesCancion = new HashMap<>();
        rolesCancion.put(Rol.VozPrincipal, 1);
        rolesCancion.put(Rol.Guitarrista, 1);
        rolesCancion.put(Rol.Bajista, 1);

        Banda b1 = new Banda("Rolling Stones");
        Banda b2 = new Banda("ACDC");

        ArrayList<Banda> bandas = new ArrayList<>();
        bandas.add(b1);
        bandas.add(b2);

        ArrayList<Rol> rolesArtista = new ArrayList<>();
        rolesArtista.add(Rol.VozPrincipal);
        rolesArtista.add(Rol.Bajista);

        a1 = new Artista("Gardel", rolesArtista, bandas, 500, 5, 15);
        c1 = new Cancion("Take me on", 3, rolesCancion);
    }

    @Test
    void artistaOcupaPrimerRol() throws Exception {
        //c1.ocuparRol(a1);

        List<Rol> faltantesReal = c1.consultarRolesFaltantes();
        List<Rol> faltantesEstimado = List.of(Rol.Guitarrista, Rol.Bajista);

        assertEquals(faltantesEstimado, faltantesReal);
    }

    /*@Test
    void artistaDesocupaPrimerRol() throws Exception {
        //c1.ocuparRol(a1);
        c1.desocuparRol(a1);

        List<Rol> faltantesReal = c1.consultarRolesFaltantes();
        List<Rol> faltantesEstimado = List.of(
        	Rol.VozPrincipal,
            Rol.Guitarrista,
            Rol.Bajista
        );

        assertEquals(
        		faltantesEstimado.stream().sorted().toList(),
        		faltantesReal.stream().sorted().toList()
    		);	//Comparo en el mismo orden
    }*/
}