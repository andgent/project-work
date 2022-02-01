package org.generation.italy.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;


@Entity
public class Location {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotEmpty(message = "Il campo \"nome\" � obbligatorio!")
	private String nome;
	
	@NotEmpty(message = "Il campo \"regione\" � obbligatorio!")
	private String regione;
	
	@NotEmpty(message = "Il campo \"città\" � obbligatorio!")
	private String citta;
	
	@NotEmpty(message = "Il campo \"indirizzo\" � obbligatorio!")
	private String indirizzo;
	
	@Positive(message = "Il campo \"capienza\" accetta solo numeri interi positivi!")
	private int capienza;
	
	private boolean openSpace;
	
	//Relazioni
	@OneToMany
	private List<Evento> eventi;


	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getCapienza() {
		return capienza;
	}

	public void setCapienza(int capienza) {
		this.capienza = capienza;
	}

	public Integer getId() {
		return id;
	}

	public List<Evento> getEventi() {
		return eventi;
	}

	public void setEventi(List<Evento> eventi) {
		this.eventi = eventi;
	}

	public String getRegione() {
		return regione;
	}

	public void setRegione(String regione) {
		this.regione = regione;
	}

	public String getCitta() {
		return citta;
	}

	public void setCitta(String citta) {
		this.citta = citta;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public boolean isOpenSpace() {
		return openSpace;
	}

	public void setOpenSpace(boolean openSpace) {
		this.openSpace = openSpace;
	}

} 
