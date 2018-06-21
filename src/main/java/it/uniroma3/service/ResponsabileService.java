package it.uniroma3.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.model.Responsabile;
import it.uniroma3.repository.ResponsabileRepository;


@Transactional
@Service
public class ResponsabileService {

	@Autowired
	private ResponsabileRepository ResponsabileRepository;


	public Responsabile save(Responsabile Responsabile) {
		return this.ResponsabileRepository.save(Responsabile);
	}

	public List<Responsabile> findByEmail(Integer matricola) {
		return this.ResponsabileRepository.findByMatricola(matricola);
	}

	public List<Responsabile> findAll() {
		return (List<Responsabile>) this.ResponsabileRepository.findAll();
	}

	public Responsabile findById(Long id) {
		Optional<Responsabile> Responsabile = this.ResponsabileRepository.findById(id);
		if (Responsabile.isPresent()) 
			return Responsabile.get();
		else
			return null;
	}

	public boolean alreadyExists(Responsabile Responsabile) {
		List<Responsabile> Responsabiles = this.ResponsabileRepository.findByNomeAndCognomeAndMatricola(Responsabile.getNome(), Responsabile.getCognome(), Responsabile.getMatricola());
		if (Responsabiles.size() > 0)
			return true;
		else 
			return false;
	}

	public Responsabile findByUsername(String username) {
		return this.ResponsabileRepository.findByUsername(username);
	}

	public Responsabile findByPassword(String password) {
		return this.ResponsabileRepository.findByPassword(password);
	}

}
