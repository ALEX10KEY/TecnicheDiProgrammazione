package it.polito.tdp.rubrica.model;

import it.polito.tdp.rubrica.db.ContattoDAO;
import java.util.*;

/**
 *  Gestisce tutti i dati e i metodi necessari alle operazioni di ricerca, inserimento, modifica e cancellazione dei {@link Contatto} 
 *  contenuti nel database RUBRICA in maniera indipendente dal database attraverso l'utilizzo del paradigma DAO
 * 
 * 
 * @author Alex10key
 *
 */
public class RubricaModel {

	/**
	 * metodo del modello per l'inserimento di un contatto nel database
	 * 
	 * @param contatto da aggiungere nel database
	 */
	public void inserimentoContatto(Contatto contatto){
				
		ContattoDAO dao = new ContattoDAO();
			
		if (!dao.contattoGiaPresente(contatto.getNome(), contatto.getCognome(), contatto.getDataNascita()))
			dao.create(contatto);
	
	}
	
	/**
	 * metodo del modello per la visualizzazione dei contatti che corrispondono alle proprietà contenute in contatto
	 * 
	 * @param contatto desiderato da ricercare
	 * 
	 * @return una lista di contatti che soddisfano i parametri, null altrimenti
	 */
	public List<Contatto> ricercaContatto(Contatto contatto){
		ContattoDAO dao = new ContattoDAO();
		return dao.trovaContatto(contatto.getNome(), contatto.getCognome(), contatto.getDataNascita(), contatto.getTelefono());
	}
	
	/**
	 * metodo del modello per ricercare un contatto contenuto nel database attraverso il campo id
	 * 
	 * @param id dell'oggetto Contatto da cercare
	 * @return un oggetto Contatto equivalente al contatto desiderato
	 */
	public Contatto visualizzaContatto(int id){
		
		ContattoDAO dao =new ContattoDAO();
		return dao.trovaContattoConId(id);
	}
	
	/**
	 * metodo del modello per la modifica di un contatto esistente nel database RUBRICA 
	 * 
	 * @param id del contatto da aggiornare
	 * @param nuovoContatto oggetto Contatto che sostituirà il vecchio contatto
	 */
	public void modificaContatto(int id,Contatto nuovoContatto){
	
		nuovoContatto.setId(id);
		
		ContattoDAO dao = new ContattoDAO();
		Contatto vecchioContatto = dao.trovaContattoConId(id);
		
		if (vecchioContatto!=null && id==vecchioContatto.getId()){
			
			dao.updateContatto(nuovoContatto);
			
		}
	}
	
	/**
	 * metodo del modello per eliminare un contatto dal database RUBRICA
	 * 
	 * @param id del contatto da eliminare
	 */
	public void eliminaContatto(int id){
		
		ContattoDAO dao =new ContattoDAO();
		Contatto c = dao.trovaContattoConId(id);
		
		if (c!=null)
			dao.delete(c);

	}
	
}
