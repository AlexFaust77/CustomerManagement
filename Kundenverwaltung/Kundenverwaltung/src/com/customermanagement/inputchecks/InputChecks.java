package com.customermanagement.inputchecks;

public class InputChecks {

	
	// check if input is Date and the correct Special Characters are use
	public void inputChecks() {   // Logger - String Textfield - Gui
	
		myTextField.textProperty().addListener((observable, oldValue, newValue) -> {
				System.out.println("TextField Text Changed (newValue: " + newValue + ")");
		});
	
	
	
	}
}
