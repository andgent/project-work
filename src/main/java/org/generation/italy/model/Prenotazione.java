package org.generation.italy.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Entity
public class Prenotazione {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@NotEmpty(message = "Il campo \"nome\" � obbligatorio!")
	private String nome;
	
	@NotEmpty(message = "Il campo \"cognome\" � obbligatorio!")
	private String cognome;
	
	private LocalDateTime dataPrenotazione;
	
	@NotEmpty(message = "Il campo \"email\" � obbligatorio!")
	private String email;
	
	@NotNull
	@Positive
	@Max(value=10, message="Si possono prenotare al massimo 10 biglietti alla volta")
	private Integer numeroPrenotati;
	
	@ManyToOne
	private Evento evento;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDateTime getDataPrenotazione() {
		return dataPrenotazione;
	}

	public void setDataPrenotazione(LocalDateTime dataPrenotazione) {
		this.dataPrenotazione = dataPrenotazione;
	}

	public Integer getNumeroPrenotati() {
		return numeroPrenotati;
	}

	public void setNumeroPrenotati(Integer numeroPrenotati) {
		this.numeroPrenotati = numeroPrenotati;
	}

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	
}
