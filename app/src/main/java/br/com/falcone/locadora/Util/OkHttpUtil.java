package br.com.falcone.locadora.Util;

import android.content.Context;
import android.location.Location;
import android.net.ConnectivityManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import br.com.falcone.locadora.model.Bem;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Alfredo on 12/08/2017.
 */

public class OkHttpUtil {

    //public static final String URL_SERVICO = "https://newsapi.org/v1/articles?source=national-geographic&sortBy=top&apiKey=c213b48804674275b759eb25f82c2a3c";
    public static final String URL_SERVICO = "http://api.openweathermap.org/data/2.5/weather?lat=%.2f&lon=%.2f&appid=28fe37b6966dc1a90cd10825e168a8ab";
    //public static final String URL_BASE_SERVICO = "https://newsapi.org/v1/";
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public static boolean IsConnected(Context contexto){
        ConnectivityManager manager = (ConnectivityManager)contexto.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(manager.getActiveNetworkInfo() != null && manager.getActiveNetworkInfo().isConnected())
            return true;

        return false;
    }



    public static double CalcularPercentualAjustePrecoJSON(String textoJSON){
        double retorno = 1;
        try {
            JSONArray array = new JSONObject(textoJSON).getJSONArray("weather");
            JSONObject objetoTempo = (JSONObject) array.get(0);

            int idTempo = objetoTempo.getInt("id");
            if(idTempo < 800 || idTempo >= 900){
                // https://openweathermap.org/weather-conditions
                // faixa de id 800 representa tempo firme (limpo ou com nuvens)
                // Se o tempo nao estiver firme, da desconto de 30% no preço
                retorno = 0.7;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return retorno;
    }


    public static double CalcularPercentualAjustePreco(Location location) {
        OkHttpClient cliente = new OkHttpClient();
        //RequestBody body = RequestBody.create(JSON, "");
        double retorno = 1;
        try {
            Response resposta = cliente.newCall(new Request.Builder().url(String.format(Locale.ROOT, URL_SERVICO, location.getLatitude(), location.getLongitude())).build()).execute();
            retorno = CalcularPercentualAjustePrecoJSON(resposta.body().string());
            resposta.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return retorno;
    }



}
