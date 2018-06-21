package it.uniroma3.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import it.uniroma3.model.Attivita;
import it.uniroma3.repository.AttivitaRepository;

@Transactional
@Service
public class AttivitaService {

	@Autowired
	private AttivitaRepository attivitaRepository;
	
	public Attivita save(Attivita attivita) {
		return this.attivitaRepository.save(attivita);
	}
	
	public List<Attivita> findAll(){
		return (List<Attivita>) this.attivitaRepository.findAll();
	}
	
	public List<Attivita> findByNome(String nome){
		return this.attivitaRepository.findByNome(nome);
	}
	
	public List<Attivita> findByNomeAndOraInizio(String nome, String oraInizio){
		return this.attivitaRepository.findByNomeAndOraInizio(nome, oraInizio);
	}
	
	public Attivita findById(Long id) {
		Optional<Attivita> attivita = this.attivitaRepository.findById(id);
		if(attivita.isPresent())
			return attivita.get();
		else
			return null;
	}
	
	public boolean alreadyExists(Attivita attivita) {
		List<Attivita> attivitas = this.attivitaRepository.findByNomeAndOraInizio(attivita.getNome(),attivita.getOraInizio());
		if (attivitas.size() > 0)
			return true;
		else 
			return false;
	} 	

}
