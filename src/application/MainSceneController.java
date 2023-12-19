package application;

import javafx.fxml.FXML;

import javafx.event.ActionEvent;

public class MainSceneController {

	// Event Listener on Button.onAction
	@FXML
	public void aguaBoton(ActionEvent event) {
		System.out.println("Le has dado al boton del agua!");
	}
}
