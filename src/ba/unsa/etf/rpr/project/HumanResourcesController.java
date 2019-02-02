package ba.unsa.etf.rpr.project;

import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

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
    private String currentUser;
    public GridPane hrGrid;
    private Timer changingColorTimer = new Timer();
    private ArrayList<String> colors = new ArrayList<>();
    public TableView<Employee> employeeTable = new TableView<>();
    public TableColumn<Employee,String> employeeIdColumn = new TableColumn<>();
    public TableColumn<Employee,String> employeeNameColumn = new TableColumn<>();
    public TableColumn<Employee,String> employeeSurnameColumn = new TableColumn<>();
    public TableColumn<Employee,String> employeeMobileNumberColumn = new TableColumn<>();
    public TableColumn<Employee,String> employeeEmailAddressColumn = new TableColumn<>();
    public TableColumn<Employee,String> employeeCreditCardColumn = new TableColumn<>();

    @Override
    public void run() {
        int random = (int )(Math. random() * 4 + 0);
        Platform.runLater( () -> {
            hrGrid.setStyle(colors.get(random));
        });
    }

    public HumanResourcesController(String currentUser) {
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

        //Adding table column content and table content.
        employeeTable.setEditable(true);
        employeeIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        employeeNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        employeeSurnameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        employeeMobileNumberColumn.setCellValueFactory(new PropertyValueFactory<>("mobileNumber"));
        employeeEmailAddressColumn.setCellValueFactory(new PropertyValueFactory<>("emailAddress"));
        employeeCreditCardColumn.setCellValueFactory(new PropertyValueFactory<>("creditCard"));
        employeeTable.setItems( dao.getEmployees() );


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

}
