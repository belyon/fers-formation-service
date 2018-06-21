package it.uniroma3.model;

import java.util.List;

import javax.persistence.*;

@Entity
public class Azienda {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(nullable=false)
	private String nome;
	
	@OneToMany
	@JoinColumn(name="azienda_id")
	private List<Centro> centri;
	
	@OneToMany
	@JoinColumn(name="azienda_id")
	private List<Allievo> allievi;
	
	@OneToMany
	@JoinColumn(name="azienda_id")
	private List<Responsabile> responsabili;
	
	public Azienda() {};

	public Azienda(String nome, List<Centro> centri, List<Allievo> allievi, List<Responsabile> responsabili) {
		super();
		this.nome = nome;
		this.centri = centri;
		this.allievi = allievi;
		this.responsabili = responsabili;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Centro> getCentri() {
		return centri;
	}

	public void setCentri(List<Centro> centri) {
		this.centri = centri;
	}

	public List<Allievo> getAllievi() {
		return allievi;
	}

	public void setAllievi(List<Allievo> allievi) {
		this.allievi = allievi;
	}

	public List<Responsabile> getResponsabili() {
		return responsabili;
	}

	public void setResponsabili(List<Responsabile> responsabili) {
		this.responsabili = responsabili;
	}
}
