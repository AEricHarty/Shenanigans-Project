package View;

import java.util.List;
import java.util.Optional;

import Model.Component;
import Model.ComponentDatabase;
import Model.ComponentListItem;
import Model.Project;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class DIYProjectPanel {
	
	private static final String RENAME = "Rename Project";
	
	private static final String ADD = "Add Component";
	
	private static final String REMOVE = "Remove Component";
	
	private Project myProject;
	
	private Button myRenameProjectButton;
	
	private Button myAddComponentButton;
	
	private Button myRemoveComponentButton;
	
	private BorderPane myProjectPanel;
	
	private FlowPane myInnerPane;
	
	private ComponentDatabase myComponentDatabase;
	
	private DIYComponentSelectorDialog dialog;
	
	private Stage myStage; //invoke myStage.sizeToScene() after an update (Aaron 3/9/2018 12:30am)
	
	/**
	 * @modified Eric Harty - hartye@uw.edu: added rename project button
	 */
	public DIYProjectPanel(final Stage theStage, final Project theProject, ComponentDatabase theComponentDatabase) {
		myStage = theStage;
		myProject = theProject;
		myComponentDatabase = theComponentDatabase;
		
		myRenameProjectButton = createRenameProjectButton();
		myAddComponentButton = createAddButton(theComponentDatabase);
		myRemoveComponentButton = createRemoveButton();
		
		myProjectPanel = buildProjectPanel(myRenameProjectButton, myAddComponentButton, myRemoveComponentButton);
		

		//dialog = new DIYComponentSelectorDialog(theComponentDatabase, this, theProject); //Aaron Bardsley (3/8/2018 11:58pm)
	}

	/**
	 * @author Eric Harty - hartye@uw.edu
	 * 
	 * @return the 'Rename Project' Button
	 * @details On action click opens the input dialog. then renames the project.
	 */
	private Button createRenameProjectButton() {
		final Button renameButton = new Button(RENAME);
		renameButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				TextInputDialog dialog = new TextInputDialog("untitled");
				dialog.setTitle("Rename Project");
				dialog.setContentText("Please enter name:");
				Optional<String> result = dialog.showAndWait();
				if (result.isPresent()){
				   myProject.setName(result.get()); 
				}
			}
		});
		return renameButton;
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
				ComponentSelector newDialog = new ComponentSelector(myComponentDatabase, myProject);
				Optional<Component> result = newDialog.view();;
				if (result.isPresent()) {
					addComponent(result.get(), 1);
					myStage.sizeToScene();
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
				
				myStage.sizeToScene();
			}
		});
		
		return removeButton;
	}
	
	/**
	 * @author Keegan Wantz - wantzkt@uw.edu
	 * @modified Eric Harty: added rename button
	 */
	private BorderPane buildProjectPanel(final Button theRenameButton, 
			final Button theAddButton, final Button theRemoveButton) {
		//final Pane pane = new Pane();

        BorderPane border = new BorderPane();
        BorderPane bottomSplitter = new BorderPane();
        
        border.setPadding(new Insets(5, 5, 5, 5)); //aesthetics (Aaron 3/9/2018 12:35am)
        bottomSplitter.setPadding(new Insets(5, 0, 0, 0)); //aesthetics (Aaron 3/9/2018 12:35am)
        
        FlowPane inner = new FlowPane();
        
        myInnerPane = inner;
        
        inner.setHgap(2);
       
        
        border.setCenter(inner);
        
        border.setBottom(bottomSplitter);
        bottomSplitter.setLeft(theRenameButton);
        bottomSplitter.setCenter(theAddButton);
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
	
	/*
	 * @author Keegan Wantz - wantzkt@uw.edu
	 */
	private void buildExistingComponents() {
		/* TEMPORARY
		addComponent(myComponentDatabase.getComponent(1), 1);
		addComponent(myComponentDatabase.getComponent(2), 1);
		addComponent(myComponentDatabase.getComponent(3), 1); */
		List<ComponentListItem> compList = myProject.getComponents();
		
		for (ComponentListItem CLI : compList) {
			DIYProjectComponent newComponentDisplay = new DIYProjectComponent(CLI.getComponent(), CLI.getQuantity());
		    myInnerPane.getChildren().add(newComponentDisplay);			
		}		
	}
	
	public void addComponent(final Component theComponent, final int theQuantity) {
		myProject.addComponent(theComponent, theQuantity);
		DIYProjectComponent newComponentDisplay = new DIYProjectComponent(theComponent, theQuantity);
	    myInnerPane.getChildren().add(newComponentDisplay);		
	}
	
	//public void setComponentDatabases(final Component the)
	public void deleteComponent(final int theID) {
		myProject.removeComponent(theID);
	}
}
