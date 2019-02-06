package ba.unsa.etf.rpr.project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public class JobController implements Initializable {

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

    public TextField fieldJobTitle = new TextField();
    Button okBtn = new Button();
    private Job currentJob;

    public JobController( Job currentJob ){
        this.currentJob = currentJob;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        okBtn.setDisable(true);

        if( currentJob != null ) {
            fieldJobTitle.setText(currentJob.getJobTitle());
            fieldJobTitle.getStyleClass().removeAll("controllerFields","fieldValid","fieldInvalid");
            fieldJobTitle.getStyleClass().add("fieldValid");
            okBtn.setDisable(false);
        }

        fieldJobTitle.textProperty().addListener((observable, oldValue, newValue) -> {
            if( fieldJobTitle.getText().isEmpty() ){
                fieldJobTitle.getStyleClass().removeAll("controllerFields","fieldValid","fieldInvalid");
                fieldJobTitle.getStyleClass().add("controllerFields");
                okBtn.setDisable(true);
            }
            else{
                fieldJobTitle.getStyleClass().removeAll("controllerFields","fieldValid","fieldInvalid");
                fieldJobTitle.getStyleClass().add("fieldValid");
                okBtn.setDisable(false);
            }
        });
    }

    private Job getJobFromWindow(){
        int index;
        if( currentJob == null ) index = dao.nextIndex("Job");
        else index = currentJob.getId();
        return new Job( index, fieldJobTitle.getText() );
    }

    public void clickOkBtn(ActionEvent actionEvent) throws SQLException {
        if( currentJob == null ){
            dao.addJob( getJobFromWindow() );
            fieldJobTitle.getScene().getWindow().hide();
        }
        else{
            dao.changeJob( getJobFromWindow() );
            fieldJobTitle.getScene().getWindow().hide();
        }
    }

    public void clickCancelBtn(ActionEvent actionEvent) throws SQLException {
        fieldJobTitle.getScene().getWindow().hide();
    }

    private ObservableList<String> getErrors(){
        ObservableList<String> errors = FXCollections.observableArrayList();
        if( fieldJobTitle.getText().isEmpty() ) errors.add("Job title field is empty");
        return errors;
    }

    public void clickErrorReportBtn (ActionEvent actionEvent){
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
}
