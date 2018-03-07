package View;

import Model.ComponentDatabase;
import Model.Component;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;

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
public class DIYComponentSelectorDialog {
	
	@SuppressWarnings("rawtypes") // I'm not sure what this is for (Aaron 3/6 9:58pm)
	private Dialog myDialog;
	private GridPane myComponentGrid;
	private ComponentDatabase myComponentDB;
	private int myOpenRow;
	
	/**
	 * @author Aaron Bardsley
	 * @date 3/6/2018 10:08pm
	 * 
	 * @param theDB the ComponentDatabase singleton to use for _____ (@ToDo Aaron)
	 * 
	 * Constructs the DIYComponentSelectorDialog.
	 */
	@SuppressWarnings("rawtypes") // I'm not sure what this is for (Aaron 3/6 9:58pm)
	public DIYComponentSelectorDialog(ComponentDatabase theDB) {
		myComponentDB = theDB;
		myDialog = new Dialog();
		myComponentGrid = new GridPane();
		myOpenRow = 1; //Keeps track of the open row like a stack pointer (Aaron 3/6 9:58pm)
		
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
		
		myComponentGrid.add(makeLabel("Name"), 1, myOpenRow);
		myComponentGrid.add(makeLabel("Initial Cost"), 2, myOpenRow);
		myComponentGrid.add(makeLabel("Monthly Cost"), 3, myOpenRow);
		myComponentGrid.add(makeLabel("Width"), 4, myOpenRow);
		myComponentGrid.add(makeLabel("Length"), 5, myOpenRow);
		myComponentGrid.add(makeLabel("Height"), 6, myOpenRow);
		myComponentGrid.add(makeLabel("Radius"), 7, myOpenRow);
		myComponentGrid.add(makeLabel("Weight"), 8, myOpenRow);
		myComponentGrid.add(makeLabel("Material"), 9, myOpenRow);
		myComponentGrid.add(makeLabel("Man-hours"), 10, myOpenRow);
		myComponentGrid.add(makeLabel("Cost Per Man-hour"), 11, myOpenRow);
		myComponentGrid.add(makeLabel("# of Subcomponents"), 12, myOpenRow);

		ButtonType okButton = new ButtonType("Ok", ButtonData.OK_DONE);
		myDialog.getDialogPane().getButtonTypes().add(okButton);
		
		myComponentGrid.setGridLinesVisible(true);
		myDialog.getDialogPane().setContent(myComponentGrid);
		
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
		
		myComponentGrid.add(makeLabel(theComponent.getName()), 1, myOpenRow);
		myComponentGrid.add(makeLabel(theComponent.getCost().toString()), 2, myOpenRow);
		myComponentGrid.add(makeLabel(theComponent.getCostPerMonth().toString()), 3, myOpenRow);
		myComponentGrid.add(makeLabel(Double.toString(theComponent.getWidth())), 4, myOpenRow);
		myComponentGrid.add(makeLabel(Double.toString(theComponent.getLength())), 5, myOpenRow);
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
	 * @date 3/6/2018 10:09pm
	 * 
	 * Displays the dialog (temporary)
	 */
	public void view() {
		myDialog.showAndWait();
	}
	
	/**
	 * @author Aaron Bardsley
	 * @date 3/6/2018 10:10pm
	 * 
	 * @param name the String name to use for the Label
	 * @return Label created with the input name
	 */
    private Label makeLabel(String name) {
    	Label label = new Label("  " + name + "  ");
		return label;
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
    private void removeRowStack() {
    	myOpenRow -= 1;
    }
    

}
