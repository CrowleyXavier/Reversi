package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage stage) {
		try {
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("Form.fxml"));
			Scene scene = new Scene(root,400,400);
			//scene.setFill(Color.GREEN);

			stage.setScene(scene);
			stage.setTitle("Reversi");
		//	stage.initStyle(StageStyle.UNDECORATED);
			stage.show();

		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
