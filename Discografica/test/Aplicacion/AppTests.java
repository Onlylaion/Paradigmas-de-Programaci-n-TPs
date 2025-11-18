package Aplicacion;

import Dominio.*;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AppTests {

    // Campos de las clases de test
    private Recital r1;
    private Cancion c1;
    private Cancion c2;
    private Cancion c3;
    private Cancion c4;
    private Artista a1;
    private Artista a2;
    private Artista a3;
    private Artista a4;
    private Artista a5;
    private Artista a6;
    private Banda b1;
    private Banda b2;
    private Banda b3;
    private Banda b4;
    private List<Artista> candidatos;

    @BeforeEach
    void setUp() throws Exception {
        HashMap<Rol, Integer> rolesCancion1 = new HashMap<>();
        HashMap<Rol, Integer> rolesCancion2 = new HashMap<>();
        HashMap<Rol, Integer> rolesCancion3 = new HashMap<>();
        rolesCancion1.put(Rol.VozPrincipal, 1);
        rolesCancion1.put(Rol.Guitarrista, 2);
        rolesCancion1.put(Rol.Bajista, 1);
        rolesCancion2.put(Rol.VozPrincipal, 1);
        rolesCancion2.put(Rol.Guitarrista, 1);
        rolesCancion2.put(Rol.Corista,1);
        rolesCancion3.put(Rol.VozPrincipal, 2);
        rolesCancion3.put(Rol.Tecladista, 3);

        b1 = new Banda("Rolling Stones");
        b2 = new Banda("ACDC");
        b3 = new Banda("Kiss");
        b4 = new Banda("No Doubt");

        ArrayList<Banda> bandas1 = new ArrayList<>();
        ArrayList<Banda> bandas2 = new ArrayList<>();
        bandas1.add(b1);
        bandas1.add(b2);

        ArrayList<Rol> rolesArtista1 = new ArrayList<>();
        ArrayList<Rol> rolesArtista2 = new ArrayList<>();
        rolesArtista1.add(Rol.Bajista);
        rolesArtista1.add(Rol.VozPrincipal);
        rolesArtista2.add(Rol.Tecladista);
        rolesArtista2.add(Rol.VozPrincipal);

        a1 = new Artista("Gardel", rolesArtista1, bandas1, 500, 5, 15);
        a2 = new Artista("Cerati", rolesArtista2, bandas2, 750, 7, 20);

        Set<Artista> base = new TreeSet<>();
        base.add(a1);
        base.add(a2);
        Set<Artista> contratados = new TreeSet<>();

        c1 = new Cancion("Take me on", 3, rolesCancion1);
        c1.ocuparRol(a1, Rol.Bajista);
        c2 = new Cancion("Amor Salvaje", 4, rolesCancion2);
        c2.ocuparRol(a2, Rol.VozPrincipal);
        c3 = new Cancion("Moscow Mule", 5, rolesCancion3);
        c3.ocuparRol(a1, Rol.VozPrincipal);
        c3.ocuparRol(a2, Rol.VozPrincipal);

        Set<Cancion> canciones = new TreeSet<>();
        canciones.add(c1);
        canciones.add(c2);
        canciones.add(c3);

        LocalDate fecha = LocalDate.parse("2025-12-15");

        r1 = new Recital(canciones, base, contratados, fecha);
    }

    @Test
    void rolesFaltantesParaCancion() throws Exception {
        HashMap<Rol, Integer> faltantesEstimado = new HashMap<>();
        faltantesEstimado.put(Rol.Guitarrista, 2);
        faltantesEstimado.put(Rol.VozPrincipal, 1);
        faltantesEstimado.put(Rol.Bajista, 0);

        HashMap<Rol, Integer> faltantesReal = (HashMap<Rol, Integer>) r1.getRolesFaltantesCancion(c1);

        assertEquals(faltantesEstimado, faltantesReal);
    }
    
    @Test
    void rolesFaltantesTotalRecital() throws Exception {
        HashMap<Rol, Integer> faltantesEstimado = new HashMap<>();
        faltantesEstimado.put(Rol.VozPrincipal, 1);
        faltantesEstimado.put(Rol.Guitarrista, 3);
        faltantesEstimado.put(Rol.Corista, 1);
        faltantesEstimado.put(Rol.Tecladista, 3);

        HashMap<Rol, Integer> faltantesReal = (HashMap<Rol, Integer>) r1.getRolesFaltantesTotal();

        assertEquals(faltantesEstimado, faltantesReal);
    }
    
    void setUpContratar() {
    	
    	HashMap<Rol, Integer> rolesCancion4 = new HashMap<>();
    	rolesCancion4.put(Rol.Baterista, 1);
    	rolesCancion4.put(Rol.Corista, 1);
    	rolesCancion4.put(Rol.VozPrincipal, 1);
    	
    	c4 = new Cancion("Luna", 2, rolesCancion4);
    	
    	ArrayList<Rol> rolesArtista3 = new ArrayList<>();
    	ArrayList<Rol> rolesArtista4 = new ArrayList<>();
    	ArrayList<Rol> rolesArtista5 = new ArrayList<>();
    	ArrayList<Rol> rolesArtista6 = new ArrayList<>();

    	rolesArtista3.add(Rol.Corista);
    	rolesArtista4.add(Rol.Baterista);
    	rolesArtista5.add(Rol.Corista);
    	rolesArtista6.add(Rol.VozPrincipal);
    	
    	ArrayList<Banda> bandas3 = new ArrayList<>();
        ArrayList<Banda> bandas4 = new ArrayList<>();
        ArrayList<Banda> bandas5 = new ArrayList<>();
        ArrayList<Banda> bandas6 = new ArrayList<>();
        bandas3.add(b2);
        bandas4.add(b1);
        bandas5.add(b3);
        bandas6.add(b4);
    	
    	
    	a3 = new Artista("Calamaro", rolesArtista3, bandas5, 500, 1, 5);
    	a4 = new Artista("Chano", rolesArtista4, bandas4, 1000, 4, 10);
    	a5 = new Artista("Adele", rolesArtista5, bandas3, 600, 3, 5);
    	a6= new Artista("Taylor", rolesArtista6, bandas6, 300, 5, 2);
    	
    	
    	candidatos = new ArrayList<>();
    	candidatos.add(a3);
    	candidatos.add(a4);
    	candidatos.add(a5);
    	candidatos.add(a6);
    }
    
    
    @Test
    void contratarArtistasParaCancion() throws Exception{
    	
    	setUpContratar();
    	
    	r1.agregarCancion(c4);
    	
    	Contrato contrato = new Contrato(r1, c4);
    	
    	contrato.contratoPorCancion(candidatos);
    	
    	//Quedarian :
    	//Adele como corista: le gana a Calamaro por el descuento al compartir banda ($300)
    	//Cerati como voz principal: primer tenido en cuenta, por ser el primer base ($750).
    	//Chano como baterista: es el único, tiene descuento por compartir banda ($500).
    	
    	HashMap<Artista, Rol> asignadosEstimado = new HashMap<>();
    	asignadosEstimado.put(a2, Rol.VozPrincipal);
    	asignadosEstimado.put(a5, Rol.Corista);
    	asignadosEstimado.put(a4, Rol.Baterista);

    	HashMap<Artista, Rol> asignadosReal = (HashMap<Artista, Rol>) contrato.getArtistasAsignados();
    	
    	
    	assertEquals(1550, contrato.getCostoTotal());
    	assertEquals(asignadosEstimado, asignadosReal);

    }
    
    @Test
    void entrenarArtista() throws Exception {
    	r1.entrenarArtista("Gardel", Rol.Baterista);
    	
    	Artista aux = r1.buscarArtistaPorNombre("Gardel");
    	
    	HashSet<Rol> entrenadosEstimado = new HashSet<>();
    	entrenadosEstimado.add(Rol.Baterista);
    	
    	HashSet<Rol> entrenadosReal = (HashSet<Rol>) aux.getRolesEntrenados();
    	
    	assertEquals(entrenadosEstimado, entrenadosReal);
    	assertEquals(1, aux.cantidadEntrenamientos());
    	assertEquals(aux.getCosto(), aux.getCostoCancionBase() + aux.getCostoCancionBase()* 0.5);
    	
    }
    
    
}
