import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ResourceBundle;

public class RunController implements Initializable {
    @FXML
    private ListView<String> listItems;
    @FXML
    private VBox controls;
    @FXML
    private TextField sendGuessText;
    @FXML
    private Button readyButton;
    @FXML
    private Button button1;
    @FXML
    private Button button2;
    @FXML
    private Button button3;
    @FXML
    private Button button4;
    @FXML
    private Button button5;

    private Client clientConnection;
    private String ipNum, portNum;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        controls.setVisible(false);
    }

    public void addData(Serializable data) {
        listItems.getItems().add(data.toString());
    }

    public void setPortIP(String port,String ip) {
        ipNum = ip;
        portNum = port;
    }

    public void setClient(Client client) {
        this.clientConnection = client;
    }

    public void setSendGuessButton() throws IOException {
        if (clientConnection.play != 0) {
            clientConnection.guess = Integer.parseInt(sendGuessText.getText());
            clientConnection.send();
            sendGuessText.clear();
        } else {

        }
    }

    private void enableButtons() {
        button1.setDisable(false);
        button2.setDisable(false);
        button3.setDisable(false);
        button4.setDisable(false);
        button5.setDisable(false);
    }

    public void button1() throws IOException {
        enableButtons();
        button1.setDisable(true);
        clientConnection.play = 1;
    }

    public void button2() throws IOException {
        enableButtons();
        button2.setDisable(true);
        clientConnection.play = 2;
    }

    public void button3() throws IOException {
        enableButtons();
        button3.setDisable(true);
        clientConnection.play = 3;
    }

    public void button4() throws IOException {
        enableButtons();
        button4.setDisable(true);
        clientConnection.play = 4;
    }

    public void button5() throws IOException {
        enableButtons();
        button5.setDisable(true);
        clientConnection.play = 5;
    }

    public void readyButton() {
        clientConnection.setReady();
        readyButton.setDisable(true);
    }

    public void enableControls() {
        controls.setVisible(true);
    }
}
