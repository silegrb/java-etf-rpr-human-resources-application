package ba.unsa.etf.rpr.project;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class ErrorReportController implements Initializable {

    ObservableList<String> errors;
    public ListView lwErrors = new ListView();

    public ErrorReportController( ObservableList<String> errors ){
        this.errors = errors;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        lwErrors.setItems( errors );
    }

    public void clickCloseBtn(ActionEvent actionEvent){
        lwErrors.getScene().getWindow().hide();
    }
}
