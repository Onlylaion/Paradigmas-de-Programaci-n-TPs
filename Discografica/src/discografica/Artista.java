package discografica;

public class Artista implements Comparable<Artista>{
	long idArtista;
	String nombre;
	
	public Artista(long idArtista, String nombre) {
		this.idArtista = idArtista;
		this.nombre = nombre;
	}
	
	public String getNombre() {
		return nombre;
	}

	
	@Override
	public String toString() {
		return "Artista [idArtista=" + idArtista + ", nombre=" + nombre + "]";
	}

	@Override
	public int compareTo(Artista otro) {
		if(this.idArtista == otro.idArtista)
			return 0;
		else if(this.idArtista < otro.idArtista)
			return -1;
		
		return 1;
	}
	
}