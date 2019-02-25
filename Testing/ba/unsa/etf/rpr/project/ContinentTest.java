package ba.unsa.etf.rpr.project;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ContinentTest {
    @Test
    public void testContinentOne(){
        Continent continent = new Continent(1,"test");
        assertEquals(1, continent.getId());
        assertEquals("test",continent.getName());
    }

    @Test
    public void testContinentTwo(){
        Continent continent = new Continent();
        continent.setId(1);
        continent.setName("test");
        assertEquals(1,continent.getId());
        assertEquals("test",continent.getName());
        continent.setName("another test");
        assertEquals("another test",continent.getName());
        continent.setId(3);
        assertEquals(3,continent.getId());
    }
}