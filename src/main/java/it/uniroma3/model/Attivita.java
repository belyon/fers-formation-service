package it.uniroma3.model;

import java.util.List;

import javax.persistence.*;

@Entity
public class Attivita {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(nullable=false)
	private String nome;
	
	@Column(nullable=false)
	private String data;
	
	@Column(nullable=false)
	private String oraInizio;
	
	@Column(nullable=false)
	private String oraFine;
	
	@ManyToMany(mappedBy="attivita", cascade = CascadeType.ALL)
	private List<Allievo> allievi;
	
	public Attivita() {
		
	}

	public Attivita(String nome, String data, String oraInizio, String oraFine, List<Allievo> allievi) {
		this.nome = nome;
		this.data = data;
		this.oraInizio = oraInizio;
		this.oraFine = oraFine;
		this.allievi = allievi;
	}
	
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getData() {
		return this.data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getOraInizio() {
		return this.oraInizio;
	}

	public void setOraInizio(String oraInizio) {
		this.oraInizio = oraInizio;
	}

	public String getOraFine() {
		return this.oraFine;
	}

	public void setOraFine(String oraFine) {
		this.oraFine = oraFine;
	}

	public List<Allievo> getAllievi() {
		return this.allievi;
	}

	public void setAllievi(List<Allievo> allievi) {
		this.allievi = allievi;
	}
	
}
