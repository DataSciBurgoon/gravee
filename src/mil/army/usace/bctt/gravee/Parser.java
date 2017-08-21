package mil.army.usace.bctt.gravee;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

public class Parser {
	
	private File inputFile;
	
	private HashMap<String, ArrayList> hmIdentifierDR = new HashMap<String, ArrayList>();
	
	public Parser(File inputFile){
		this.inputFile = inputFile;
		parseFile();
	}
	
	private void parseFile(){
		Path filepath = Paths.get(inputFile.getAbsolutePath());
		String line;
		ArrayList<Double> doses = new ArrayList<Double>();
		
		try(BufferedReader reader = Files.newBufferedReader(filepath, StandardCharsets.UTF_8)){
			int i = 0;
			String identifier = "";
			ArrayList<DoseResponse> alDoseResponses = new ArrayList<DoseResponse>();;
			while((line = reader.readLine()) != null){
				String[] entries = line.split("\t");
				if(i == 0){
					for(int j = 1; j < entries.length; j++){
						doses.add(new Double(Double.parseDouble(entries[j])));
						i++;
					}
				}
				else{
					if(identifier.equals("")){
						TreeMap<Double, Double> tmDoseResponseData = new TreeMap<Double, Double>();
						for(int j = 1; j < entries.length; j++){
							tmDoseResponseData.put(doses.get(j-1), new Double(Double.parseDouble(entries[j])));
						}
						DoseResponse doseResponse = new DoseResponse(entries[0], tmDoseResponseData);
						alDoseResponses.add(doseResponse);
						identifier = entries[0];
					}
					else if(entries[0].equals(identifier)){
						TreeMap<Double, Double> tmDoseResponseData = new TreeMap<Double, Double>();
						for(int j = 1; j < entries.length; j++){
							tmDoseResponseData.put(doses.get(j-1), new Double(Double.parseDouble(entries[j])));
						}
						DoseResponse doseResponse = new DoseResponse(entries[0], tmDoseResponseData);
						alDoseResponses.add(doseResponse);
					}
					else{
						alDoseResponses.trimToSize();
						hmIdentifierDR.put(identifier, alDoseResponses);
						alDoseResponses = new ArrayList<DoseResponse>();
						identifier = entries[0];
						TreeMap<Double, Double> tmDoseResponseData = new TreeMap<Double, Double>();
						for(int j = 1; j < entries.length; j++){
							tmDoseResponseData.put(doses.get(j-1), new Double(Double.parseDouble(entries[j])));
						}
						DoseResponse doseResponse = new DoseResponse(entries[0], tmDoseResponseData);
						alDoseResponses.add(doseResponse);
					}
				}
				alDoseResponses.trimToSize();
				hmIdentifierDR.put(identifier, alDoseResponses);
			}
		}
		catch(IOException e){
			e.printStackTrace();
			
		}
	}
	
	public HashMap<String, ArrayList> getDoseResponseData(){
		return this.hmIdentifierDR;
	}

}
