package it.uniroma3.repository;

import it.uniroma3.model.Attivita;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface AttivitaRepository extends CrudRepository<Attivita, Long>{

	public List<Attivita> findByNome(String nome);
	public List<Attivita> findByNomeAndOraInizio(String nome, String oraInizio);

}