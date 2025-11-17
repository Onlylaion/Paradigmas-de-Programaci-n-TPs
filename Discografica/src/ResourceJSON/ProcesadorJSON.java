package ResourceJSON;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.GsonBuilder;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonPrimitive;

import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;
import java.util.LinkedHashSet;

import java.time.LocalDate;

import Dominio.Artista;
import Dominio.Recital;

public class ProcesadorJSON {

	public static Recital cargarRecital(String path) {
		
		try {
			Gson gson = new GsonBuilder()
					.registerTypeAdapter(LocalDate.class,(JsonDeserializer<LocalDate>) (json, type, context) ->LocalDate.parse(json.getAsString()))
					.registerTypeAdapter(LocalDate.class,(JsonSerializer<LocalDate>) (date, type, context) -> new JsonPrimitive(date.toString()))
					.create();
			FileReader reader = new FileReader(path);
			
			Recital recital = gson.fromJson(reader, Recital.class);
			
			reader.close();
			return recital;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
    public static Set<Artista> cargarArtistas(String path) {
        try {
            Gson gson = new Gson();
            FileReader reader = new FileReader(path);

            Type tipo = new TypeToken<List<Artista>>() {}.getType();

            List<Artista> artistas = gson.fromJson(reader, tipo);
            reader.close();

            return new LinkedHashSet<>(artistas);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    
    public static Set<Artista> cargarArtistasBase(String path) {
        try {
            Gson gson = new GsonBuilder().create();
            FileReader reader = new FileReader(path);

            Type tipo = new TypeToken<List<Artista>>(){}.getType();

            List<Artista> artistas = gson.fromJson(reader, tipo);

            reader.close();
            return new LinkedHashSet<>(artistas);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
    