package ba.unsa.etf.rpr.project;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public class EmployeeController implements Initializable {

    private HumanResourcesDAO dao;

    {
        try {
            dao = HumanResourcesDAO.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Employee currentEmployee;
    public TextField fieldFirstName = new TextField();
    public TextField fieldLastName = new TextField();
    public TextField fieldParentName = new TextField();
    public DatePicker dpBirthDate = new DatePicker();
    public TextField fieldUmcn = new TextField();
    public TextField fieldMobileNumber = new TextField();
    public TextField fieldEmailAddress = new TextField();
    public TextField fieldCreditCard = new TextField();
    public Spinner<Integer> spinnerSalary = new Spinner();
    public ChoiceBox<String> cbLocations = new ChoiceBox();
    public ChoiceBox<String> cbDepartments = new ChoiceBox();
    public ChoiceBox<String> cbJobs = new ChoiceBox();
    public ChoiceBox<String> cbManagers = new ChoiceBox();
    public Button errorReportBtn = new Button();
    public Button okBtn = new Button();
    public Button cancelBtn = new Button();
    public Button imageBtn = new Button();
    public ChoiceBox<String> cbGender = new ChoiceBox<>();

    public EmployeeController( Employee currentEmployee ){
        this.currentEmployee = currentEmployee;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

        okBtn.setDisable(true);
        dpBirthDate.setEditable(false);
        dpBirthDate.getEditor().getStyleClass().removeAll();
        dpBirthDate.getEditor().getStyleClass().add("controllerFields");
        spinnerSalary.setEditable(false);
        spinnerSalary.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,10000,0,100));
        spinnerSalary.getEditor().getStyleClass().removeAll("controllerFields","fieldValid","fieldInvalid");
        spinnerSalary.getEditor().getStyleClass().add("controllerFields");

        Image image = new Image("/Images/noImage.png");
        ImageView imageView = new ImageView( image );
        imageView.setFitHeight(250);
        imageView.setFitWidth(200);
        imageBtn.setGraphic( imageView );

        //Lets add locations to LOCATION CHOICEBOX.
        ObservableList<String> locations = FXCollections.observableArrayList();
        for ( Location l : dao.getLocations() )
            locations.add(l.getStreetAddress() + " (" + l.getPostalCode() + ")");

        cbLocations.setItems( locations );

        //Lets add departments to DEPARTMENT CHOICEBOX.
        ObservableList<String> departments = FXCollections.observableArrayList();
        for ( Department d : dao.getDepartments() )
            departments.add( d.getName() );
        cbDepartments.setItems( departments );

        //Lets add departments to JOB CHOICEBOX.
        ObservableList<String> jobs = FXCollections.observableArrayList();
        for ( Job j : dao.getJobs() )
            jobs.add( j.getJobTitle() );
        cbJobs.setItems( jobs );

        //Lets add employees to MANAGER CHOICEBOX, employee cannot be his/hers own manager.
        ObservableList<String> managers = FXCollections.observableArrayList();
        String currentEmployeeName = null;
        if( currentEmployee != null )
            currentEmployeeName = currentEmployee.getFirstName() + " " + currentEmployee.getLastName();
        for ( Employee e : dao.getEmployees() ) {
            if (currentEmployeeName == null)
                managers.add(e.getFirstName() + " " + e.getLastName());
            else if (!currentEmployeeName.equals(e.getFirstName() + " " + e.getLastName()))
                managers.add(e.getFirstName() + " " + e.getLastName());
        }
        cbManagers.setItems( managers );

        ObservableList<String> genders = FXCollections.observableArrayList();
        genders.add( "Male" );
        genders.add( "Female" );
        cbGender.setItems( genders );

        if( currentEmployee != null ){
            fieldFirstName.setText( currentEmployee.getFirstName() );
            fieldLastName.setText( currentEmployee.getLastName() );
            fieldParentName.setText( currentEmployee.getParentName() );
            dpBirthDate.getEditor().setText( currentEmployee.getBirthDate().format( DateTimeFormatter.ofPattern("dd/MM/yyyy") ) );
            fieldUmcn.setText( currentEmployee.getUmcn() );
            fieldMobileNumber.setText( currentEmployee.getMobileNumber() );
            fieldEmailAddress.setText( currentEmployee.getEmailAddress() );
            fieldCreditCard.setText( currentEmployee.getCreditCard() );
            spinnerSalary.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,10000,currentEmployee.getSalary(),100));
            Image currentGender =  new Image( currentEmployee.getPhoto() );
            ImageView imageViewGender = new ImageView(currentGender);
            imageViewGender.setFitHeight(250);
            imageViewGender.setFitWidth(200);
            imageBtn.setGraphic( imageViewGender );
            if( currentEmployee.getPhoto().equals( "/Images/MaleIcon.png" ) )
                cbGender.setValue( "Male" );
            else
                cbGender.setValue("Female");
            if( currentEmployee.getLocation() != null )
                cbLocations.setValue( currentEmployee.getLocation().getStreetAddress() + " (" + currentEmployee.getLocation().getPostalCode() + ")" );
            if( currentEmployee.getDepartment() != null )
                cbDepartments.setValue( currentEmployee.getDepartment().getName() );
            if( currentEmployee.getJob() != null )
                cbJobs.setValue( currentEmployee.getJob().getJobTitle() );
            if( currentEmployee.getManager() != null )
                cbManagers.setValue( currentEmployee.getManager().getFirstName() + " " + currentEmployee.getManager().getLastName() );

            fieldFirstName.getStyleClass().removeAll("controllerFields","fieldValid","fieldInvalid");
            fieldFirstName.getStyleClass().add("fieldValid");
            fieldLastName.getStyleClass().removeAll("controllerFields","fieldValid","fieldInvalid");
            fieldLastName.getStyleClass().add("fieldValid");
            fieldParentName.getStyleClass().removeAll("controllerFields","fieldValid","fieldInvalid");
            fieldParentName.getStyleClass().add("fieldValid");
            dpBirthDate.getEditor().getStyleClass().removeAll("controllerFields","fieldValid","fieldInvalid");
            dpBirthDate.getEditor().getStyleClass().add("fieldValid");
            spinnerSalary.getEditor().getStyleClass().removeAll("controllerFields","fieldValid","fieldInvalid");
            spinnerSalary.getEditor().getStyleClass().add("fieldValid");
            fieldUmcn.getStyleClass().removeAll("controllerFields","fieldValid","fieldInvalid");
            fieldUmcn.getStyleClass().add("fieldValid");
            fieldMobileNumber.getStyleClass().removeAll("controllerFields","fieldValid","fieldInvalid");
            fieldMobileNumber.getStyleClass().add("fieldValid");
            fieldEmailAddress.getStyleClass().removeAll("controllerFields","fieldValid","fieldInvalid");
            fieldEmailAddress.getStyleClass().add("fieldValid");
            fieldCreditCard.getStyleClass().removeAll("controllerFields","fieldValid","fieldInvalid");
            fieldCreditCard.getStyleClass().add("fieldValid");
            if( currentEmployee.getLocation() != null ) {
                cbLocations.getStyleClass().removeAll("controllerFields", "fieldValid", "fieldInvalid");
                cbLocations.getStyleClass().add("fieldValid");
            }
            if( currentEmployee.getDepartment() != null ) {
                cbDepartments.getStyleClass().removeAll("controllerFields", "fieldValid", "fieldInvalid");
                cbDepartments.getStyleClass().add("fieldValid");
            }
            if( currentEmployee.getJob() != null ) {
                cbJobs.getStyleClass().removeAll("controllerFields", "fieldValid", "fieldInvalid");
                cbJobs.getStyleClass().add("fieldValid");
            }
            if( currentEmployee.getManager() != null ) {
                cbManagers.getStyleClass().removeAll("controllerFields", "fieldValid", "fieldInvalid");
                cbManagers.getStyleClass().add("fieldValid");
            }
            okBtn.setDisable(false);
        }

        fieldEmptyListeners(fieldFirstName);
        fieldEmptyListeners(fieldLastName);
        fieldEmptyListeners(fieldParentName);
        fieldEmptyListeners(fieldCreditCard);

        fieldEmailAddress.textProperty().addListener((observable, oldValue, newValue) -> {
            if( newValue.isEmpty() ){
                fieldEmailAddress.getStyleClass().removeAll("controllerFields","fieldValid","fieldInvalid");
                fieldEmailAddress.getStyleClass().add( "controllerFields" );
                okBtn.setDisable(true);
            }
            else if( emailAddressCheck(newValue) ){
                fieldEmailAddress.getStyleClass().removeAll("controllerFields","fieldValid","fieldInvalid");
                fieldEmailAddress.getStyleClass().add( "fieldValid" );
                okBtn.setDisable( disableOkBtn() );
            }
            else{
                fieldEmailAddress.getStyleClass().removeAll("controllerFields","fieldValid","fieldInvalid");
                fieldEmailAddress.getStyleClass().add( "fieldInvalid" );
                okBtn.setDisable(true);
            }
        });

        fieldMobileNumber.textProperty().addListener((observable, oldValue, newValue) -> {
            if( newValue.isEmpty() ){
                fieldMobileNumber.getStyleClass().removeAll("controllerFields","fieldValid","fieldInvalid");
                fieldMobileNumber.getStyleClass().add( "controllerFields" );
                okBtn.setDisable(true);
            }
            else if( mobileNumberCheck(newValue) ){
                fieldMobileNumber.getStyleClass().removeAll("controllerFields","fieldValid","fieldInvalid");
                fieldMobileNumber.getStyleClass().add( "fieldValid" );
                okBtn.setDisable( disableOkBtn() );
            }
            else{
                fieldMobileNumber.getStyleClass().removeAll("controllerFields","fieldValid","fieldInvalid");
                fieldMobileNumber.getStyleClass().add( "fieldInvalid" );
                okBtn.setDisable(true);
            }
        });

        fieldUmcn.textProperty().addListener((observable, oldValue, newValue) -> {
            if( newValue.isEmpty() ){
                fieldUmcn.getStyleClass().removeAll("controllerFields","fieldValid","fieldInvalid");
                fieldUmcn.getStyleClass().add( "controllerFields" );
                if( !dpBirthDate.getEditor().getText().isEmpty() ){
                    dpBirthDate.getEditor().getStyleClass().removeAll("controllerFields","fieldValid","fieldInvalid");
                    dpBirthDate.getEditor().getStyleClass().add( "fieldValid" );
                }
                okBtn.setDisable(true);
            }
            else if( umcnCheck(newValue) ){
                fieldUmcn.getStyleClass().removeAll("controllerFields","fieldValid","fieldInvalid");
                fieldUmcn.getStyleClass().add( "fieldValid" );
                if( !dpBirthDate.getEditor().getText().isEmpty()  ) {
                    if ( birthDateCheck( dpBirthDate.getEditor().getText() ) ) {
                        dpBirthDate.getEditor().getStyleClass().removeAll("controllerFields", "fieldValid", "fieldInvalid");
                        dpBirthDate.getEditor().getStyleClass().add("fieldValid");
                    }
                    else  {
                        dpBirthDate.getEditor().getStyleClass().removeAll("controllerFields", "fieldValid", "fieldInvalid");
                        dpBirthDate.getEditor().getStyleClass().add("fieldInvalid");
                    }
                }
                okBtn.setDisable( disableOkBtn() );
            }
            else{
                fieldUmcn.getStyleClass().removeAll("controllerFields","fieldValid","fieldInvalid");
                fieldUmcn.getStyleClass().add( "fieldInvalid" );
                if( !dpBirthDate.getEditor().getText().isEmpty()  ) {
                        dpBirthDate.getEditor().getStyleClass().removeAll("controllerFields", "fieldValid", "fieldInvalid");
                        dpBirthDate.getEditor().getStyleClass().add("fieldInvalid");
                }
                okBtn.setDisable(true);
            }
        });



        dpBirthDate.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            if( newValue.isEmpty() ){
                dpBirthDate.getEditor().getStyleClass().removeAll("controllerFields","fieldValid","fieldInvalid");
                dpBirthDate.getEditor().getStyleClass().add( "controllerFields" );
                okBtn.setDisable(true);
            }
            else if( birthDateCheck( dpBirthDate.getEditor().getText() ) ){
                dpBirthDate.getEditor().getStyleClass().removeAll("controllerFields","fieldValid","fieldInvalid");
                dpBirthDate.getEditor().getStyleClass().add( "fieldValid" );
                okBtn.setDisable( disableOkBtn() );
            }
            else{
                dpBirthDate.getEditor().getStyleClass().removeAll("controllerFields","fieldValid","fieldInvalid");
                dpBirthDate.getEditor().getStyleClass().add( "fieldInvalid" );
                okBtn.setDisable( true );
            }
        });

        spinnerSalary.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            spinnerSalary.getEditor().getStyleClass().removeAll("controllerFields","fieldValid","fieldInvalid");
            spinnerSalary.getEditor().getStyleClass().add("fieldValid");
            okBtn.setDisable( disableOkBtn() );
        });

        cbGender.valueProperty().addListener((observable, oldValue, newValue) -> {
            if( newValue != null && newValue.equals("Male") ){
                Image maleImg = new Image("/Images/MaleIcon.png");
                ImageView maleImgView = new ImageView(maleImg);
                maleImgView.setFitWidth(200);
                maleImgView.setFitHeight(250);
                imageBtn.setGraphic( maleImgView );
            }
            else if( newValue != null && newValue.equals("Female") ){
                Image femaleImg = new Image("/Images/FemaleIcon.png");
                ImageView femaleImgView = new ImageView(femaleImg);
                femaleImgView.setFitWidth(200);
                femaleImgView.setFitHeight(250);
                imageBtn.setGraphic( femaleImgView );
            }
        });

        choiceBoxEmptyListeners(cbLocations);
        choiceBoxEmptyListeners(cbDepartments);
        choiceBoxEmptyListeners(cbJobs);
        choiceBoxEmptyListeners(cbManagers);
    }

    private void choiceBoxEmptyListeners(ChoiceBox<String> choiceBox) {
        choiceBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if( newValue == null || newValue.isEmpty() ){
                choiceBox.getStyleClass().removeAll("fieldValid","fieldInvalid","controllerFields");
                choiceBox.getStyleClass().add("controllerFields");
            }
            else {
                choiceBox.getStyleClass().removeAll("fieldValid","fieldInvalid","controllerFields");
                choiceBox.getStyleClass().add("fieldValid");
            }
        });
    }

    private void fieldEmptyListeners(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if( newValue.isEmpty() ){
                textField.getStyleClass().removeAll("controllerFields","fieldValid","fieldInvalid");
                textField.getStyleClass().add( "controllerFields" );
                okBtn.setDisable(true);
            }
            else{
                textField.getStyleClass().removeAll("controllerFields","fieldValid","fieldInvalid");
                textField.getStyleClass().add( "fieldValid" );
                okBtn.setDisable( disableOkBtn() );
            }
        });
    }

    private boolean emailAddressCheck( String s ){
        //If there is nothing before @, then email address format isn't correct.
        if( s.length() != 0 && s.charAt(0) == '@') return false;

        //Format should be a@b.c, so that is the minimum length for String s.
        if( s.length() < 5 ) return false;

        int i = 0;
        while( s.charAt(i) != '@' ){

            //If we are in the end of string and @ is not found, our format is not correct.
            if( i == s.length() - 1 ) return false;

            //Before @, we can only use digits, alphabetical characters and '_'.
            if( !( Character.isAlphabetic(s.charAt(i)) ) && !( Character.isDigit(s.charAt(i)) ) && s.charAt(i) != '_' ) return false;
            i++;
        }

        i++;

        //If we are in the end of the string and we haven't found a dot or if there is nothing between @ and . , format is wrong.
        if( i == s.length() ) return false;
        if( s.charAt(i) == '.' ) return false;


        while( s.charAt(i) != '.' ){

            //If the dot is in the end, format is wrong
            if( i == s.length() - 1 ) return false;

            //After a dot, we can only use digits and alphabetical characters.
            if( !( Character.isDigit(s.charAt(i)) ) && !( Character.isAlphabetic(s.charAt(i)) ) ) return false;
            i++;
        }

        i++;
        if( i == s.length() ) return false;
        while( i != s.length() ){
            //Extensions are only made out of alphabetical characters.
            if(!( Character.isAlphabetic(s.charAt(i)) ) ) return false;
            i++;
        }

        for (Employee e: dao.getEmployees())
            if( e.getEmailAddress().equals( s ) ) return false;
        return true;
    }

    private boolean mobileNumberCheck( String s ) {
        //Format of contact number can be xxx/xxx-xxx or xxx/xxx-xxxx where x is a digit
        //so we have two cases; s is 12 characters long and s is 11 characters long
        if (s.length() == 12) {
            for (int i = 0; i < 12; i++) {
                if (i == 3 && s.charAt(i) != '/') return false;
                if (i == 7 && s.charAt(i) != '-') return false;
                if (i != 3 && i != 7 && !(Character.isDigit(s.charAt(i)))) return false;
            }
            for ( Employee e : dao.getEmployees())
                if( e.getMobileNumber().equals( s ) ) return false;

            return true;
        }
        else if (s.length() == 11) {
            for (int i = 0; i < 11; i++) {
                if (i == 3 && s.charAt(i) != '/') return false;
                if (i == 7 && s.charAt(i) != '-') return false;
                if (i != 3 && i != 7 && !(Character.isDigit(s.charAt(i)))) return false;
            }
            for ( Employee e : dao.getEmployees())
                if( e.getMobileNumber().equals( s ) ) return false;
            return true;
        }
        return false;
    }

    private boolean umcnCheck( String s ) {
        if (s.length() != 13) return false;

        //UMCN can only contain digits.
        for (int i = 0; i < 13; i++)
            if (!Character.isDigit(s.charAt(i))) return false;

        //UMCN has another check
        int realLastDigit = toNumber(s.charAt(12));
        int calculatedLastDigit = 11 - ( ( 7 * ( toNumber(s.charAt(0)) + toNumber(s.charAt(6)) ) + 6 * ( toNumber(s.charAt(1)) + toNumber(s.charAt(7)) ) + 5 * ( toNumber(s.charAt(2)) + toNumber(s.charAt(8)) ) + 4 *  (toNumber(s.charAt(3)) + toNumber(s.charAt(9)) ) + 3 * ( toNumber(s.charAt(4)) + toNumber(s.charAt(10)))  + 2 * ( toNumber(s.charAt(5)) + toNumber(s.charAt(11)) ) ) % 11 );
        if( calculatedLastDigit > 9 ) calculatedLastDigit = 0;
        if( calculatedLastDigit != realLastDigit ) return false;

        //UMCN is unique
        for (Employee e: dao.getEmployees())
            if( e.getUmcn().equals( s ) ) return false;

        //UMCN's date cannot be in future
        LocalDate localDate = LocalDate.now();
        String umcnDateString = s.substring(0,2) + "/" + s.substring(2,4) + "/";
        if( s.charAt(4) == '0'  ) umcnDateString += "2";
        else umcnDateString += "1";
        umcnDateString += s.substring(4,7);
        LocalDate umcnDate = LocalDate.parse(  umcnDateString, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        if( umcnDate.isAfter(localDate) ) return false;
        return true;
    }

    private int toNumber( char c ){
        return c -  '0';
    }

    private boolean birthDateCheck( String s ){
        //If we can't parse this string, then format isn't ok.
        try{
            LocalDate.parse( s, DateTimeFormatter.ofPattern("dd/MM/yyyy") );
        }
        catch (Exception e){
            return false;
        }

        //We have three cases, if umcn field is empty, if umcn field contains x characters where x != 13 or umcn field
        //contains 13 characters but we have to check if they are legal.
        if( fieldUmcn.getText().isEmpty() ){
            LocalDate birthDate = LocalDate.parse( s, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            LocalDate nowDate = LocalDate.now();
            if( birthDate.isAfter( nowDate ) ) return false;
            return true;
        }
        else if( !fieldUmcn.getText().isEmpty() && fieldUmcn.getText().length() != 13 ){
            return false;
        }
        else {
            LocalDate birthDateTemp = LocalDate.parse( s, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            LocalDate nowDate = LocalDate.now();
            if( birthDateTemp.isAfter( nowDate ) ) return false;

            int dayFromUmcn = Integer.parseInt(fieldUmcn.getText(0, 2));
            int monthFromUmcn = Integer.parseInt(fieldUmcn.getText(2, 4));
            int yearFromUmcn = Integer.parseInt(fieldUmcn.getText(4, 7));

            LocalDate birthDate = LocalDate.parse( s, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            int dayFromBirthDate = birthDate.getDayOfMonth();
            int monthFromBirthDate = birthDate.getMonthValue();
            //We will do yearFromBirthDate%1000 because umcn uses three last digits of year.
            int yearFromBirthDate = birthDate.getYear()%1000;
            if( dayFromUmcn != dayFromBirthDate || monthFromUmcn != monthFromBirthDate || yearFromUmcn != yearFromBirthDate ) return false;
            return true;
        }
    }

    private boolean disableOkBtn() {
        if( fieldFirstName.getStyleClass().contains("fieldInvalid") || fieldFirstName.getStyleClass().contains("controllerFields") ) return true;
        if( fieldLastName.getStyleClass().contains("fieldInvalid") || fieldLastName.getStyleClass().contains("controllerFields") ) return true;
        if( fieldParentName.getStyleClass().contains("fieldInvalid") || fieldParentName.getStyleClass().contains("controllerFields") ) return true;
        if( dpBirthDate.getEditor().getStyleClass().contains("fieldInvalid") || dpBirthDate.getEditor().getStyleClass().contains("controllerFields") ) return true;
        if( fieldUmcn.getStyleClass().contains("fieldInvalid") || fieldUmcn.getStyleClass().contains("controllerFields") ) return true;
        if( fieldMobileNumber.getStyleClass().contains("fieldInvalid") || fieldMobileNumber.getStyleClass().contains("controllerFields") ) return true;
        if( fieldEmailAddress.getStyleClass().contains("fieldInvalid") || fieldEmailAddress.getStyleClass().contains("controllerFields") ) return true;
        if( fieldCreditCard.getStyleClass().contains("fieldInvalid") || fieldCreditCard.getStyleClass().contains("controllerFields") ) return true;
        if( cbGender.getValue() == null ) return true;
        return false;
    }

    private Employee getEmployeeFromWindow(){
        int index;
        if( currentEmployee == null ) index = dao.nextIndex( "Employee" );
        else index = currentEmployee.getId();
        Location location = null;
        if( cbLocations.getValue() != null ) {
            for (Location l : dao.getLocations()) {
                String tempLocation = l.getStreetAddress() + " (" + l.getPostalCode() + ")";
                if (tempLocation.equals(cbLocations.getValue())) {
                    location = l;
                    break;
                }
            }
        }

        Department department = null;
        if( cbDepartments.getValue() != null ) {
            for (Department d : dao.getDepartments())
                if (d.getName().equals(cbDepartments.getValue())) {
                    department = d;
                    break;
                }
        }

        Job job = null;
        if( cbJobs.getValue() != null ) {
            for (Job j : dao.getJobs())
                if (j.getJobTitle().equals(cbJobs.getValue())) {
                    job = j;
                    break;
                }
        }

        Employee manager = null;
        if( cbManagers.getValue() != null ) {
            for (Employee e : dao.getEmployees()) {
                String tempManager = e.getFirstName() + " " + e.getLastName();
                if (tempManager.equals(cbManagers.getValue())) {
                    manager = e;
                    break;
                }
            }
        }

        return new Employee( index, fieldFirstName.getText(), fieldLastName.getText(), fieldParentName.getText(), LocalDate.parse( dpBirthDate.getEditor().getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy")), fieldUmcn.getText(), fieldMobileNumber.getText(), fieldEmailAddress.getText(), fieldCreditCard.getText(), spinnerSalary.getValue(), "/Images/" + cbGender.getValue() + "Icon.png", location, department, job, manager );
    }

    public void clickErrorReportBtn(ActionEvent actionEvent) {
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

    public void clickOkBtn(ActionEvent actionEvent) throws SQLException {
        if( currentEmployee == null ){
            dao.addEmployee( getEmployeeFromWindow() );
            fieldFirstName.getScene().getWindow().hide();
        }
        else{
            dao.changeEmployee( getEmployeeFromWindow() );
            fieldFirstName.getScene().getWindow().hide();
        }
    }

    public void clickCancelBtn(ActionEvent actionEvent) {
        fieldFirstName.getScene().getWindow().hide();
    }

    private ObservableList<String> getErrors(){
        ObservableList<String> errors = FXCollections.observableArrayList();
        if( fieldFirstName.getStyleClass().contains("controllerFields") ) errors.add("First name field is empty");
        if( fieldLastName.getStyleClass().contains("controllerFields") ) errors.add("Last name field is empty");
        if( fieldParentName.getStyleClass().contains("controllerFields") ) errors.add("Parent name field is empty");
        if( dpBirthDate.getEditor().getStyleClass().contains("controllerFields") ) errors.add("No birth date selected");
        if( dpBirthDate.getEditor().getStyleClass().contains("fieldInvalid") ) errors.add("Birth date doesn't match UMCN or\nor birth date is in future");
        if( fieldUmcn.getStyleClass().contains("controllerFields") ) errors.add("UMCN field is empty");
        if( fieldUmcn.getStyleClass().contains("fieldInvalid") ) errors.add("Wrong UMCN format or\nUMCN's date is in future or\nthis UMCN is already in use");
        if( fieldMobileNumber.getStyleClass().contains("controllerFields") ) errors.add("Mobile number field is empty");
        if( fieldMobileNumber.getStyleClass().contains("fieldInvalid") ) errors.add("Wrong mobile number format or\nthis mobile number is\nalready in use\nEXPECTED FORMAT:\nxxx/xxx-xxx(x)\n(x has to be a digit)");
        if( fieldEmailAddress.getStyleClass().contains("controllerFields") ) errors.add("Email address field is empty");
        if( fieldEmailAddress.getStyleClass().contains("fieldInvalid") ) errors.add("Wrong email address format or\nthis email address is\nalready in use\nEXPECTED FORMAT:\nx@x.x\n(extension cannot contain digits)");
        if( fieldCreditCard.getStyleClass().contains("controllerFields") ) errors.add("Credit card field is empty");
        if( fieldCreditCard.getStyleClass().contains("fieldInvalid") ) errors.add("This credit card is already in use");

        return errors;
    }

    public void clickOnCancelLocationBtn(ActionEvent actionEvent){
        cbLocations.getStyleClass().removeAll("fieldValid","fieldInvalid","controllerFields");
        cbLocations.getStyleClass().add("controllerFields");
        cbLocations.setValue(null);
    }

    public void clickOnCancelDepartmentBtn(ActionEvent actionEvent){
        cbDepartments.getStyleClass().removeAll("fieldValid","fieldInvalid","controllerFields");
        cbDepartments.getStyleClass().add("controllerFields");
        cbDepartments.setValue(null);
    }

    public void clickOnCancelJobBtn(ActionEvent actionEvent){
        cbJobs.getStyleClass().removeAll("fieldValid","fieldInvalid","controllerFields");
        cbJobs.getStyleClass().add("controllerFields");
        cbJobs.setValue(null);
    }

    public void clickOnCancelManagerBtn(ActionEvent actionEvent){
        cbManagers.getStyleClass().removeAll("fieldValid","fieldInvalid","controllerFields");
        cbManagers.getStyleClass().add("controllerFields");
        cbManagers.setValue(null);
    }

}
