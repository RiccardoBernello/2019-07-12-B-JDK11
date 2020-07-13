/**
 * Sample Skeleton for 'Food.fxml' Controller Class
 */

package it.polito.tdp.food;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.food.model.Cibo;
import it.polito.tdp.food.model.CiboPeso;
import it.polito.tdp.food.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FoodController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtPorzioni"
    private TextField txtPorzioni; // Value injected by FXMLLoader

    @FXML // fx:id="txtK"
    private TextField txtK; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnalisi"
    private Button btnAnalisi; // Value injected by FXMLLoader

    @FXML // fx:id="btnGrassi"
    private Button btnGrassi; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimula"
    private Button btnSimula; // Value injected by FXMLLoader

    @FXML // fx:id="boxFood"
    private ComboBox<Cibo> boxFood; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	Integer calorie;
    	try {
    		calorie = Integer.parseInt(this.txtPorzioni.getText());
    	}catch(NumberFormatException e) {
    		this.txtResult.appendText("Nessuna porzione selezionata");
    		return;
    	}
    	String msg = this.model.creaGrafo(calorie);
    	this.txtResult.appendText(msg+"\n");
    	this.boxFood.setDisable(false);
    	this.txtK.setDisable(false);
    	this.btnGrassi.setDisable(false);
    	this.btnGrassi.setDisable(false);
    	this.btnSimula.setDisable(false);
    	this.boxFood.getItems().clear();
    	this.boxFood.getItems().addAll(this.model.getCibi());
    }

    @FXML
    void doGrassi(ActionEvent event) {
    	txtResult.clear();
    	Cibo c= this.boxFood.getValue();
    	if(c==null) {
    		this.txtResult.appendText("Nessun Cibo selezionato");
    		return;
    	}
    	List<CiboPeso> viciniPeso=new ArrayList<>(this.model.getViciniMin(c));
    	if(viciniPeso.size()==0) {
    		this.txtResult.appendText("Nessun vicino trovato");
    		return;
    	}else {
    		this.txtResult.appendText("Vicini di: "+ c.toString()+"\n");
    		Integer counter =0;
    		for(CiboPeso cp: viciniPeso) {
    			if(counter<5) {
    				this.txtResult.appendText(cp.getC().toString()+" : "+cp.getPeso()+"\n");
    				counter++;
    			}
    			
    		}
    	}
    }

    @FXML
    void doSimula(ActionEvent event) {
    	txtResult.clear();
    	Integer k;
    	try {
    		k= Integer.parseInt(this.txtK.getText());
    	}catch(NumberFormatException e) {
    		this.txtResult.appendText("il numero di stazioni deve essere un numero intero");
    		return;
    	}
    	Cibo sorgente = this.boxFood.getValue();
    	if(sorgente==null) {
    		this.txtResult.appendText("Nessun cibo selezionato");
    		return;
    	}
    	this.model.doSimulazione(sorgente, k);
    	Integer numeroCibi = this.model.getNumeroCibiTOT();
    	Double tempoTOT = this.model.getTempoPrepTOT();
    	this.txtResult.appendText("Numero Cibi Processati: "+numeroCibi+"\n");
    	this.txtResult.appendText("Tempo di processamento TOT: "+ tempoTOT+"\n");
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtPorzioni != null : "fx:id=\"txtPorzioni\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtK != null : "fx:id=\"txtK\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnAnalisi != null : "fx:id=\"btnAnalisi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnGrassi != null : "fx:id=\"btnGrassi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Food.fxml'.";
        assert boxFood != null : "fx:id=\"boxFood\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Food.fxml'.";
    }
    
    public void setModel(Model model) {
    	this.model = model;
    	this.boxFood.setDisable(true);
    	this.txtK.setDisable(true);
    	this.btnGrassi.setDisable(true);
    	this.btnGrassi.setDisable(true);
    	this.btnSimula.setDisable(true);
    }
}
