package View;

import java.util.LinkedList;
import java.util.List;

import Model.Component;
import Model.ComponentDatabase;
import Model.Project;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuBar;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;
 
public class DIYWindow extends Application {

	static int numOfProjects = 4;
	ObservableList<Project> observableProjectList = FXCollections.observableArrayList();
	ListView<Project> projectListView = new ListView<Project>();
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
        final List<Project> list = new LinkedList<>();
        for (int i = 0; i < numOfProjects; i++) {
        	list.add(new Project());
        }
        
        // Create the ListView
        for (int i = 0; i < numOfProjects; i++) {
        	observableProjectList.add(list.get(i));
        }
        projectListView = new ListView<Project>(observableProjectList);
        projectListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        
        // Change the displayed name of project in ListView
        projectListView.setCellFactory(new Callback<ListView<Project>, ListCell<Project>>() {
            @Override
            public ListCell<Project> call(ListView<Project> param) {
                 ListCell<Project> cell = new ListCell<Project>() {
                     @Override
                    protected void updateItem(Project item, boolean empty) {
                        super.updateItem(item, empty);
                        if(item != null) {
                            setText(item.getName());
                        } else {
                            setText(null);
                        }
                    }
                 };
                return cell;
            }
        });
        
        BorderPane border = new BorderPane();
        
        // Side Panel (left)
        //final DIYSidePanel sidePanel = new DIYSidePanel(primaryStage, list);
        border.setLeft(projectListView);
        
        // Analysis Panel (right)
        final List<DIYAnalysisPanel> analysisPanels = new LinkedList<>();
        for (int i = 0; i < numOfProjects; i++) {
        	analysisPanels.add(new DIYAnalysisPanel(primaryStage, list.get(i)));
        }
        border.setRight(analysisPanels.get(0).getPanel());
        
        // Project Panel (center)
        int j;
        final List<DIYProjectPanel> projectPanels = new LinkedList<>();
        for (j = 0; j < numOfProjects; j++) {
        	projectPanels.add(new DIYProjectPanel(primaryStage, list.get(j), myComponentDatabase));
        }
        border.setCenter(projectPanels.get(0).getPanel());   
        
        // Menu bar (top)
        final DIYMenu myMenu = new DIYMenu(primaryStage, list.get(j-1));	//Added to make save/load work -EH
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(myMenu.getFileMenu(), myMenu.getHelpMenu());
        border.setTop(menuBar);
        
        // Add listeners to ListView's Items
        projectListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Project>() {
        	public void changed(ObservableValue<? extends Project> observable,Project oldValue, Project newValue) {
        		System.out.println("New Project: " + newValue.getName());
        		myMenu.updateProject(newValue);		//Added to make save/load work -EH
        		int index = list.indexOf(newValue);
        		border.setCenter(projectPanels.get(index).getPanel());
        		border.setRight(analysisPanels.get(index).getPanel());
        	}
        });
        
        // Select first project by default
        projectListView.getSelectionModel().select(0);
        
        
        Scene sceneMain = new Scene(border);
        primaryStage.setScene(sceneMain);
        
        //primaryStage.setResizable(false); // Automatically adjust size (Aaron 3/9/2018 1:18am)
        primaryStage.sizeToScene(); // Dynamic window size (Aaron 3/9/2018 12:36am)
        
        primaryStage.show();
    }
}