package it.uniroma3.repository;

import it.uniroma3.model.Azienda;
import org.springframework.data.repository.CrudRepository;

public interface AziendaRepository extends CrudRepository<Azienda, Long>{
	
	public Azienda findByNome(String nome);
}
