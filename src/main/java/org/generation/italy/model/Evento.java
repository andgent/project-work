package org.generation.italy.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;




@Entity
public class Evento {

	// Attributes

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	//@NotEmpty(message = "Il campo \"nome\" � obbligatorio!")
	private String nome;

	private String descrizione;

	/*
	@Pattern(regexp = "[^0-9]*", message = "Il campo \"location\" può contenere solo lettere.")
	@NotEmpty(message = "Il campo \"location\" non può essere vuoto!")
	private String location;
	*/

	// @FutureOrPresent(message = "Il campo \"data\" non può presentare valori
	// passati!")
	private LocalDateTime dataInizio;

	// @Future(message = "Il campo \"data\" non può presentare valori passati!")
	private LocalDateTime dataFine;

	/*
	@Positive(message = "Il campo \"capienza\" accetta solo numeri positivi!")
	@Max(value = 999, message = "Il campo \"capienza\" accetta come valore massimo 999!")
	private int capienza;
	*/



	//@PositiveOrZero(message = "Il campo \"biglietto\" accetta solo numeri interi positivi!")
	//@Max(value = 10000, message = "Il campo \"biglietto\" accetta solo valori inferiori a 10'000!")
	private float biglietto;

	@Lob
	private byte[] locandina;
	

	// Relation
	@ManyToMany
	private List<Categoria> categorie;

	@ManyToOne
	private Location location;
	// Getters and Setters

	public String getNome() {
		return nome;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public LocalDateTime getDataInizio() {
		return dataInizio;
	}

	public void setDataInizio(LocalDateTime dataInizio) {
		this.dataInizio = dataInizio;
	}

	public LocalDateTime getDataFine() {
		return dataFine;
	}

	public void setDataFine(LocalDateTime dataFine) {
		this.dataFine = dataFine;
	}



	public Integer getId() {
		return id;
	}

	public float getBiglietto() {
		return biglietto;
	}

	public void setBiglietto(float biglietto) {
		this.biglietto = biglietto;
	}

	public List<Categoria> getCategorie() {
		return categorie;
	}

	public void setCategorie(List<Categoria> categorie) {
		this.categorie = categorie;
	}


	public byte[] getLocandina() {
		return locandina;
	}

	public void setLocandina(byte[] locandina) {
		this.locandina = locandina;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}


	public String categorieString() {
        List<String> categorieNew = new ArrayList<String>();
        for(Categoria i : categorie) {
            categorieNew.add(i.getNome());
        }
        return String.join(", ", categorieNew);
    }
	
	
	public String inizioToString() { 
		  String dateString; 
		  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
		   dateString = dataInizio.format(formatter);
		  return dateString; 
	  }
	  
	  public String fineToString() { 
		  String dateString; 
		  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
		   dateString = dataFine.format(formatter);
		  return dateString; 
	  }

	
	
}
