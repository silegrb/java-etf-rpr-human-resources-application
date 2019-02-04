package ba.unsa.etf.rpr.project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

public class HumanResourcesDAO {

    private static HumanResourcesDAO instance;
    private static Connection connection;
    private PreparedStatement addAdministrator,addCountry,addCity,addLocation,addJob,addDepartment,addEmployee,addContract,addLogin,
            updateAdministrator,updateCountry,updateCity,updateLocation,updateJob,updateDepartment,updateEmployee,updateContract,updateLogin,
            getAdministrator,getContinent,getCountry,getCity,getLocation,getJob,getDepartment,getEmployee,getContract,getLogin,
            removeAdministrator,removeCountry,removeCity,removeLocation,removeJob,removeDepartment,removeEmployee,removeContract,removeLogin;
    public ObservableList<Administrator> administrators = FXCollections.observableArrayList();
    public ObservableList<Continent> continents = FXCollections.observableArrayList();
    public ObservableList<Country> countries = FXCollections.observableArrayList();
    public ObservableList<City> cities = FXCollections.observableArrayList();
    public ObservableList<Location> locations = FXCollections.observableArrayList();
    public ObservableList<Job> jobs = FXCollections.observableArrayList();
    public ObservableList<Department> departments = FXCollections.observableArrayList();
    public ObservableList<Employee> employees = FXCollections.observableArrayList();
    public ObservableList<Contract> contracts = FXCollections.observableArrayList();
    public ObservableList<Login> logins = FXCollections.observableArrayList();

    public Location currentlySelectedLocation = null;

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
            //If we can't select an item from one of the following tables, we have to run
            //SQL script "HumanResourcesDB.sql via regenerateBase() function.
            PreparedStatement preparedStatement;
            preparedStatement = connection.prepareStatement("SELECT 'X' FROM Administrator");
            preparedStatement.executeQuery();
            preparedStatement = connection.prepareStatement("SELECT 'X' FROM Login");
            preparedStatement.executeQuery();
            preparedStatement = connection.prepareStatement("SELECT 'X' FROM Continent");
            preparedStatement.executeQuery();
            preparedStatement = connection.prepareStatement("SELECT 'X' FROM Country");
            preparedStatement.executeQuery();
            preparedStatement = connection.prepareStatement("SELECT 'X' FROM City");
            preparedStatement.executeQuery();
            preparedStatement = connection.prepareStatement("SELECT 'X' FROM Location");
            preparedStatement.executeQuery();
            preparedStatement = connection.prepareStatement("SELECT 'X' FROM Job");
            preparedStatement.executeQuery();
            preparedStatement = connection.prepareStatement("SELECT 'X' FROM Department");
            preparedStatement.executeQuery();
            preparedStatement = connection.prepareStatement("SELECT 'X' FROM Employee");
            preparedStatement.executeQuery();
            preparedStatement = connection.prepareStatement("SELECT 'X' FROM Contract");
            preparedStatement.executeQuery();
        } catch (SQLException e) {
            regenerateBase();

        }
        prepareStatements();
        fetchData();
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

    private void prepareStatements() throws SQLException {
        addAdministrator = connection.prepareStatement("INSERT INTO Administrator VALUES (?,?,?)");
        addLogin = connection.prepareStatement("INSERT INTO Login VALUES (?,?,?)");
        addCountry = connection.prepareStatement("INSERT INTO Country VALUES (?,?,?)");
        addCity = connection.prepareStatement("INSERT INTO City VALUES ( ?,?,? )");
        addLocation = connection.prepareStatement("INSERT INTO Location VALUES ( ?,?,?,? )");
        addJob = connection.prepareStatement("INSERT INTO Job VALUES ( ?,? )");
        addDepartment = connection.prepareStatement("INSERT INTO Department VALUES ( ?,?,?,? )");
        addEmployee = connection.prepareStatement("INSERT INTO Employee VALUES ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,? )");
        addContract = connection.prepareStatement("INSERT INTO Contract VALUES ( ?,?,?,?,?,? )");
        getAdministrator = connection.prepareStatement("SELECT * FROM Administrator");
        getLogin = connection.prepareStatement("SELECT * FROM Login");
        getContinent = connection.prepareStatement("SELECT * FROM Continent");
        getCountry = connection.prepareStatement("SELECT * FROM Country");
        getCity = connection.prepareStatement("SELECT * FROM City");
        getLocation = connection.prepareStatement("SELECT * FROM Location");
        getJob = connection.prepareStatement("SELECT * FROM Job");
        getDepartment = connection.prepareStatement("SELECT * FROM Department");
        getEmployee = connection.prepareStatement("SELECT * FROM Employee");
        getContract = connection.prepareStatement("SELECT * FROM Contract");
        updateAdministrator = connection.prepareStatement("UPDATE Administrator SET username=?, password=? WHERE id=?");
        updateLogin = connection.prepareStatement("UPDATE Login SET user=?, time=? WHERE id=?");
        updateCountry = connection.prepareStatement("UPDATE Country SET name=?, continent=? WHERE id=?");
        updateCity = connection.prepareStatement("UPDATE City SET name=?, country=? WHERE id=?");
        updateLocation = connection.prepareStatement("UPDATE Location SET postal_code=?, street_adress=?, city=? WHERE id=?");
        updateJob = connection.prepareStatement("UPDATE Job SET job_title=? WHERE id=?");
        updateDepartment = connection.prepareStatement("UPDATE Department SET name=?, location=? WHERE id=?");
        updateEmployee = connection.prepareStatement("UPDATE Employee SET first_name=?, last_name=?, parent_name=?, birth_date=?, umcn=?, mobile_number=?, email_address=?, credit_card=?, salary=?, photo=?, location=?, department=?, job=?, manager=? WHERE id=?");
        updateContract = connection.prepareStatement("UPDATE Contract SET contract_number=?, start_date=?, end_date=?, job=?, employee=? WHERE id=?");
        removeAdministrator = connection.prepareStatement("DELETE FROM Administrator WHERE id=?");
        removeLogin = connection.prepareStatement("DELETE FROM Login WHERE id=?");
        removeCountry = connection.prepareStatement("DELETE FROM Country WHERE id=?");
        removeCity = connection.prepareStatement("DELETE FROM City WHERE id=?");
        removeLocation = connection.prepareStatement("DELETE FROM Location WHERE id=?");
        removeJob = connection.prepareStatement("DELETE FROM Job WHERE id=?");
        removeDepartment = connection.prepareStatement("DELETE FROM Department WHERE id=?");
        removeEmployee = connection.prepareStatement("DELETE FROM Employee WHERE id=?");
        removeContract = connection.prepareStatement("DELETE FROM Contract WHERE id=?");
    }

    void fetchData() throws SQLException {
        //First, lets download all administrators from database.
        ResultSet resultSet = getAdministrator.executeQuery();
        while( resultSet.next() ){
            Administrator administrator = new Administrator( resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3) );
            administrators.add( administrator );
        }

        //Lets add all logins.
        resultSet = getLogin.executeQuery();
        while( resultSet.next() ){
            Login login = new Login( resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3)  );
            logins.add( login );
        }

        //Lets add all the continents.
        resultSet = getContinent.executeQuery();
        while( resultSet.next() ){
            Continent continent = new Continent( resultSet.getInt(1), resultSet.getString(2) );
            continents.add( continent );
        }

        //Lets add all the countries, but we have to find its corresponding continent.
        resultSet = getCountry.executeQuery();
        while( resultSet.next() ){
            Country country = new Country( resultSet.getInt(1), resultSet.getString(2), null );
            int continentFK = resultSet.getInt(3);
            for ( Continent c : continents)
                if( c.getId() == continentFK )
                    country.setContinent( c );
            countries.add( country ) ;
        }

        //Lets add all the cities, but we have to find its corresponding country.
        resultSet = getCity.executeQuery();
        while( resultSet.next() ){
            City city = new City( resultSet.getInt(1), resultSet.getString(2), null );
            int countryFK = resultSet.getInt(3);
            for ( Country c : countries)
                if( c.getId() == countryFK )
                    city.setCountry( c );
            cities.add( city ) ;
        }

        //Lets add all the locations, but we have to find its correspoding city.
        resultSet = getLocation.executeQuery();
        while( resultSet.next() ){
            Location location = new Location( resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), null );
            int cityFK = resultSet.getInt(4);
            for ( City c : cities)
                if( c.getId() == cityFK )
                    location.setCity( c );
            locations.add( location ) ;
        }

        //Lets add all the jobs.
        resultSet = getJob.executeQuery();
        while( resultSet.next() ){
            Job job = new Job( resultSet.getInt(1), resultSet.getString(2) );
            jobs.add( job );
        }

        //Lets add all the departments, but without manager, because employee array is still empty.
        resultSet = getDepartment.executeQuery();
        while( resultSet.next() ){
            Department department = new Department( resultSet.getInt(1), resultSet.getString(2),null,null );
            int locationFK = resultSet.getInt(3);
            for ( Location l: locations )
                if( l.getId() == locationFK )
                    department.setLocation( l );
            departments.add( department );
        }

        //Lets add all the employees. Manager will be fixed afterwards.
        resultSet = getEmployee.executeQuery();
        while( resultSet.next() ){
            Employee employee = new Employee( resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getDate(5).toLocalDate() , resultSet.getString(6), resultSet.getString(7), resultSet.getString(8), resultSet.getString(9), resultSet.getInt(10), resultSet.getString(11), null,null,null, null );
            int locationFK = resultSet.getInt(12);
            for ( Location l: locations )
                if( l.getId() == locationFK )
                    employee.setLocation( l );
            int departmentFK = resultSet.getInt(13);
            for ( Department d: departments )
                if( d.getId() == departmentFK )
                    employee.setDepartment( d );
            int jobFK = resultSet.getInt(14);
            for ( Job j: jobs )
                if( j.getId() == jobFK )
                    employee.setJob( j );
            employees.add( employee );
        }

        //Lets set managers for departments
        resultSet = getDepartment.executeQuery();
        while( resultSet.next() ){
            int departmentPK = resultSet.getInt(1);
            int managerFK = resultSet.getInt(4);
            for ( int i = 0; i < departments.size(); i++ )
                for ( int j = 0; j < employees.size(); j++ )
                    if( departments.get(i).getId() == departmentPK && employees.get(j).getId() == managerFK ){
                        departments.get(i).setManager( employees.get(j) );
                        break;
                    }
        }

        //Lets set managers for employees
        resultSet = getEmployee.executeQuery();
        while( resultSet.next() ){
            int employeePK = resultSet.getInt(1);
            int managerFK = resultSet.getInt(15);
            for( int i = 0; i < employees.size(); i++  )
                for ( int j = 0; j < employees.size(); j++ )
                    if( employees.get(i).getId() == employeePK && employees.get(j).getId() == managerFK ){
                        employees.get(i).setManager( employees.get(j) );
                        break;
                    }
        }

        //Lets add all the contracts.
        resultSet = getContract.executeQuery();
        while( resultSet.next() ){
            Contract contract = new Contract( resultSet.getInt(1), resultSet.getString(2), resultSet.getDate(3).toLocalDate(), resultSet.getDate(4).toLocalDate(), null, null );
            int jobFK = resultSet.getInt(5);
            for ( Job j: jobs )
                if( j.getId() == jobFK )
                    contract.setJob( j );
            int employeeFK = resultSet.getInt(6);
            for ( Employee e: employees )
                if( e.getId() == employeeFK )
                    contract.setEmployee( e );
            contracts.add( contract );
        }
    }

    public void clearData(){
        administrators.clear();
        logins.clear();
        continents.clear();
        countries.clear();
        cities.clear();
        locations.clear();
        departments.clear();
        jobs.clear();
        employees.clear();
        contracts.clear();
    }

    public ObservableList<Administrator> getAdministrators() {
        return administrators;
    }

    public void setAdministrators(ObservableList<Administrator> administrators) {
        this.administrators = administrators;
    }

    public ObservableList<Continent> getContinents() {
        return continents;
    }

    public void setContinents(ObservableList<Continent> continents) {
        this.continents = continents;
    }

    public ObservableList<Country> getCountries() {
        return countries;
    }

    public void setCountries(ObservableList<Country> countries) {
        this.countries = countries;
    }

    public ObservableList<City> getCities() {
        return cities;
    }

    public void setCities(ObservableList<City> cities) {
        this.cities = cities;
    }

    public ObservableList<Location> getLocations() {
        return locations;
    }

    public void setLocations(ObservableList<Location> locations) {
        this.locations = locations;
    }

    public ObservableList<Job> getJobs() {
        return jobs;
    }

    public void setJobs(ObservableList<Job> jobs) {
        this.jobs = jobs;
    }

    public ObservableList<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(ObservableList<Department> departments) {
        this.departments = departments;
    }

    public ObservableList<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(ObservableList<Employee> employees) {
        this.employees = employees;
    }

    public ObservableList<Contract> getContracts() {
        return contracts;
    }

    public void setContracts(ObservableList<Contract> contracts) {
        this.contracts = contracts;
    }

    public ObservableList<Login> getLogins() {
        return logins;
    }

    public void setLogins(ObservableList<Login> logins) {
        this.logins = logins;
    }

    public int nextIndex(String s ){
        try {
            PreparedStatement preparedStatement;
            preparedStatement = connection.prepareStatement("SELECT id FROM " + s + " ORDER BY id DESC LIMIT 1");
            ResultSet resultSet = preparedStatement.executeQuery();
            int index = 0;
            while( resultSet.next() )
                index = resultSet.getInt(1);
            return ++index;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -999;
    }

    public void recordUserLogin( String user, String time ) throws SQLException {
        Login login = new Login( nextIndex("Login"), user, time );
        addLogin.setInt(1, login.getId() );
        addLogin.setString(2, user );
        addLogin.setString(3, time );
        addLogin.executeUpdate();
        logins.add( login );

    }

    public void changeCurrentUser(String user,String password) throws SQLException {
        int wantedId = -1;
        for (Administrator a: getAdministrators())
            if(a.getUsername().equals(user)){
                wantedId = a.getId();
                break;
            }
         updateAdministrator.setString(1,user);
         updateAdministrator.setString(2,password);
         updateAdministrator.setInt(3, wantedId);
         updateAdministrator.executeUpdate();
        }

    public void addLocation( Location location ) throws SQLException {
        addLocation.setInt(1, location.getId());
        addLocation.setString(2, location.getPostalCode());
        addLocation.setString(3, location.getStreetAddress());
        addLocation.setInt(4, location.getCity().getId());
        addLocation.executeUpdate();
        clearData();
        fetchData();
    }

    public void changeLocation( Location location ) throws SQLException {
        updateLocation.setString(1, location.getPostalCode() );
        updateLocation.setString(2, location.getStreetAddress() );
        updateLocation.setInt(3, location.getCity().getId() );
        updateLocation.setInt(4, location.getId());
        updateLocation.executeUpdate();
        clearData();
        fetchData();
    }

    public void deleteLocation( Location location ) throws SQLException {
            removeLocation.setInt(1, location.getId());
            removeLocation.executeUpdate();
            clearData();
            fetchData();
    }

    public void addCountry( Country country ) throws SQLException {
        addCountry.setInt(1, country.getId());
        addCountry.setString(2, country.getName());
        addCountry.setInt( 3, country.getContinent().getId() );
        addCountry.executeUpdate();
        clearData();
        fetchData();
    }

    public void changeCountry( Country country ) throws SQLException {
        updateCountry.setString(1, country.getName());
        updateCountry.setInt( 2, country.getContinent().getId() );
        updateCountry.setInt(3, country.getId() );
        updateCountry.executeUpdate();
        clearData();
        fetchData();
    }

    public void deleteCountry( Country country ) throws SQLException {
        removeCountry.setInt(1, country.getId() );
        removeCountry.executeUpdate();
        clearData();
        fetchData();
    }

    public void addCity( City city ) throws SQLException {
        addCity.setInt(1, city.getId());
        addCity.setString(2, city.getName());
        addCity.setInt( 3, city.getCountry().getId() );
        addCity.executeUpdate();
        clearData();
        fetchData();
    }

    public void changeCity( City city ) throws SQLException {
        updateCity.setString(1, city.getName());
        updateCity.setInt( 2, city.getCountry().getId() );
        updateCity.setInt(3, city.getId() );
        updateCity.executeUpdate();
        clearData();
        fetchData();
    }

    public void deleteCity( City city ) throws SQLException {
        removeCity.setInt(1, city.getId() );
        removeCity.executeUpdate();
        clearData();
        fetchData();
    }
}

