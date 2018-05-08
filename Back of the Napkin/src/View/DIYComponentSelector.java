package View;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import Model.Component;
import Model.ComponentDatabase;
import Model.Project;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

/**
 * 
 * @author Aaron Bardsley
 * 
 * This is the component selector dialog for the project panel.
 * Displays all existing components from the component database
 * and allows the user to select a component to add to a project.
 */
public class DIYComponentSelector extends Dialog<Component>{
	
	/** The table for components **/
	private TableView<Component> myTable;
	
	/** The existing database of components **/
	private ComponentDatabase myDatabase;
	
	/** The currently selected component **/
	private Component mySelectedComponent;
	
	/** The Current project to work with **/
	private Project myProject;
	
	/** The observable list of components for the table **/
	private ObservableList<Component> myObservableList;
	
	/** The new component to add to the database and table **/
	private Component myNewComponent;
	
	/**
	 * @author Aaron Bardsley
	 * 
	 * @param theDatabase the existing component database
	 * @param theProject the current project
	 */
	public DIYComponentSelector(final ComponentDatabase theDatabase, final Project theProject) {
		
		myTable = new TableView<Component>();
		myDatabase = theDatabase;
		myProject = theProject;

		this.buildInitialTable();
		
		ButtonType applyButton = new ButtonType("Apply", ButtonData.APPLY);
		ButtonType cancelButton = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
		
		//The currently selected component is returned when the user clicks 'Apply' button
		this.setResultConverter(new Callback<ButtonType, Component>() {
			@Override
			public Component call(ButtonType b) {				
				if (b == applyButton) {
					return mySelectedComponent;
				}
				return null;
			}			
		});

		this.getDialogPane().getButtonTypes().add(applyButton);
		this.getDialogPane().getButtonTypes().add(cancelButton);
		
		BorderPane buttonSplitter = new BorderPane();
		FlowPane buttonFlow = new FlowPane();
		
		Button newComponentButton = new Button("Create New Component");
		Button deleteComponentButton = new Button("Delete Component");
		buttonFlow.getChildren().add(newComponentButton);
		buttonFlow.getChildren().add(deleteComponentButton);
		
		newComponentButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				DIYAddComponentDialog newDialog = new DIYAddComponentDialog();
				Optional<Component> result = newDialog.view();
				if (result.isPresent()) {
					myNewComponent = result.get();
					
					myObservableList.add(myNewComponent);
					myDatabase.addComponent(myNewComponent);
				}
			}
		});
		
		deleteComponentButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {				
				if (mySelectedComponent != null) {
					myDatabase.deleteComponent(mySelectedComponent.getMyID());
					int rowIndex = myTable.getSelectionModel().getSelectedIndex();
					myObservableList.remove(rowIndex);	
				} else {
					return;
				}			
			}			
		});

		buttonSplitter.setLeft(buttonFlow);
		VBox vbox = new VBox(10);
		vbox.setPadding(new Insets(10, 10, 10, 10));
		vbox.getChildren().addAll(myTable, buttonSplitter);

		this.getDialogPane().setContent(vbox);

		this.setTitle("Select Component");
		this.setHeaderText("Choose a component then click apply to add it to your " + myProject.getName());
		this.setResizable(true);
		
	}
	
	/**
	 * @author Aaron Bardsley
	 * @return returns an Optional Component
	 */
	public Optional<Component> view() {
		return this.showAndWait();
	}
	
	/**
	 * @author Aaron Bardsley
	 * @return The observable list of components used by the table
	 */
	private void setObservableList() {		
		List<Component> list = new ArrayList<Component>();
		for(Component initialComponent : myDatabase.getAllComponents()) {
			list.add(initialComponent);
		}		
		myObservableList = FXCollections.observableList(list);
	}

	/**
	 * @author Aaron Bardsley
	 * 
	 * Initialize the table from the constructor.
	 */
	//@SuppressWarnings("unchecked") //Unnecessary warning
	private void buildInitialTable() {

		setObservableList();
		myTable.setItems(myObservableList);
		
		TableColumn<Component, String> nameCol = new TableColumn<Component, String>("Name");
		nameCol.setCellValueFactory(new PropertyValueFactory<Component, String>("name"));
		
		TableColumn<Component, BigDecimal> initialCostCol = new TableColumn<Component, BigDecimal>("Initial Cost");
		initialCostCol.setCellValueFactory(new PropertyValueFactory<Component, BigDecimal>("cost"));
		
		TableColumn<Component, BigDecimal> monthlyCostCol = new TableColumn<Component, BigDecimal>("Monthly Cost");
		monthlyCostCol.setCellValueFactory(new PropertyValueFactory<Component, BigDecimal>("unitCPM"));
		
		TableColumn<Component, Double> lengthCol = new TableColumn<Component, Double>("Length");
		lengthCol.setCellValueFactory(new PropertyValueFactory<Component, Double>("length"));
		
		TableColumn<Component, Double> widthCol = new TableColumn<Component, Double>("Width");
		widthCol.setCellValueFactory(new PropertyValueFactory<Component, Double>("width"));
		
		TableColumn<Component, Double> heightCol = new TableColumn<Component, Double>("Height");
		heightCol.setCellValueFactory(new PropertyValueFactory<Component, Double>("height"));
		
		TableColumn<Component, Double> radiusCol = new TableColumn<Component, Double>("Radius");
		radiusCol.setCellValueFactory(new PropertyValueFactory<Component, Double>("myRadius"));
		
		TableColumn<Component, Double> weightCol = new TableColumn<Component, Double>("Weight");
		weightCol.setCellValueFactory(new PropertyValueFactory<Component, Double>("weight"));
		
		TableColumn<Component, String> materialCol = new TableColumn<Component, String>("Materials");
		materialCol.setCellValueFactory(new PropertyValueFactory<Component, String>("material"));

		TableColumn<Component, Double> manHrsCol = new TableColumn<Component, Double>("Man Hours Amount");
		manHrsCol.setCellValueFactory(new PropertyValueFactory<Component, Double>("unitManHrs"));
		
		TableColumn<Component, BigDecimal> costPerManHrCol = new TableColumn<Component, BigDecimal>("Cost Per Man-hour");
	    costPerManHrCol.setCellValueFactory(new PropertyValueFactory<Component, BigDecimal>("unitCostPerManHr"));
		
		myTable.getColumns().setAll(nameCol, initialCostCol, monthlyCostCol, lengthCol, widthCol, heightCol,
				radiusCol, materialCol, manHrsCol, costPerManHrCol);
		myTable.setPrefWidth(1200);
		myTable.setPrefHeight(500);
		myTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
		myTable.getSelectionModel().selectedIndexProperty().addListener((obs, oldSel, newSel) -> {
			if (newSel != null) {
				mySelectedComponent = myTable.getSelectionModel().getSelectedItem();
			}
		});
	}
	
}
