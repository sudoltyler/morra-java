import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StartController implements Initializable {
    @FXML
    private BorderPane root;
    @FXML
    private TextField enterIP;
    @FXML
    private TextField enterPort;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void connectClientButton() throws IOException {
        String ipNum = enterIP.getText();
        String portNum = enterPort.getText();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/ClientRun.fxml"));
        loader.getNamespace().put("ipNum", "Client is connected to IP: " + ipNum);
        loader.getNamespace().put("portNum", " on port: " + portNum);
        Parent root2 = loader.load();                       //load view into parent
        root2.getStylesheets().add("/styles/ClientStyle.css");  //set style
        root.getScene().setRoot(root2); //update scene graph

        RunController controller = loader.getController();
        controller.setPortIP(enterPort.getText(),enterIP.getText());

        // start client connection, send data to run scene controller
        Client clientConnection = new Client(data -> Platform.runLater(() -> controller.addData(data)));
        clientConnection.start();
        clientConnection.setPortIp(Integer.parseInt(enterPort.getText()),enterIP.getText());
        controller.setClient(clientConnection);
    }
}
