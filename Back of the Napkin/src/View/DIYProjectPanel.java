package View;

import Model.Component;
import Model.ComponentDatabase;
import Model.Project;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

public class DIYProjectPanel {
	
	private static final String ADD = "Add";
	
	private static final String REMOVE = "Remove";
	
	private Project myProject;
	
	private Button myAddComponentButton;
	
	private Button myRemoveComponentButton;
	
	private Pane myProjectPanel;
	
	public DIYProjectPanel(final Project theProject, ComponentDatabase theComponentDatabase) {
		myProject = theProject;
		myAddComponentButton = createAddButton(theComponentDatabase);
		myRemoveComponentButton = createRemoveButton();
		
		myProjectPanel = buildProjectPanel(myAddComponentButton, myRemoveComponentButton);
	}
	
	private Button createAddButton(ComponentDatabase theComponentDatabase) {
		final Button addButton = new Button(ADD);
		//addButton.setLayoutX(100);
		//addButton.setLayoutY(650);
		DIYProjectPanel that = this;
		addButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				new DIYComponentSelectorDialog(theComponentDatabase, that).view();
			}
		});
		
		return addButton;
	}
	
	private Button createRemoveButton() {
		final Button removeButton = new Button(REMOVE);
		//removeButton.setLayoutX(590);
		//removeButton.setLayoutY(650);
		removeButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				
			}
		});
		
		return removeButton;
	}
	
	/**
	 * @author Keegan Wantz - wantzkt@uw.edu
	 */
	private Pane buildProjectPanel(final Button theAddButton, final Button theRemoveButton) {
		//final Pane pane = new Pane();

        BorderPane border = new BorderPane();
        BorderPane bottomSplitter = new BorderPane();
        
        FlowPane inner = new FlowPane();
        
        border.setCenter(inner);
        
        border.setBottom(bottomSplitter);
        bottomSplitter.setLeft(theAddButton);
        bottomSplitter.setRight(theRemoveButton);
        
        border.setStyle("-fx-background-color: #ffffff");
        return border;
	}
	
	public Project getProject() {
		return myProject;
	}
	
	public Button getAddComponentButton() {
		return myAddComponentButton;
	}
	
	public Button getRemoveComponentButton() {
		return myRemoveComponentButton;
	}
	
	public Pane getPanel() {
		return myProjectPanel;
	}
	
	private void buildExistingComponents() {
		
	}
	
	public void addComponent(final Component theComponent, final int theQuantity) {
		myProject.addComponent(theComponent, theQuantity);
	}
	
	public void deleteComponent(final int theID) {
		myProject.removeComponent(theID);
		
	}
}
