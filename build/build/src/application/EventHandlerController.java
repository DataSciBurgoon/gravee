package application;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import org.apache.commons.math3.stat.descriptive.rank.Percentile;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import mil.army.usace.bctt.gravee.DoseResponse;
import mil.army.usace.bctt.gravee.Parser;
import mil.army.usace.bctt.gravee.Spmr;

public class EventHandlerController implements Initializable, ControlledScreen{
	
	@FXML
    private ComboBox<String> comboBox = new ComboBox<String>();
	
	@FXML private Stage stage;
	@FXML Label fileNameLabel;
	@FXML private TableView<PodResults> podTable;
	@FXML private TableColumn<PodResults, String> columnIdentifier;
	@FXML private TableColumn<PodResults, Double> columnPod5;
	@FXML private TableColumn<PodResults, Double> columnPod50;
	@FXML private TableColumn<PodResults, Double> columnPod95;
	@FXML private ObservableList<PodResults> olPodResults = FXCollections.observableArrayList();
	
	private String aopn;
	private File file;
	
	final FileChooser fileChooser = new FileChooser();
	
	ScreensController myController;
	
	public void handleFileOpenButtonAction(ActionEvent event){
		file = fileChooser.showOpenDialog(stage);
		fileNameLabel.setText(file.getName());
		
	}
	
	public void handleSubmitButtonAction(ActionEvent event){
		//System.out.println("file name: " + file.getName());
		Parser parser = new Parser(file);
		if(olPodResults != null){
			olPodResults.clear();
		}
		HashMap<String, ArrayList> hmDoseResponseData = parser.getDoseResponseData();
		
		hmDoseResponseData.forEach((identifier, alDoseResponseData) -> {
			if(!identifier.equals("")){
				System.out.println("identifier: " + identifier);
				Spmr sprm = new Spmr(alDoseResponseData, 1000);
				double[] pods = sprm.getPods();
				Percentile percentile = new Percentile();
				percentile.withEstimationType(Percentile.EstimationType.R_7);
				percentile.setData(pods);
				double pod5 = percentile.evaluate(5);
				double pod50 = percentile.evaluate(50);
				double pod95 = percentile.evaluate(95);
				olPodResults.add(new PodResults(identifier, pod5, pod50, pod95));
			}
			
		});
		
		System.out.println("olPodResults size: " + olPodResults.size());
		
		podTable.setItems(olPodResults);
		columnIdentifier.setCellValueFactory(cellData -> cellData.getValue().identifierProperty());
		columnPod5.setCellValueFactory(cellData -> cellData.getValue().pod5Property().asObject());
		columnPod50.setCellValueFactory(cellData -> cellData.getValue().pod50Property().asObject());
		columnPod95.setCellValueFactory(cellData -> cellData.getValue().pod95Property().asObject());
		
		try{
			
			FileWriter fw = new FileWriter(file.getPath() + "_predictions.txt");
			fw.write("AssayName\t5% POD\t50% POD\t95% POD\n");
			for(PodResults podResults: olPodResults){
				String output = podResults.identifierProperty().getValue() + "\t";
				output += podResults.pod5Property().getValue().toString() + "\t";
				output += podResults.pod50Property().getValue().toString() + "\t";
				output += podResults.pod95Property().getValue().toString() + "\n";
				fw.write(output);
				}
			fw.close();
			}
		catch(IOException e){
			e.printStackTrace();
		}
		
	}
	
	
	

	@Override
	public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
//		comboBox.getSelectionModel().selectedItemProperty();
//		
//		
//		comboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
//
//			@Override
//			public void changed(ObservableValue<? extends String> selected, String oldValue, String newValue) {
//				if(newValue != null){
//					if(newValue.contentEquals("Steatosis")){
//						aopn = "steatosis";
//						
//					}
//					else if(newValue.contentEquals("Steroidogenesis")){
//						System.out.println("yep");
//						aopn = "steroidogenesis";
//						
//					}
//				}
//				
//			}
//			
//		});
			
	}




	@Override
	public void setScreenParent(ScreensController screenPage) {
//		myController = screenParent;
		
	}
	
//	@FXML
//	private void goToMain(ActionEvent event){
//		myController.setScreen(ScreensFramework.MAIN_SCREEN);
//	}

}
