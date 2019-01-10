package parser;
import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.Scanner;

public class Main {

    @SneakyThrows
    public static void main(String[] args) {
        String url = "https://uk.wikipedia.org/wiki/%D0%9C%D1%96%D1%81%D1%82%D0%B0_%D0%A3%D0%BA%D1%80%D0%B0%D1%97%D0%BD%D0%B8_(%D0%B7%D0%B0_%D0%B0%D0%BB%D1%84%D0%B0%D0%B2%D1%96%D1%82%D0%BE%D0%BC)";
        Document document = Jsoup.connect(url).get();
        Elements cities = document.select("table tr");
        City[] parsedCities = new City[cities.size()];
        int i = 0;
        for (Element city : cities) {
            City myCity = City.parse(city);
            System.out.println(myCity);
            if (myCity != null) { parsedCities[i] = myCity; i++; }
        }

        WeatherForecaster weatherForecaster = new WeatherForecaster();
        System.out.println("Your city name: ");
        Scanner input = new Scanner(System.in);
        String cityName = input.nextLine();
        for (City city : parsedCities) {
            if (city != null && cityName.equals(city.getName())) {
                weatherForecaster.forecast(city); }
        }

    }
}

