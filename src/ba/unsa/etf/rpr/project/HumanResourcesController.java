package ba.unsa.etf.rpr.project;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class HumanResourcesController extends TimerTask implements Initializable {

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

    public Label homeTabWelcomeLabel;

    public Button addEmployeeBtn = new Button();
    public Button editEmployeeBtn = new Button();
    public Button deleteEmployeeBtn = new Button();
    public Button printEmployeeBtn = new Button();
    public Button addContractBtn = new Button();
    public Button editContractBtn = new Button();
    public Button deleteContractBtn = new Button();
    public Button printContractBtn = new Button();
    public Button addDepartmentBtn = new Button();
    public Button editDepartmentBtn = new Button();
    public Button deleteDepartmentBtn = new Button();
    public Button printDepartmentBtn = new Button();
    public Button addJobBtn = new Button();
    public Button editJobBtn = new Button();
    public Button deleteJobBtn = new Button();
    public Button printJobBtn = new Button();
    public Button addCountryBtn = new Button();
    public Button editCountryBtn = new Button();
    public Button deleteCountryBtn = new Button();
    public Button printCountryBtn = new Button();
    public Button addCityBtn = new Button();
    public Button editCityBtn = new Button();
    public Button deleteCityBtn = new Button();
    public Button printCityBtn = new Button();
    public Button addLocationBtn = new Button();
    public Button editLocationBtn = new Button();
    public Button deleteLocationBtn = new Button();
    public Button printLocationBtn = new Button();

    private String currentUser;
    public GridPane hrGrid;
    private Timer changingColorTimer = new Timer();
    private ArrayList<String> colors = new ArrayList<>();

    public TableView<Employee> employeeTable = new TableView<>();
    public TableView<Contract> contractTable = new TableView<>();
    public TableView<Department> departmentTable = new TableView<>();
    public TableView<Job> jobTable = new TableView<>();
    public TableView<Country> countryTable = new TableView<>();
    public TableView<City> cityTable = new TableView<>();
    public TableView<Location> locationTable = new TableView<>();

    public TableColumn<Employee,Integer> employeeIdColumn = new TableColumn<>();
    public TableColumn<Employee,String> employeeNameColumn = new TableColumn<>();
    public TableColumn<Employee,String> employeeSurnameColumn = new TableColumn<>();
    public TableColumn<Employee,String> employeeMobileNumberColumn = new TableColumn<>();
    public TableColumn<Employee,String> employeeEmailAddressColumn = new TableColumn<>();
    public TableColumn<Employee,String> employeeCreditCardColumn = new TableColumn<>();
    public TableColumn<Contract,Integer> contractIdColumn = new TableColumn<>();
    public TableColumn<Contract,String> contractNumberColumn = new TableColumn<>();
    public TableColumn<Contract,String> contractEmployeeColumn = new TableColumn<>();
    public TableColumn<Contract,String> contractJobColumn = new TableColumn<>();
    public TableColumn<Department,Integer> departmentIdColumn = new TableColumn<>();
    public TableColumn<Department,String> departmentNameColumn = new TableColumn<>();
    public TableColumn<Department,String> departmentLocationColumn = new TableColumn<>();
    public TableColumn<Department,String> departmentManagerColumn = new TableColumn<>();
    public TableColumn<Job,Integer> jobIdColumn = new TableColumn<>();
    public TableColumn<Job,String> jobTitleColumn = new TableColumn<>();
    public TableColumn<Country,Integer> countryIdColumn = new TableColumn<>();
    public TableColumn<Country,String> countryNameColumn = new TableColumn<>();
    public TableColumn<City,Integer> cityIdColumn = new TableColumn<>();
    public TableColumn<City,String> cityNameColumn = new TableColumn<>();
    public TableColumn<Location,Integer> locationIdColumn = new TableColumn<>();
    public TableColumn<Location,String> locationPostalCodeColumn = new TableColumn<>();
    public TableColumn<Location,String> locationStreetAddressColumn = new TableColumn<>();

    private Location currentLocation = null;
    private Country currentCountry = null;
    private City currentCity = null;

    @Override
    public void run() {
        int random = (int )(Math. random() * 4 + 0);
        Platform.runLater( () -> {
            hrGrid.setStyle(colors.get(random));
        });
    }

    public HumanResourcesController( String currentUser ) {

        this.currentUser = currentUser;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        //We're setting a welcome message to Home Tab.
        homeTabWelcomeLabel.setText( "   Welcome " + currentUser + "!" );

        //Adding colors that will dinamically change in main Grid Pane.
        colors.add("-fx-background-color: linear-gradient(black, mediumpurple)");
        colors.add("-fx-background-color: linear-gradient(black, orange)");
        colors.add("-fx-background-color: linear-gradient(black, yellowgreen)");
        colors.add("-fx-background-color: linear-gradient(black, darkred)");
        colors.add("-fx-background-color: linear-gradient(black, yellow)");
        changingColorTimer.schedule( this,0,5000 );

        //Adding hover effect for buttons on tabs other then Home Tab.
        hoverEffect(addEmployeeBtn);
        hoverEffect(editEmployeeBtn);
        hoverEffect(deleteEmployeeBtn);
        hoverEffect(printEmployeeBtn);
        hoverEffect(addContractBtn);
        hoverEffect(editContractBtn);
        hoverEffect(deleteContractBtn);
        hoverEffect(printContractBtn);
        hoverEffect(addDepartmentBtn);
        hoverEffect(editDepartmentBtn);
        hoverEffect(deleteDepartmentBtn);
        hoverEffect(printDepartmentBtn);
        hoverEffect(addJobBtn);
        hoverEffect(editJobBtn);
        hoverEffect(deleteJobBtn);
        hoverEffect(printJobBtn);
        hoverEffect(addCountryBtn);
        hoverEffect(editCountryBtn);
        hoverEffect(deleteCountryBtn);
        hoverEffect(printCountryBtn);
        hoverEffect(addCityBtn);
        hoverEffect(editCityBtn);
        hoverEffect(deleteCityBtn);
        hoverEffect(printCityBtn);
        hoverEffect(addLocationBtn);
        hoverEffect(editLocationBtn);
        hoverEffect(deleteLocationBtn);
        hoverEffect(printLocationBtn);

        //Adding table column content and table content.
        employeeIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        employeeNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        employeeSurnameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        employeeMobileNumberColumn.setCellValueFactory(new PropertyValueFactory<>("mobileNumber"));
        employeeEmailAddressColumn.setCellValueFactory(new PropertyValueFactory<>("emailAddress"));
        employeeCreditCardColumn.setCellValueFactory(new PropertyValueFactory<>("creditCard"));
        employeeTable.setItems( dao.getEmployees() );

        contractIdColumn.setCellValueFactory( new PropertyValueFactory<>("id") );
        contractNumberColumn.setCellValueFactory( new PropertyValueFactory<>("contractNumber") );
        contractEmployeeColumn.setCellValueFactory( cellData -> Bindings.concat( cellData.getValue().getEmployee().getFirstName(), " (", cellData.getValue().getEmployee().getParentName(), ") ", cellData.getValue().getEmployee().getLastName() ) );
        contractJobColumn.setCellValueFactory( cellData -> Bindings.concat( cellData.getValue().getJob().getJobTitle() ) );
        contractTable.setItems( dao.getContracts() );

        departmentIdColumn.setCellValueFactory( new PropertyValueFactory<>("id") );
        departmentNameColumn.setCellValueFactory( new PropertyValueFactory<>("name") );
        departmentLocationColumn.setCellValueFactory( cellData -> {
            if( cellData.getValue().getLocation() != null )
            return Bindings.concat(cellData.getValue().getLocation().getStreetAddress());
            else return null;
        } );
        departmentManagerColumn.setCellValueFactory( cellData -> Bindings.concat(cellData.getValue().getManager().getFirstName()," ", cellData.getValue().getManager().getLastName()) );
        departmentTable.setItems( dao.getDepartments() );

        jobIdColumn.setCellValueFactory( new PropertyValueFactory<>("id") );
        jobTitleColumn.setCellValueFactory( new PropertyValueFactory<>("jobTitle") );
        jobTable.setItems( dao.getJobs() );

        countryIdColumn.setCellValueFactory( new PropertyValueFactory<>("id") );
        countryNameColumn.setCellValueFactory( new PropertyValueFactory<>("name") );
        countryTable.setItems( dao.getCountries() );

        cityIdColumn.setCellValueFactory( new PropertyValueFactory<>("id") );
        cityNameColumn.setCellValueFactory( new PropertyValueFactory<>("name") );
        cityTable.setItems( dao.getCities() );

        locationIdColumn.setCellValueFactory( new PropertyValueFactory<>("id") );
        locationPostalCodeColumn.setCellValueFactory( new PropertyValueFactory<>("postalCode") );
        locationStreetAddressColumn.setCellValueFactory( new PropertyValueFactory<>("streetAddress") );
        locationTable.setItems( dao.getLocations() );

        //Listeners for current values in tables
        locationTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                currentLocation = locationTable.getSelectionModel().getSelectedItem();
            }
        });

        countryTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                currentCountry = countryTable.getSelectionModel().getSelectedItem();
            }
        });

        cityTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                currentCity = cityTable.getSelectionModel().getSelectedItem();
            }
        });

        //Now lets add the option of clicking a row that is not empty, that will open window for changing
        //a clicked row.
        locationTable.setRowFactory(tr ->
        { TableRow<Location> row = new TableRow<>();
            row.setOnMouseClicked(
                    event -> {
                        if( event.getClickCount() == 2 && (!row.isEmpty()) )
                            try{
                                clickOnEditLocationBtn(null);
                            }
                            catch (Exception ignored){

                            }
                    }
            ); return row; } );

        countryTable.setRowFactory(tr ->
        { TableRow<Country> row = new TableRow<>();
            row.setOnMouseClicked(
                    event -> {
                        if( event.getClickCount() == 2 && (!row.isEmpty()) )
                            try{
                                clickOnEditCountryBtn(null);
                            }
                            catch (Exception ignored){

                            }
                    }
            ); return row; } );

        cityTable.setRowFactory(tr ->
        { TableRow<City> row = new TableRow<>();
            row.setOnMouseClicked(
                    event -> {
                        if( event.getClickCount() == 2 && (!row.isEmpty()) )
                            try{
                                clickOnEditCityBtn(null);
                            }
                            catch (Exception ignored){

                            }
                    }
            ); return row; } );

    }

    private void hoverEffect(Button editEmployeeBtn) {
        editEmployeeBtn.hoverProperty().addListener((observable, oldValue, newValue) -> {
            if( editEmployeeBtn.isHover() ){
                editEmployeeBtn.getStyleClass().removeAll("blackTheme");
                editEmployeeBtn.getStyleClass().add("blackGreyTheme");
            }
            else{
                editEmployeeBtn.getStyleClass().removeAll("blackGreyTheme");
                editEmployeeBtn.getStyleClass().add("blackTheme");
            }
        });
    }

    public Timer getChangingColorTimer() {
        return changingColorTimer;
    }

    public void clickOnChangeUsername(ActionEvent actionEvent) {
        String oldUsername = "";
        String currentPassword = "";
        for (Administrator a: dao.getAdministrators())
            if( a.getUsername().equals( currentUser ) ){
                oldUsername = a.getUsername();
                currentPassword = a.getPassword();
                break;
            }
        Stage secondaryStage = new Stage();
        FXMLLoader secondaryLoader = new FXMLLoader(getClass().getResource("/FXML/usernameWindow.fxml"));
        UsernameController uc = new UsernameController( currentPassword, oldUsername );
        secondaryLoader.setController(uc);
        Parent secondaryRoot = null;
        try {
            secondaryRoot = secondaryLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        secondaryStage.setTitle("Username update");
        secondaryStage.setResizable(false);
        secondaryStage.initModality(Modality.APPLICATION_MODAL);
        secondaryStage.setScene(new Scene(secondaryRoot, 370, 150));
        secondaryStage.show();
        secondaryStage.setOnHidden(event -> {
            if( uc.isUsernameChanged() ) {
                homeTabWelcomeLabel.setText("   Welcome " + uc.getNewUsernameField().getText() + "!");
                currentUser = uc.getNewUsernameField().getText();
            }
        });
    }

    public void clickOnChangePassword(ActionEvent actionEvent) {

            String userPassword = "";
            for (Administrator a : dao.getAdministrators())
                if (a.getUsername().equals(currentUser)) {
                    userPassword = a.getPassword();
                    break;
                }
            Stage secondaryStage = new Stage();
            FXMLLoader secondaryLoader = new FXMLLoader(getClass().getResource("/FXML/passwordWindow.fxml"));
            PasswordController pc = new PasswordController(currentUser, userPassword);
            secondaryLoader.setController(pc);
            Parent secondaryRoot = null;
            try {
                secondaryRoot = secondaryLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            secondaryStage.setTitle("Password update");
            secondaryStage.setResizable(false);
            secondaryStage.initModality(Modality.APPLICATION_MODAL);
            secondaryStage.setScene(new Scene(secondaryRoot, 370, 150));
            secondaryStage.show();

    }

    public void clickOnLogout(ActionEvent actionEvent) {
        homeTabWelcomeLabel.getScene().getWindow().hide();
    }

    public void clickOnAddLocationBtn(ActionEvent actionEvent){
        Stage secondaryStage = new Stage();
        FXMLLoader secondaryLoader = new FXMLLoader(getClass().getResource("/FXML/locationWindow.fxml"));
        LocationController lc = new LocationController( null );
        secondaryLoader.setController(lc);
        Parent secondaryRoot = null;
        try {
            secondaryRoot = secondaryLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        secondaryStage.setTitle("Add location");
        secondaryStage.setResizable(false);
        secondaryStage.initModality(Modality.APPLICATION_MODAL);
        secondaryStage.setScene(new Scene(secondaryRoot, 370, 150));
        secondaryStage.show();
    }

    public void clickOnEditLocationBtn(ActionEvent actionEvent){
        if( currentLocation != null ){
            Stage secondaryStage = new Stage();
            FXMLLoader secondaryLoader = new FXMLLoader(getClass().getResource("/FXML/locationWindow.fxml"));
            LocationController lc = new LocationController( currentLocation );
            secondaryLoader.setController( lc );
            Parent secondaryRoot = null;
            try {
                secondaryRoot = secondaryLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            secondaryStage.setTitle("Edit location");
            secondaryStage.setResizable(false);
            secondaryStage.initModality(Modality.APPLICATION_MODAL);
            secondaryStage.setScene(new Scene(secondaryRoot, 370, 150));
            secondaryStage.show();
            secondaryStage.setOnHidden(event -> {
                currentLocation = null;
                locationTable.getSelectionModel().clearSelection();
            });
        }
    }

    public void clickOnDeleteLocationBtn(ActionEvent actionEvent) throws SQLException {
        if( currentLocation != null ) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete location");
            alert.setHeaderText("Are you sure you want to delete " + currentLocation.getStreetAddress() + "?");
            Optional<ButtonType> result = alert.showAndWait();
            if( result.get() == ButtonType.OK )
                dao.deleteLocation( currentLocation );
            currentLocation = null;
            locationTable.getSelectionModel().clearSelection();
        }
    }

    public void clickOnAddCountryBtn(ActionEvent actionEvent){
        Stage secondaryStage = new Stage();
        FXMLLoader secondaryLoader = new FXMLLoader(getClass().getResource("/FXML/countryWindow.fxml"));
        CountryController cc = new CountryController( null );
        secondaryLoader.setController(cc);
        Parent secondaryRoot = null;
        try {
            secondaryRoot = secondaryLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        secondaryStage.setTitle("Add country");
        secondaryStage.setResizable(false);
        secondaryStage.initModality(Modality.APPLICATION_MODAL);
        secondaryStage.setScene(new Scene(secondaryRoot, 370, 120));
        secondaryStage.show();
    }

    public void clickOnEditCountryBtn(ActionEvent actionEvent){
        if( currentCountry != null ){
            Stage secondaryStage = new Stage();
            FXMLLoader secondaryLoader = new FXMLLoader(getClass().getResource("/FXML/countryWindow.fxml"));
            CountryController cc = new CountryController( currentCountry );
            secondaryLoader.setController( cc );
            Parent secondaryRoot = null;
            try {
                secondaryRoot = secondaryLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            secondaryStage.setTitle("Edit country");
            secondaryStage.setResizable(false);
            secondaryStage.initModality(Modality.APPLICATION_MODAL);
            secondaryStage.setScene(new Scene(secondaryRoot, 370, 120));
            secondaryStage.show();
            secondaryStage.setOnHidden(event -> {
                currentCountry = null;
                countryTable.getSelectionModel().clearSelection();
            });
        }
    }

    public void clickOnDeleteCountryBtn(ActionEvent actionEvent) throws SQLException {
        if( currentCountry != null ) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete country");
            alert.setHeaderText("Are you sure you want to delete " + currentCountry.getName() + "?");
            Optional<ButtonType> result = alert.showAndWait();
            if( result.get() == ButtonType.OK ) {
                ArrayList<City> citiesToDelete = new ArrayList<>();
                //Lets fetch all cities we need to delete.
                for ( City c : dao.getCities())
                    if( c.getCountry().equals( currentCountry ) )
                        citiesToDelete.add(c);

                 ArrayList<Location> locationsToDelete = new ArrayList<>();
                //Lets fetch all locations in cities we need to delete.
                for ( City c : citiesToDelete )
                    for ( Location l: dao.getLocations() )
                        if( l.getCity().equals(c) )
                            locationsToDelete.add( l );

                //Lets delete cities from citiesToDelete
                for (City c : citiesToDelete)
                    dao.deleteCity(c);

                //Lets delete locations from locationsToDelete
                for (Location l: locationsToDelete)
                    dao.deleteLocation(l);

                //Lets delete selected country.
                dao.deleteCountry(currentCountry);
            }
            currentCountry = null;
            countryTable.getSelectionModel().clearSelection();
        }
    }

    public void clickOnAddCityBtn(ActionEvent actionEvent){
        Stage secondaryStage = new Stage();
        FXMLLoader secondaryLoader = new FXMLLoader(getClass().getResource("/FXML/cityWindow.fxml"));
        CityController cc = new CityController( null );
        secondaryLoader.setController(cc);
        Parent secondaryRoot = null;
        try {
            secondaryRoot = secondaryLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        secondaryStage.setTitle("Add city");
        secondaryStage.setResizable(false);
        secondaryStage.initModality(Modality.APPLICATION_MODAL);
        secondaryStage.setScene(new Scene(secondaryRoot, 370, 150));
        secondaryStage.show();
    }

    public void clickOnEditCityBtn(ActionEvent actionEvent){
        if( currentCity != null ){
            Stage secondaryStage = new Stage();
            FXMLLoader secondaryLoader = new FXMLLoader(getClass().getResource("/FXML/cityWindow.fxml"));
            CityController cc = new CityController( currentCity );
            secondaryLoader.setController( cc );
            Parent secondaryRoot = null;
            try {
                secondaryRoot = secondaryLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            secondaryStage.setTitle("Edit city");
            secondaryStage.setResizable(false);
            secondaryStage.initModality(Modality.APPLICATION_MODAL);
            secondaryStage.setScene(new Scene(secondaryRoot, 370, 150));
            secondaryStage.show();
            secondaryStage.setOnHidden(event -> {
                currentCity = null;
                cityTable.getSelectionModel().clearSelection();
            });
        }
    }

    public void clickOnDeleteCityBtn(ActionEvent actionEvent) throws SQLException {
        if( currentCity != null ) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete city");
            alert.setHeaderText("Are you sure you want to delete " + currentCity.getName() + "?");
            Optional<ButtonType> result = alert.showAndWait();
            if( result.get() == ButtonType.OK ) {
                ArrayList<Location> locationsToDelete = new ArrayList<>();
                //Lets fetch all locations that are in selected city.
                for ( Location l : dao.getLocations())
                    if( l.getCity().equals( currentCity ) )
                        locationsToDelete.add(l);

                 //Lets delete those locations.
                for ( Location l : locationsToDelete )
                    dao.deleteLocation(l);

                //Lets delete selected city.
                dao.deleteCity(currentCity);
            }
            currentCity = null;
            cityTable.getSelectionModel().clearSelection();
        }
    }

    public String getCurrentUser() {
        return currentUser;
    }
}
