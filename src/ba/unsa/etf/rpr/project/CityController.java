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

public class CityController implements Initializable {

    private HumanResourcesDAO dao;

    {
        try {
            dao = HumanResourcesDAO.getInstance();
        } catch (FileNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private City currentCity;
    public TextField fieldName = new TextField();
    public Button okBtn = new Button();
    public ChoiceBox<String> cbCountries = new ChoiceBox<>();

    public CityController( City currentCity ){
        this.currentCity = currentCity;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        okBtn.setDisable(true);
        if( currentCity != null ){
            fieldName.setText( currentCity.getName() );
            cbCountries.setValue( currentCity.getCountry().getName() );

            //Lets set styleClasses for this case.
            fieldName.getStyleClass().removeAll("fieldValid","fieldInvalid","controllerFields");
            fieldName.getStyleClass().add( "fieldValid" );
            cbCountries.getStyleClass().removeAll("fieldValid","fieldInvalid","controllerFields");
            cbCountries.getStyleClass().add( "fieldValid" );
        }

        ObservableList<String> countryNames = FXCollections.observableArrayList();
        for ( Country c: dao.getCountries())
            countryNames.add( c.getName() );
        cbCountries.setItems( countryNames );

        //Duplication of code must be done because of the condition in "else" block.
        //In that condition there is validation on two other fields.

        fieldName.textProperty().addListener((observable, oldValue, newValue) -> {
            if( newValue.equals("") ){
                fieldName.getStyleClass().removeAll("fieldValid","fieldInvalid","controllerFields");
                fieldName.getStyleClass().add("controllerFields");
                okBtn.setDisable(true);
            }
            else {
                fieldName.getStyleClass().removeAll("fieldValid","fieldInvalid","controllerFields");
                fieldName.getStyleClass().add("fieldValid");
                if( cbCountries.getStyleClass().contains("fieldValid") )
                    okBtn.setDisable(false);
            }
        });

        cbCountries.valueProperty().addListener((observable, oldValue, newValue) -> {
            if( newValue.equals("") ){
                cbCountries.getStyleClass().removeAll("fieldValid","fieldInvalid","controllerFields");
                cbCountries.getStyleClass().add("controllerFields");
                cbCountries.setDisable(true);
            }
            else {
                cbCountries.getStyleClass().removeAll("fieldValid","fieldInvalid","controllerFields");
                cbCountries.getStyleClass().add("fieldValid");
                if( fieldName.getStyleClass().contains("fieldValid") )
                    okBtn.setDisable(false);
            }
        });
    }

    public void clickOnQuicklyAddCountry(ActionEvent actionEvent){
        Stage secondaryStage = new Stage();
        FXMLLoader secondaryLoader = new FXMLLoader(getClass().getResource("/FXML/countryWindow.fxml"));
        CountryController cc = new CountryController( null );
        secondaryLoader.setController(cc);
        Parent secondaryRoot = null;
        try {
            secondaryRoot = secondaryLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        secondaryStage.setTitle("Add country");
        secondaryStage.setResizable(false);
        secondaryStage.initModality(Modality.APPLICATION_MODAL);
        secondaryStage.setScene(new Scene(secondaryRoot, 370, 120));
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
                cbCountries.getItems().clear();
                ObservableList<String> countryNames = FXCollections.observableArrayList();
                for (Country c : dao.getCountries())
                    countryNames.add(c.getName());
                cbCountries.setItems(countryNames);
                cbCountries.setValue(dao.getCountries().get(dao.getCountries().size() - 1).getName());
            }
        });

    }

    private City getCityFromWindow(){

        int index;
        if( currentCity != null )
            index = currentCity.getId();
        else index = dao.nextIndex("City");
        Country country = new Country(-1,"Unknown",null );
        for (Country c: dao.getCountries())
            if( c.getName().equals( cbCountries.getValue() ) ){
                System.out.println("OK");
                country = c;
                break;
            }

        return new City( index, fieldName.getText(), country );
    }

    public void clickOkBtn(javafx.event.ActionEvent actionEvent) throws SQLException {
        if( currentCity == null ){
            dao.addCity( getCityFromWindow() );
            fieldName.getScene().getWindow().hide();
        }
        else{
            dao.changeCity( getCityFromWindow() );
            fieldName.getScene().getWindow().hide();
        }
    }

    public void clickCancelBtn(javafx.event.ActionEvent actionEvent) throws SQLException {
        dao.deleteCountry( dao.getCountries().get( dao.getCountries().size() - 1 ) );
        dao.clearData();
        dao.fetchData();
        fieldName.getScene().getWindow().hide();
    }


}
