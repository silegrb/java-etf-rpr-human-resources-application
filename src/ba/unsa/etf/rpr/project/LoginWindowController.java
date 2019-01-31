package ba.unsa.etf.rpr.project;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class LoginWindowController implements Initializable {
    private static HumanResourcesDAO dao;
    public TextField usernameField;
    public PasswordField passwordField;
    public Button confirmLogin;
    public Label statusLabelImage;
    public Label statusLabelText;
    private int failCounter;

    public LoginWindowController() throws FileNotFoundException, SQLException {
        failCounter = 3;
        dao = HumanResourcesDAO.getInstance();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle ){
        usernameField.setPromptText("Username");
        passwordField.setPromptText("Password");
        
    }

    public static void pause() throws InterruptedException {
        Thread.sleep(2000);
    }

    public void clickLogin(ActionEvent actionEvent) throws InterruptedException {

        new Thread( () -> {
            Platform.runLater( () -> {
                statusLabelText.setText("Processing, please wait...");
                ImageView processingImage = new ImageView("/Images/processing.png");
                processingImage.setFitWidth(25);
                processingImage.setFitHeight(25);
                statusLabelImage.setGraphic(processingImage);
            } );
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Platform.runLater( () -> {
                boolean userFound = false;
                for ( Administrator a : dao.getAdministrators() )
                    if (a.getUsername().equals( usernameField.getText() ) && a.getPassword().equals(passwordField.getText())) {
                        userFound = true;
                        break;
                    }
                if( userFound ){
                    statusLabelText.setText("Confirmed");
                    ImageView confirmationImage = new ImageView("/Images/confirmation.png");
                    confirmationImage.setFitWidth(25);
                    confirmationImage.setFitHeight(25);
                    statusLabelImage.setGraphic(confirmationImage);
                }
                else{
                    failCounter--;
                    String errorString = "Denied (";
                    errorString += failCounter;
                    if( failCounter == 1 ) errorString += " try ";
                    else errorString += " tries ";
                    errorString += "left)";
                    statusLabelText.setText( errorString );
                    ImageView denialImage = new ImageView("/Images/denial.png");
                    denialImage.setFitWidth(25);
                    denialImage.setFitHeight(25);
                    statusLabelImage.setGraphic(denialImage);
                    usernameField.setText("");
                    passwordField.setText("");
                    if( failCounter == 0 ){
                        confirmLogin.setDisable(true);
                        usernameField.setEditable(false);
                        passwordField.setEditable(false);
                    }
                }
            } );
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Platform.runLater( () -> {
                if( statusLabelText.getText().equals("Confirmed") )
                    statusLabelText.getScene().getWindow().hide();
                if( failCounter == 0 ){
                    statusLabelText.getScene().getWindow().hide();
                }
            } );

        } ).start();

    }


}
