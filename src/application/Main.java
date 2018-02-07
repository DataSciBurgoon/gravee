package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	
	private Stage primaryStage;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			this.primaryStage = primaryStage;
			BorderPane root = new BorderPane();
			//Scene scene = new Scene(root,400,400);
			
			this.primaryStage.setTitle("GRAVEE: Good Risk Assessment Values for Environmental Exposures");
			FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("gravee.fxml"));
            AnchorPane sbne_overview = (AnchorPane) loader.load(); 
            Scene scene = new Scene(sbne_overview);
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			this.primaryStage.setScene(scene);
			this.primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
