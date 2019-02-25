package ba.unsa.etf.rpr.project;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LoginTest {
    @Test
    public void loginTestOne(){
        Login login = new Login(1,"user","time");
        assertEquals(1, login.getId());
        assertEquals("user", login.getUser());
        assertEquals("time", login.getTime());
    }

    @Test
    public void loginTestTwo(){
        Login login = new Login();
        login.setId(1);
        login.setUser("user");
        login.setTime("time");
        assertEquals(1, login.getId());
        assertEquals("user", login.getUser());
        assertEquals("time", login.getTime());
    }
}