/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.PremierLeague;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.PremierLeague.model.Adiacenza;
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
    	this.txtResult.appendText("Grafo Creato:"+"\n");
    	String mese= this.cmbMese.getSelectionModel().getSelectedItem();
    	int mm= this.mese(mese);
    	String minS= this.txtMinuti.getText();
    	int minuti;
    	try {
    		minuti=Integer.parseInt(minS);
    	}catch(NumberFormatException e) {
    		this.txtResult.setText("Inserisci un numero di minuti valido!");
    		return;
    	}
    	this.model.creaGrafo(minuti, mm);
    	List <Adiacenza> result= new ArrayList<>(this.model.connessioniMax(minuti, mm, this.model.getIdMap() ));
    	for(Adiacenza aa:result) {
    		this.txtResult.appendText(aa.toString()+"\n");
    	}
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	this.txtResult.clear();
    	this.txtResult.appendText("Grafo Creato:"+"\n");
    	String mese= this.cmbMese.getSelectionModel().getSelectedItem();
    	int mm= this.mese(mese);
    	String minS= this.txtMinuti.getText();
    	int minuti;
    	try {
    		minuti=Integer.parseInt(minS);
    	}catch(NumberFormatException e) {
    		this.txtResult.setText("Inserisci un numero di minuti valido!");
    		return;
    	}
    	this.model.creaGrafo(minuti, mm);
    	
    	this.txtResult.appendText("#VERTICI: "+this.model.getNVertici()+"\n");
    	this.txtResult.appendText("#ARCHI: "+this.model.getNArchi()+"\n");
    	this.cmbM1.getItems().addAll(this.model.getGrafo().vertexSet());
    	this.cmbM2.getItems().addAll(this.model.getGrafo().vertexSet());
    	
    }

    @FXML
    void doCollegamento(ActionEvent event) {
    	this.txtResult.clear();
    	List <Match> result= new ArrayList <>(model.trovaPercorso(this.cmbM1.getValue(), this.cmbM2.getValue()));
    	for(Match mm: result) {
    		this.txtResult.appendText(mm.toString()+"\n");
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
    
    public void setModel(Model model) {
    	this.model = model;
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
    
    public int mese(String mese) {
    	int mm=0;
    	if(mese.equals("Gennaio"))
    		mm=1;
    	if(mese.equals("Febbraio"))
    		mm=2;
    	if(mese.equals("Marzo"))
    		mm=3;
    	if(mese.equals("Aprile"))
    		mm=4;
    	if(mese.equals("Maggio"))
    		mm=5;
    	if(mese.equals("Giugno"))
    		mm=6;
    	if(mese.equals("Luglio"))
    		mm=7;
    	if(mese.equals("Agosto"))
    		mm=8;
    	if(mese.equals("Settembre"))
    		mm=9;
    	if(mese.equals("Ottobre"))
    		mm=10;
    	if(mese.equals("Novembre"))
    		mm=11;
    	if(mese.equals("Dicembre"))
    		mm=12;
    	return mm;
    }
    
    
}
