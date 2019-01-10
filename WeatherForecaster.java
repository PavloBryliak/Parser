package parser;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;


class WeatherForecaster {

    void forecast(City city) throws UnirestException {
        String cityInfo = String.format("City %1$s\n" +
                        "Wiki: %2$s\n" +
                        "Administrative area: %3$s\n" +
                        "Number of citizens: %4$s\n" +
                        "Year of found: %5$s\n" +
                        "Area: %6$s\n" +
                        "Coordinates: %7$s\n", city.getName(), city.getUrl(), city.getAdministrativeArea(),
                city.getNumberOfCitizens(), city.getYearOfFound(), city.getArea(), city.getCoordinates());
        System.out.println(cityInfo); // represents the city

        if (city.getCoordinates() == null){ // means that we have all info about the city, except the coordinates
            System.out.println("No data");
            return;
        }

        JsonNode request = Unirest.post("http://api.apixu.com/v1/current.json?key=f6da3a783d34446f8" +
                "f4120423180410&q=" + city.getCoordinates().getLatitude() + "%20" +
                city.getCoordinates().getLongtitude()).asJson().getBody();
        JSONObject request_obj = request.getObject();
        System.out.println("CURRENT WEATHER");
        System.out.printf("Temperature in °C: %s\n" +
                        "Condition: %s\n" +
                        "Humidity: %s\n" +
                        "Last update: %s\n", request_obj.getJSONObject("current").getDouble("temp_c"),
                request_obj.getJSONObject("current").getJSONObject("condition").getString("text"),
                request_obj.getJSONObject("current").getDouble("humidity"),
                request_obj.getJSONObject("current").getString("last_updated"));
        System.out.println("Raw json response:"+request.toString()); //prints all available info from the request
    }
}
