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
    	String mS= this.txtMinuti.getText();
    	int minuti;
    	try {
    		minuti=Integer.parseInt(mS);
    		
    	}catch(NumberFormatException e ) {
    		this.txtResult.setText("Inserisci un valore numerico valido!");
    		return;
    	}
    	String mese= this.cmbMese.getValue();
    	if(mese==null) {
    		this.txtResult.appendText("Seleziona un mese!");
    		return;
    	}
    	int meseInt = this.ottieniMese(mese);
    	List<Adiacenza> result= this.model.getAdiacenza(minuti, meseInt);
    	for(Adiacenza a: result)
    		this.txtResult.appendText(a.toString());
    	
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	this.txtResult.clear();
    	String mS= this.txtMinuti.getText();
    	int minuti;
    	try {
    		minuti=Integer.parseInt(mS);
    		
    	}catch(NumberFormatException e ) {
    		this.txtResult.setText("Inserisci un valore numerico valido!");
    		return;
    	}
    	String mese= this.cmbMese.getValue();
    	if(mese==null) {
    		this.txtResult.appendText("Seleziona un mese!");
    		return;
    	}
    	int meseInt = this.ottieniMese(mese);
    	
    	this.txtResult.appendText("Grafo creato...\n");
    	this.model.creaGrafo(minuti, meseInt);
    	this.txtResult.appendText("#VERTICI: "+this.model.getNVertici()+"\n");
    	this.txtResult.appendText("#ARCHI: "+this.model.getNArchi()+"\n");
    	this.cmbM1.getItems().addAll(model.getGrafo().vertexSet());
    	this.cmbM2.getItems().addAll(model.getGrafo().vertexSet());
    	
    	
    }

    @FXML
    void doCollegamento(ActionEvent event) {
    	this.txtResult.clear();
    	Match m1= this.cmbM1.getValue();
    	Match m2= this.cmbM2.getValue();
    	if(m1==null || m2==null) {
    		this.txtResult.setText("Seleziona entrambe i match");
    		return;
    	}
    	if(m1.equals(m2)) {
    		this.txtResult.setText("Selezionata due matches diversi!");
    		return;
    	}
    	if(m1.getTeamHomeID()!=m2.getTeamHomeID() && m1.getTeamAwayID()!=m2.getTeamAwayID() && m1.getTeamAwayID()!=m2.getTeamHomeID()) {
    		List <Match> result= model.trovaPercorso(m1, m2);
    		for(Match m: result) {
    			this.txtResult.appendText(m.toString()+"\n");
    		}
    		
    	}
    	else {
    		this.txtResult.setText("Seleziona due match con le squadre diverse!");
    		return;
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
    
    
    public int ottieniMese (String mese) {
    	if(mese.equals("Gennaio"))
    		return 1;
    	if(mese.equals("Febbraio"))
    		return 2;
    	if(mese.equals("Marzo"))
    		return 3;
    	if(mese.equals("Aprile"))
    		return 4;
    	if(mese.equals("Maggio"))
    		return 5;
    	if(mese.equals("Giugno"))
    		return 6;
    	if(mese.equals("Luglio"))
    		return 7;
    	if(mese.equals("Agosto"))
    		return 8;
    	if(mese.equals("Settembre"))
    		return 9;
    	if(mese.equals("Ottobre"))
    		return 10;
    	if(mese.equals("Novembre"))
    		return 11;
    	if(mese.equals("Dicembre"))
    		return 12;
    	return -1;
    }
    
  
}
