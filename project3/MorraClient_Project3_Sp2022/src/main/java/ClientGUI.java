import javafx.application.Application;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;

public class ClientGUI extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/FXML/ClientStart.fxml"));
			primaryStage.setTitle("Morra Client");
			Scene scene = new Scene(root, 900,600);
			scene.getStylesheets().add("/styles/ClientStyle.css");

			primaryStage.setOnCloseRequest(t -> {
				Platform.exit();
				System.exit(0);
			});

			primaryStage.setScene(scene);
			primaryStage.show();
		}
		catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
}
