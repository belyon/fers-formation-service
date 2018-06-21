package it.uniroma3.repository;

import it.uniroma3.model.Centro;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface CentroRepository extends CrudRepository<Centro, Long>{

	public Centro findByEmail(String email);
	public List<Centro> findByEmailAndIndirizzo(String email, String indirizzo);
	
}