package it.polito.tdp.rubrica.model;

import java.sql.Date;
import java.time.LocalDate;

/**
 * JavaBean per memorizzare le informazioni di un singolo contatto
 * 
 * @author Alex10key
 *
 */
public class Contatto {
	
	private int id;
	
	private String nome;
	private String cognome;
	private LocalDate dataNascita;
	private long telefono;
	
	public Contatto(String nome, String cognome, LocalDate dataNascita, long telefono) {
		this.nome = nome;
		this.cognome = cognome;
		this.dataNascita = dataNascita;
		this.telefono = telefono;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public LocalDate getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(LocalDate dataNascita) {
		this.dataNascita = dataNascita;
	}

	public long getTelefono() {
		return telefono;
	}

	public void setTelefono(long telefono) {
		this.telefono = telefono;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.id+" - "+this.nome+" "+this.cognome+" - "+this.dataNascita+" - "+this.telefono;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		Contatto c = (Contatto)obj;
		if (this.nome.compareTo(c.nome)==0 && this.cognome.compareTo(c.cognome)==0 && this.dataNascita==c.dataNascita )
			return true;
		else
			return false;
	}
	
	
	
	
	
	
	
	
	
}
