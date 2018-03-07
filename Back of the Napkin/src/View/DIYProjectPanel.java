package View;

import java.util.List;

import Model.ComponentListItem;
import Model.DIYComponent;
import Model.DIYProject;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class DIYProjectPanel {
	
	private static final String ADD = "Add";
	
	private static final String REMOVE = "Remove";
	
	private DIYProject myProject;
	
	private Button myAddComponentButton;
	
	private Button myRemoveComponentButton;
	
	private List<ComponentListItem> myComponentListItem;
	
	private Pane myProjectPanel;
	
	public DIYProjectPanel(final DIYProject theProject) {
		myProject = theProject;
		myAddComponentButton = createAddButton();
		myRemoveComponentButton = createRemoveButton();
		myComponentListItem = theProject.getComponents();
		
		myProjectPanel = buildProjectPanel(myAddComponentButton, myRemoveComponentButton);
	}
	
	private Button createAddButton() {
		final Button addButton = new Button(ADD);
		addButton.setLayoutX(0);
		addButton.setLayoutY(650);
		addButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				
			}
		});
		
		return addButton;
	}
	
	private Button createRemoveButton() {
		final Button removeButton = new Button(REMOVE);
		removeButton.setLayoutX(590);
		removeButton.setLayoutY(650);
		removeButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				
			}
		});
		
		return removeButton;
	}
	
	private Pane buildProjectPanel(final Button theAddButton, final Button theRemoveButton) {
		final Pane pane = new Pane();
        pane.getChildren().add(theAddButton);
        pane.getChildren().add(theRemoveButton);
        pane.setStyle("-fx-background-color: #ffffff");
        return pane;
	}
	
	public DIYProject getProject() {
		return myProject;
	}
	
	public Button getAddComponentButton() {
		return myAddComponentButton;
	}
	
	public Button getRemoveComponentButton() {
		return myRemoveComponentButton;
	}
	
	public List<ComponentListItem> getComponentListItem() {
		return myComponentListItem;
	}
	
	public Pane getPanel() {
		return myProjectPanel;
	}
	
	private void buildExistingComponents() {
		
	}
	
	public void addComponent(final DIYComponent theComponent, final int theQuantity) {
		myComponentListItem.add(new ComponentListItem(theComponent, theQuantity));
	}
	
	public void deleteComponent(final int theIndex) {
		myComponentListItem.remove(theIndex);
		
	}
}
