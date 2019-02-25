package ba.unsa.etf.rpr.project;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CountryTest {
    @Test
    public void countryTestOne(){
        Country country = new Country( 1, "test country", new Continent(1, "test continent") );
        assertEquals(1, country.getId());
        assertEquals(1, country.getContinent().getId());
        assertEquals("test country", country.getName());
        assertEquals("test continent", country.getContinent().getName());
    }

    @Test
    public void countryTestTwo(){
        Country country = new Country();
        country.setId(1);
        country.setName("test");
        country.setContinent( new Continent(1,"test") );
        assertEquals(1, country.getId());
        assertEquals(1, country.getContinent().getId());
        assertEquals("test",country.getName());
        assertEquals("test",country.getContinent().getName());
    }
}