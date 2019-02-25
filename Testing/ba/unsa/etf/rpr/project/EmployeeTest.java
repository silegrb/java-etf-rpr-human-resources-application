package ba.unsa.etf.rpr.project;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {
    @Test
    public void employeeTestOne(){
        Employee employee = new Employee(1,"first","last","parent", LocalDate.now(),"123","061/111-111","a@b.c","000",1000,"photo",null,null,null,null);
        assertEquals( 1,employee.getId() );
        assertEquals( "first",employee.getFirstName() );
        assertNotEquals("last",employee.getFirstName());
        assertEquals("last",employee.getLastName());
        assertEquals("parent",employee.getParentName());
        assertEquals("123",employee.getUmcn());
        assertEquals("061/111-111", employee.getMobileNumber());
        assertEquals("a@b.c",employee.getEmailAddress());
        assertEquals("000",employee.getCreditCard());
        assertEquals(1000, employee.getSalary());
        assertEquals("photo", employee.getPhoto());
        assertNull( employee.getLocation() );
        assertNull( employee.getJob() );
        assertNull( employee.getDepartment() );
        assertNull( employee.getManager() );
    }

    @Test
    public void employeeTestTwo(){
        Employee employee = new Employee();
        employee.setId(1);
        employee.setFirstName("test");
        employee.setLastName("test");
        employee.setParentName("test");
        employee.setUmcn("test");
        employee.setCreditCard("test");
        employee.setEmailAddress("test");
        employee.setBirthDate(LocalDate.now());
        employee.setPhoto("test");
        employee.setMobileNumber("test");
        employee.setSalary(100);
        employee.setDepartment(null);
        employee.setJob(null);
        employee.setManager(null);
        employee.setLocation(null);
        assertEquals(1, employee.getId());
        assertEquals("test", employee.getFirstName());
        assertEquals("test", employee.getLastName());
        assertEquals("test", employee.getParentName());
        assertEquals("test", employee.getCreditCard());
        assertEquals("test", employee.getEmailAddress());
        assertEquals("test", employee.getUmcn());
        assertEquals("test", employee.getPhoto());
        assertEquals(100, employee.getSalary());
        assertNull(employee.getJob());
        assertNull(employee.getManager());
        assertNull(employee.getLocation());
        assertNull(employee.getDepartment());
    }
}