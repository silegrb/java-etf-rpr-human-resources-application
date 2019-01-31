package ba.unsa.etf.rpr.project;

public class Location {


    private int id;
    private String postalCode;
    private String streetAdress;
    private City city;

    public Location( int id, String postalCode, String streetAdress, City city ) {
        this.id = id;
        this.postalCode = postalCode;
        this.streetAdress = streetAdress;
        this.city = city;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getStreetAdress() {
        return streetAdress;
    }

    public void setStreetAdress(String streetAdress) {
        this.streetAdress = streetAdress;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

}
