package org.generation.italy.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;

import org.springframework.web.multipart.MultipartFile;

public class EventoForm {
	

	private Integer id;
	
	
	@NotEmpty(message = "Il campo \"nome\" � obbligatorio!")
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

	@PositiveOrZero(message = "Il campo \"biglietto\" accetta solo numeri interi positivi!")
	@Max(value = 10000, message = "Il campo \"biglietto\" accetta solo valori inferiori a 10'000!")
	private float biglietto;

	private MultipartFile locandina;

	private List<Categoria> categorie;
	
	private Location location;
	

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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
	
}
