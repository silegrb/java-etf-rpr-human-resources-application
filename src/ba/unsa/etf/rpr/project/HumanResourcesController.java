package ba.unsa.etf.rpr.project;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

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

    public TextField createNewAccountUsernameField = new TextField();
    public TextField createNewAccountPasswordField = new TextField();
    public TextField createNewAccountConfirmPasswordField = new TextField();


    public Button createNewAccountCreateBtn = new Button();
    public Button createNewAccountResetBtn = new Button();
    public Button createNewAccountErrorReportBtn = new Button();
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

    public TableView<Login> loginTable = new TableView<>();
    public TableView<Employee> employeeTable = new TableView<>();
    public TableView<Contract> contractTable = new TableView<>();
    public TableView<Department> departmentTable = new TableView<>();
    public TableView<Job> jobTable = new TableView<>();
    public TableView<Country> countryTable = new TableView<>();
    public TableView<City> cityTable = new TableView<>();
    public TableView<Location> locationTable = new TableView<>();

    public TableColumn<Login,Integer> loginIdColumn = new TableColumn<>();
    public TableColumn<Login,String> loginUserColumn = new TableColumn<>();
    public TableColumn<Login,String> loginTimeColumn = new TableColumn<>();
    public TableColumn<Employee,Integer> employeeIdColumn = new TableColumn<>();
    public TableColumn<Employee,String> employeeNameColumn = new TableColumn<>();
    public TableColumn<Employee,String> employeeSurnameColumn = new TableColumn<>();
    public TableColumn<Employee,String> employeeMobileNumberColumn = new TableColumn<>();
    public TableColumn<Employee,String> employeeEmailAddressColumn = new TableColumn<>();
    public TableColumn<Employee,String> employeeCreditCardColumn = new TableColumn<>();
    public TableColumn<Contract,Integer> contractIdColumn = new TableColumn<>();
    public TableColumn<Contract,String> contractNumberColumn = new TableColumn<>();
    public TableColumn<Contract,String> contractEmployeeFullnameColumn = new TableColumn<>();
    public TableColumn<Contract,String> contractJobTitleColumn = new TableColumn<>();
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
    private Job currentJob = null;
    private Contract currentContract = null;
    private Department currentDepartment = null;
    private Employee currentEmployee = null;

    public RadioButton radioBtnAllLogins = new RadioButton();
    public RadioButton radioBtnMyLogins = new RadioButton();

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
//        createNewAccountCreateBtn.setDisable(true);

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
        loginIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        loginUserColumn.setCellValueFactory(new PropertyValueFactory<>("user"));
        loginTimeColumn.setCellValueFactory( cellData -> Bindings.concat(cellData.getValue().getTime().substring(0,20) + cellData.getValue().getTime().substring(24)) );
        loginTable.setItems( dao.getLogins() );

        employeeIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        employeeNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        employeeSurnameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        employeeMobileNumberColumn.setCellValueFactory(new PropertyValueFactory<>("mobileNumber"));
        employeeEmailAddressColumn.setCellValueFactory(new PropertyValueFactory<>("emailAddress"));
        employeeCreditCardColumn.setCellValueFactory(new PropertyValueFactory<>("creditCard"));
        employeeTable.setItems( dao.getEmployees() );

        contractIdColumn.setCellValueFactory( new PropertyValueFactory<>("id") );
        contractNumberColumn.setCellValueFactory( new PropertyValueFactory<>("contractNumber") );
        contractEmployeeFullnameColumn.setCellValueFactory( new PropertyValueFactory<>("employeeFullname") );
        contractJobTitleColumn.setCellValueFactory( new PropertyValueFactory<>("jobTitle") );
        contractTable.setItems( dao.getContracts() );

        departmentIdColumn.setCellValueFactory( new PropertyValueFactory<>("id") );
        departmentNameColumn.setCellValueFactory( new PropertyValueFactory<>("name") );
        departmentLocationColumn.setCellValueFactory( cellData -> {
            if( cellData.getValue().getLocation() != null )
            return Bindings.concat(cellData.getValue().getLocation().getStreetAddress());
            else return null;
        } );
        departmentManagerColumn.setCellValueFactory(
                cellData -> {
                    if( cellData.getValue().getManager() != null )
                        return Bindings.concat( cellData.getValue().getManager().getFirstName() + " " + cellData.getValue().getManager().getLastName() );
                    else return null;
                } );
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

        //Listeners for all the textfields
        createNewAccountUsernameField.textProperty().addListener((observable, oldValue, newValue) -> {
            String typedUsername = null;
            if( !createNewAccountUsernameField.getText().isEmpty() ) typedUsername = createNewAccountUsernameField.getText();
            boolean usernameAlreadyExists = false;
            for ( Administrator a : dao.getAdministrators())
                if(a.getUsername().equals(typedUsername))
                    usernameAlreadyExists = true;
            if( typedUsername == null ){
                createNewAccountUsernameField.getStyleClass().removeAll("createAccountFields","fieldValid","fieldInvalid");
                createNewAccountUsernameField.getStyleClass().add("createAccountFields");
            }
            else if(!usernameAlreadyExists){
                createNewAccountUsernameField.getStyleClass().removeAll("createAccountFields","fieldValid","fieldInvalid");
                createNewAccountUsernameField.getStyleClass().add("fieldValid");
            }
            else {
                createNewAccountUsernameField.getStyleClass().removeAll("createAccountFields","fieldValid","fieldInvalid");
                createNewAccountUsernameField.getStyleClass().add("fieldInvalid");
            }

        });

        createNewAccountPasswordField.textProperty().addListener((observable, oldValue, newValue) -> {
            if( createNewAccountPasswordField.getText().isEmpty() ){
                createNewAccountPasswordField.getStyleClass().removeAll("createAccountFields","fieldValid","fieldInvalid");
                createNewAccountPasswordField.getStyleClass().add("createAccountFields");
                if( !createNewAccountConfirmPasswordField.getText().isEmpty() ){
                    createNewAccountConfirmPasswordField.getStyleClass().removeAll("createAccountFields", "fieldValid", "fieldInvalid");
                    createNewAccountConfirmPasswordField.getStyleClass().add("fieldInvalid");
                }
            }
            else{
                createNewAccountPasswordField.getStyleClass().removeAll("createAccountFields","fieldValid","fieldInvalid");
                createNewAccountPasswordField.getStyleClass().add("fieldValid");
                if( !createNewAccountConfirmPasswordField.getText().isEmpty()  ) {
                    if (createNewAccountConfirmPasswordField.getText().equals(newValue)) {
                        createNewAccountConfirmPasswordField.getStyleClass().removeAll("createAccountFields", "fieldValid", "fieldInvalid");
                        createNewAccountConfirmPasswordField.getStyleClass().add("fieldValid");
                    } else {
                        createNewAccountConfirmPasswordField.getStyleClass().removeAll("createAccountFields", "fieldValid", "fieldInvalid");
                        createNewAccountConfirmPasswordField.getStyleClass().add("fieldInvalid");
                    }
                }
            }
        });

        createNewAccountConfirmPasswordField.textProperty().addListener((observable, oldValue, newValue) -> {
            if( createNewAccountConfirmPasswordField.getText().isEmpty() ){
                createNewAccountConfirmPasswordField.getStyleClass().removeAll("createAccountFields", "fieldValid", "fieldInvalid");
                createNewAccountConfirmPasswordField.getStyleClass().add("createAccountFields");
            }
            else if( !createNewAccountConfirmPasswordField.getText().isEmpty() && !createNewAccountPasswordField.getText().isEmpty() &&  createNewAccountConfirmPasswordField.getText().equals(createNewAccountPasswordField.getText()) ){
                createNewAccountConfirmPasswordField.getStyleClass().removeAll("createAccountFields", "fieldValid", "fieldInvalid");
                createNewAccountConfirmPasswordField.getStyleClass().add("fieldValid");
            }
            else{
                createNewAccountConfirmPasswordField.getStyleClass().removeAll("createAccountFields", "fieldValid", "fieldInvalid");
                createNewAccountConfirmPasswordField.getStyleClass().add("fieldInvalid");
            }
        });

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

        jobTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                currentJob = jobTable.getSelectionModel().getSelectedItem();
            }
        });

         contractTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
             if( newValue != null )
                 currentContract = contractTable.getSelectionModel().getSelectedItem();
         });

        departmentTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if( newValue != null )
                currentDepartment = departmentTable.getSelectionModel().getSelectedItem();
        });

        employeeTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if( newValue != null )
                currentEmployee = employeeTable.getSelectionModel().getSelectedItem();
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


        jobTable.setRowFactory(tr ->
        { TableRow<Job> row = new TableRow<>();
            row.setOnMouseClicked(
                    event -> {
                         if( event.getClickCount() == 2 && (!row.isEmpty()) )
                            try{

                                clickOnEditJobBtn(null);
                            }
                            catch (Exception ignored){

                            }
                    }
            ); return row; } );

        departmentTable.setRowFactory(tr ->
        { TableRow<Department> row = new TableRow<>();
            row.setOnMouseClicked(
                    event -> {
                        if( event.getClickCount() == 2 && (!row.isEmpty()) )
                            try{
                                clickOnEditDepartmentBtn(null);
                            }
                            catch (Exception ignored){

                            }
                    }
            ); return row; } );

        employeeTable.setRowFactory(tr ->
        { TableRow<Employee> row = new TableRow<>();
            row.setOnMouseClicked(
                    event -> {
                        if( event.getClickCount() == 2 && (!row.isEmpty()) )
                            try{
                                clickOnEditEmployeeBtn(null);
                            }
                            catch (Exception ignored){

                            }
                    }
            ); return row; } );

        ToggleGroup clickingRadioBtns = new ToggleGroup();
        radioBtnAllLogins.setToggleGroup( clickingRadioBtns );
        radioBtnAllLogins.setSelected( true );
        radioBtnMyLogins.setToggleGroup( clickingRadioBtns );

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
        secondaryStage.setScene(new Scene(secondaryRoot, 370, 180));
        secondaryStage.show();
        secondaryStage.setOnHidden(event -> {
            if( uc.isUsernameChanged() ) {
                homeTabWelcomeLabel.setText("   Welcome " + uc.getNewUsernameField().getText() + "!");
                currentUser = uc.getNewUsernameField().getText();
                if( radioBtnAllLogins.isSelected() )
                    clickRadioBtnAllLogins(null);
                 if( radioBtnMyLogins.isSelected() )
                    clickRadioBtnMyLogins(null);




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
            secondaryStage.setScene(new Scene(secondaryRoot, 370, 180));
            secondaryStage.show();

    }

   public void clickOnLogout(ActionEvent actionEvent) throws IOException, SQLException {
        homeTabWelcomeLabel.getScene().getWindow().hide();
       Stage stage = new Stage();
       FXMLLoader loader = new FXMLLoader( getClass().getResource("/FXML/loginWindow.fxml") );
       LoginWindowController lwc = new LoginWindowController();
       loader.setController( lwc );
       Parent root = loader.load();
       stage.setTitle("Login");
       stage.setResizable(false);
       stage.setScene(new Scene(root, 220, 140));
       stage.show();
       stage.setOnHidden(event -> {
           if( lwc.loginSuccess() ) {
               Date date = new Date();
               try {
                   dao.recordUserLogin( lwc.getUsernameField().getText(), date.toString() );
               } catch (SQLException e) {
                   e.printStackTrace();
               }
               Stage secondaryStage = new Stage();
               FXMLLoader secondaryLoader = new FXMLLoader(getClass().getResource("/FXML/humanResourcesController.fxml"));
               HumanResourcesController hrc = new HumanResourcesController( lwc.getUsernameField().getText() );

               secondaryLoader.setController(hrc);
               Parent secondaryRoot = null;
               try {
                   secondaryRoot = secondaryLoader.load();
               } catch (IOException e) {
                   e.printStackTrace();
               }
               secondaryStage.setTitle("Human Resources");
               secondaryStage.setResizable(false);
               secondaryStage.setScene(new Scene(secondaryRoot, 850, 650));
               secondaryStage.show();
               secondaryStage.setOnHidden(event1 -> hrc.getChangingColorTimer().cancel());
           }
       });
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
        secondaryStage.setScene(new Scene(secondaryRoot, 370, 210));
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
            secondaryStage.setScene(new Scene(secondaryRoot, 370, 210));
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
        secondaryStage.setScene(new Scene(secondaryRoot, 370, 150));
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
            secondaryStage.setScene(new Scene(secondaryRoot, 370, 150));
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
        secondaryStage.setScene(new Scene(secondaryRoot, 370, 180));
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
            secondaryStage.setScene(new Scene(secondaryRoot, 370, 180));
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

    public void clickOnAddContractBtn(ActionEvent actionEvent){
        Stage secondaryStage = new Stage();
        FXMLLoader secondaryLoader = new FXMLLoader(getClass().getResource("/FXML/contractWindow.fxml"));
        ContractController cc = new ContractController();
        secondaryLoader.setController(cc);
        Parent secondaryRoot = null;
        try {
            secondaryRoot = secondaryLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        secondaryStage.setTitle("Add contract");
        secondaryStage.setResizable(false);
        secondaryStage.initModality(Modality.APPLICATION_MODAL);
        secondaryStage.setScene(new Scene(secondaryRoot, 370, 250));
        secondaryStage.show();
    }

    public void clickOnDeleteContractBtn( ActionEvent actionEvent ) throws SQLException {
        if (currentContract != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete contract");
            alert.setHeaderText("Are you sure you want to delete contract " + currentContract.getContractNumber() + "?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK)
                dao.deleteContract(currentContract);
            currentContract = null;
            contractTable.getSelectionModel().clearSelection();
        }
    }

    public void clickOnAddJobBtn(ActionEvent actionEvent){
        Stage secondaryStage = new Stage();
        FXMLLoader secondaryLoader = new FXMLLoader(getClass().getResource("/FXML/jobWindow.fxml"));
        JobController jc = new JobController( null );
        secondaryLoader.setController(jc);
        Parent secondaryRoot = null;
        try {
            secondaryRoot = secondaryLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        secondaryStage.setTitle("Add job");
        secondaryStage.setResizable(false);
        secondaryStage.initModality(Modality.APPLICATION_MODAL);
        secondaryStage.setScene(new Scene(secondaryRoot, 370, 120));
        secondaryStage.show();
    }

    public void clickOnEditJobBtn(ActionEvent actionEvent) {
        if (currentJob != null) {
            Stage secondaryStage = new Stage();
            FXMLLoader secondaryLoader = new FXMLLoader(getClass().getResource("/FXML/jobWindow.fxml"));
            JobController jc = new JobController(currentJob);
            secondaryLoader.setController(jc);
            Parent secondaryRoot = null;
            try {
                secondaryRoot = secondaryLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            secondaryStage.setTitle("Edit job");
            secondaryStage.setResizable(false);
            secondaryStage.initModality(Modality.APPLICATION_MODAL);
            secondaryStage.setScene(new Scene(secondaryRoot, 370, 120));
            secondaryStage.show();
            secondaryStage.setOnHidden(event -> {
                currentJob = null;
                jobTable.getSelectionModel().clearSelection();
            });
        }
    }

    public void clickOnDeleteJobBtn(ActionEvent actionEvent) throws SQLException {
        if ( currentJob != null ) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete job");
            alert.setHeaderText("Are you sure you want to delete job " + currentJob.getJobTitle() + "?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK)
                dao.deleteJob(currentJob);
            currentJob = null;
            jobTable.getSelectionModel().clearSelection();
        }
    }

    public void clickOnAddDepartmentBtn(ActionEvent actionEvent){
        Stage secondaryStage = new Stage();
        FXMLLoader secondaryLoader = new FXMLLoader(getClass().getResource("/FXML/departmentWindow.fxml"));
        DepartmentController dc = new DepartmentController( null );
        secondaryLoader.setController(dc);
        Parent secondaryRoot = null;
        try {
            secondaryRoot = secondaryLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        secondaryStage.setTitle("Add department");
        secondaryStage.setResizable(false);
        secondaryStage.initModality(Modality.APPLICATION_MODAL);
        secondaryStage.setScene(new Scene(secondaryRoot, 400, 190));
        secondaryStage.show();
    }

    public void clickOnEditDepartmentBtn(ActionEvent actionEvent){
        if( currentDepartment != null ){
            Stage secondaryStage = new Stage();
            FXMLLoader secondaryLoader = new FXMLLoader(getClass().getResource("/FXML/departmentWindow.fxml"));
            DepartmentController dc = new DepartmentController( currentDepartment );
            secondaryLoader.setController( dc );
            Parent secondaryRoot = null;
            try {
                secondaryRoot = secondaryLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            secondaryStage.setTitle("Edit department");
            secondaryStage.setResizable(false);
            secondaryStage.initModality(Modality.APPLICATION_MODAL);
            secondaryStage.setScene(new Scene(secondaryRoot, 400, 190));
            secondaryStage.show();
            secondaryStage.setOnHidden(event -> {
                currentDepartment = null;
                departmentTable.getSelectionModel().clearSelection();

            });
        }
    }

    public void clickOnDeleteDepartmentBtn(ActionEvent actionEvent) throws SQLException {
        if ( currentDepartment != null ) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete department");
            alert.setHeaderText("Are you sure you want to delete " + currentDepartment.getName() + "?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK)
                dao.deleteDepartment(currentDepartment);
            currentDepartment = null;
            departmentTable.getSelectionModel().clearSelection();


        }
    }

    public void clickRadioBtnAllLogins(ActionEvent actionEvent){
        loginTable.setItems( dao.getLogins() );
    }

    public void clickRadioBtnMyLogins(ActionEvent actionEvent){
        ObservableList<Login> logins = FXCollections.observableArrayList();
        for ( Login l : dao.getLogins() )
            if( l.getUser().equals( currentUser ) )
                logins.add( l );
        loginTable.setItems( logins );

    }

    public String getCurrentUser() {
        return currentUser;
    }



    public void clickOnCreateNewAccountCreateBtn(ActionEvent actionEvent) throws SQLException {
        ObservableList<String> errors = getErrors();
        if( errors.size() != 0 ){
            clickOnCreateNewAccountErrorReportBtn(null);
        }
        else{
            dao.addAdministrator( new Administrator( dao.nextIndex("Administrator"), createNewAccountUsernameField.getText(), createNewAccountPasswordField.getText() ) );
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("New Account Information");
            alert.setHeaderText("Congratulations! New account '" + createNewAccountUsernameField.getText() + "' successfully created!");
            alert.showAndWait();
            clickOnCreateNewAccountResetBtn(null);
        }
    }

    public void clickOnCreateNewAccountResetBtn(ActionEvent actionEvent){
        createNewAccountUsernameField.setText("");
        createNewAccountUsernameField.getStyleClass().removeAll("createAccountFields", "fieldValid", "fieldInvalid");
        createNewAccountUsernameField.getStyleClass().add("createAccountFields");
        createNewAccountPasswordField.setText("");
        createNewAccountPasswordField.getStyleClass().removeAll("createAccountFields", "fieldValid", "fieldInvalid");
        createNewAccountPasswordField.getStyleClass().add("createAccountFields");
        createNewAccountConfirmPasswordField.setText("");
        createNewAccountConfirmPasswordField.getStyleClass().removeAll("createAccountFields", "fieldValid", "fieldInvalid");
        createNewAccountConfirmPasswordField.getStyleClass().add("createAccountFields");
    }

    public void clickOnCreateNewAccountErrorReportBtn(ActionEvent actionEvent){
        Stage secondaryStage = new Stage();
        FXMLLoader secondaryLoader = new FXMLLoader(getClass().getResource("/FXML/errorReportWindow.fxml"));
        ErrorReportController erc = new ErrorReportController( getErrors() );
        secondaryLoader.setController(erc);
        Parent secondaryRoot = null;
        try {
            secondaryRoot = secondaryLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        secondaryStage.setTitle("Error report");
        secondaryStage.setResizable(false);
        secondaryStage.initModality(Modality.APPLICATION_MODAL);
        secondaryStage.setScene(new Scene(secondaryRoot, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        secondaryStage.show();
    }

    public void clickOnAddEmployeeBtn(ActionEvent actionEvent){
        Stage secondaryStage = new Stage();
        FXMLLoader secondaryLoader = new FXMLLoader(getClass().getResource("/FXML/employeeWindow.fxml"));
        EmployeeController ec = new EmployeeController( null );
        secondaryLoader.setController(ec);
        Parent secondaryRoot = null;
        try {
            secondaryRoot = secondaryLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        secondaryStage.setTitle("Add employee");
        secondaryStage.setResizable(false);
        secondaryStage.initModality(Modality.APPLICATION_MODAL);
        secondaryStage.setScene(new Scene(secondaryRoot, 610, 550));
        secondaryStage.show();
    }

    public void clickOnEditEmployeeBtn(ActionEvent actionEvent){
        if( currentEmployee != null ){
            Stage secondaryStage = new Stage();
            FXMLLoader secondaryLoader = new FXMLLoader(getClass().getResource("/FXML/employeeWindow.fxml"));
            EmployeeController ec = new EmployeeController( currentEmployee );
            secondaryLoader.setController( ec );
            Parent secondaryRoot = null;
            try {
                secondaryRoot = secondaryLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            secondaryStage.setTitle("Edit employee");
            secondaryStage.setResizable(false);
            secondaryStage.initModality(Modality.APPLICATION_MODAL);
            secondaryStage.setScene(new Scene(secondaryRoot, 610, 550));
            secondaryStage.show();
            secondaryStage.setOnHidden(event -> {
                currentEmployee = null;
                employeeTable.getSelectionModel().clearSelection();

            });
        }
    }

    public void clickOnDeleteEmployeeBtn(ActionEvent actionEvent) throws SQLException {
        if ( currentEmployee != null ) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete employee");
            alert.setHeaderText("Are you sure you want to delete " + currentEmployee.getFirstName() + " " + currentEmployee.getLastName() + "?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK)
                dao.deleteEmployee(currentEmployee);
            currentEmployee = null;
            employeeTable.getSelectionModel().clearSelection();
        }
    }

    private ObservableList<String> getErrors(){
        ObservableList<String> errors = FXCollections.observableArrayList();
        if( createNewAccountUsernameField.getText().isEmpty() ) errors.add("Username field is empty");
        if( createNewAccountUsernameField.getStyleClass().contains("fieldInvalid") ) errors.add("This username is already in use");
        if( createNewAccountPasswordField.getText().isEmpty() ) errors.add("Password field is empty");
        if( createNewAccountConfirmPasswordField.getText().isEmpty() ) errors.add("Confirm password field is empty");
        if( createNewAccountConfirmPasswordField.getStyleClass().contains("fieldInvalid") ) errors.add("Confirmation failed");
        return errors;
    }

}
