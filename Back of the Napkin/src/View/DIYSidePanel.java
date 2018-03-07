package View;

import java.util.List;

import Model.Project;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class DIYSidePanel {
	
	private GridPane mySidePanel;
	
	public DIYSidePanel(final List<Project> theProjectList) {
		mySidePanel = buildSidePanel(theProjectList);
	}
	
	private GridPane buildSidePanel(final List<Project> theProjectList) {
		final GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);
        final ToggleGroup group = new ToggleGroup();
        
        for (int i = 0; i < theProjectList.size(); i++) {
        	RadioButton radioButton =
        			new RadioButton(theProjectList.get(i).getName());
        	radioButton.setToggleGroup(group);
        	GridPane.setConstraints(radioButton, 0, i);
        	grid.getChildren().add(radioButton);
        }
        ((RadioButton) grid.getChildren().get(0)).setSelected(true);
        
        return grid;
	}
	
	public GridPane getPanel() {
		return mySidePanel;
	}
}
