package it.polito.tdp.rubrica;
	
import it.polito.tdp.rubrica.model.RubricaModel;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Rubrica.fxml")) ;
			BorderPane root = (BorderPane)loader.load();
			//BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("Rubrica.fxml"));
			
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			RubricaController controller = loader.getController() ;
			RubricaModel model = new RubricaModel() ;
			controller.setModel(model) ;
			
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
