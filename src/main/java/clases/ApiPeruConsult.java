/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

/**
 *
 * @author gerenciatecnica
 */
public class ApiPeruConsult {

    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/36.0.1985.125 Safari/537.36";

    public static String getJSONRUC(String ruc) {

        StringBuffer response = null;

        try {
            //Generar la URL
            //String url = SERVER_PATH + "consultas_json/composer/consulta_sunat_JMP.php?ruc=" + ruc;
            String url = "https://chimbote.store/apis/peru-consult/public/consultaRUC.php?ruc=" + ruc;
            //Creamos un nuevo objeto URL con la url donde pedir el JSON
            URL obj = new URL(url);
            //Creamos un objeto de conexión
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            //Añadimos la cabecera
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", USER_AGENT);
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
            // Enviamos la petición por POST
            con.setDoOutput(true);
            //Capturamos la respuesta del servidor
            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);

            //if (responseCode != 200) {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            //Mostramos la respuesta del servidor por consola
            System.out.println("Respuesta del servidor: " + response);
            //cerramos la conexión
            in.close();
            // }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response.toString();
    }

    public static String getJSONDNI(String dni) {

        StringBuffer response = null;

        try {
            //Generar la URL
            //String url = SERVER_PATH + "consultas_json/composer/consultas_dni_JMP.php?dni=" + dni;
            String url = "https://www.goempresarial.com/apis/peru-consult/public/consultaDNI.php?dni=" + dni;
            //Creamos un nuevo objeto URL con la url donde pedir el JSON
            URL obj = new URL(url);
            //Creamos un objeto de conexión
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            //Añadimos la cabecera
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", USER_AGENT);
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
            // Enviamos la petición por POST
            con.setDoOutput(true);
            //Capturamos la respuesta del servidor
            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'POST' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);

            //if (responseCode != 200) {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            //Mostramos la respuesta del servidor por consola
            System.out.println("Respuesta del servidor: " + response);
            //cerramos la conexión
            in.close();
            // }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response.toString();
    }

    public static String[] showJSONRUC(String json) throws ParseException {
        String[] datos = new String[4];
        System.out.println("INFORMACIÓN OBTENIDA DE LA BASE DE DATOS:");

        JSONParser Jparser = new JSONParser();
        JSONObject result = (JSONObject) Jparser.parse(json); //jsonObject
        //boolean estatus = (Boolean) jsonObject.get("success");

        datos[0] = result.get("razonSocial").toString();
        datos[1] = result.get("direccion").toString();
        datos[2] = result.get("condicion").toString();
        datos[3] = result.get("estado").toString();
        return datos;
    }

    public static ArrayList showJSONDNI(String json) throws ParseException {
        ArrayList datos = new ArrayList(3);
        System.out.println("INFORMACIÓN OBTENIDA DE LA BASE DE DATOS:");

        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        JsonElement obj = parser.parse(json);
        JsonObject gsonObj = obj.getAsJsonObject();
        System.out.println(gsonObj.get("apellidoPaterno"));
        System.out.println(gsonObj.get("apellidoMaterno"));
        System.out.println(gsonObj.get("nombres"));
        
        datos.add(gsonObj.get("apellidoPaterno").getAsString());
        datos.add(gsonObj.get("apellidoMaterno").getAsString());
        datos.add(gsonObj.get("nombres").getAsString());

        return datos;
    }
}
