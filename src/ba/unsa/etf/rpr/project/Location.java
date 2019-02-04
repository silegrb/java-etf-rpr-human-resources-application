package ba.unsa.etf.rpr.project;

import java.util.Objects;

public class Location {


    private int id;
    private String postalCode;
    private String streetAddress;
    private City city;

    public Location( int id, String postalCode, String streetAdress, City city ) {
        this.id = id;
        this.postalCode = postalCode;
        this.streetAddress = streetAdress;
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

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAdress) {
        this.streetAddress = streetAdress;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return id == location.id &&
                Objects.equals(postalCode, location.postalCode) &&
                Objects.equals(streetAddress, location.streetAddress) &&
                Objects.equals(city, location.city);
    }
}
