package ba.unsa.etf.rpr.project;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;

import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

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

    public PasswordField oldPasswordField;
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

        oldPasswordField.textProperty().addListener((observable, oldValue, newValue) -> {

            if( newValue.equals("") ){
                oldPasswordField.getStyleClass().removeAll("fieldInvalid","fieldValid","passwordFields");
                oldPasswordField.getStyleClass().add("passwordFields");
                okBtn.setDisable(true);
            }
            else if( newValue.equals( oldPassword ) ){
                oldPasswordField.getStyleClass().removeAll("fieldInvalid","fieldValid","passwordFields");
                oldPasswordField.getStyleClass().add("fieldValid");
                if( newPasswordField.getStyleClass().contains("fieldValid") && confirmNewPasswordField.getStyleClass().contains("fieldValid") )
                    okBtn.setDisable(false);
            }
            else{
                oldPasswordField.getStyleClass().removeAll("fieldInvalid","fieldValid","passwordFields");
                oldPasswordField.getStyleClass().add("fieldInvalid");
                okBtn.setDisable(true);

            }
        });

        newPasswordField.textProperty().addListener((observable, oldValue, newValue) -> {
            if( !newValue.equals( "" ) ){
                newPasswordField.getStyleClass().removeAll("fieldInvalid","fieldValid","passwordFields");
                newPasswordField.getStyleClass().add("fieldValid");
                if( oldPasswordField.getStyleClass().contains("fieldValid") && confirmNewPasswordField.getStyleClass().contains("fieldValid") )
                    okBtn.setDisable(false);
            }
            else{
                newPasswordField.getStyleClass().removeAll("fieldInvalid","fieldValid","passwordFields");
                newPasswordField.getStyleClass().add("passwordFields");
                okBtn.setDisable(true);
            }
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
                if( oldPasswordField.getStyleClass().contains("fieldValid") && newPasswordField.getStyleClass().contains("fieldValid"))
                    okBtn.setDisable(false);
            }
            else{
                confirmNewPasswordField.getStyleClass().removeAll("fieldInvalid","fieldValid","passwordFields");
                confirmNewPasswordField.getStyleClass().add("fieldInvalid");
                okBtn.setDisable(true);
            }
        });
    }

    public PasswordField getOldPasswordField() {
        return oldPasswordField;
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

    public void clickCancelBtn(ActionEvent actionEvent){
        oldPasswordField.getScene().getWindow().hide();
    }
}
