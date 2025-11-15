package concierto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class App {

	public static void main(String[] args) throws Exception {
		System.out.println("        SISTEMA DE CONTRATACIÓN DE ARTISTAS - PRUEBAS");
		// Crear bandas
		System.out.println("1️CREANDO BANDAS\n");

		List<Artista> artistasQueen = new ArrayList<>();
		Banda queen = new Banda("Queen", artistasQueen);

		List<Artista> artistasBowie = new ArrayList<>();
		Banda bowieBand = new Banda("Bowie Band", artistasBowie);

		System.out.println("Banda Queen creada");
		System.out.println("Banda Bowie Band creada\n");

		// Crear artistas base
		System.out.println("2️ CREANDO ARTISTAS BASE (Queen)\n");

		List<Rol> rolesBrian = new ArrayList<>();
		rolesBrian.add(Rol.Guitarrista);
		rolesBrian.add(Rol.VozPrincipal);
		List<Banda> bandasBrian = new ArrayList<>();
		bandasBrian.add(queen);
		Artista brian = new Artista("Brian May", rolesBrian, bandasBrian, 0, 100);

		List<Rol> rolesRoger = new ArrayList<>();
		rolesRoger.add(Rol.Baterista);
		List<Banda> bandasRoger = new ArrayList<>();
		bandasRoger.add(queen);
		Artista roger = new Artista("Roger Taylor", rolesRoger, bandasRoger, 0, 100);

		List<Rol> rolesJohn = new ArrayList<>();
		rolesJohn.add(Rol.Bajista);
		List<Banda> bandasJohn = new ArrayList<>();
		bandasJohn.add(queen);
		Artista john = new Artista("John Deacon", rolesJohn, bandasJohn, 0, 100);

		System.out.println(brian);
		System.out.println(roger);
		System.out.println(john);
		System.out.println();

		// Crear artista contratado
		System.out.println("3️⃣  CREANDO ARTISTA CONTRATADO\n");

		List<Rol> rolesDavid = new ArrayList<>();
		rolesDavid.add(Rol.VozPrincipal);
		List<Banda> bandasDavid = new ArrayList<>();
		bandasDavid.add(bowieBand);
		Artista david = new Artista("David Bowie", rolesDavid, bandasDavid, 1500, 2);

		System.out.println(" " + david);
		System.out.println("  Costo base: $" + david.getCostoCancionBase());
		System.out.println();

		// Comparti banda
		System.out.println("4️⃣  PRUEBA 1: ¿Comparten banda?\n");

		System.out.println("¿Brian y Roger comparten banda? " + brian.compartioBandaCon(roger));
		System.out.println("¿Brian y David comparten banda? " + brian.compartioBandaCon(david));
		System.out.println();

		// Aplicar descuento
		System.out.println("5️ PRUEBA 2: Aplicar descuento\n");

		System.out.println("Costo de David ANTES: $" + String.format("%.2f", david.getCosto()));
		david.aplicarDescuento();
		System.out.println("Costo de David DESPUÉS: $" + String.format("%.2f", david.getCosto()));
		System.out.println();

		// Entrenar artista
		System.out.println("6️  PRUEBA 3: Entrenar artista\n");

		List<Rol> rolesLisa = new ArrayList<>();
		rolesLisa.add(Rol.VozPrincipal);
		List<Banda> bandasLisa = new ArrayList<>();
		Artista lisa = new Artista("Lisa Stansfield", rolesLisa, bandasLisa, 800, 2);

		System.out.println("Costo de Lisa ANTES: $" + String.format("%.2f", lisa.getCosto()));
		lisa.entrenar(Rol.Tecladista);
		System.out.println("Costo de Lisa DESPUÉS: $" + String.format("%.2f", lisa.getCosto()));
		System.out.println();

		// Crear canción
		System.out.println("7️  PRUEBA 4: Crear canción\n");

		Map<Rol, Integer> rolesCancion = new HashMap<>();
		rolesCancion.put(Rol.VozPrincipal, 1);
		rolesCancion.put(Rol.Guitarrista, 1);
		rolesCancion.put(Rol.Bajista, 1);
		rolesCancion.put(Rol.Baterista, 1);

		Cancion cancion = new Cancion("We Will Rock You", 3.5, rolesCancion);

		System.out.println(" Canción: " + cancion.getNombreCancion());
		System.out.println("  Duración: " + cancion.getDuracion() + " minutos");
		System.out.println("  ¿Está completa? " + cancion.puestosCubiertos());
		System.out.println();

		// Asignar artistas
		System.out.println("8  PRUEBA 5: Asignar artistas a canción\n");

		System.out.println("¿Se asignó a Brian? " + cancion.ocuparRol(brian));
		System.out.println("¿Se asignó a Roger? " + cancion.ocuparRol(roger));
		System.out.println("¿Se asignó a John? " + cancion.ocuparRol(john));
		System.out.println("¿Se asignó a David? " + cancion.ocuparRol(david));
		System.out.println();

		// Verificar estado
		System.out.println("9  PRUEBA 6: Verificar estado\n");

		System.out.println("Artistas asignados: " + cancion.getListArtAsignados().size());
		System.out.println("¿Está completa? " + cancion.puestosCubiertos());
		System.out.println("Roles faltantes: " + cancion.consultarRolesFaltantes().size());

		if (cancion.puestosCubiertos()) {
			System.out.println("¡La canción está lista!");
		}
		System.out.println();

		// Intenta asignar repetido
		System.out.println("10 PRUEBA 7: Intenta asignar artista repetido\n");

		try {
			cancion.ocuparRol(brian);
		} catch (Exception e) {
			System.out.println("✓ Error capturado: " + e.getMessage());
		}
		System.out.println();

		// Error entrenar contratado
		System.out.println("1️1  PRUEBA 8: Intenta entrenar artista contratado\n");

		david.asignarACancion();

		try {
			david.entrenar(Rol.Tecladista);
		} catch (IllegalArgumentException e) {
			System.out.println("✓ Error capturado: " + e.getMessage());
		}
		System.out.println();

		// Desasignar artista
		System.out.println("1️2  PRUEBA 9: Desasignar artista\n");

		System.out.println("Antes: " + cancion.getListArtAsignados().size() + " artistas");

		try {
			cancion.descuparRol(roger);
			System.out.println(" Roger desasignado");
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}

		System.out.println("Después: " + cancion.getListArtAsignados().size() + " artistas");
		System.out.println();

		// Información final
		System.out.println("1️3  PRUEBA 10: Información final\n");

		System.out.println("Artistas en la canción:");
		for (Artista artista : cancion.getListArtAsignados()) {
			System.out.println("  - " + artista.getNombre() + " | $" + String.format("%.2f", artista.getCosto()));
		}

		System.out.println("PRUEBAS COMPLETADAS");
	}
}
