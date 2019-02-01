package ba.unsa.etf.rpr.project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/FXML/loginWindow.fxml") );
        LoginWindowController lwc = new LoginWindowController();
        loader.setController( lwc );
        Parent root = loader.load();
        primaryStage.setTitle("Login");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, 220, 140));
        primaryStage.show();
        primaryStage.setOnHidden(event -> {
            if( lwc.loginSuccess() ) {
                Stage secondaryStage = new Stage();
                FXMLLoader secondaryLoader = new FXMLLoader(getClass().getResource("/FXML/humanResourcesController.fxml"));
                HumanResourcesController hrc = new HumanResourcesController();
                secondaryLoader.setController(hrc);
                Parent secondaryRoot = null;
                try {
                    secondaryRoot = secondaryLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                secondaryStage.setTitle("Human Resources");
                secondaryStage.setResizable(false);
                secondaryStage.setScene(new Scene(secondaryRoot, 750, 500));
                secondaryStage.show();
            }
        });


    }


    public static void main(String[] args) {
        launch(args);
    }
}

