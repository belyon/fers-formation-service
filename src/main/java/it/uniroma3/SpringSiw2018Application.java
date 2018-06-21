package it.uniroma3;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import it.uniroma3.model.Allievo;
import it.uniroma3.model.Attivita;
import it.uniroma3.model.Azienda;
import it.uniroma3.model.Centro;
import it.uniroma3.model.Responsabile;
import it.uniroma3.service.AllievoService;
import it.uniroma3.service.AttivitaService;
import it.uniroma3.service.AziendaService;
import it.uniroma3.service.CentroService;
import it.uniroma3.service.ResponsabileService;


@SpringBootApplication
public class SpringSiw2018Application {
	
	@Autowired
	private AllievoService allievoService; 
	
	@Autowired
	private AziendaService aziendaService;
	
	@Autowired
	private AttivitaService attivitaService;
	
	@Autowired
	private ResponsabileService responsabileService;
	
	@Autowired
	private CentroService centroService;
	
	public static void main(String[] args) {
		SpringApplication.run(SpringSiw2018Application.class, args);
	}
	
	@PostConstruct
	public void init() {
		List<Centro> centri = new ArrayList<Centro>();
		Allievo allievo = new Allievo("Massimo", "Bianchi", "bianchi@gmail.com","123456789", "2017-05-03", "Roma",null);
		List<Allievo> allievi = new ArrayList<Allievo>();
		allievi.add(allievo);
	
		List<Responsabile> responsabili = new ArrayList<Responsabile>();
		
		Azienda azienda = new Azienda("FERS", centri, allievi, responsabili);
		allievoService.save(allievo);
		aziendaService.save(azienda);
		
		Attivita attivita01 = new Attivita("Attivita01", "05-03-2018", "09:00", "10:00", new ArrayList<Allievo>());
		attivitaService.save(attivita01);
		
		Attivita attivita02 = new Attivita("Attivita02", "04-10-2018", "10:00", "11:00", new ArrayList<Allievo>());
		attivitaService.save(attivita02);
		
		List<Attivita> listaAttivita01 = new ArrayList<Attivita>();
		List<Attivita> listaAttivita02 = new ArrayList<Attivita>();
		
		listaAttivita01.add(attivita01);
		listaAttivita02.add(attivita02);
		
		Centro centro01 = new Centro("Centro_01", "Via della Vasca Navale, 55", "centro_01@gmail.com", "333333333", "10", listaAttivita01);
		Centro centro02 = new Centro("Centro_02", "Via Pincherle, 23", "centro_02@gmail.com", "5555555555", "15", listaAttivita02);
		this.centroService.save(centro01);
		this.centroService.save(centro02);
		
		azienda.getCentri().add(centro01);
		azienda.getCentri().add(centro02);
		this.aziendaService.save(azienda);
		
		allievo.setCentro(centro01);
		this.allievoService.save(allievo);
		
		this.centroService.save(centro01);
																						//PASSWORD "passdir"
		Responsabile direttore = new Responsabile("Paolo", "Bruni", 33333, "dir00", "$2a$09$GdJlgA5JoQQzlwCJyGpk..vmGJ4AcbV.b.j.sv10Wdt4hrck8CHCm", "DIRETTORE", null);
		this.responsabileService.save(direttore);
																						   //PASSWORD "pass01"
		Responsabile responsabile01 = new Responsabile("Giorgio", "Verdi", 4321, "resp01", "$2a$09$5TSrzDG8gapAfwrCSXg08ObGpN3PRVz6gXQ7i0lDaZmPAvK7sOe4G", "RESPONSABILE", centro01);
		this.responsabileService.save(responsabile01);
																						//PASSWORD "pass02"
		Responsabile responsabile02 = new Responsabile("Marco", "Rossi", 4322, "resp02", "$2a$09$c0UbFD7mwgiebqWCM04Rcuoe1WUqlxejtFkPH3eu1S.llu/DjP3nW", "RESPONSABILE", centro02);
		this.responsabileService.save(responsabile02);
	}
}
