package View;

import java.util.Optional;

import Model.Component;
import Model.ComponentDatabase;
import Model.Project;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

/**
 * 
 * @author Aaron Bardsley
 * @lastupdate 3/8/2018 11:58pm
 * 
 * The component selector dialog is for choosing a Component to add to a Project.
 * Should be called from component panel (center of the main border) 'Add Component' button.
 *  
 * @return This dialog class has a special return type of Optional<Component> via the .view() method.
 */
public class DIYComponentSelectorDialog extends DialogPane {
	
	//The outer border containing the grid and buttons
	private BorderPane border;
	
	//Splitter for the buttons
	private BorderPane borderSplitter;
	
	//Returned by this dialog to the 'add component' button.
	private Component mySelectedComponent;
	
	//The component database for the current project
	private ComponentDatabase myDB;
	
	//The dialog that returns type Component
	private Dialog<Component> myDialog;
	
	//Layout for the buttons
	private FlowPane flowPane;
	
	//The grid to view and select components
	private GridPane myComponentGrid;
	
	//The stack pointer for the available grid row
	private int myOpenRow;
	
	private Project myProject;
	
	//The radio button toggle group for the components grid
	private ToggleGroup mySelectGroup;
	
	/**
	 * @author Aaron Bardsley
	 * @lastupdate 3/8/2018 9:00pm
	 * 
	 * @param theDB the ComponentDatabase singleton to use for populating the grid.
	 * 
	 * Constructs the DIYComponentSelectorDialog.
	 */
	public DIYComponentSelectorDialog(ComponentDatabase theDB, DIYProjectPanel theProjectPanel, Project theProject) {
		border = new BorderPane();
		borderSplitter = new BorderPane();
		myDB = theDB;
		myDialog = new Dialog<Component>();
		flowPane = new FlowPane();
		myComponentGrid = new GridPane();
		myOpenRow = 1; //Keeps track of the open row like a stack pointer (Aaron 3/6 9:58pm)
		mySelectGroup = new ToggleGroup();
		mySelectedComponent = null;
		myProject = theProject;
		
		this.initDialog();
	}

	/**
	 * @author Aaron Bardsley
	 * @lastupdate 3/8/2018 5:03pm
	 * 
	 * @return the Component returned by the dialog.
	 * 
	 * @details Functions both to make the dialog visible and returns the Component selected.
	 */
	public Optional<Component> view() {
		return myDialog.showAndWait();
	}
	
	/**
	 * @author Aaron Bardsley
	 * @lastupdate 3/8/2018 11:59pm
	 * 
	 * Initializes base-line parts to this dialog.
	 * Only called alongside instantiation.
	 */
	private void initDialog() {
		myDialog.setTitle("Select Component");
		myDialog.setHeaderText("Choose a component then click apply to add it to your " + myProject.getName() + " project.");
		myDialog.setResizable(true);

		// Associates each toggle button with its respective userData Component (Aaron 3/8/2018 6:36pm)
		mySelectGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			@Override
			public void changed(ObservableValue<? extends Toggle> observableValue, Toggle oldToggle, Toggle newToggle) {
				if (mySelectGroup.getSelectedToggle() != null
						&& mySelectGroup.getSelectedToggle().getUserData() != null) {
					mySelectedComponent = (Component) mySelectGroup.getSelectedToggle().getUserData();
				}				
			}			
		});
		
		// Column titles
		myComponentGrid.add(makeLabel("Choose Component"), 1, myOpenRow);
		myComponentGrid.add(makeLabel("Initial Cost"), 2, myOpenRow);
		myComponentGrid.add(makeLabel("Monthly Cost"), 3, myOpenRow);
		myComponentGrid.add(makeLabel("Length"), 4, myOpenRow);
		myComponentGrid.add(makeLabel("Width"), 5, myOpenRow);
		myComponentGrid.add(makeLabel("Height"), 6, myOpenRow);
		myComponentGrid.add(makeLabel("Radius"), 7, myOpenRow);
		myComponentGrid.add(makeLabel("Weight"), 8, myOpenRow);
		myComponentGrid.add(makeLabel("Material"), 9, myOpenRow);
		myComponentGrid.add(makeLabel("Man-hours"), 10, myOpenRow);
		myComponentGrid.add(makeLabel("Cost Per Man-hour"), 11, myOpenRow);
		myComponentGrid.add(makeLabel("# of Subcomponents"), 12, myOpenRow);
		
		//Grab everything from ComponentDatabase
		this.populateFromDB();
		
		myComponentGrid.setAlignment(Pos.TOP_CENTER);
		myComponentGrid.setGridLinesVisible(true);
		
		Button newComponentButton = new Button("Create New Component");
		Button deleteComponentButton = new Button("Delete Component");
		flowPane.getChildren().add(newComponentButton);
		flowPane.getChildren().add(deleteComponentButton);
		
		ButtonType applyButton = new ButtonType("Apply", ButtonData.APPLY);
		ButtonType cancelButton = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

		myDialog.getDialogPane().getButtonTypes().add(applyButton);
		myDialog.getDialogPane().getButtonTypes().add(cancelButton);
		
		// Associates the 'APPLY' ButtonType with the selected Component for returning from this dialog.
		// Actual return value (Optional<Component>) is retrieved via the view() method.
		myDialog.setResultConverter(new Callback<ButtonType, Component>() {
			@Override
			public Component call(ButtonType b) {				
				if (b == applyButton) {
					return mySelectedComponent;
				}
				return null;
			}			
		});
		
		newComponentButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				//Start new component dialog here
			}
		});
		
		deleteComponentButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				//removeFromRowStack();
			}
			
		});

		borderSplitter.setLeft(flowPane);
		border.setCenter(myComponentGrid);
		border.setTop(borderSplitter);
		borderSplitter.setPadding(new Insets(0, 0, 10, 0));
		myDialog.getDialogPane().setContent(border);
	}
	
	/**
	 * @author Aaron Bardsley
	 * @lastupdate 3/6/2018 10:08pm
	 * 
	 * @param theComponent the Component object to add to a row in the grid.
	 * 
	 */
	protected void addComponent(Component theComponent) {
		this.addToRowStack();

		ToggleButton selectButton = new ToggleButton();
		selectButton.setToggleGroup(mySelectGroup);
		selectButton.setMinWidth(118);
		selectButton.setText(theComponent.getName());
		
		//Associates theComponent with selectButton.
		selectButton.setUserData(theComponent);
		
		myComponentGrid.add(selectButton, 1, myOpenRow);
		myComponentGrid.add(makeLabel(theComponent.getCost().toString()), 2, myOpenRow);
		myComponentGrid.add(makeLabel(theComponent.getCostPerMonth().toString()), 3, myOpenRow);
		myComponentGrid.add(makeLabel(Double.toString(theComponent.getLength())), 4, myOpenRow);
		myComponentGrid.add(makeLabel(Double.toString(theComponent.getWidth())), 5, myOpenRow);
		myComponentGrid.add(makeLabel(Double.toString(theComponent.getHeight())), 6, myOpenRow);
		myComponentGrid.add(makeLabel(Double.toString(theComponent.getMyRadius())), 7, myOpenRow);
		myComponentGrid.add(makeLabel(Double.toString(theComponent.getWeight())), 8, myOpenRow);
		myComponentGrid.add(makeLabel(theComponent.getMaterial()), 9, myOpenRow);
		myComponentGrid.add(makeLabel(Double.toString(theComponent.getManHrs())), 10, myOpenRow);
		myComponentGrid.add(makeLabel(theComponent.getCostPerManHr().toString()), 11, myOpenRow);
		myComponentGrid.add(makeLabel(Integer.toString(theComponent.getSubComponents().size())), 12, myOpenRow);
	}
	
	/**
	 * @author Aaron Bardsley
	 * @lastupdate 3/8/2018 4:08pm
	 * 
	 * @details initializes components from database. Only used by constructor.
	 */
	private void populateFromDB() {
		for (Component component : myDB.getAllComponents()) {
			addComponent(component);
		}
	}
	
	/**
	 * @author Aaron Bardsley
	 * @lastupdate 3/6/2018 10:10pm
	 * 
	 * @param name the String name to use for the Label
	 * @return Label created with the input name
	 */
    private Label makeLabel(String name) {
		return new Label("  " + name + "  ");
    }
    
    /**
     * @author Aaron Bardsley
     * @lastupdate 3/6/2018 10:11pm
     * 
     * Increments the row stack pointer
     */
    private void addToRowStack() {
    	myOpenRow += 1;
    }
    
    /**
     * @author Aaron Bardsley
     * @lastupdate 3/6/2018 10:11pm
     * 
     * Decrements the row stack pointer
     */
    private void removeFromRowStack() {
    	myOpenRow -= 1;
    }
    

}
