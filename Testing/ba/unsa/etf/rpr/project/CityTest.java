package ba.unsa.etf.rpr.project;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CityTest {
    @Test
    public void cityTestOne() {
        Continent continent = new Continent(1,"Europe");
        Country country = new Country(1,"Bosnia and Herzegovina", continent);
        City city = new City( 1, "Sarajevo",country );
        assertEquals( 1, city.getId() );
        assertEquals( 1, country.getId() );
        assertEquals( 1, continent.getId() );
        assertEquals( "Sarajevo", city.getName() );
        assertEquals( "Bosnia and Herzegovina", city.getCountry().getName() );
        assertEquals( "Europe", city.getCountry().getContinent().getName() );
    }
    @Test
    public void cityTestTwo(){
        City city = new City();
        city.setCountry( new Country(1, "test",new Continent(1,"test")) );
        city.setName( "test" );
        city.setId(-123);
        assertEquals(-123, city.getId());
        assertEquals("test",city.getName());
        assertEquals("test",city.getCountry().getName());
        assertEquals("test",city.getCountry().getContinent().getName());
    }
}