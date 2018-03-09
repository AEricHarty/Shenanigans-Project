package View;

import Model.Component;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class DIYProjectComponent extends BorderPane {
	
	private final Component myComponent;
	
	public DIYProjectComponent(Component theComponent) {
		myComponent = theComponent;
		
		Label Title = new Label(myComponent.getName());
		Label CostLabel = new Label("Cost:");
		Label MonthlyLabel = new Label("Monthly Cost:");
		Label Cost = new Label("$"+myComponent.getCost().toString());
		Label MonthlyCost = new Label("$"+myComponent.getCostPerMonth().toString());
		
		setTop(Title);
		
		GridPane inner = new GridPane();
		
		inner.add(CostLabel, 1, 1);
		inner.add(Cost, 2, 1);
		inner.add(MonthlyLabel, 1, 2);
		inner.add(MonthlyCost, 2, 2);
	}
}
