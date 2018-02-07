package application;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class PodResults {
	
	private SimpleStringProperty identifier;
	private SimpleDoubleProperty pod5;
	private SimpleDoubleProperty pod50;
	private SimpleDoubleProperty pod95;
	
//	public AopbnResult(String nodeName, double probNodeActive, double probNodeInactive){
//		this.nodeName = new SimpleStringProperty(nodeName);
//		this.probNodeActive = new SimpleDoubleProperty(probNodeActive);
//		this.probNodeInactive = new SimpleDoubleProperty(probNodeInactive);
//	}
	
	public PodResults(String identifier, double pod5, double pod50, double pod95){
		this.identifier = new SimpleStringProperty(identifier);
		this.pod5 = new SimpleDoubleProperty(pod5);
		this.pod50 = new SimpleDoubleProperty(pod50);
		this.pod95 = new SimpleDoubleProperty(pod95);
	}
	
	public SimpleStringProperty identifierProperty(){
		return this.identifier;
	}
	
	public SimpleDoubleProperty pod5Property(){
		return this.pod5;
	}
	
	public SimpleDoubleProperty pod50Property(){
		return this.pod50;
	}
	
	public SimpleDoubleProperty pod95Property(){
		return this.pod95;
	}

}
