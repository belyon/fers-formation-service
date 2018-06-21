package it.uniroma3.model;

import java.util.List;

import javax.persistence.*;

@Entity
public class Centro {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable=false)
	private String nome;
	
	@Column(nullable=false)
	private String indirizzo;
	
	@Column(nullable=false)
	private String email;
	
	@Column(nullable=false)
	private String telefono;
	
	@Column(nullable=false)
	private String maxA;
	
	@OneToMany
	@JoinColumn(name="centro_id")
	private List<Attivita> attivita;
	
	@OneToMany(mappedBy = "centro")
	private List <Allievo> allievi;
	
	public Centro() {
		super();
	}

	public Centro(String nome, String indirizzo, String email, String telefono, String maxA, List<Attivita> attivita) {
		super();
		this.nome = nome;
		this.indirizzo = indirizzo;
		this.email = email;
		this.telefono = telefono;
		this.maxA = maxA;
		this.attivita = attivita;
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

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getMaxA() {
		return maxA;
	}

	public void setMaxA(String maxA) {
		this.maxA = maxA;
	}
	
	public List<Attivita> getAttivita() {
		return attivita;
	}

	public void setAttivita(List<Attivita> attivita) {
		this.attivita = attivita;
	}

	public List<Allievo> getAllievi() {
		return allievi;
	}

	public void setAllievi(List<Allievo> allievi) {
		this.allievi = allievi;
	}
}
