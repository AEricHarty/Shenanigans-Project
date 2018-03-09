package View;

import Model.ComponentDatabase;

import java.util.Optional;

import Model.Component;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

/**
 * 
 * @author Aaron Bardsley
 * @date 3/6/2018 9:54pm
 * 
 * The component selector dialog is for choosing a Component to add to a Project.
 * Should be called from component panel (center of the main border) 'Add Component' button.
 * 
 * This class also calls the add new component button.
 */
public class DIYComponentSelectorDialog extends Dialog<Component> {
	
	private Dialog<Component> myDialog;
	private GridPane myComponentGrid;
	private ComponentDatabase myDB;
	private int myOpenRow; //stack pointer
	private ToggleGroup mySelectGroup;
	private Component myReturnComponent; //returned by this dialog to the add component button.
	
	/**
	 * @author Aaron Bardsley
	 * @date 3/6/2018 10:08pm
	 * 
	 * @param theDB the ComponentDatabase singleton to use for populating the grid.
	 * 
	 * Constructs the DIYComponentSelectorDialog.
	 */
	public DIYComponentSelectorDialog(ComponentDatabase theDB, DIYProjectPanel theProjectPanel) {
		myDB = theDB;
		myDialog = new Dialog<Component>();
		myComponentGrid = new GridPane();
		myOpenRow = 1; //Keeps track of the open row like a stack pointer (Aaron 3/6 9:58pm)
		mySelectGroup = new ToggleGroup();
		myReturnComponent = null;
		
		
		initDialog();
	}
	

	/**
	 * @author Aaron Bardsley
	 * @date 3/6/2018 10:08pm
	 * 
	 * initDialog() initializes basic parts to this dialog including the column titles.
	 * Called alongside instantiation.
	 */
	private void initDialog() {
		myDialog.setTitle("Select Component");
		myDialog.setHeaderText("Temporary select component text.");
		myDialog.setResizable(false);
		
		myComponentGrid.add(makeLabel("Choose Component"), 1, myOpenRow);
		myComponentGrid.add(makeLabel("Name"), 2, myOpenRow);
		myComponentGrid.add(makeLabel("Initial Cost"), 3, myOpenRow);
		myComponentGrid.add(makeLabel("Monthly Cost"), 4, myOpenRow);
		myComponentGrid.add(makeLabel("Length"), 5, myOpenRow);
		myComponentGrid.add(makeLabel("Width"), 6, myOpenRow);
		myComponentGrid.add(makeLabel("Height"), 7, myOpenRow);
		myComponentGrid.add(makeLabel("Radius"), 8, myOpenRow);
		myComponentGrid.add(makeLabel("Weight"), 9, myOpenRow);
		myComponentGrid.add(makeLabel("Material"), 10, myOpenRow);
		myComponentGrid.add(makeLabel("Man-hours"), 11, myOpenRow);
		myComponentGrid.add(makeLabel("Cost Per Man-hour"), 12, myOpenRow);
		myComponentGrid.add(makeLabel("# of Subcomponents"), 13, myOpenRow);
		
		populateFromDB();

		ButtonType okButton = new ButtonType("Ok", ButtonData.OK_DONE);
		myDialog.getDialogPane().getButtonTypes().add(okButton);
		
		myComponentGrid.setGridLinesVisible(true);
		myDialog.getDialogPane().setContent(myComponentGrid);
		
		// Associates each radio button with its respective Component (Aaron 3/8/2018 6:36pm)
		mySelectGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			@Override
			public void changed(ObservableValue<? extends Toggle> ov, Toggle oldToggle, Toggle newToggle) {
				if (mySelectGroup.getSelectedToggle() != null
						&& mySelectGroup.getSelectedToggle().getUserData() != null) {
					myReturnComponent = (Component) mySelectGroup.getSelectedToggle().getUserData();
				}				
			}			
		});
		
		// Associates the 'OK' button with selected Component for returning from the dialog.
		myDialog.setResultConverter(new Callback<ButtonType, Component>() {
			@Override
			public Component call(ButtonType b) {				
				if (b == okButton) {
					return myReturnComponent;
				}				
				return null;
			}			
		});
		
	}
	
	/**
	 * @author Aaron Bardsley
	 * @date 3/6/2018 10:08pm
	 * 
	 * @param theComponent the Component object to add to a row in the grid.
	 * 
	 */
	public void addComponent(Component theComponent) {
		addRowStack();

		ToggleButton componentSelect = new ToggleButton();
		componentSelect.setToggleGroup(mySelectGroup);
		componentSelect.setUserData(theComponent);
		
		myComponentGrid.add(componentSelect, 1, myOpenRow);
		myComponentGrid.add(makeLabel(theComponent.getName()), 2, myOpenRow);
		myComponentGrid.add(makeLabel(theComponent.getCost().toString()), 3, myOpenRow);
		myComponentGrid.add(makeLabel(theComponent.getCostPerMonth().toString()), 4, myOpenRow);
		myComponentGrid.add(makeLabel(Double.toString(theComponent.getLength())), 5, myOpenRow);
		myComponentGrid.add(makeLabel(Double.toString(theComponent.getWidth())), 6, myOpenRow);
		myComponentGrid.add(makeLabel(Double.toString(theComponent.getHeight())), 7, myOpenRow);
		myComponentGrid.add(makeLabel(Double.toString(theComponent.getMyRadius())), 8, myOpenRow);
		myComponentGrid.add(makeLabel(Double.toString(theComponent.getWeight())), 9, myOpenRow);
		myComponentGrid.add(makeLabel(theComponent.getMaterial()), 10, myOpenRow);
		myComponentGrid.add(makeLabel(Double.toString(theComponent.getManHrs())), 11, myOpenRow);
		myComponentGrid.add(makeLabel(theComponent.getCostPerManHr().toString()), 12, myOpenRow);
		myComponentGrid.add(makeLabel(Integer.toString(theComponent.getSubComponents().size())), 13, myOpenRow);
		
	}
	
	/**
	 * @author Aaron Bardsleu
	 * @date 3/8/2016 5:38pm
	 * 
	 * @return the Component returned by the dialog.
	 * 
	 * @details can be called more than once.
	 */
	public Optional<Component> view() {
		return myDialog.showAndWait();
	}
	
	/**
	 * @author Aaron Bardsley
	 * @date 3/8/2018 4:08pm
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
	 * @date 3/6/2018 10:10pm
	 * 
	 * @param name the String name to use for the Label
	 * @return Label created with the input name
	 */
    private Label makeLabel(String name) {
		return new Label("  " + name + "  ");
    }
    
    /**
     * @author Aaron Bardsley
     * @date 3/6/2018 10:11pm
     * 
     * Increments the row stack pointer
     */
    private void addRowStack() {
    	myOpenRow += 1;
    }
    
    /**
     * @author Aaron Bardsley
     * @date 3/6/2018 10:11pm
     * 
     * Decrements the row stack pointer
     */
//    private void removeRowStack() {
//    	myOpenRow -= 1;
//    }
    

}
