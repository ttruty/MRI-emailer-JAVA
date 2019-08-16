package controllers;
	
import java.io.IOException;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;


public class Main extends Application {	
	public static void main(String[] args) { launch(args); }
	 @Override public void start(Stage stage) throws IOException {
		    Scene scene = new Scene(new StackPane());
		    
		    LoginManager loginManager = new LoginManager(scene);
		    loginManager.showLoginScreen();

		    stage.setScene(scene);
		    stage.setTitle("MRI Emailer");
		    stage.setHeight(605);
		    stage.setWidth(520);
		    stage.show();	
		    
		    
		    //launch(args);
		//System.out.println("Reading Data");
		//StringBuffer data = ReadData.getDataFromFile("Resources/test_mmse.txt");
		
	}
	 
	 
}
