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
    private TextField enterPort;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void startServerButton() throws IOException {
        String portNum = enterPort.getText();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/ServerRun.fxml"));
        loader.getNamespace().put("portNum", "Server is running on port " + portNum);
        Parent root2 = loader.load();                           //load view into parent
        root2.getStylesheets().add("/styles/ServerStyle.css");  //set style

        RunController controller = loader.getController();  // create a RunController

        if (!portNum.equals("")) {
            root.getScene().setRoot(root2); //update scene graph

            //start server on port
            Server serverConnection = new Server(data -> Platform.runLater(() -> controller.addData(data)));
            serverConnection.startServer(portNum);
        } else {
            System.out.println("No port to start server!");
        }
    }
}
