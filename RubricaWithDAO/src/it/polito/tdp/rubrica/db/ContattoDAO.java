package it.polito.tdp.rubrica.db;

import it.polito.tdp.rubrica.model.Contatto;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.*;

/**
 * Data Access Object per manipolare la tabella CONTATTI
 * del database RUBRICA
 * 
 * @author alex10key
 *
 */
public class ContattoDAO {

	/**
	 * Crea un nuovo contatto
	 * 
	 * @param contatto oggetto Contatto da inserire
	 */
	public void create(Contatto contatto){
		
		Connection conn = DBConnect.getConnection();
		
		String query = String.format("insert into contatti(nome,cognome,telefono,data_nascita) values('%s','%s',%d,'%s')", contatto.getNome(),contatto.getCognome(), contatto.getTelefono(),contatto.getDataNascita() );
		
		try{
			Statement st2 = conn.createStatement();
			
			boolean res2 = st2.execute(query);
			
			conn.close();

		} catch (SQLException e){
			System.out.println("Eccezione SQL");
		}
		
	}
	
	/**
	 * Verifica se il contatto con i parametri passati (nome, cognome, data di nascita)
	 * 
	 * @param nome del contatto da cercare
	 * @param cognome del contatto da cercare
	 * @param dataNascita del contatto da cercare
	 * 
	 * @return true se è presente nel database un contatto con i seguenti parametri, false altrimenti
	 */
	public boolean contattoGiaPresente(String nome,String cognome, LocalDate dataNascita){
		
		Connection conn = DBConnect.getConnection();
		
		String query = String.format("select * from contatti where nome='%s' and cognome='%s' and data_nascita='%s' ",nome, cognome, dataNascita) ;
		
		try{
			
			Statement st2 = conn.createStatement();
			ResultSet res2 = st2.executeQuery(query);
			
			boolean trovato ;
			
			if ( res2.first() )
				trovato = true;
			else
				trovato = false;
			
			res2.close();
			conn.close();
			
			return trovato;
			
		} catch (SQLException e){
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Restituisce una collection di tipo List<Contatto> contenente i contatti della rubrica che hanno un nome o un cognome simile 
	 * al nome o cognome passato e/o che hanno telefono o data di nascita uguale ai valori passati come parametro
	 * 
	 * @param nome del contatto da ricercare
	 * @param cognome del contatto da ricercare
	 * @param dataNascita del contatto da ricercare
	 * @param telefono del contatto da ricercare
	 * 
	 * @return una lista contenente i contatti che soddisfano la ricerca
	 */
	public List<Contatto> trovaContatto(String nome,String cognome,LocalDate dataNascita,long telefono){
		
		Connection conn = DBConnect.getConnection();
		
		String query = "select * from contatti ";
			  
			  query += "where nome like '" + nome + "%'";
			  query += " or cognome like '"+ cognome +"%'";
			  query += " or data_nascita = '"+ dataNascita +"'";
			  query += " or telefono = '"+ telefono +"'";
		
		List<Contatto> cerca = new ArrayList<Contatto>();
		
		try{
			
			Statement st = conn.createStatement();
			ResultSet res = st.executeQuery(query);
			
			while (res.next()){
				
				int id = res.getInt("id");
				
				Contatto c = new Contatto(res.getString("nome"), res.getString("cognome"), res.getDate("data_nascita").toLocalDate(), Long.parseLong(res.getString("telefono")));
				c.setId(id);
				cerca.add( c );
				
			}
			
			res.close();
			conn.close();
			
			System.out.println(cerca);
			
			return cerca;
		} catch (SQLException e){
			e.printStackTrace();
			return null;
		}
		
	}
	
	/**
	 * metodo DAO per la ricerca di un contatto attraverso il campo id della tabella contatti
	 * 
	 * @param id campo chiave della tabella CONTATTI per ricercare un oggetto Contatto
	 * @return oggetto Contatto estratto dal database
	 */
	public Contatto trovaContattoConId(int id){
		
		Connection conn = DBConnect.getConnection();
		
		String query2 = String.format("select id,nome,cognome,data_nascita,telefono from contatti where id=%d", id);
		
		try{
		Statement st2 = conn.createStatement();
		ResultSet res2 = st2.executeQuery(query2);
		
		Contatto c ;
		if ( res2.first() ){
			c = new Contatto(res2.getString("nome"),res2.getString("cognome"),res2.getDate("data_nascita").toLocalDate(),res2.getLong("telefono"));
			c.setId(id);
		} else
			c = null;
		
		res2.close();
		conn.close();
		
		return c;
		} catch (SQLException e){
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * metodo DAO per aggiornare un contatto
	 * 
	 * @param nuovoContatto oggetto Contatto che deve sostituire il contatto salvato in database 
	 */
	public void updateContatto(Contatto nuovoContatto){
		Connection conn = DBConnect.getConnection();
		
		String query = String.format("update contatti SET nome='%s', cognome='%s', telefono=%d, data_nascita='%s' where ID=%d ", nuovoContatto.getNome(),nuovoContatto.getCognome(), nuovoContatto.getTelefono(),nuovoContatto.getDataNascita(), nuovoContatto.getId() );
		
		try{
			Statement st2 = conn.createStatement();
			
			boolean res2 = st2.execute(query);
			
			conn.close();

		} catch (SQLException e){
			e.printStackTrace();
			return ;
		}
	}
	
	/**
	 * metodo DAO per la rimozione di un contatto dalla tabella CONTATTI
	 * 
	 * @param contatto oggetto Contatto da rimuovere
	 */
	public void delete(Contatto contatto){
		
		Connection conn = DBConnect.getConnection();
		
		String query = String.format("DELETE FROM contatti where ID=%d", contatto.getId() );
		
		try{
			
			Statement st = conn.createStatement();
			
			boolean res = st.execute(query);
			
			conn.close();

		} catch (SQLException e){
			e.printStackTrace();
			return ;
		}
		
	}

}
