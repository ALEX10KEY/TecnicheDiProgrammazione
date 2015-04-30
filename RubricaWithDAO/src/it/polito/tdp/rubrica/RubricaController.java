package it.polito.tdp.rubrica;

import it.polito.tdp.rubrica.model.Contatto;
import it.polito.tdp.rubrica.model.RubricaModel;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class RubricaController {

	private RubricaModel model ;
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private DatePicker dtpckNascita;

    @FXML
    private Button btnModifica;

    @FXML
    private Button btnRicerca;

    @FXML
    private Button btnCancella;

    @FXML
    private Button btnApplicaModifica;

    @FXML
    private TextArea txtArea;

    @FXML
    private TextField txtNome;
    

    @FXML
    private TextField txtTelefono;

    @FXML
    private Button btnApplicaCancella;

    @FXML
    private TextField txtCognome;

    @FXML
    private TextField TxtIdCancella;

    @FXML
    private TextField txtIdModifica;

    @FXML
    private Button btnInserimento;

    @FXML
    void doInserimento(ActionEvent event) {
    	
    	boolean datiValidi = true;
    	
    	String nome = this.txtNome.getText().trim().toUpperCase();
    	String cognome = this.txtCognome.getText().trim().toUpperCase();
    	long telefono;
    	LocalDate dataNascita = this.dtpckNascita.getValue();
    	
    	if ( nome==null || nome.isEmpty() ){
    		this.txtNome.setText("Nome non valido!");
    		datiValidi = false;
    	}
    	
    	if ( cognome==null || cognome.isEmpty() ){
    		this.txtCognome.setText("Cognome non valido!");
    		datiValidi = false;
    	}
    	
    	if (dataNascita == null)
    		datiValidi = false;
    	
    	
    	try{
    		telefono = Long.parseLong(this.txtTelefono.getText());
    	}catch(NumberFormatException nfe){
    		this.txtTelefono.setText("Formato numero non valido");
    		datiValidi = false;
    		return ;
    	}
    	
    	if (!datiValidi){
    		System.out.println("Dati non validi");
    		return ;
    	} else{
    
    		this.model.inserimentoContatto(new Contatto(nome, cognome, dataNascita, telefono));
    		this.pulisciCampi();
    	}
  
    }

    private void pulisciCampi() {
    	
    	this.txtNome.setText("");
		this.txtCognome.setText("");
		this.txtTelefono.setText("");
		this.dtpckNascita.setValue(null);
		
		this.TxtIdCancella.setText("");
		this.txtIdModifica.setText("");
		
		this.txtArea.clear();
	}

	@FXML
    void doRicerca(ActionEvent event) {
    		
    	String nome;
        String cognome;
        LocalDate data;
        Long telefono=(long) -1;
        
        if (!this.txtTelefono.getText().trim().isEmpty()){
        	
        	//System.out.println("Controllo che il numero di telefono inserito abbia un formato valido");
        	
	    	try{
	        	telefono = Long.parseLong(this.txtTelefono.getText());
	        }catch(NumberFormatException nfe){
	        	this.txtTelefono.setText("Formato numero non valido");
	        	return ;
	        }
        }
    	
        this.txtArea.clear();
        
    	nome = this.txtNome.getText().trim().toUpperCase();
    	cognome = this.txtCognome.getText().trim().toUpperCase();
    	data = this.dtpckNascita.getValue();
    	
    	if ( nome.isEmpty() && cognome.isEmpty() && data==null && telefono==-1 ){
    		this.txtArea.appendText("Inserisci almeno qualche campo per effettuare la ricerca\n");
    		return ;
    	}
    	
    	List<Contatto> lista = this.model.ricercaContatto(new Contatto(nome,cognome,data,telefono));
    	
    	int count = 0;
	    for(Contatto c : lista){
	    	this.txtArea.appendText(c.toString()+"\n");
	    	count++;
	    }
	    if (count==0)
	    	this.txtArea.appendText("Nessun risultato compatibile\n");
    
    }

    @FXML
    void doModifica(ActionEvent event) {
    	
    	this.svuotaTextField();
    	
    	int id;
    	try{
    		id = Integer.parseInt( this.txtIdModifica.getText() );
    	} catch (NumberFormatException nfe){
    		this.txtIdModifica.setText("Id non valido !");
    		return ;
    	}
    	
    	Contatto ctemp = this.model.visualizzaContatto(id);
    	
    	if (ctemp!=null){
	    	this.txtNome.setText(ctemp.getNome());
	    	this.txtCognome.setText(ctemp.getCognome());
	    	this.dtpckNascita.setValue(ctemp.getDataNascita());
	    	this.txtTelefono.setText(Long.toString(ctemp.getTelefono()));
	    	this.btnApplicaModifica.setDisable(false);
    	} else
    		this.txtIdModifica.setText("Id non valido !");
    	
    }

    @FXML
    void doCancella(ActionEvent event) {
    	
    	this.svuotaTextField();
    	
    	int id;
    	try{
    		id = Integer.parseInt( this.TxtIdCancella.getText() );
    	} catch (NumberFormatException nfe){
    		this.TxtIdCancella.setText("Id non valido !");
    		return ;
    	}
    	
    	Contatto ctemp = this.model.visualizzaContatto(id);
    	if (ctemp!=null){
	    	this.txtNome.setText(ctemp.getNome());
	    	this.txtCognome.setText(ctemp.getCognome());
	    	this.dtpckNascita.setValue(ctemp.getDataNascita());
	    	this.txtTelefono.setText(Long.toString(ctemp.getTelefono()));
	    	this.btnApplicaCancella.setDisable(false);
    	} else
    		this.txtArea.appendText("Contatto non trovato !");;
    		
    }

    @FXML
    void doApplicaModifica(ActionEvent event) {
    	
    	this.txtArea.clear();
    	
    	int id = Integer.parseInt(this.txtIdModifica.getText());
    	
    	boolean datiValidi = true;
    	
    	String nome = this.txtNome.getText().trim().toUpperCase();
    	String cognome = this.txtCognome.getText().trim().toUpperCase();
    	long telefono;
    	LocalDate dataNascita = this.dtpckNascita.getValue();
    	
    	if ( nome==null || nome.isEmpty() ){
    		this.txtNome.setText("Nome non valido!");
    		datiValidi = false;
    	}
    	
    	if ( cognome==null || cognome.isEmpty() ){
    		this.txtCognome.setText("Cognome non valido!");
    		datiValidi = false;
    	}
    	
    	if (dataNascita == null)
    		datiValidi = false;
    	
    	try{
    		telefono = Long.parseLong(this.txtTelefono.getText());
    	}catch(NumberFormatException nfe){
    		this.txtTelefono.setText("Formato numero non valido");
    		datiValidi = false;
    		return ;
    	}
    	
    	if (!datiValidi){
    		System.out.println("Dati non validi");
    		return ;
    	} else{
    		
    		this.model.modificaContatto(id, new Contatto(nome, cognome, dataNascita, telefono));
    		
    		this.svuotaTextField();
    		this.btnApplicaModifica.setDisable(true);
    		this.txtIdModifica.setText("");
    	
    	}
    }

    private void aggiornaElencoContattiRubrica() {
		/*
    	this.txtArea.clear();
    	
    	for(Contatto ctemp : this.model.elencoContattiDellaRubrica() ){
    		this.txtArea.appendText(ctemp.getId()+" - "+ctemp.toString()+"\n");
    	}*/
	}

	private void svuotaTextField() {
		// TODO Auto-generated method stub
    	this.txtNome.setText("");
		this.txtCognome.setText("");
		this.txtTelefono.setText("");
		this.dtpckNascita.setValue(null);
	}

	@FXML
    void doApplicaCancella(ActionEvent event) {
    	
		this.txtArea.clear();
		
    	int id;
    	
		try{
			id = Integer.parseInt(this.TxtIdCancella.getText());
		} catch(NumberFormatException e){
			e.printStackTrace();
			this.TxtIdCancella.setText("Id non valido");
			return ;
		}
		
		this.model.eliminaContatto(id);
		
    	this.btnApplicaCancella.setDisable(true);
    	this.svuotaTextField();
		this.TxtIdCancella.setText("");
    	
    }
    
    public void setModel(RubricaModel model) {
    	this.model = model ;
    }
    
    @FXML
    void initialize() {
        assert dtpckNascita != null : "fx:id=\"dtpckNascita\" was not injected: check your FXML file 'Rubrica.fxml'.";
        assert btnModifica != null : "fx:id=\"btnModifica\" was not injected: check your FXML file 'Rubrica.fxml'.";
        assert btnRicerca != null : "fx:id=\"btnRicerca\" was not injected: check your FXML file 'Rubrica.fxml'.";
        assert btnCancella != null : "fx:id=\"btnCancella\" was not injected: check your FXML file 'Rubrica.fxml'.";
        assert btnApplicaModifica != null : "fx:id=\"btnApplicaModifica\" was not injected: check your FXML file 'Rubrica.fxml'.";
        assert txtArea != null : "fx:id=\"txtArea\" was not injected: check your FXML file 'Rubrica.fxml'.";
        assert txtNome != null : "fx:id=\"txtNome\" was not injected: check your FXML file 'Rubrica.fxml'.";
        assert btnApplicaCancella != null : "fx:id=\"btnApplicaCancella\" was not injected: check your FXML file 'Rubrica.fxml'.";
        assert txtCognome != null : "fx:id=\"txtCognome\" was not injected: check your FXML file 'Rubrica.fxml'.";
        assert TxtIdCancella != null : "fx:id=\"TxtIdCancella\" was not injected: check your FXML file 'Rubrica.fxml'.";
        assert txtIdModifica != null : "fx:id=\"txtIdModifica\" was not injected: check your FXML file 'Rubrica.fxml'.";
        assert btnInserimento != null : "fx:id=\"btnInserimento\" was not injected: check your FXML file 'Rubrica.fxml'.";
        
        this.btnRicerca.setDisable(false);
        this.txtIdModifica.setDisable(false);
        this.btnModifica.setDisable(false);
        this.btnApplicaModifica.setDisable(true);
        this.TxtIdCancella.setDisable(false);
        this.btnCancella.setDisable(false);
        this.btnApplicaCancella.setDisable(true);
        
    }
}
