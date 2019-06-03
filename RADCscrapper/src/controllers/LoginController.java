package controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import models.HTMLUnit;

/** Controls the login screen */
public class LoginController {
	
	@FXML private MenuBar fileMenuBar;
	@FXML private TextField user;
	@FXML private PasswordField password;
	@FXML private Button loginButton;
	@FXML private Label statusLabel;
	@FXML private ImageView  rushLogo;
	
	public static HTMLUnit webClient;
  
  private LoginManager manager;
 // public void initialize() {}
  
  public void initManager(LoginManager loginManager) {
	  manager = loginManager;
	  //model = new LoginModel();
	  
	  Image logo = new Image("file:resources/rushLogo.jpg");
	  rushLogo.setImage(logo);
	  
	   loginButton.setOnAction((e) -> {
//	    	  String userName = authorize();
//	          if (userName!= null) 
//	            loginManager.authenticated(userName);
		  login();
		});
	 
  }
  
  public void login() {
		String username = this.user.getText();
		String password = this.password.getText();

		// Validations
		
		if (username == null || username.trim().equals("") && 
				   (password == null || password.trim().equals(""))) {
			statusLabel.setText("User name / password Cannot be empty or spaces");
					return;
				}
		
		if (username == null || username.trim().equals("")) {
			statusLabel.setText("Username cannot be empty or spaces");
			return;

		}
		if (password == null || password.trim().equals("")) {
			statusLabel.setText("Password cannot be empty or spaces");
			return;
		}
		
		// authentication check
		checkCredentials(username, password);
	}
  
  public void checkCredentials(String username, String password) {
		Boolean isValid = true;
		
		System.out.println("********************");
		webClient = new HTMLUnit();
		try {
			webClient.loginForm(username, password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// model.getCredentials(username, password);
		if (!webClient.isAuthenticated()) {
			statusLabel.setText("Check username and password!");
			return;
		}
		else {
			manager.authenticated();
		}

	}
  
  @FXML
  public void aboutHelp(ActionEvent event) {
	  // select file from the menu bar
	  Alert alert = new Alert(AlertType.INFORMATION,
 	           "MRI EMAILER TOOL\n\n\n"
 	         + "Generate an Email for the car service\n "
 	         + " CREATED BY TIM TRUTY\n"
 	         + " Summer 2019 \n"
 	         + " RUSH ALZHEIMER'S DISEASE CENTER\n"
 	         + " CHICAGO IL\n");


 	 alert.setTitle("About info");
 	 alert.showAndWait();
  } //end aboutmenu
  
  
  @FXML
  public void closeApp(ActionEvent event) {
	  //select close on the menu bar
	  Platform.exit();
	  System.exit(0);
	  } //end closeApp


}
