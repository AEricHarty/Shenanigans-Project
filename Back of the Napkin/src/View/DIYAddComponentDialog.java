package View;

import java.util.Optional;

import Model.Component;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

public class DIYAddComponentDialog extends Dialog<Component> {
	
	private Component myNewComponent;	
	
	public DIYAddComponentDialog() {
		this.setTitle("Create a new component");
		this.setResizable(true);
		
		Label nameLabel = new Label("Name: ");
		Label initialCostLabel = new Label("Initial Cost: ");
		Label monthlyCostLabel = new Label("Monthly Cost: ");
		Label lengthLabel = new Label("Length: ");
		Label widthLabel = new Label("Width: ");
		Label heightLabel = new Label("Height: ");
		Label radiusLabel = new Label("Radius: ");
		Label weightLabel = new Label("Weight: ");
		Label materialsLabel = new Label("Materials: ");
		Label costPerManHrLabel = new Label("Cost Per Man-hour: ");
		
		TextField text1 = new TextField();
		TextField text2 = new TextField();
		TextField text3 = new TextField();
		TextField text4 = new TextField();
		TextField text5 = new TextField();
		TextField text6 = new TextField();
		TextField text7 = new TextField();
		TextField text8 = new TextField();
		TextField text9 = new TextField();
		TextField text10 = new TextField();
		
		GridPane grid = new GridPane();
		grid.add(nameLabel, 1, 1);
		grid.add(initialCostLabel, 1, 2);
		grid.add(monthlyCostLabel, 1, 3);
		grid.add(lengthLabel, 1, 4);
		grid.add(widthLabel, 1, 5);
		grid.add(heightLabel, 1, 6);
		grid.add(radiusLabel, 1, 7);
		grid.add(weightLabel, 1, 8);
		grid.add(materialsLabel, 1, 9);
		grid.add(costPerManHrLabel, 1, 10);
		grid.add(text1, 2, 1);
		grid.add(text2, 2, 2);
		grid.add(text3, 2, 3);
		grid.add(text4, 2, 4);
		grid.add(text5, 2, 5);
		grid.add(text6, 2, 6);
		grid.add(text7, 2, 7);
		grid.add(text8, 2, 8);
		grid.add(text9, 2, 9);
		grid.add(text10, 2, 10);
		
		this.getDialogPane().setContent(grid);

		ButtonType cancelButton = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
		this.getDialogPane().getButtonTypes().add(cancelButton);
		ButtonType applyButton = new ButtonType("Apply", ButtonData.APPLY);
		this.getDialogPane().getButtonTypes().add(applyButton);
		
		this.setResultConverter(new Callback<ButtonType, Component>() {
			@Override
			public Component call(ButtonType b) {				
				if (b == applyButton) {
					return myNewComponent; //temp
				}
				return null;
			}			
		});
		
	}
	
	public Optional<Component> view() {
		return this.showAndWait();
	}
	
}
