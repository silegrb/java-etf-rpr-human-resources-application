package ba.unsa.etf.rpr.project;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class DepartmentTest {
    @Test
    public void departmentTestOne(){
        Department department = new Department(1, "test dept",new Location(1,"123","test address",null),null);
        assertEquals(1, department.getId());
        assertEquals("test dept", department.getName());
        assertNull( department.getManager() );
        assertNull( department.getLocation().getCity() );
        assertEquals( "123", department.getLocation().getPostalCode() );
        assertEquals( "test address", department.getLocation().getStreetAddress() );
    }

    @Test
    public void departmentTestTwo(){
        Department department = new Department();
        department.setId( 1 );
        assertEquals(1, department.getId());
        department.setName("test");
        assertEquals("test", department.getName());
        department.setLocation(null);
        assertNull( department.getLocation() );
        department.setManager(null);
        assertNull( department.getManager() );
    }
}