package org.generation.italy.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

@Entity
public class Evento {

	// Attributes

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotEmpty(message = "Il campo \"nome\" è obbligatorio!")
	private String nome;

	private String descrizione;

	@NotEmpty(message = "Il campo \"location\" non può essere vuoto!")
	private String location;

	// @FutureOrPresent(message = "Il campo \"data\" non può presentare valori
	// passati!")
	private LocalDateTime dataInizio;

	// @Future(message = "Il campo \"data\" non può presentare valori passati!")
	private LocalDateTime dataFine;

	@Positive(message = "Il campo \"capienza\" accetta solo numeri interi positivi!")
	@Size(min = 1, max = 999, message = "Il campo \"capienza\" accetta solo valori compresi tra {min} e {max}!")
	private int capienza;

	@PositiveOrZero(message = "Il campo \"prenotati\" accetta solo numeri interi superiori allo \"0\"!")
	@Size(min = 0, max = 999, message = "Il campo \"prenotati\" accetta solo valori compresi tra {min} e {max}!")
	private int prenotati;

	@Positive(message = "Il campo \"biglietto\" accetta solo numeri interi positivi!")
	@Size(min = 0, max = 10000, message = "Il campo \"biglietto\" accetta solo valori compresi tra {min} e {max}!")
	private float biglietto;
	
	// Relation
	@ManyToMany
	private List<Categoria> categorie;

	// Getters and Setters

	public String getNome() {
		return nome;
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

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
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

	public int getCapienza() {
		return capienza;
	}

	public void setCapienza(int capienza) {
		this.capienza = capienza;
	}

	public int getPrenotati() {
		return prenotati;
	}

	public void setPrenotati(int prenotati) {
		this.prenotati = prenotati;
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

}
