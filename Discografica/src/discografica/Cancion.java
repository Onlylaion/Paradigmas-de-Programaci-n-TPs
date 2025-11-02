package discografica;


import java.util.Map;
import java.util.HashMap;
//import java.util.ArrayList;
//import java.util.List;

public class Cancion {
	private long idCancion;
	private String nombreCancion;
	private double duracion;
	//List<ContratacionArtista>listArtAsignados= new ArrayList<>();
	Map<Rol,Integer> mapRoles = new HashMap<>();
	
	private Cancion(long idCancion,String nombreCancion,double duracion) {
		this.idCancion=idCancion;
		this.nombreCancion=nombreCancion;
		this.duracion=duracion;
	};
	public static Cancion agregarCancion(long idCancion,String nombreCancion,double duracion) {
		if(duracion<0);
		if(idCancion<0);
		return new Cancion(idCancion,nombreCancion,duracion);
	}
	//para que deje compilar noma
	@Override
	public String toString() {
		return "id = "+ this.idCancion + "\nNombre = " + this.nombreCancion + "\nDuracion" + this.duracion;
	};
	public void ocuparRol() {};
	public void desocuparRol() {};
	public void consultarRolesCubiertos() {};
	public void consultarRolesFaltantes() {};
}
