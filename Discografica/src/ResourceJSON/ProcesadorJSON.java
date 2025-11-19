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
import java.util.LinkedList;
import java.time.LocalDate;

import Dominio.Artista;
import Dominio.Recital;

public class ProcesadorJSON {

    public static Recital cargarRecital(String path) {

        try {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class,
                            (JsonDeserializer<LocalDate>) (json, type, context) -> LocalDate.parse(json.getAsString()))
                    .registerTypeAdapter(LocalDate.class,
                            (JsonSerializer<LocalDate>) (date, type, context) -> new JsonPrimitive(date.toString()))
                    .create();
            String absPath = absolutePath(path);
            FileReader reader = new FileReader(absPath);

            Recital recital = gson.fromJson(reader, Recital.class);

            reader.close();
            return recital;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Artista> cargarArtistas(String path) {
        try {
            Gson gson = new Gson();
            String absPath = absolutePath(path);
            FileReader reader = new FileReader(absPath);

            Type tipo = new TypeToken<List<Artista>>() {
            }.getType();

            List<Artista> artistas = gson.fromJson(reader, tipo);
            reader.close();

            return new LinkedList<>(artistas);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Artista> cargarArtistasBase(String path) {
        try {
            Gson gson = new GsonBuilder().create();
            String absPath = absolutePath(path);
            FileReader reader = new FileReader(absPath);

            Type tipo = new TypeToken<List<Artista>>() {
            }.getType();

            List<Artista> artistas = gson.fromJson(reader, tipo);

            reader.close();
            return new LinkedList<>(artistas);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String absolutePath(String relativePath) {
        String base = System.getProperty("user.dir");
        return base + "/" + relativePath;
    }

}
