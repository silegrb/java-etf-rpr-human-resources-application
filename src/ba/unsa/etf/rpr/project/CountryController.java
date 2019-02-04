package ba.unsa.etf.rpr.project;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CountryController implements Initializable {

    private HumanResourcesDAO dao;

    {
        try {
            dao = HumanResourcesDAO.getInstance();
        } catch (FileNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private Country currentCountry;
    public TextField fieldName = new TextField();
    public Button okBtn = new Button();
    public ChoiceBox<String> cbContinents = new ChoiceBox<>();
    private boolean okBtnClicked = false;

    public CountryController( Country currentCountry ){
        this.currentCountry = currentCountry;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        okBtn.setDisable(true);
        if( currentCountry != null ){
            fieldName.setText( currentCountry.getName() );
            cbContinents.setValue( currentCountry.getContinent().getName() );
            okBtn.setDisable(false);

            //Lets set styleClasses for this case.
            fieldName.getStyleClass().removeAll("fieldValid","fieldInvalid","controllerFields");
            fieldName.getStyleClass().add( "fieldValid" );
            cbContinents.getStyleClass().removeAll("fieldValid","fieldInvalid","controllerFields");
            cbContinents.getStyleClass().add( "fieldValid" );
        }

        ObservableList<String> continentNames = FXCollections.observableArrayList();
        for ( Continent c: dao.getContinents())
            continentNames.add( c.getName() );
        cbContinents.setItems( continentNames );

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
                if( cbContinents.getStyleClass().contains("fieldValid") )
                    okBtn.setDisable(false);
            }
        });

        cbContinents.valueProperty().addListener((observable, oldValue, newValue) -> {
            if( newValue.equals("") ){
                cbContinents.getStyleClass().removeAll("fieldValid","fieldInvalid","controllerFields");
                cbContinents.getStyleClass().add("controllerFields");
                okBtn.setDisable(true);
            }
            else {
                cbContinents.getStyleClass().removeAll("fieldValid","fieldInvalid","controllerFields");
                cbContinents.getStyleClass().add("fieldValid");
                if( fieldName.getStyleClass().contains("fieldValid") )
                    okBtn.setDisable(false);
            }
        });
    }

    private Country getCountryFromWindow(){
        int index;
        if( currentCountry != null )
            index = currentCountry.getId();
        else index = dao.nextIndex("Country");
        Continent continent = new Continent(-1,"Unknown");
        for (Continent c: dao.getContinents())
            if( c.getName().equals( cbContinents.getValue() ) ){
                System.out.println("OK");
                continent = c;
                break;
            }

        return new Country( index, fieldName.getText(), continent );
    }

    public void clickOkBtn(ActionEvent actionEvent) throws SQLException {
        okBtnClicked = true;
        if( currentCountry == null ){
            dao.addCountry( getCountryFromWindow() );
            fieldName.getScene().getWindow().hide();
        }
        else{
            dao.changeCountry( getCountryFromWindow() );
            fieldName.getScene().getWindow().hide();
        }
    }

    public void clickCancelBtn(ActionEvent actionEvent){
        fieldName.getScene().getWindow().hide();
    }

    public boolean isOkBtnClicked() {
        return okBtnClicked;
    }

}
