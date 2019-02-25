package ba.unsa.etf.rpr.project;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class LocationTest  {
    @Test
    public void locationTestOne(){
        Location location = new Location(1,"test","test",null);
        assertEquals(1, location.getId());
        assertEquals("test", location.getPostalCode());
        assertEquals("test", location.getStreetAddress());
        assertNull( location.getCity() );
    }

    @Test
    public void locationTestTwo(){
        Location location = new Location();
        location.setId(1);
        location.setPostalCode("test");
        location.setStreetAddress("test");
        location.setCity(null);
        assertEquals(1, location.getId());
        assertEquals("test", location.getPostalCode());
        assertEquals("test", location.getStreetAddress());
        assertNull( location.getCity() );
    }
}