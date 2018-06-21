package it.uniroma3.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.model.Allievo;
import it.uniroma3.model.Centro;
import it.uniroma3.repository.AllievoRepository;

@Transactional
@Service
public class AllievoService {

	@Autowired
	private AllievoRepository allievoRepository;

	@Autowired 
	private CentroService centroService;

	public Allievo save(Allievo allievo) {
		return this.allievoRepository.save(allievo);
	}

	public List<Allievo> findAll() {
		return (List<Allievo>) this.allievoRepository.findAll();
	}

	public Allievo findByEmail(String email) {
		return this.allievoRepository.findByEmail(email);
	}

	public Allievo findById(Long id) {
		Optional<Allievo> allievo = this.allievoRepository.findById(id);
		if (allievo.isPresent()) 
			return allievo.get();
		else
			return null;
	}

	public boolean alreadyExists(Allievo allievo) {
		List<Allievo> allievos = this.allievoRepository.findByNomeAndCognomeAndEmail(allievo.getNome(), allievo.getCognome(), allievo.getEmail());
		if (allievos.size() > 0)
			return true;
		else 
			return false;
	}


	public boolean alreadyExists(Allievo allievo, Long id) {
		Centro centroTrovato = this.centroService.findById(id);

		List <Allievo> listaAllievi = centroTrovato.getAllievi();
		boolean esiste = false;

		for(Allievo a: listaAllievi) {
			if(	   a.getNome().equals(allievo.getNome()) 
					&& a.getCognome().equals(allievo.getCognome()) 
					&& a.getEmail().equals(allievo.getEmail())) {
				esiste = true;;
			}
		} 

		return esiste;
	}

}
