package ba.unsa.etf.rpr.project;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginWindowController implements Initializable {

    public TextField usernameField;
    public PasswordField passwordField;
    public Button confirmLogin;
    public Label statusLabelImage;
    public Label statusLabelText;
    private int failCounter;

    public LoginWindowController(){
        failCounter = 0;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle ){
        usernameField.setPromptText("Username");
        passwordField.setPromptText("Password");
        
    }

    public void clickLogin(ActionEvent actionEvent) throws InterruptedException {

//            statusLabelText.setText("Processing...");
//            ImageView processingImage = new ImageView("/Images/processing.png");
//            processingImage.setFitWidth(25);
//            processingImage.setFitHeight(25);
//            statusLabelImage.setGraphic(processingImage);
//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//
//
//        if( usernameField.getText().equals("admin") && passwordField.getText().equals("admin") ) {
//            statusLabelText.setText("Confirmed");
//            ImageView confirmationImage = new ImageView("/Images/confirmation.png");
//            confirmationImage.setFitWidth(25);
//            confirmationImage.setFitHeight(25);
//            statusLabelImage.setGraphic(confirmationImage);
//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//
//        else{
//
//                statusLabelText.setText("Denied");
//                ImageView denialImage = new ImageView("/Images/denial.png");
//                denialImage.setFitWidth(25);
//                denialImage.setFitHeight(25);
//                statusLabelImage.setGraphic(denialImage);
//                usernameField.setText("");
//                passwordField.setText("");
//                failCounter++;
//
//        }
//
//        if( statusLabelText.getText().equals("Confirmed") )
//            statusLabelText.getScene().getWindow().hide();

        Thread processThread = new Thread( () -> {
            statusLabelText.setText("Processing...");
            ImageView processingImage = new ImageView("/Images/processing.png");
            processingImage.setFitWidth(25);
            processingImage.setFitHeight(25);
            statusLabelImage.setGraphic(processingImage);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } );

        processThread.start();
        processThread.join();


//            Thread confirmationThread = new Thread( () -> {
//
//                statusLabelText.setText("Confirmed");
//                ImageView confirmationImage = new ImageView("/Images/confirmation.png");
//                confirmationImage.setFitWidth(25);
//                confirmationImage.setFitHeight(25);
//                statusLabelImage.setGraphic(confirmationImage);
//                try {
//                    Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            } );
//
//
//            Thread denialThread = new Thread(() -> {
//                statusLabelText.setText("Denied");
//                ImageView denialImage = new ImageView("/Images/denial.png");
//                denialImage.setFitWidth(25);
//                denialImage.setFitHeight(25);
//                statusLabelImage.setGraphic(denialImage);
//                usernameField.setText("");
//                passwordField.setText("");
//                failCounter++;
//                try {
//                    Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            });
//
//            if( usernameField.getText().equals("admin") && passwordField.getText().equals("admin") ){
//                confirmationThread.start();
//                confirmationThread.join();
//            }
//            else{
//                denialThread.start();
//                denialThread.join();
//            }

        statusLabelText.getScene().getWindow().setOnCloseRequest(event -> {
            processThread.stop();
        });
    }
}
