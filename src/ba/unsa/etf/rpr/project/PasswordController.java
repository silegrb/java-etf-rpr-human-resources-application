package ba.unsa.etf.rpr.project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public class PasswordController implements Initializable {
    
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

    public PasswordField currentPasswordField;
    public PasswordField newPasswordField;
    public PasswordField confirmNewPasswordField;
    public Button okBtn = new Button();
    private String user;
    private String oldPassword;

    public PasswordController(String user, String oldPassword) {
        this.user = user;
        this.oldPassword = oldPassword;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        okBtn.setDisable(true);

        currentPasswordField.textProperty().addListener((observable, oldValue, newValue) -> {

            if( newValue.equals("") ){
                currentPasswordField.getStyleClass().removeAll("fieldInvalid","fieldValid","passwordFields");
                currentPasswordField.getStyleClass().add("passwordFields");
                okBtn.setDisable(true);
            }
            else if( newValue.equals( oldPassword ) ){
                currentPasswordField.getStyleClass().removeAll("fieldInvalid","fieldValid","passwordFields");
                currentPasswordField.getStyleClass().add("fieldValid");
                if( newPasswordField.getStyleClass().contains("fieldValid") && confirmNewPasswordField.getStyleClass().contains("fieldValid") )
                    okBtn.setDisable(false);
            }
            else{
                currentPasswordField.getStyleClass().removeAll("fieldInvalid","fieldValid","passwordFields");
                currentPasswordField.getStyleClass().add("fieldInvalid");
                okBtn.setDisable(true);

            }
        });

        newPasswordField.textProperty().addListener((observable, oldValue, newValue) -> {
            if( newValue.equals( "" ) ){
                newPasswordField.getStyleClass().removeAll("fieldInvalid","fieldValid","passwordFields");
                newPasswordField.getStyleClass().add("passwordFields");
                if( !confirmNewPasswordField.getText().isEmpty() ){
                    confirmNewPasswordField.getStyleClass().removeAll("fieldInvalid","fieldValid","passwordFields");
                    confirmNewPasswordField.getStyleClass().add("fieldInvalid");
                }
                okBtn.setDisable(true);
            }
            else{
                newPasswordField.getStyleClass().removeAll("fieldInvalid","fieldValid","passwordFields");
                newPasswordField.getStyleClass().add("fieldValid");
                if( !confirmNewPasswordField.getText().isEmpty() ) {
                    if (confirmNewPasswordField.getText().equals(newValue)) {
                        confirmNewPasswordField.getStyleClass().removeAll("fieldInvalid","fieldValid","passwordFields");
                        confirmNewPasswordField.getStyleClass().add("fieldValid");
                    } else {
                        confirmNewPasswordField.getStyleClass().removeAll("fieldInvalid","fieldValid","passwordFields");
                        confirmNewPasswordField.getStyleClass().add("fieldInvalid");
                    }
                }
                if( currentPasswordField.getStyleClass().contains("fieldValid") && confirmNewPasswordField.getStyleClass().contains("fieldValid") )
                    okBtn.setDisable(false);            }
        });

        confirmNewPasswordField.textProperty().addListener((observable, oldValue, newValue) -> {
            if( newValue.equals("") ){
                confirmNewPasswordField.getStyleClass().removeAll("fieldInvalid","fieldValid","passwordFields");
                confirmNewPasswordField.getStyleClass().add("passwordFields");
                okBtn.setDisable(true);
            }
            else if( newValue.equals( newPasswordField.getText() ) ){
                confirmNewPasswordField.getStyleClass().removeAll("fieldInvalid","fieldValid","passwordFields");
                confirmNewPasswordField.getStyleClass().add("fieldValid");
                if( currentPasswordField.getStyleClass().contains("fieldValid") && newPasswordField.getStyleClass().contains("fieldValid"))
                    okBtn.setDisable(false);
            }
            else{
                confirmNewPasswordField.getStyleClass().removeAll("fieldInvalid","fieldValid","passwordFields");
                confirmNewPasswordField.getStyleClass().add("fieldInvalid");
                okBtn.setDisable(true);
            }
        });
    }

    public PasswordField getcurrentPasswordField() {
        return currentPasswordField;
    }

    public PasswordField getNewPasswordField() {
        return newPasswordField;
    }

    public PasswordField getConfirmNewPasswordField() {
        return confirmNewPasswordField;
    }
    
    public void clickOkBtn(ActionEvent actionEvent) throws SQLException {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setTitle("Updating password");
        a.setHeaderText("Are you sure you want to change your password?");
        Optional<ButtonType> result = a.showAndWait();
        if( result.get() == ButtonType.OK ) {
            int wantedId = -1;
           for( int i = 0; i < dao.getAdministrators().size(); i++ )
               if(dao.getAdministrators().get(i).getUsername().equals(user)){
                   wantedId = i;
                   break;
               }
               dao.getAdministrators().get(wantedId).setPassword(  newPasswordField.getText() );
            dao.changeCurrentUser(user, newPasswordField.getText());
            confirmNewPasswordField.getScene().getWindow().hide();
        }
        confirmNewPasswordField.getScene().getWindow().hide();

    }

    public void clickErrorReportBtn(ActionEvent actionEvent){
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

    private ObservableList<String> getErrors() {
        ObservableList<String> errors = FXCollections.observableArrayList();
        if( currentPasswordField.getText().isEmpty() ) errors.add("Current password field is empty");
        if( currentPasswordField.getStyleClass().contains("fieldInvalid") ) errors.add( "Wrong current password" );
        if(  newPasswordField.getText().isEmpty() ) errors.add("New password field is empty");
        if( confirmNewPasswordField.getText().isEmpty() ) errors.add("Confirm new password field is empty");
        if( !newPasswordField.getStyleClass().contains("fieldInvalid") && confirmNewPasswordField.getStyleClass().contains("fieldInvalid") ) errors.add("Confirmation failed");
        return errors;
    }

    public void clickCancelBtn(ActionEvent actionEvent){
        currentPasswordField.getScene().getWindow().hide();
    }
}
