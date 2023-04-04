import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.io.Serializable;
import java.net.URL;
import java.util.ResourceBundle;

public class RunController implements Initializable {
    @FXML
    ListView<String> listItems;

    private String portNum;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void addData(Serializable data) {
        listItems.getItems().add(data.toString());
    }

    public void setPort(String port) {
        portNum = port;
    }
}
