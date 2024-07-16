import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class CurrencyConverter {

    private static final String API_KEY = "YOUR_API_KEY";
    private static final String API_URL = "https://api.exchangerate-api.com/v4/latest/";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Escolha a conversão de moeda:");
        System.out.println("1. USD para BRL");
        System.out.println("2. BRL para USD");
        System.out.println("3. EUR para BRL");
        System.out.println("4. BRL para EUR");
        System.out.println("5. GBP para BRL");
        System.out.println("6. BRL para GBP");

        int option = scanner.nextInt();
        System.out.print("Digite o valor para conversão: ");
        double amount = scanner.nextDouble();

        String fromCurrency = "";
        String toCurrency = "";

        switch (option) {
            case 1 -> { fromCurrency = "USD"; toCurrency = "BRL"; }
            case 2 -> { fromCurrency = "BRL"; toCurrency = "USD"; }
            case 3 -> { fromCurrency = "EUR"; toCurrency = "BRL"; }
            case 4 -> { fromCurrency = "BRL"; toCurrency = "EUR"; }
            case 5 -> { fromCurrency = "GBP"; toCurrency = "BRL"; }
            case 6 -> { fromCurrency = "BRL"; toCurrency = "GBP"; }
            default -> {
                System.out.println("Opção inválida.");
                System.exit(0);
            }
        }

        double convertedAmount = convertCurrency(fromCurrency, toCurrency, amount);
        if (convertedAmount != -1) {
            System.out.printf("O valor convertido é: %.2f %s\n", convertedAmount, toCurrency);
        }
    }

    private static double convertCurrency(String fromCurrency, String toCurrency, double amount) {
        try {
            URL url = new URL(API_URL + fromCurrency);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("apikey", API_KEY);

            InputStreamReader reader = new InputStreamReader(connection.getInputStream());
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
            double exchangeRate = jsonObject.getAsJsonObject("rates").get(toCurrency).getAsDouble();
            return amount * exchangeRate;
        } catch (Exception e) {
            System.out.println("Erro ao obter a taxa de câmbio: " + e.getMessage());
            return -1;
        }
    }
}
