package View;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
 
public class DIYWindow extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Back of the Napkin!");
        
        BorderPane border = new BorderPane();

        final Menu fileMenu = new Menu("File");
        final Menu helpMenu = new Menu("Help");
        MenuItem loadMenuItem = new MenuItem("Load");
        MenuItem saveMenuItem = new MenuItem("Save");
        MenuItem aboutMenuItem = new MenuItem("About");
        helpMenu.getItems().add(aboutMenuItem);
        fileMenu.getItems().add(loadMenuItem);
        fileMenu.getItems().add(saveMenuItem);
        
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
        
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu, helpMenu);
        
        border.setTop(menuBar);

        Scene sceneMain = new Scene(border, 1280,720);
        primaryStage.setScene(sceneMain);
        primaryStage.show();
    }
}