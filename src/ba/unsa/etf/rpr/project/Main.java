package ba.unsa.etf.rpr.project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {

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

    @Override
    public void start(Stage primaryStage) throws Exception{
       Locale.setDefault(new Locale("en_US"));
        ResourceBundle bundle = ResourceBundle.getBundle("Translation/Translation");
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/FXML/loginWindow.fxml"), bundle );
        LoginWindowController lwc = new LoginWindowController();
        loader.setController( lwc );
        Parent root = loader.load();
        primaryStage.setTitle("Login");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, 220, 140));
        primaryStage.show();
        primaryStage.setOnHidden(event -> {
            if( lwc.loginSuccess() ) {
                Date date = new Date();
                try {
                    dao.recordUserLogin( lwc.getUsernameField().getText(), date.toString() );
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                Stage secondaryStage = new Stage();
                FXMLLoader secondaryLoader = new FXMLLoader(getClass().getResource("/FXML/humanResourcesController.fxml"),bundle);
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



    public static void main(String[] args) {
        launch(args);
    }
}

