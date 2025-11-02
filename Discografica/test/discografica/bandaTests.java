package discografica;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class bandaTests {

	@Test
	void artistaEsParteDeBanda() {
		Artista a1 = new Artista(1, "Juan");
		Artista a2 = new Artista(2, "Ana");
		Banda b = new Banda(1, "ACDC");
		
		b.agregarArtista(a1);
		b.agregarArtista(a2);
		
		assertTrue(b.esParteDeBanda(a1));
	}
	
	@Test
	void artistaNoEsParteDeBanda() {
		Artista a1 = new Artista(1, "Juan");
		Artista a2 = new Artista(2, "Ana");
		Artista a3 = new Artista(3, "Fede");
		Banda b = new Banda(1, "ACDC");
		
		b.agregarArtista(a1);
		b.agregarArtista(a2);
		
		assertFalse(b.esParteDeBanda(a3));
	}
	
	@Test
	void artistaEncontrado() {
		String nombre = "Flor";
		Artista a1 = new Artista(1, "Juan");
		Artista a2 = new Artista(2, nombre);
		Banda b = new Banda(1, "ACDC");
		
		b.agregarArtista(a1);
		b.agregarArtista(a2);
		
		assertEquals(b.buscarArtista(nombre), a2);
	}
	
	@Test
	void artistaNoEncontrado() {
		String nombre = "Flor";
		Artista a1 = new Artista(1, "Juan");
		Artista a2 = new Artista(2, "Ana");
		Banda b = new Banda(1, "ACDC");
		
		b.agregarArtista(a1);
		b.agregarArtista(a2);
		
		assertEquals(b.buscarArtista(nombre), null);
	}

}
