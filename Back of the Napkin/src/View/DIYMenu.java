package View;

import java.io.File;

import Model.Project;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;

/**
 * The Menu bar.
 * 
 * @author Aaron Bardsley
 * @modified Khoa Doan - khoadoan@uw.edu (move code to new class)
 * @modified Keegan Wantz - wantzkt@uw.edu (added EventHandler for the About button)
 * @modified Eric Harty - hartye@uw.edu (added EventHandler for the Save and Load buttons)
 * @version .75
 */
public class DIYMenu {
	
	/** The File menu. */
	private final Menu myFileMenu;
	
	/** The Help menu. */
	private final Menu myHelpMenu;
	
	/** The owner window. */
	private final Stage myStage;
	
	/** The currently selected project. */
	private Project myProject;
	
	/** The file chooser for load. */
	private final FileChooser fileChooser = new FileChooser();
	
	/**
     * Constructs the Menu bar.
     * @author Aaron Bardsley
	 * @param primaryStage
	 * @param theProject currently displayed project
     * @modified Keegan Wantz - wantzkt@uw.edu (added EventHandler for the About button)
     * @modified Eric Harty - hartye@uw.edu (added EventHandler for the Save/Load 
     * 										and added the project as arg)
     */
	public DIYMenu(final Stage primaryStage, final Project theProject) {
		myProject = theProject;
		myStage = primaryStage;
		myFileMenu = new Menu("File");
        myHelpMenu = new Menu("Help");
        MenuItem loadMenuItem = new MenuItem("Load");
        MenuItem saveMenuItem = new MenuItem("Save");
        MenuItem aboutMenuItem = new MenuItem("About");
        myHelpMenu.getItems().add(aboutMenuItem);
        myFileMenu.getItems().add(loadMenuItem);
        myFileMenu.getItems().add(saveMenuItem);

        aboutMenuItem.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				Alert information = new Alert(AlertType.INFORMATION);
				information.setTitle("About Back Of The Napkin");
				information.setHeaderText("Version: 0.0.001");
				information.setContentText("Authors: Aaron Bardsley, Eric Harty, Keegan Wantz, Khoa Doan"
						+ "\n\nBack of the Napkin is a small stand-alone program designed for 'do-it-yourself'ers who "
						+ "need an electronic way to manage projects.");
				information.showAndWait();
			}
        	
        });
        
        saveMenuItem.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				myProject.saveProject(); 	//Works in JUnit but gives error
			}
        	
        });
        
        loadMenuItem.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				File file = fileChooser.showOpenDialog(myStage);
                if (file != null) {
                	Project p = new Project(file);
                	//Then we need a method to display this new project...
                }
                
			}
        	
        });
        
        
	}
	
	/**
	 * @author Khoa Doan - khoadoan@uw.edu
	 * @return the File menu
	 */
	public Menu getFileMenu() {
		return myFileMenu;
	}
	
	/**
	 * @author Khoa Doan - khoadoan@uw.edu
	 * @return the Help menu
	 */
	public Menu getHelpMenu() {
		return myHelpMenu;
	}

	/**
	 * updates the currently selected project
	 * 
	 * @author Eric Harty - hartye@uw.edu
	 * @param theProject to change to
	 */
	public void updateProject(Project theProject) {
		myProject = theProject;
	}
}
