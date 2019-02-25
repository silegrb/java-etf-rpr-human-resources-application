package ba.unsa.etf.rpr.project;

import java.util.Objects;

public class City {

    private int id;
    private String name;
    private Country country;

    public City( int id, String name, Country country ) {
        this.id = id;
        this.name = name;
        this.country = country;
    }

    public City() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return id == city.id &&
                Objects.equals(name, city.name) &&
                Objects.equals(country, city.country);
    }
}
