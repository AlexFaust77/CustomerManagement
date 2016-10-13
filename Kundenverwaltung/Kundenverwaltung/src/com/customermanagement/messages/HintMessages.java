package com.customermanagement.messages;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public class HintMessages {

	public Optional<ButtonType> inputFailMessage(String title, String headerText, String contentText) {
		
		Alert infoMessage = new Alert(AlertType.INFORMATION);
			  infoMessage.setTitle(title);
			  infoMessage.setHeaderText(headerText);
			  infoMessage.setContentText(contentText);
        Optional<ButtonType> result = infoMessage.showAndWait();
		
	return result;	
	}
	
	
}
