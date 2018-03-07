package View;

import java.util.LinkedList;
import java.util.List;

import Model.DIYProject;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
 
public class DIYWindow extends Application {

	
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Back of the Napkin!");
        
        // temp project and list, used for testing
        final DIYProject project = new DIYProject();
        final List<DIYProject> list = new LinkedList<>();
        list.add(new DIYProject());
        list.add(new DIYProject());
        list.add(new DIYProject());
        
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
        final DIYProjectPanel projectPanel = new DIYProjectPanel(project);
        border.setCenter(projectPanel.getPanel());
        
        
        Scene sceneMain = new Scene(border, 1280,720);
        primaryStage.setScene(sceneMain);
        primaryStage.show();
    }
}