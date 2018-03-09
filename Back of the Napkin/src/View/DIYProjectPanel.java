package View;

import java.util.Optional;

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
	
	private BorderPane myProjectPanel;
	
	private FlowPane myInnerPane;
	
	private ComponentDatabase myComponentDatabase;
	
	private DIYComponentSelectorDialog dialog;
	
	public DIYProjectPanel(final Project theProject, ComponentDatabase theComponentDatabase) {
		myProject = theProject;
		myComponentDatabase = theComponentDatabase;
		
		myAddComponentButton = createAddButton(theComponentDatabase);
		myRemoveComponentButton = createRemoveButton();
		
		myProjectPanel = buildProjectPanel(myAddComponentButton, myRemoveComponentButton);
		

		dialog = new DIYComponentSelectorDialog(theComponentDatabase, this); //Aaron Bardsley
	}
	
	/**
	 * @author Aaron Bardsley
	 * @date 3/8/2018
	 * 
	 * @param theComponentDatabase the component database for populating the selection grid.
	 * @return the 'add' Button
	 * 
	 * @details On action click opens the selector dialog. Dialog returns a Component and the button adds it to
	 * the panel.
	 */
	private Button createAddButton(ComponentDatabase theComponentDatabase) {
		final Button addButton = new Button(ADD); //Khoa Doan
		
		addButton.setOnAction(new EventHandler<ActionEvent>() { //Aaron Bardsley (start)
			@Override
			public void handle(ActionEvent event) {
				Optional<Component> result = dialog.view();
				if (result.isPresent()) {
					addComponent(result.get(), 1);
				}
			}
		}); //Aaron Bardsley (end)
		
		return addButton; //Khoa Doan
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
	private BorderPane buildProjectPanel(final Button theAddButton, final Button theRemoveButton) {
		//final Pane pane = new Pane();

        BorderPane border = new BorderPane();
        BorderPane bottomSplitter = new BorderPane();
        
        FlowPane inner = new FlowPane();
        
        myInnerPane = inner;
        
        inner.setHgap(2);
       
        
        border.setCenter(inner);
        
        border.setBottom(bottomSplitter);
        bottomSplitter.setLeft(theAddButton);
        bottomSplitter.setRight(theRemoveButton);
        
        buildExistingComponents();
        
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
		/* TEMPORARY */
		addComponent(myComponentDatabase.getComponent(1), 1);
		addComponent(myComponentDatabase.getComponent(2), 1);
		addComponent(myComponentDatabase.getComponent(3), 1);
	}
	
	public void addComponent(final Component theComponent, final int theQuantity) {
		myProject.addComponent(theComponent, theQuantity);
		DIYProjectComponent newComponentDisplay = new DIYProjectComponent(theComponent, theQuantity);
	    myInnerPane.getChildren().add(newComponentDisplay);		
	}
	
	public void deleteComponent(final int theID) {
		myProject.removeComponent(theID);
		
	}
}
