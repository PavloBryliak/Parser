package parser;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;

@Getter
@Setter
@ToString

public class City {
    private String name;
    private String url;
    private String administrativeArea;
    private int numberOfCitizens;
    private String yearOfFound;
    private Coordinates coordinates; // Set this
    private double area;

    private static final int INFO_SIZE = 6;

    public static City parse(Element city) throws IOException {
        Elements info = city.select("td");
        if (info.size() != INFO_SIZE) return null; //return null if there is no info about the city


        City myCity = new City();

        Element anchor = info.get(1).select("a").get(0);
        myCity.setName(anchor.attr("title"));
        myCity.setUrl(String.format("https://uk.wikipedia.org%s", anchor.attr("href")));

        anchor = info.get(2).select("a").get(0);
        myCity.setAdministrativeArea(anchor.attr("title"));

        anchor = info.get(3);
        myCity.setNumberOfCitizens(Integer.parseInt(anchor.text().split("!")[0]
                .replaceAll("\\s","").replaceAll("&nbsp;","")
                .replaceAll(String.valueOf((char) 160), "")));

        anchor = info.get(4).select("a").get(0);
        myCity.setYearOfFound(anchor.attr("title"));

        anchor = info.get(5);
        myCity.setArea(Double.parseDouble(anchor.text()));

        Document doc = Jsoup.connect(myCity.url).timeout(0).get();
        Element cords = doc.select("span.geo").first();
        if (cords == null){ myCity.setCoordinates(null); }else{
            myCity.setCoordinates(new Coordinates(cords.text().split("; ")[0],cords.text().split("; ")[1]));
        }

        return myCity;
    }
}
