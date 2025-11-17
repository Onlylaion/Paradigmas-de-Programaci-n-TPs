package ResourceJSON;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.List;


import Dominio.Artista;
import Dominio.Recital;

public class ProcesadorJSON {

    public static List<Artista> cargarArtistas(String path) {
        try {
            Gson gson = new Gson();
            FileReader reader = new FileReader(path);

            Type tipoLista = new TypeToken<List<Artista>>() {}.getType();

            List<Artista> lista = gson.fromJson(reader, tipoLista);
            reader.close();

            return lista;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static Recital cargarRecital(String path) {

         try {
            Gson gson = new GsonBuilder().create();
            FileReader reader = new FileReader(path);

            Recital recital = gson.fromJson(reader, Recital.class);

            reader.close();
            return recital;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static List<Artista> cargarArtistasBase(String path) {
        try {
            Gson gson = new GsonBuilder().create();
            FileReader reader = new FileReader(path);

            Type listType = new TypeToken<List<Artista>>(){}.getType();

            List<Artista> artistas = gson.fromJson(reader, listType);

            reader.close();
            return artistas;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
    