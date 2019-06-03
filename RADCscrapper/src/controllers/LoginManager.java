package controllers;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

/** Manages control flow for logins */
public class LoginManager {

	private Scene scene;

	public LoginManager(Scene scene) {
		this.scene = scene;
	}

	/**
	 * Callback method invoked to notify that a user has been authenticated. Will
	 * show the main application screen.
	 */
	public void authenticated() {
		showMainView();
	}

	/**
	 * Callback method invoked to notify that a user has logged out of the main
	 * application. Will show the login application screen.
	 */
	public void logout() {
		showLoginScreen();
	}

	public void showLoginScreen() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/login.fxml"));
			scene.setRoot((Parent) loader.load());
			LoginController controller = loader.getController();
			controller.initManager(this);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	private void showMainView() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/main.fxml"));
			scene.setRoot((Parent) loader.load());
			MainViewController controller = loader.getController();
			controller.start(this);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
