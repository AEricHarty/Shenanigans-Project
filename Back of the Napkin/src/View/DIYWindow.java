package View;

import java.util.LinkedList;
import java.util.List;

import Model.Component;
import Model.ComponentDatabase;
import Model.Project;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
 
public class DIYWindow extends Application {

	ComponentDatabase myComponentDatabase;
	
	/**
	 * @author Keegan Wantz - wantzkt@uw.edu
	 * 
	 * Constructs a DIY Window and connects to the component database. If it fails to connect, then the application cannot run.
	 **/
	public DIYWindow() {
    	myComponentDatabase = new ComponentDatabase();
    	if (myComponentDatabase.connect() != 1) {
			Alert information = new Alert(AlertType.INFORMATION);
			information.setTitle("Failed to Open Database");
			information.setHeaderText("Failed to open the database.");
			information.setContentText("Closing application.");
			information.showAndWait();
	    	Platform.exit();
    	} else {
    		System.out.println("Database test:");
    		List<Component> cList = myComponentDatabase.getAllComponents();
    		for (Component c : cList) {
    			System.out.println(c.getMyID() + ", " + c.getName() + ", " + c.getCost() + ", " + c.getCostPerMonth());
    		}
    		
    	}
	}
	
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Back of the Napkin!");
        
        // temp project and list, used for testing
        final Project project = new Project();
        final List<Project> list = new LinkedList<>();
        list.add(new Project());
        list.add(new Project());
        list.add(new Project());
        
        BorderPane border = new BorderPane();

        // Menu bar (top)
        final DIYMenu myMenu = new DIYMenu();
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(myMenu.getFileMenu(), myMenu.getHelpMenu());
        border.setTop(menuBar);
        
        // Analysis Panel (right)
        final DIYAnalysisPanel analysisPanel = new DIYAnalysisPanel(primaryStage, project);
        border.setRight(analysisPanel.getPanel());
        
        // Side Panel (left)
        final DIYSidePanel sidePanel = new DIYSidePanel(primaryStage, list);
        border.setLeft(sidePanel.getPanel());
        
        // Project Panel (center)
        final DIYProjectPanel projectPanel = new DIYProjectPanel(primaryStage, project, myComponentDatabase);
        border.setCenter(projectPanel.getPanel());
        
        Scene sceneMain = new Scene(border);
        primaryStage.setScene(sceneMain);
        
        primaryStage.setResizable(false); // Automatically adjust size (Aaron 3/9/2018 1:18am)
        primaryStage.sizeToScene(); // Dynamic window size (Aaron 3/9/2018 12:36am)
        
        primaryStage.show();
    }
}