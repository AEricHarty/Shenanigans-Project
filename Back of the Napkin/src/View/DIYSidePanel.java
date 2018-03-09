package View;

import java.util.List;

import Model.Project;
import javafx.geometry.Insets;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * The Side Panel.
 * 
 * @author Khoa Doan - khoadoan@uw.edu
 * @version .75
 */
public class DIYSidePanel {
	
	/** The Side panel. */
	private GridPane mySidePanel;
	
	private Stage myStage; //invoke myStage.sizeToScene() after an update (Aaron 3/9/2018 12:30am)
	
	/**
     * Constructs a Side Panel with the specified list of projects.
     * @author Khoa Doan - khoadoan@uw.edu
     * 
     * @param theProjectList list of DIY projects
     */
	public DIYSidePanel(final Stage theStage, final List<Project> theProjectList) {
		myStage = theStage;
		mySidePanel = buildSidePanel(theProjectList);
	}
	
	/**
     * Build the Side panel.
     * @author Khoa Doan - khoadoan@uw.edu
     * 
     * @param theProjectList list of DIY projects
     * @return the Side panel
     */
	private GridPane buildSidePanel(final List<Project> theProjectList) {
		final GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);
        final ToggleGroup group = new ToggleGroup();
        
        if (theProjectList != null) {
	        for (int i = 0; i < theProjectList.size(); i++) {
	        	RadioButton radioButton =
	        			new RadioButton(theProjectList.get(i).getName());
	        	radioButton.setToggleGroup(group);
	        	GridPane.setConstraints(radioButton, 0, i);
	        	grid.getChildren().add(radioButton);
	        }
	        ((RadioButton) grid.getChildren().get(0)).setSelected(true);
        }
        
        return grid;
	}
	
	/**
	 * @return the Side panel
	 */
	public GridPane getPanel() {
		return mySidePanel;
	}
}
