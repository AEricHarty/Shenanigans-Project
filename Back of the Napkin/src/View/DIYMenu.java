package View;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import Model.Component;
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
	 * @param theWindow 
     * @modified Keegan Wantz - wantzkt@uw.edu (added EventHandler for the About button)
     * @modified Eric Harty - hartye@uw.edu (added EventHandler for the Save/Load 
     * 										and added the project as arg)
     */
	public DIYMenu(final Stage primaryStage, final Project theProject, DIYWindow theWindow) {
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
                	/**
                	 * Loads a project
                	 * @author Keegan Wantz - wantzkt@uw.edu
                	 * 
                	 * This is super hacky and I hate it.
                	 */
                	//Project p = Project.loadFile(file);
                	Scanner fileScanner;
					try {
						fileScanner = new Scanner(file);
	                	Project proj = new Project();
	                	proj.setName(fileScanner.nextLine());
	                	while (fileScanner.hasNextInt()) {
	                		int id = fileScanner.nextInt();
	                		int qty = fileScanner.nextInt();
	                		Component c = theWindow.myComponentDatabase.getComponent(id);
	                		if (c != null)
	                			proj.addComponent(c, qty);
	                	}

	                	theWindow.observableProjectList.add(proj);
	                	theWindow.myAnalysisPanels.add(new DIYAnalysisPanel(primaryStage, proj));
	                	theWindow.myProjectPanels.add(new DIYProjectPanel(primaryStage, proj, theWindow.myComponentDatabase));
	    
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                    
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
