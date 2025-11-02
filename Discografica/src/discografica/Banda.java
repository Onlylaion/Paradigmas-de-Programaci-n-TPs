package discografica;

import java.util.ArrayList;
import java.util.List;

public class Banda implements Comparable<Banda>{
	long idBanda;
	String nombreBanda;
	List<Artista> artistasIntegrantes = new ArrayList<>();
	
	public Banda(long idBanda, String nombreBanda) {
		this.idBanda = idBanda;
		this.nombreBanda = nombreBanda;
	}
	
	public String getNombre() {
		return nombreBanda;
	}
	
	public void agregarArtista(Artista a) {
		artistasIntegrantes.add(a);
	}
	
	public List<Artista> getArtistasBanda(){
		return artistasIntegrantes;
	}
	
	public boolean esParteDeBanda(Artista artista) {
		if(artistasIntegrantes.contains(artista))
			return true;
		else
			return false;
	}
	
	public Artista buscarArtista(String nombre) {
		for(Artista a: artistasIntegrantes) {
			if(a.getNombre() == nombre)
				return a;
		}
		
		return null;
	}
	
	public void eliminarBanda(Banda banda) {
		
	}

	
	@Override
	public String toString() {
		return "Banda [idBanda=" + idBanda + ", nombreBanda=" + nombreBanda + ", artistasIntegrantes="
				+ artistasIntegrantes + "]";
	}

	@Override
	public int compareTo(Banda otra) {
		if(this.idBanda == otra.idBanda)
			return 0;
		else if(this.idBanda < otra.idBanda)
			return -1;	
		
		return 1;
	}
	
}