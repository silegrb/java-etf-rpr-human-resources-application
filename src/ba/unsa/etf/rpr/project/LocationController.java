package ba.unsa.etf.rpr.project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LocationController implements Initializable {

    private HumanResourcesDAO dao;

    {
        try {
            dao = HumanResourcesDAO.getInstance();
        } catch (FileNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public TextField fieldPostalCode = new TextField();
    public TextField fieldStreetAddress = new TextField();
    public ChoiceBox<String> cbCities = new ChoiceBox<>();
    private Location currentLocation;
    public Button okBtn = new Button();

    public LocationController( Location currentLocation ){
        this.currentLocation = currentLocation;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        okBtn.setDisable(true);
        if( currentLocation != null ){
            fieldPostalCode.setText( currentLocation.getPostalCode() );
            fieldStreetAddress.setText( currentLocation.getStreetAddress() );
            cbCities.setValue( currentLocation.getCity().getName() );
            okBtn.setDisable(false);

            //Lets set styleClasses for this case.
            fieldPostalCode.getStyleClass().removeAll("fieldValid","fieldInvalid","controllerFields");
            fieldPostalCode.getStyleClass().add( "fieldValid" );
            fieldStreetAddress.getStyleClass().removeAll("fieldValid","fieldInvalid","controllerFields");
            fieldStreetAddress.getStyleClass().add( "fieldValid" );
            cbCities.getStyleClass().removeAll("fieldValid","fieldInvalid","controllerFields");
            cbCities.getStyleClass().add( "fieldValid" );
        }
        ObservableList<String> cityNames = FXCollections.observableArrayList();
        for ( City c: dao.getCities())
            cityNames.add( c.getName() );
        cbCities.setItems( cityNames );

        //Duplication of code must be done because of the condition in "else" block.
        //In that condition there is validation on two other fields.

        fieldPostalCode.textProperty().addListener((observable, oldValue, newValue) -> {
            if( newValue.equals("") ){
                fieldPostalCode.getStyleClass().removeAll("fieldValid","fieldInvalid","controllerFields");
                fieldPostalCode.getStyleClass().add("controllerFields");
                okBtn.setDisable(true);
            }
            else {
                fieldPostalCode.getStyleClass().removeAll("fieldValid","fieldInvalid","controllerFields");
                fieldPostalCode.getStyleClass().add("fieldValid");
                if( fieldStreetAddress.getStyleClass().contains("fieldValid") && cbCities.getStyleClass().contains("fieldValid") )
                    okBtn.setDisable(false);
            }
        });

        fieldStreetAddress.textProperty().addListener((observable, oldValue, newValue) -> {
            if( newValue.equals("") ){
                fieldStreetAddress.getStyleClass().removeAll("fieldValid","fieldInvalid","controllerFields");
                fieldStreetAddress.getStyleClass().add("controllerFields");
                okBtn.setDisable(true);
            }
            else {
                fieldStreetAddress.getStyleClass().removeAll("fieldValid","fieldInvalid","controllerFields");
                fieldStreetAddress.getStyleClass().add("fieldValid");
                if( fieldStreetAddress.getStyleClass().contains("fieldValid") && cbCities.getStyleClass().contains("fieldValid") )
                    okBtn.setDisable(false);
            }
        });

        cbCities.valueProperty().addListener((observable, oldValue, newValue) -> {
            if( newValue.equals("") ){
                cbCities.getStyleClass().removeAll("fieldValid","fieldInvalid","controllerFields");
                cbCities.getStyleClass().add("controllerFields");
                okBtn.setDisable(true);
            }
            else {
                cbCities.getStyleClass().removeAll("fieldValid","fieldInvalid","controllerFields");
                cbCities.getStyleClass().add("fieldValid");
                if( fieldStreetAddress.getStyleClass().contains("fieldValid") && fieldPostalCode.getStyleClass().contains("fieldValid") )
                    okBtn.setDisable(false);
            }

        });
    }

    public void clickOkBtn(ActionEvent actionEvent) throws SQLException {
        if( currentLocation == null ){
            dao.addLocation( getLocationFromWindow() );
            fieldStreetAddress.getScene().getWindow().hide();
        }
        else{
            dao.changeLocation( getLocationFromWindow() );
            fieldStreetAddress.getScene().getWindow().hide();
        }
    }

    public void clickCancelBtn(ActionEvent actionEvent){
        fieldStreetAddress.getScene().getWindow().hide();
    }

    public void clickOnQuicklyAddCity(ActionEvent actionEvent){
        Stage secondaryStage = new Stage();
        FXMLLoader secondaryLoader = new FXMLLoader(getClass().getResource("/FXML/cityWindow.fxml"));
        CityController cc = new CityController( null );
        secondaryLoader.setController(cc);
        Parent secondaryRoot = null;
        try {
            secondaryRoot = secondaryLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        secondaryStage.setTitle("Add city");
        secondaryStage.setResizable(false);
        secondaryStage.initModality(Modality.APPLICATION_MODAL);
        secondaryStage.setScene(new Scene(secondaryRoot, 370, 150));
        secondaryStage.show();
        secondaryStage.setOnHidden(event -> {
            //If OK button in CountryController is not clicked, nothing will happen.
            if( cc.isOkBtnClicked() ) {
                dao.clearData();
                try {
                    dao.fetchData();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                ObservableList<String> cityNames = FXCollections.observableArrayList();
                for (City c : dao.getCities())
                    cityNames.add(c.getName());
                cbCities.setItems(cityNames);
            }
        });

    }

    private Location getLocationFromWindow(){
        int index;
        if( currentLocation != null )
            index = currentLocation.getId();
        else index = dao.nextIndex("Location");
        City city = new City(-1,"Unknown",null);
        for (City c: dao.getCities())
            if( c.getName().equals( cbCities.getValue() ) ){
                System.out.println("OK");
                city = c;
                break;
            }

            return new Location( index, fieldPostalCode.getText(), fieldStreetAddress.getText(), city );
    }

}
