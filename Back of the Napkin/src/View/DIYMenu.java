package View;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Alert.AlertType;

/**
 * The Menu bar.
 * 
 * @author Aaron Bardsley
 * @modified Khoa Doan - khoadoan@uw.edu (move code to new class)
 * @modified Keegan Wantz - wantzkt@uw.edu (added EventHandler for the About button)
 * @version .75
 */
public class DIYMenu {
	
	/** The File menu. */
	private final Menu myFileMenu;
	
	/** The Help menu. */
	private final Menu myHelpMenu;
	
	/**
     * Constructs the Menu bar.
     * @author Aaron Bardsley
     * @modified Keegan Wantz - wantzkt@uw.edu (added EventHandler for the About button)
     */
	public DIYMenu() {
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
}
