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
import javafx.geometry.Insets;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class ComponentSelector extends Dialog<Component>{
	
	private TableView<Component> myTable;
	
	private ComponentDatabase myDatabase;
	
	private Component mySelectedComponent;
	
	private Project myProject;
	
	public ComponentSelector(ComponentDatabase theDatabase, Project theProject) {
		
		myTable = new TableView<Component>();
		myDatabase = theDatabase;
		myProject = theProject;

		myTable.setItems(getInitialData());
		 
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
		radiusCol.setCellValueFactory(new PropertyValueFactory<Component, Double>("radius"));
		TableColumn<Component, Double> weightCol = new TableColumn<Component, Double>("Weight");
		weightCol.setCellValueFactory(new PropertyValueFactory<Component, Double>("weight"));
		TableColumn<Component, String> materialCol = new TableColumn<Component, String>("Materials");
		materialCol.setCellValueFactory(new PropertyValueFactory<Component, String>("material"));
		TableColumn<Component, Double> costPerManHrCol = new TableColumn<Component, Double>("Cost Per Man-hour");
	    costPerManHrCol.setCellValueFactory(new PropertyValueFactory<Component, Double>("manHrs"));
		
		myTable.getColumns().setAll(nameCol, initialCostCol, monthlyCostCol, lengthCol, widthCol, heightCol,
				radiusCol, materialCol, costPerManHrCol);
		myTable.setPrefWidth(1000);
		myTable.setPrefHeight(500);
		myTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
		myTable.getSelectionModel().selectedIndexProperty().addListener((obs, oldSel, newSel) -> {
			if (newSel != null) {
				mySelectedComponent = myTable.getSelectionModel().getSelectedItem();
			}
		});
		
		ButtonType applyButton = new ButtonType("Apply", ButtonData.APPLY);
		ButtonType cancelButton = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
		
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
		
		VBox vbox = new VBox(10);
		vbox.setPadding(new Insets(10, 10, 10, 10));
		vbox.getChildren().addAll(myTable);

		this.getDialogPane().setContent(vbox);

		this.setTitle("Select Component");
		this.setHeaderText("Choose a component then click apply to add it to your " + myProject.getName());
		this.setResizable(true);
		
	}
	
	public Optional<Component> view() {
		return this.showAndWait();
	}
	
	private ObservableList<Component> getInitialData() {		
		List<Component> list = new ArrayList<Component>();
		for(Component initialComponent : myDatabase.getAllComponents()) {
			list.add(initialComponent);
		}		
		ObservableList<Component> data = FXCollections.observableList(list);		
		return data;		
	}

}
