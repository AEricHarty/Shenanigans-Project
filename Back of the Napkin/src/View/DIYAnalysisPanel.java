package View;

import Model.Project;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

/**
 * The Analysis Panel.
 * 
 * @author Khoa Doan - khoadoan@uw.edu
 * @modified Keegan Wantz - wantzkt@uw.edu (added updateFields method)
 * @version .75
 */
public class DIYAnalysisPanel {
	
	/** The Header label. */
	private static final Label HEADER = new Label("Header");
	
	/** The Cost label. */
	private static final Label COST = new Label("Cost");
	
	/** The Cost Per Month label. */
	private static final Label COST_PER_MONTH = new Label("Cost Per Month");
	
	/** The Weight label. */
	private static final Label WEIGHT = new Label("Weight");
	
	/** The Man Hours label. */
	private static final Label MAN_HOURS = new Label("Man Hours");
	
	/** The Cost Per Man Hour label. */
	private static final Label COST_PER_MAN_HOUR = new Label("Cost Per Man Hour");
	
	/** The header of the project. */
	private Label myHeader;
	
	/** The cost the project. */
	private Label myCostLabel;
	
	/** The cost per month of the project. */
	private Label myCostPerMonthLabel;
	
	/** The weight of the project. */
	private Label myWeightLabel;
	
	/** The man hours of the project. */
	private Label myManHoursLabel;
	
	/** The cost per man hour of the project. */
	private Label myCostPerManHourLabel;
	
	/** The analysis panel. */
	private GridPane myAnalysisPanel;
	
	/** The project. */
	private Project myProject;
	
	/**
     * Constructs an Analysis Panel with the specified project.
     * @author Khoa Doan - khoadoan@uw.edu
     * @modified Keegan Wantz - wantzkt@uw.edu (used updateFields)
     * 
     * @param theProject
     */
	public DIYAnalysisPanel(Project theProject) {		
		myProject = theProject;
		updateFields();
		
		myAnalysisPanel = buildAnalysisPanel();
	}

	/**
	 * Build an analysis panel
	 * @author Khoa Doan - khoadoan@uw.edu
	 * 
	 * @return the panel
	 */
	private GridPane buildAnalysisPanel() {
		GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);
        
        GridPane.setConstraints(HEADER, 0, 0);
        GridPane.setConstraints(myHeader, 1, 0);
        GridPane.setConstraints(COST, 0, 1);
        GridPane.setConstraints(myCostLabel, 1, 1);
        GridPane.setConstraints(COST_PER_MONTH, 0, 3);
        GridPane.setConstraints(myCostPerMonthLabel, 1, 3);
        GridPane.setConstraints(WEIGHT, 0, 4);
        GridPane.setConstraints(myWeightLabel, 1, 4);
        GridPane.setConstraints(MAN_HOURS, 0, 5);
        GridPane.setConstraints(myManHoursLabel, 1, 5);
        GridPane.setConstraints(COST_PER_MAN_HOUR, 0, 6);
        GridPane.setConstraints(myCostPerManHourLabel, 1, 6);
        grid.getChildren().addAll(HEADER, myHeader, COST, myCostLabel,
        		COST_PER_MONTH, myCostPerMonthLabel,
        		WEIGHT, myWeightLabel, MAN_HOURS, myManHoursLabel,
        		COST_PER_MAN_HOUR, myCostPerManHourLabel);
        
        return grid;
	}
	
	/**
	 * @author Keegan Wantz - wantzkt@uw.edu
	 */
	public void updateFields() {
		myHeader = new Label(myProject.getName());
		myCostLabel = new Label(myProject.getTotalUpfrontCost().toString());
		myCostPerMonthLabel = new Label(myProject.getCostPerMonth().toString());
		myWeightLabel = new Label(myProject.getMiscCost().toString());
		myManHoursLabel = new Label(myProject.getManHrs() + "");
		myCostPerManHourLabel = new Label(myProject.getCostPerMonth().toString());
	}
	
	/**
	 * @author Khoa Doan - khoadoan@uw.edu
	 * @return the header
	 */
	public Label getHeader() {
		return myHeader;
	}
	
	/**
	 * @author Khoa Doan - khoadoan@uw.edu
	 * @return the cost
	 */
	public Label getCostLabel() {
		return myCostLabel;
	}
	
	/**
	 * @author Khoa Doan - khoadoan@uw.edu
	 * @return the cost per month
	 */
	public Label getCostPerMonthLabel() {
		return myCostPerMonthLabel;
	}
	
	/**
	 * @author Khoa Doan - khoadoan@uw.edu
	 * @return the weight
	 */
	public Label getWeightLabel() {
		return myWeightLabel;
	}
	
	/**
	 * @author Khoa Doan - khoadoan@uw.edu
	 * @return the man hours
	 */
	public Label getManHoursLabel() {
		return myManHoursLabel;
	}
	
	/**
	 * @author Khoa Doan - khoadoan@uw.edu
	 * @return the cost per man hour
	 */
	public Label getCostPerManHourLabel() {
		return myCostPerManHourLabel;
	}
	
	/**
	 * @author Khoa Doan - khoadoan@uw.edu
	 * @return the analysis panel
	 */
	public GridPane getPanel() {
		return myAnalysisPanel;
	}
}
