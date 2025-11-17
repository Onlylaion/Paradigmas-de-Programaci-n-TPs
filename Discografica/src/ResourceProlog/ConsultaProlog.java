package ResourceProlog;

import Dominio.Artista;
import Dominio.Cancion;
import Dominio.Rol;
import Dominio.Recital;

import org.jpl7.Query;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;


public class ConsultaProlog {
	private String archivoPath;
	
	public ConsultaProlog(String path)
	{
		this.archivoPath = path;
	}
	
//	private String rolToProlog(Rol rol) {
//	    return rol.name().toLowerCase();  
//	}
	
//	private String listaCancionesToProlog(Set<Cancion> canciones) {
//		return "[" + canciones.stream()
//		.map(c -> cancionToProlog(c))
//		.collect(Collectors.joining(", "))
//		+ "]";
//	}
	
//	private String cancionToProlog(Cancion c) {		
//		String roles = c.consultarRolesFaltantes().stream()
//				.map(r -> String.format("(%s)", r.name().toLowerCase()))
//				.collect(Collectors.joining(", "));
//		return String.format("cancion('%s', [%s])", c.getNombreCancion(), roles);
//	}
	
	private String artistaToProlog(Artista a) {

	    String roles = a.getRolesHistoricos().stream()
	            .map(r -> r.name().toLowerCase())
	            .collect(Collectors.joining(", "));

	    return String.format(
	        "artista('%s', [%s], %f)",
	        a.getNombre(),
	        roles,
	        a.getCosto()
	    );
	}
	
	private String listaArtistasToProlog(Set<Artista> artistas) {
	    return "[" + artistas.stream()
	            .map(a -> artistaToProlog(a))
	            .collect(Collectors.joining(", "))
	            + "]";
	}
	
	private String rolesTotalesToProlog(Set<Cancion> canciones) {

	    List<String> roles = new ArrayList<>();

	    for (Cancion c : canciones) {
	        for (Rol entry : c.consultarRolesFaltantes( )) {
	                roles.add(entry.name().toLowerCase());
	        }
	    }

	    return "[" + String.join(", ", roles) + "]";
	}
	

	public void ConsultarEntrenamientosMinimos(Recital r)
	{
		Query load = new Query("consult('" +this.archivoPath  +"')");
		load.hasSolution();
		System.out.println(r.getArtistasBase());
		System.out.println(r.getListaCanciones());
		
		String artistas = listaArtistasToProlog(r.getArtistasBase());
		String roles = rolesTotalesToProlog(r.getListaCanciones());

		String consulta = String.format(
		    "entrenamientos_necesarios(%s, %s, E)",
		    artistas, roles
		);
		
		Query q = new Query(consulta);

		int entrenamientos = q.oneSolution().get("E").intValue();
		System.out.println("Entrenamientos mínimos: " +  entrenamientos);
	}
	
	@Override
	public String toString()
	{
		return "{"+ "RUTA_ARCHIVO_PROLOG= "+this.archivoPath +"}";
	}
}
