package it.uniroma3.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.model.Azienda;
import it.uniroma3.repository.AziendaRepository;


@Transactional
@Service
public class AziendaService {
		
	@Autowired
	private AziendaRepository aziendaRepository;
	
	
	public Azienda save(Azienda azienda) {
		return this.aziendaRepository.save(azienda);
	}
	
	public Azienda findByNome(String nome) {
		return this.aziendaRepository.findByNome(nome);
	}
}
