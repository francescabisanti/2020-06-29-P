/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.PremierLeague;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.PremierLeague.model.Match;
import it.polito.tdp.PremierLeague.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnConnessioneMassima"
    private Button btnConnessioneMassima; // Value injected by FXMLLoader

    @FXML // fx:id="btnCollegamento"
    private Button btnCollegamento; // Value injected by FXMLLoader

    @FXML // fx:id="txtMinuti"
    private TextField txtMinuti; // Value injected by FXMLLoader

    @FXML // fx:id="cmbMese"
    private ComboBox<String> cmbMese; // Value injected by FXMLLoader

    @FXML // fx:id="cmbM1"
    private ComboBox<Match> cmbM1; // Value injected by FXMLLoader

    @FXML // fx:id="cmbM2"
    private ComboBox<Match> cmbM2; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doConnessioneMassima(ActionEvent event) {
    	this.txtResult.clear();
    	List <DefaultWeightedEdge> result= model.trovaConnMax();
    	if(this.model.getGrafo()==null) {
    		this.txtResult.setText("Crea prima il grafo");
    		return;
    	}
    	this.txtResult.appendText("Coppie con connessione massima\n");
    	for(DefaultWeightedEdge e: result) {
    		this.txtResult.appendText(model.getGrafo().getEdgeSource(e)+"  -  "+model.getGrafo().getEdgeTarget(e)+" ("+model.getGrafo().getEdgeWeight(e)+")\n");
    	}
    	
    	
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	this.txtResult.clear();
    	String mS=this.txtMinuti.getText();
    	Integer minuti;
    	try {
    		minuti=Integer.parseInt(mS);
    	}catch(NumberFormatException e) {
    		this.txtMinuti.setText("Inserisci un numero valido!");
    		return;
    	}
    	String meseS=this.cmbMese.getValue();
    	int mese= this.ottieniMese(meseS);
    	this.model.creaGrafo(mese, minuti);
    	if(this.model.getGrafo()==null) {
    		this.txtResult.setText("Crea il grafo!");
    		return;
    		
    	}
    	this.txtResult.appendText("Creazione grafo...\n");
    	this.txtResult.appendText("#VERTICI: "+this.model.getNVertici()+"\n");
    	this.txtResult.appendText("#ARCHI: "+this.model.getNArchi()+"\n");
    	for(Match m: model.getGrafo().vertexSet()) {
    		this.cmbM1.getItems().add(m);
    		this.cmbM2.getItems().add(m);
    	}
    }

    @FXML
    void doCollegamento(ActionEvent event) {
    	this.txtResult.clear();
    	if(model.getGrafo()==null) {
    		this.txtResult.setText("Crea prima il grafo");
    		return;
    	}
    	Match m1= this.cmbM1.getValue();
    	Match m2= this.cmbM2.getValue();
    	if(m1.equals(m2)) {
    		this.txtResult.setText("Seleziona due match diversi");
    		return;
    	}
    	List <Match> result= model.trovaPercorsoMigliore(m1, m2);
    	for(Match m: result) {
    		this.txtResult.appendText(m.toString()+"\n");
    	}
    	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnConnessioneMassima != null : "fx:id=\"btnConnessioneMassima\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCollegamento != null : "fx:id=\"btnCollegamento\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtMinuti != null : "fx:id=\"txtMinuti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbMese != null : "fx:id=\"cmbMese\" was not injected: check your FXML file 'Scene.fxml'.";        assert cmbM1 != null : "fx:id=\"cmbM1\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbM2 != null : "fx:id=\"cmbM2\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    public int ottieniMese(String mese) {
    	if(mese.equals("Gennaio"))
    		return 1;
    	else if(mese.equals("Febbraio"))
    		return 2;
    	else if(mese.equals("Marzo"))
    		return 3;
    	else if(mese.equals("Aprile"))
    		return 4;
    	else if(mese.equals("Maggio"))
    		return 5;
    	else if(mese.equals("Giugno"))
    		return 6;
    	else if(mese.equals("Luglio"))
    		return 7;
    	else if(mese.equals("Agosto"))
    		return 8;
    	else if(mese.equals("Settembre"))
    		return 9;
    	else if(mese.equals("Ottobre"))
    		return 10;
    	else if(mese.equals("Novembre"))
    		return 11;
    	else if(mese.equals("Dicembre"))
    		return 12;
    	else
    		return -1;
    	
    }
    
    public void setModel(Model model) {
    	this.model=model;
    	this.cmbMese.getItems().add("Gennaio");
    	this.cmbMese.getItems().add("Febbraio");
    	this.cmbMese.getItems().add("Marzo");
    	this.cmbMese.getItems().add("Aprile");
    	this.cmbMese.getItems().add("Maggio");
    	this.cmbMese.getItems().add("Giugno");
    	this.cmbMese.getItems().add("Luglio");
    	this.cmbMese.getItems().add("Agosto");
    	this.cmbMese.getItems().add("Settembre");
    	this.cmbMese.getItems().add("Ottobre");
    	this.cmbMese.getItems().add("Novembre");
    	this.cmbMese.getItems().add("Dicembre");
    	
  
    }
    
    
  
    
  
}
