package ba.unsa.etf.rpr.project;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AdministratorTest {
    @Test
    public void administratorTestOne(){
        Administrator administrator = new Administrator(1,"admin","admin");
        assertEquals(1,administrator.getId());
        assertEquals("admin",administrator.getUsername());
        assertEquals("admin",administrator.getPassword());
        administrator.setId(2);
        administrator.setUsername("ADMIN");
        administrator.setPassword("ADMIN");
        assertEquals(2,administrator.getId());
        assertEquals("ADMIN",administrator.getUsername());
        assertEquals("ADMIN",administrator.getPassword());
    }

    @Test
    public void administratorTestTwo(){
        Administrator administrator = new Administrator();
        administrator.setId(1);
        administrator.setUsername("ADMIN");
        administrator.setPassword("ADMIN");
        assertEquals(1,administrator.getId());
        assertEquals("ADMIN",administrator.getUsername());
        assertEquals("ADMIN",administrator.getPassword());
    }
}