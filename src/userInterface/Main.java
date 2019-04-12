/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Icesi University (Cali - Colombia)
 * Faculty of Engineering (algorithms and programming 2)
 * laboratory 4
 * By: Carlos Andrés Restrepo Marín 
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ 
 */
package userInterface;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	
	@Override
	/**
	 * this method allows start the application
	 * 
	 */
	public void start(Stage stage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("screen.FXML"));
		Parent root = loader.load();
		ScreenController sc = loader.getController();
		
		sc.setStage(stage);
		
		Scene scene = new Scene(root);
		String c = Main.class.getResource("styleScreen.css").toExternalForm();
		scene.getStylesheets().add(c);
		stage.setTitle("DEPARTURES");
		stage.setScene(scene);
		stage.show();
		
		
	}
	
	/**
	 * this method allows run the program
	 * @param args, unused
	 */
	public static void main(String[] args) {
		launch(args);
	
	}

	

}
