package org.generation.italy.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import org.springframework.web.multipart.MultipartFile;

public class EventoForm {
	

	@NotEmpty(message = "Il campo \"nome\" è obbligatorio!")
	private String nome;

	private String descrizione;
	
	/*
	@Pattern(regexp = "[^0-9]*", message = "Il campo \"location\" può contenere solo lettere.")
	@NotEmpty(message = "Il campo \"location\" non può essere vuoto!")
	private String location;
	*/

	// @FutureOrPresent(message = "Il campo \"data\" non può presentare valori passati!")
	private LocalDateTime dataInizio;

	// @Future(message = "Il campo \"data\" non può presentare valori passati!")
	private LocalDateTime dataFine;

	@Positive(message = "Il campo \"capienza\" accetta solo numeri positivi!")
	@Max(value = 999, message = "Il campo \"capienza\" accetta come valore massimo 999!")
	private int capienza;

	@PositiveOrZero(message = "Il campo \"biglietto\" accetta solo numeri interi positivi!")
	@Max(value = 10000, message = "Il campo \"biglietto\" accetta solo valori inferiori a 10'000!")
	private float biglietto;

	private MultipartFile locandina;

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

	public MultipartFile getLocandina() {
		return locandina;
	}

	public void setLocandina(MultipartFile locandina) {
		this.locandina = locandina;
	}
	
}
