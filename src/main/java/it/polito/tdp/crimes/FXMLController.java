/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.crimes;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.crimes.model.Arco;
import it.polito.tdp.crimes.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxAnno"
    private ComboBox<Integer> boxAnno; // Value injected by FXMLLoader

    @FXML // fx:id="boxMese"
    private ComboBox<Integer> boxMese; // Value injected by FXMLLoader

    @FXML // fx:id="boxGiorno"
    private ComboBox<Integer> boxGiorno; // Value injected by FXMLLoader

    @FXML // fx:id="btnCreaReteCittadina"
    private Button btnCreaReteCittadina; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimula"
    private Button btnSimula; // Value injected by FXMLLoader

    @FXML // fx:id="txtN"
    private TextField txtN; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaReteCittadina(ActionEvent event) {
    	this.txtResult.clear();
    	
    	Integer anno = this.boxAnno.getValue();
    	model.creaGrafo(anno);
    	txtResult.appendText(String.format("Grafo creato!\n#vertici:  %d\n#archi:  %d\n", model.nVertici(),model.nArchi()));
    	
    	for(int i=1; i<8; i++) {
    		txtResult.appendText("\nDistretti adiacenti a distretto n°  "+i+"\n");
    		
    		for(Arco a: model.distrettiAdiacenti(i)) {
    			this.txtResult.appendText(a.toString()+"\n");		
    		}
    	}
    	
    	for(int i=1;i<13;i++) {
    		this.boxMese.getItems().add(i);
    	}
    	this.boxMese.setValue(1);
    	
    	for(int i=1;i<32;i++) {
    		this.boxGiorno.getItems().add(i);
    	}
    	this.boxGiorno.setValue(1);
    	
    }

    @FXML
    void doSimula(ActionEvent event) {
    	//controllo mese-giorno
    	this.txtResult.clear();
    	
    	try {
    	Integer agentiD = Integer.valueOf(this.txtN.getText());
    	if(agentiD>10 || agentiD<1) {
    		this.txtResult.appendText("Inserire valore di N compreso tra 1 e 10\n");
    		return;
    	}
    	
  
    	Integer n = model.simula(this.boxGiorno.getValue(), this.boxMese.getValue(), this.boxAnno.getValue(), agentiD);
    	
    	if(n==null) {
    		this.txtResult.appendText("Giorno/Mese non disponibili\n");
    		
    	}else {
    		this.txtResult.appendText(String.format("Il giorno %d/%d/%d, si sono verificati %d eventi mal gestiti, in presenza di %d agenti",
    				this.boxGiorno.getValue(), this.boxMese.getValue(), this.boxAnno.getValue(), n, agentiD));
    		this.txtResult.appendText("\nEventi a cui non è arrivato nessuno: "+model.getCont());
    	}
    	
    	
    	}catch(NumberFormatException nfe) {
    		this.txtResult.appendText("Inserire valore di N corretto");
    	}
    	
    
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxAnno != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxMese != null : "fx:id=\"boxMese\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxGiorno != null : "fx:id=\"boxGiorno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCreaReteCittadina != null : "fx:id=\"btnCreaReteCittadina\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtN != null : "fx:id=\"txtN\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	this.boxAnno.getItems().addAll(model.anni());
    	this.boxAnno.setValue(model.anni().get(0));
    }
}
