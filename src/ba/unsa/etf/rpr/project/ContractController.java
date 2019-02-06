package ba.unsa.etf.rpr.project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class ContractController implements Initializable {
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

    public TextField fieldContractNumber = new TextField();
    public DatePicker dpStartDate = new DatePicker();
    public DatePicker dpEndDate = new DatePicker();
    public ChoiceBox<Employee> cbEmployees = new ChoiceBox<>();
    public ChoiceBox<String> cbJobs = new ChoiceBox<>();
    public Button okBtn = new Button();

    public ContractController(){ }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)    {

        //Lets add all the items to choiceBoxes
        cbEmployees.setItems( dao.getEmployees() );

        ObservableList<String> jobs = FXCollections.observableArrayList();
        for ( Job j: dao.getJobs())
            jobs.add( j.getJobTitle() );
        cbJobs.setItems( jobs );

        okBtn.setDisable(true);
        dpStartDate.setEditable(false);
        dpEndDate.setEditable(false);
        dpStartDate.getEditor().getStyleClass().removeAll();
        dpStartDate.getEditor().getStyleClass().add("controllerFields");
        dpEndDate.getEditor().getStyleClass().removeAll();
        dpEndDate.getEditor().getStyleClass().add("controllerFields");


        fieldContractNumber.textProperty().addListener((observable, oldValue, newValue) -> {
            if( newValue.isEmpty() ){
                fieldContractNumber.getStyleClass().removeAll("controllerFields","fieldValid","fieldInvalid");
                fieldContractNumber.getStyleClass().add( "controllerFields" );
                okBtn.setDisable(true);
            }
            else if( checkContractNumberFormat( newValue ) ){
                fieldContractNumber.getStyleClass().removeAll("controllerFields","fieldValid","fieldInvalid");
                fieldContractNumber.getStyleClass().add( "fieldValid" );
                if( dpEndDate.getStyleClass().contains("fieldValid") && dpStartDate.getStyleClass().contains("fieldValid") && cbEmployees.getStyleClass().contains("fieldValid") && cbJobs.getStyleClass().contains("fieldValid"))
                    okBtn.setDisable(false);
            }
            else {
                fieldContractNumber.getStyleClass().removeAll("controllerFields","fieldValid","fieldInvalid");
                fieldContractNumber.getStyleClass().add( "fieldInvalid" );
                okBtn.setDisable(true);
            }
        });

        dpStartDate.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            //Contract can be signed on todays date, but not earlier.
            LocalDate localDate = LocalDate.now().minusDays(1);
            if( newValue == null ){
                dpStartDate.getStyleClass().removeAll("controllerFields","fieldValid","fieldInvalid");
                dpStartDate.getStyleClass().add( "controllerFields" );
                okBtn.setDisable(true);
            }

            else if( LocalDate.parse( newValue, DateTimeFormatter.ofPattern("dd/MM/yyyy") ).isBefore( localDate ) ){
                dpStartDate.getStyleClass().removeAll("controllerFields","fieldValid","fieldInvalid");
                dpStartDate.getStyleClass().add( "fieldInvalid" );
                if( dpEndDate.getValue() != null ){
                    if( dpEndDate.getValue().isAfter( dpStartDate.getValue() ) ){
                        ///We are doing a cross check on dpEndDate field if it isnt empty.
                        dpEndDate.getStyleClass().removeAll("controllerFields","fieldValid","fieldInvalid");
                        dpEndDate.getStyleClass().add("fieldInvalid");
                    }
                }
                okBtn.setDisable(true);
            }
            else {
                dpStartDate.getStyleClass().removeAll("controllerFields","fieldValid","fieldInvalid");
                dpStartDate.getStyleClass().add( "fieldValid" );
                if( dpEndDate.getValue() != null ){
                    if( dpEndDate.getValue().isAfter( dpStartDate.getValue() ) ){
                        ///We are doing a cross check on dpEndDate field if it isnt empty.
                        dpEndDate.getStyleClass().removeAll("controllerFields","fieldValid","fieldInvalid");
                        dpEndDate.getStyleClass().add("fieldValid");
                    }
                }
                if( fieldContractNumber.getStyleClass().contains("fieldValid") && dpEndDate.getStyleClass().contains("fieldValid") && cbEmployees.getStyleClass().contains("fieldValid") && cbJobs.getStyleClass().contains("fieldValid") )
                    okBtn.setDisable(false);
            }
        });

        dpEndDate.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            //End date of a contract cannot be earlier that start date, and cannot be in past.
            LocalDate localDate = LocalDate.now().minusDays(1);
            //A contract cannot be shorter than three months.
            LocalDate startDate = dpStartDate.getValue().plusMonths(3);
            if( newValue == null ){
                dpEndDate.getStyleClass().removeAll("controllerFields","fieldValid","fieldInvalid");
                dpEndDate.getStyleClass().add( "controllerFields" );
                okBtn.setDisable(true);
            }
            else if( startDate == null || dpStartDate.getStyleClass().contains("fieldInvalid") ||  startDate.isAfter( LocalDate.parse( newValue, DateTimeFormatter.ofPattern("dd/MM/yyyy") ) ) ){
                dpEndDate.getStyleClass().removeAll("controllerFields","fieldValid","fieldInvalid");
                dpEndDate.getStyleClass().add( "fieldInvalid" );
                okBtn.setDisable(true);
            }
            else if( LocalDate.parse( newValue, DateTimeFormatter.ofPattern("dd/MM/yyyy") ).isBefore( localDate ) ){
                dpEndDate.getStyleClass().removeAll("controllerFields","fieldValid","fieldInvalid");
                dpEndDate.getStyleClass().add( "fieldInvalid" );
                okBtn.setDisable(true);
            }
            else {
                dpEndDate.getStyleClass().removeAll("controllerFields","fieldValid","fieldInvalid");
                dpEndDate.getStyleClass().add( "fieldValid" );
                if( fieldContractNumber.getStyleClass().contains("fieldValid") && dpStartDate.getStyleClass().contains("fieldValid") && cbEmployees.getStyleClass().contains("fieldValid") && cbJobs.getStyleClass().contains("fieldValid") )
                    okBtn.setDisable(false);
            }
        });

        cbEmployees.valueProperty().addListener((observable, oldValue, newValue) -> {
            if( newValue == null ){
                cbEmployees.getStyleClass().removeAll("fieldValid","fieldInvalid","controllerFields");
                cbEmployees.getStyleClass().add("controllerFields");
                okBtn.setDisable(true);
            }
            else {
                cbEmployees.getStyleClass().removeAll("fieldValid","fieldInvalid","controllerFields");
                cbEmployees.getStyleClass().add("fieldValid");
                if( fieldContractNumber.getStyleClass().contains("fieldValid") && dpEndDate.getStyleClass().contains("fieldValid") && dpStartDate.getStyleClass().contains("fieldValid") && cbJobs.getStyleClass().contains("fieldValid"))
                    okBtn.setDisable(false);
            }

        });

        cbJobs.valueProperty().addListener((observable, oldValue, newValue) -> {
            if( newValue.isEmpty() ){
                cbJobs.getStyleClass().removeAll("fieldValid","fieldInvalid","controllerFields");
                cbJobs.getStyleClass().add("controllerFields");
                okBtn.setDisable(true);
            }
            else {
                cbJobs.getStyleClass().removeAll("fieldValid","fieldInvalid","controllerFields");
                cbJobs.getStyleClass().add("fieldValid");
                if( fieldContractNumber.getStyleClass().contains("fieldValid") && dpEndDate.getStyleClass().contains("fieldValid") && dpStartDate.getStyleClass().contains("fieldValid") && cbEmployees.getStyleClass().contains("fieldValid"))
                    okBtn.setDisable(false);
            }

        });

    }

    private boolean checkContractNumberFormat( String s ){
        if( s.length() != 10 ) return false;
        if( s.charAt(0) < 'A' || s.charAt(0) > 'Z' ) return false;
        if( s.charAt(4) != '-' ) return false;
        for( int i = 1; i < 10; i++ ){
            if( i == 4 ) continue;
            if( !Character.isDigit( s.charAt(1) ) ) return false;
        }
        return true;
    }

    private Contract getContractFromWindow() {
        int index = dao.nextIndex("Contract");
        LocalDate startDate = dpStartDate.getValue();
        LocalDate endDate = dpEndDate.getValue();
        Job job = null;
        for ( Job j: dao.getJobs() )
            if( cbJobs.getValue().equals( j.getJobTitle() ) ){
                job = j;
                break;
            }

        return new Contract( index, fieldContractNumber.getText(), startDate, endDate, job, cbEmployees.getValue() );
    }

    public void clickOkBtn(ActionEvent actionEvent) throws SQLException {
            dao.addContract( getContractFromWindow() );
            fieldContractNumber.getScene().getWindow().hide();
    }

    public void clickCancelBtn(ActionEvent actionEvent){
        fieldContractNumber.getScene().getWindow().hide();
    }

}
