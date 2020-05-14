/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.crimes;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.crimes.model.Adiacenza;
import it.polito.tdp.crimes.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxCategoria"
    private ComboBox<String> boxCategoria; // Value injected by FXMLLoader

    @FXML // fx:id="boxMese"
    private ComboBox<Integer> boxMese; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnalisi"
    private Button btnAnalisi; // Value injected by FXMLLoader

    @FXML // fx:id="boxArco"
    private ComboBox<Adiacenza> boxArco; // Value injected by FXMLLoader

    @FXML // fx:id="btnPercorso"
    private Button btnPercorso; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCalcolaPercorso(ActionEvent event) {
    	this.txtResult.clear();
    	Adiacenza a = this.boxArco.getValue();
    	if(a==null) {
    		this.txtResult.appendText("Errore nella scelta dell'arco!");
    		return;
    	}
    	String source = a.getO1();
    	String target = a.getO2();
    	List<String> path = this.model.findPath(source, target);
    	for(String s : path) {
    		this.txtResult.appendText(s+"\n");
    	}
        

    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	this.txtResult.clear();
    	String category = this.boxCategoria.getValue();
    	if(category == null) {
    		this.txtResult.appendText("Scegli una categoria");
    		return;
    	}
    	
    	Integer month = this.boxMese.getValue();
    	if(month == null) {
    		this.txtResult.appendText("Scegli una mese");
    		return;
    	}
    	this.model.createGraph(category, month);
    	
        this.txtResult.appendText(String.format("Numero archi %d e numero vertici %d", this.model.numEdges(),this.model.numVertexes()));
        this.txtResult.appendText("Il peso medio del grafo e' "+this.model.calculateAvg()+"\n");
        List<Adiacenza> lista = this.model.getAdiacenze();
        if(lista == null) {
        	this.txtResult.appendText("Non vi sono archi collegati");
    		return;
        }
        for(Adiacenza a : lista) {
        	this.txtResult.appendText(a.toString()+"\n");
        }
        this.boxArco.getItems().addAll(lista);

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxCategoria != null : "fx:id=\"boxCategoria\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxMese != null : "fx:id=\"boxMese\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnAnalisi != null : "fx:id=\"btnAnalisi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxArco != null : "fx:id=\"boxArco\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnPercorso != null : "fx:id=\"btnPercorso\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	this.boxCategoria.getItems().addAll(this.model.getOffensesId());
    	this.boxMese.getItems().addAll(this.model.getMonths());
    	
    }
}
