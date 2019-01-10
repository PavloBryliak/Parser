package parser;
//TODO Create class that represents coordinates
public class Coordinates {
    private String latitude;
    private String longtitude;


    Coordinates(String latitude, String longtitude){ this.latitude = latitude; this.longtitude = longtitude; }
    String getLatitude(){ return this.latitude; }
    String getLongtitude(){ return this.longtitude; }

    @Override
    public String toString(){ return this.longtitude+";"+this.latitude;}
}