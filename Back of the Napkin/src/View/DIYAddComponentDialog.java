package View;

import java.math.BigDecimal;
import java.util.Optional;

import Model.Component;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

/**
 * 
 * @author Aaron Bardsley
 *
 */
public class DIYAddComponentDialog extends Dialog<Component> {
	
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
		Label manHrsLabel = new Label("Man Hours Amount: ");
		Label costPerManHrLabel = new Label("Cost Per Man-hour: ");
		
		TextField nameField = new TextField();
		TextField initialCostField = new TextField();
		TextField monthlyCostField = new TextField();
		TextField lengthField = new TextField();
		TextField widthField = new TextField();
		TextField heightField = new TextField();
		TextField radiusField = new TextField();
		TextField weightField = new TextField();
		TextField materialsField = new TextField();
		TextField manHrsField = new TextField();
		TextField costPerManHrsField = new TextField();
		
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
		grid.add(manHrsLabel, 1, 10);
		grid.add(costPerManHrLabel, 1, 11);
		grid.add(nameField, 2, 1);
		grid.add(initialCostField, 2, 2);
		grid.add(monthlyCostField, 2, 3);
		grid.add(lengthField, 2, 4);
		grid.add(widthField, 2, 5);
		grid.add(heightField, 2, 6);
		grid.add(radiusField, 2, 7);
		grid.add(weightField, 2, 8);
		grid.add(materialsField, 2, 9);
		grid.add(manHrsField, 2, 10);
		grid.add(costPerManHrsField, 2, 11);
		
		this.getDialogPane().setContent(grid);

		ButtonType cancelButton = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
		this.getDialogPane().getButtonTypes().add(cancelButton);
		ButtonType applyButton = new ButtonType("Apply", ButtonData.APPLY);
		this.getDialogPane().getButtonTypes().add(applyButton);
		
		this.setResultConverter(new Callback<ButtonType, Component>() {
			@Override
			public Component call(ButtonType b) {				
				if (b == applyButton) {
					return new Component(0, nameField.getText(), 
							new BigDecimal(initialCostField.getText()), 
							new BigDecimal(monthlyCostField.getText()), 
							Double.parseDouble(lengthField.getText()), 
							Double.parseDouble(widthField.getText()), 
							Double.parseDouble(heightField.getText()), 
							Double.parseDouble(radiusField.getText()), 
							Double.parseDouble(weightField.getText()), 
							materialsField.getText(), 
							Double.parseDouble(manHrsField.getText()), 
							new BigDecimal(costPerManHrsField.getText()), 
							null); //Temporary null for nested components (Aaron)
				}
				return null;
			}			
		});
		
	}
	
	public Optional<Component> view() {
		return this.showAndWait();
	}
	
}
