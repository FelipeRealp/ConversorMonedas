
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        Scanner scanner = new Scanner(System.in);
        int opcion;
        String monedaBase = "";
        String monedaAConvertir = "";
        List<String> listaHistoricoConversiones = new ArrayList<>();

        String APIKEY = "039bc7e4e26afbbbf8db5407";
        String menu = """
                ****************************
                Bienvenid@ al conversor de monedas
                1. USD -> ARG
                2. ARG -> USD
                3. USD -> COP
                4. COP -> USD
                5. USD -> BRL
                6. BRL -> USD
                7. Salir         
                *****************************
                Digita una opcion:""";
        while(true){
            System.out.println(menu);
            opcion = Integer.parseInt(scanner.nextLine());
            if (opcion == 7){
                break;
            }
            switch (opcion){
                case 1:
                    monedaBase = "USD";
                    monedaAConvertir = "ARS";
                break;
                case 2:
                    monedaBase = "ARS";
                    monedaAConvertir = "USD";
                break;
                case 3:
                    monedaBase = "USD";
                    monedaAConvertir = "COP";
                    break;
                case 4:
                    monedaBase = "COP";
                    monedaAConvertir = "USD";
                    break;
                case 5:
                    monedaBase = "USD";
                    monedaAConvertir = "BRL";
                    break;
                case 6:
                    monedaBase = "BRL";
                    monedaAConvertir = "USD";
                    break;
            }
            String URL = "https://v6.exchangerate-api.com/v6/"+ APIKEY +"/latest/"+ monedaBase;

            System.out.print("Ingrese la cantidad: ");

            double cantidadAConvertir = Double.parseDouble(scanner.nextLine());

            HttpClient client = HttpClient.newHttpClient();
    
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL))
                    .build();
            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
    
            String json = response.body();
    
            JsonObject objeto = JsonParser.parseString(json).getAsJsonObject();
    
            JsonObject tasasDeCambio = objeto.getAsJsonObject("conversion_rates");
    
            double tasaMonedaAConvertir = tasasDeCambio.get(monedaAConvertir).getAsDouble();

            double resultadoConversion = cantidadAConvertir * tasaMonedaAConvertir;

            String resultadoFormateado = String.format("%.2f [%s] -> %.2f [%s]",
                    cantidadAConvertir, monedaBase, resultadoConversion, monedaAConvertir);

            listaHistoricoConversiones.add(resultadoFormateado);

            System.out.println(resultadoFormateado);

        }

        System.out.println("Conversiones realizadas");
        for (String conversion : listaHistoricoConversiones){
            System.out.println(conversion);
        }

    }
}
