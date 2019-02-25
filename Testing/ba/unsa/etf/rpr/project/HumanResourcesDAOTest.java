package ba.unsa.etf.rpr.project;

import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HumanResourcesDAOTest {
    HumanResourcesDAO dao;

    {
        try {
            dao = HumanResourcesDAO.getInstance();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void daoTestOne() throws FileNotFoundException, SQLException {
        dao.clearData();
        dao.fetchData();
        ObservableList<Employee> employees = dao.getEmployees();
        ObservableList<Department> departments = dao.getDepartments();
        ObservableList<Job> jobs = dao.getJobs();
        ObservableList<City> cities = dao.getCities();
        ObservableList<Continent> continents = dao.getContinents();
        ObservableList<Country> countries = dao.getCountries();
        ObservableList<Location> locations = dao.getLocations();
        ObservableList<Administrator> administrators = dao.getAdministrators();
        ObservableList<Login> logins = dao.getLogins();
        ObservableList<Contract> contracts = dao.getContracts();
        assertEquals(5, employees.size());
        assertEquals(5,departments.size());
        assertEquals(5,jobs.size());
        assertEquals(7,continents.size());
        assertEquals(5,countries.size());
        assertEquals(5,cities.size());
        assertEquals(5,locations.size());
        assertEquals(2,administrators.size());
        assertEquals(0,logins.size());
        assertEquals(4,contracts.size());
    }
}