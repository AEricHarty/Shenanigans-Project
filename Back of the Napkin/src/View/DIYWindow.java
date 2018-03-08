package View;

import java.util.LinkedList;
import java.util.List;

import Model.ComponentDatabase;
import Model.Project;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
 
public class DIYWindow extends Application {

	ComponentDatabase myComponentDatabase;
	
	public DIYWindow() {
    	myComponentDatabase = new ComponentDatabase();
    	if (myComponentDatabase.connect() != 1) {
			Alert information = new Alert(AlertType.INFORMATION);
			information.setTitle("Failed to Open Database");
			information.setHeaderText("Closing application.");
			information.showAndWait();
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
        final DIYAnalysisPanel analysisPanel = new DIYAnalysisPanel(project);
        border.setRight(analysisPanel.getPanel());
        
        // Side Panel (left)
        final DIYSidePanel sidePanel = new DIYSidePanel(list);
        border.setLeft(sidePanel.getPanel());
        
        // Project Panel (center)
        final DIYProjectPanel projectPanel = new DIYProjectPanel(project, myComponentDatabase);
        border.setCenter(projectPanel.getPanel());
        
        
        Scene sceneMain = new Scene(border, 1280,720);
        primaryStage.setScene(sceneMain);
        primaryStage.show();
    }
}