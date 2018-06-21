package it.uniroma3.repository;

import it.uniroma3.model.Responsabile;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface ResponsabileRepository extends CrudRepository<Responsabile, Long>{
	
	public List<Responsabile> findByMatricola(Integer matricola);
	public List<Responsabile> findByNomeAndMatricola(String nome, String email);
	public List<Responsabile> findByNomeAndCognomeAndMatricola(String nome, String cognome, Integer matricola);
	public Responsabile findByUsername(String username);
	public Responsabile findByPassword(String password);
}
