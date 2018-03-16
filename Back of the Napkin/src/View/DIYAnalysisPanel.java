package View;

import java.util.Observable;
import java.util.Observer;

import Model.Project;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


/**
 * The Analysis Panel.
 * 
 * @author Khoa Doan - khoadoan@uw.edu
 * @modified Keegan Wantz - wantzkt@uw.edu (added updateFields method)
 * @version .75
 */
public class DIYAnalysisPanel implements Observer {
	
	/** The Header label. */
	private final Label header = new Label("Header");
	
	/** The Cost label. */
	private final Label cost = new Label("Cost");
	
	/** The Cost Per Month label. */
	private final Label costPerMonth = new Label("Cost Per Month");
	
	/** The Weight label. */
	private final Label weight = new Label("Weight");
	
	/** The Man Hours label. */
	private final Label manHours = new Label("Man Hours");
	
	/** The Cost Per Man Hour label. */
	private final Label costPerManHours = new Label("Cost Per Man Hour");
	
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
	
	private Stage myStage; //invoke myStage.sizeToScene() after an update (Aaron 3/9/2018 12:30am)
	
	/**
     * Constructs an Analysis Panel with the specified project.
     * @author Khoa Doan - khoadoan@uw.edu
     * @modified Keegan Wantz - wantzkt@uw.edu (used updateFields)
     * 
     * @param theProject
     */
	public DIYAnalysisPanel(final Stage theStage, Project theProject) {		
		myStage = theStage;
		myProject = theProject;
		
		myProject.addObserver(this);
		
		myAnalysisPanel = buildAnalysisPanel();
		updateFields();
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

		myHeader = new Label(myProject.getName());
		myCostLabel = new Label(myProject.getTotalUpfrontCost().toString());
		myCostPerMonthLabel = new Label(myProject.getCostPerMonth().toString());
		myWeightLabel = new Label(myProject.getTotalWeight() + "");
		myManHoursLabel = new Label(myProject.getTotalManHrs() + "");
		myCostPerManHourLabel = new Label(myProject.getCostPerMonth().toString());
		
        GridPane.setConstraints(header, 0, 0);
        GridPane.setConstraints(myHeader, 1, 0);
        GridPane.setConstraints(cost, 0, 1);
        GridPane.setConstraints(myCostLabel, 1, 1);
        GridPane.setConstraints(costPerMonth, 0, 3);
        GridPane.setConstraints(myCostPerMonthLabel, 1, 3);
        GridPane.setConstraints(weight, 0, 4);
        GridPane.setConstraints(myWeightLabel, 1, 4);
        GridPane.setConstraints(manHours, 0, 5);
        GridPane.setConstraints(myManHoursLabel, 1, 5);
        GridPane.setConstraints(costPerManHours, 0, 6);
        GridPane.setConstraints(myCostPerManHourLabel, 1, 6);
        grid.getChildren().addAll(header, myHeader, cost, myCostLabel,
        		costPerMonth, myCostPerMonthLabel,
        		weight, myWeightLabel, manHours, myManHoursLabel,
        		costPerManHours, myCostPerManHourLabel);
        
        return grid;
	}
	
	/**
	 * @author Keegan Wantz - wantzkt@uw.edu
	 */
	public void updateFields() {
		myHeader.setText(myProject.getName());
		myCostLabel.setText(myProject.getTotalUpfrontCost().toString());
		myCostPerMonthLabel.setText(myProject.getCostPerMonth().toString());
		myWeightLabel.setText(myProject.getTotalWeight() + "");
		myManHoursLabel.setText(myProject.getTotalManHrs() + "");
		myCostPerManHourLabel.setText(myProject.getCostPerMonth().toString());
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

	@Override
	public void update(Observable theProject, Object theUpdate) {
		updateFields();
	}
}
