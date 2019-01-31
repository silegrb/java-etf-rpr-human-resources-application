package ba.unsa.etf.rpr.project;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

public class HumanResourcesDAO {

    private static HumanResourcesDAO instance;
    private static Connection connection;
    private PreparedStatement addAdministrator,addCountry,addCity,addLocation,addJob,addDepartment,addEmployee,addContract,
            updateAdministrator,updateCountry,updateCity,updateLocation,updateJob,updateDepartment,updateEmployee,updateContract,
            getAdministrator,getContinent,getCountry,getCity,getLocation,getJob,getDepartment,getEmployee,getContract,
            removeAdministrator,removeCountry,removeCity,removeLocation,removeJob,removeDepartment,removeEmployee,removeContract;

    private static void initialize() throws FileNotFoundException, SQLException {
        instance = new HumanResourcesDAO();
    }

    public static HumanResourcesDAO getInstance() throws FileNotFoundException, SQLException {
        if( instance == null ) initialize();
        return instance;
    }

    public static void removeInstance() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        instance = null;
    }

    private HumanResourcesDAO() throws FileNotFoundException, SQLException {
        try{
            connection = DriverManager.getConnection("jdbc:sqlite:HumanResources.db");
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        try {
            addAdministrator = connection.prepareStatement("INSERT INTO Administrator VALUES (administrator_sequence.nextval,?,?)");
            addCountry = connection.prepareStatement("INSERT INTO Country VALUES (country_sequence.nextval,?,?)");
            addCity = connection.prepareStatement("INSERT INTO City VALUES ( city_sequence.nextval,?,? )");
            addLocation = connection.prepareStatement("INSERT INTO Location VALUES ( location_sequence.nextval,?,?,? )");
            addJob = connection.prepareStatement("INSERT INTO Job VALUES ( job_sequence.nextval,? )");
            addDepartment = connection.prepareStatement("INSERT INTO Department VALUES ( department_sequence.nextval,?,? )");
            addEmployee = connection.prepareStatement("INSERT INTO Employee VALUES ( employee_sequence.nextval,?,?,?,?,?,?,?,?,?,?,?,?,?,? )");
            addContract = connection.prepareStatement("INSERT INTO Contract VALUES ( contract_sequence.nextval,?,?,?,?,? )");
            getAdministrator = connection.prepareStatement("SELECT * FROM Administrator");
            getContinent = connection.prepareStatement("SELECT * FROM Continent");
            getCountry = connection.prepareStatement("SELECT * FROM Country");
            getCity = connection.prepareStatement("SELECT * FROM City");
            getLocation = connection.prepareStatement("SELECT * FROM Location");
            getJob = connection.prepareStatement("SELECT * FROM Job");
            getDepartment = connection.prepareStatement("SELECT * FROM Department");
            getEmployee = connection.prepareStatement("SELECT * FROM Employee");
            getContract = connection.prepareStatement("SELECT * FROM Contract");
            updateAdministrator = connection.prepareStatement("UPDATE Administrator SET username=?, password=? WHERE id=?");
            updateCountry = connection.prepareStatement("UPDATE Country SET name=?, continent=? WHERE id=?");
            updateCity = connection.prepareStatement("UPDATE City SET name=?, country=? WHERE id=?");
            updateLocation = connection.prepareStatement("UPDATE Location SET postal_code=?, street_adress=?, city=? WHERE id=?");
            updateJob = connection.prepareStatement("UPDATE Job SET job_title=? WHERE id=?");
            updateDepartment = connection.prepareStatement("UPDATE Department SET name=?, location=? WHERE id=?");
            updateEmployee = connection.prepareStatement("UPDATE Employee SET first_name=?, last_name=?, parent_name=?, birth_date=?, umcn=?, mobile_number=?, email_address=?, credit_card=?, salary=?, photo=?, location=?, department=?, job=?, manager=? WHERE id=?");
            updateContract = connection.prepareStatement("UPDATE Contract SET contract_number=?, start_date=?, end_date=?, job=?, employee=? WHERE id=?");
        } catch (SQLException e) {
            regenerateBase();
        }
    }

    private void regenerateBase() throws FileNotFoundException, SQLException {
        Scanner input = new Scanner(new FileInputStream(System.getProperty("user.dir") + "/Resources/SQL/HumanResourcesDB.sql"));
        String sqlQuerry = "";
        while( input.hasNext() ){
            sqlQuerry += input.nextLine();
            if( sqlQuerry.length() != 0 && sqlQuerry.charAt( sqlQuerry.length() - 1 ) == ';' ){
                Statement statement = connection.createStatement();
                statement.execute(sqlQuerry);
                sqlQuerry = "";
            }
        }
        input.close();
    }

}
