package View;

import Model.Component;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

/**
 * 
 * @author Keegan Wantz - wantzkt@uw.edu
 *
 */
public class DIYProjectComponent extends BorderPane {
	
	private final Component myComponent;
	
	
	public DIYProjectComponent(Component theComponent, int theQty) {
		myComponent = theComponent;
		
		Label Title = new Label(myComponent.getName() + " : " + theQty);
		
		FlowPane TitleHolder = new FlowPane();
		
		TitleHolder.setBorder(new Border(new BorderStroke(Color.BLACK, 
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		
		TitleHolder.getChildren().add(Title);
		
		Label CostLabel = new Label("Cost:");
		Label MonthlyLabel = new Label("Monthly Cost:");
		Label Cost = new Label("$"+myComponent.getCost().toString());
		Label MonthlyCost = new Label("$"+myComponent.getCostPerMonth().toString());
		
		setTop(TitleHolder);
		
		GridPane inner = new GridPane();
		
		inner.add(CostLabel, 1, 1);
		inner.add(Cost, 2, 1);
		inner.add(MonthlyLabel, 1, 2);
		inner.add(MonthlyCost, 2, 2);
		
		setCenter(inner);
		
		setBorder(new Border(new BorderStroke(Color.BLACK, 
	                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
	}
}
