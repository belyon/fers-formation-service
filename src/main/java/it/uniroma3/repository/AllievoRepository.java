package it.uniroma3.repository;

import it.uniroma3.model.Allievo;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface AllievoRepository extends CrudRepository<Allievo, Long>{
	
	public Allievo findByEmail(String email);
	public List<Allievo> findByNomeAndCognomeAndEmail(String nome, String Cognome, String email);

}
