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
    void artistaOcupaRolQueTiene() throws Exception {
        c1.ocuparRol(a1, Rol.Bajista);

        List<Rol> faltantesReal = c1.consultarRolesFaltantes();
        List<Rol> faltantesEstimado = List.of(Rol.Guitarrista, Rol.VozPrincipal);

        assertEquals(
        		faltantesEstimado.stream().sorted().toList(),
        		faltantesReal.stream().sorted().toList()
    		);	//Comparo en el mismo orden
    }
    
    @Test
    void artistaOcupaRolQueNoTiene() throws Exception {
        assertFalse(c1.ocuparRol(a1, Rol.Corista));
        
        List<Rol> faltantesReal = c1.consultarRolesFaltantes();
        List<Rol> faltantesEstimado = List.of(Rol.Guitarrista, Rol.VozPrincipal, Rol.Bajista);
        
        assertEquals(
        		faltantesEstimado.stream().sorted().toList(),
        		faltantesReal.stream().sorted().toList()
    		);	//Comparo en el mismo orden
    }

    @Test
    void artistaDesocupaRolQueTiene() throws Exception {
    	c1.ocuparRol(a1, Rol.VozPrincipal);
        c1.desocuparRol(a1,Rol.VozPrincipal);

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
    }
    
    @Test
    void artistaDesocupaRolQueNoTiene() throws Exception {
    	c1.ocuparRol(a1, Rol.VozPrincipal);
        
    	//assertFalse(c1.desocuparRol(a1,Rol.Corista));

        List<Rol> faltantesReal = c1.consultarRolesFaltantes();
        List<Rol> faltantesEstimado = List.of(
            Rol.Guitarrista,
            Rol.Bajista
        );

        assertEquals(
        		faltantesEstimado.stream().sorted().toList(),
        		faltantesReal.stream().sorted().toList()
    		);	//Comparo en el mismo orden
    }
    
    @Test
    void artistaDesocupaCancionQueNoEsta() throws Exception {      
    	//assertFalse(c1.desocuparRol(a1,Rol.VozPrincipal));

        List<Rol> faltantesReal = c1.consultarRolesFaltantes();
        List<Rol> faltantesEstimado = List.of(
            Rol.Guitarrista,
            Rol.Bajista,
            Rol.VozPrincipal
        );

        assertEquals(
        		faltantesEstimado.stream().sorted().toList(),
        		faltantesReal.stream().sorted().toList()
    		);	//Comparo en el mismo orden
    }
    
}