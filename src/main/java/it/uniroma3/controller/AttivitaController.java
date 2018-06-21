package it.uniroma3.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.controller.validator.AttivitaValidator;
import it.uniroma3.model.Allievo;
import it.uniroma3.model.Attivita;
import it.uniroma3.model.Centro;
import it.uniroma3.model.Responsabile;
import it.uniroma3.service.AllievoService;
import it.uniroma3.service.AttivitaService;
import it.uniroma3.service.CentroService;

@Controller
public class AttivitaController {

	@Autowired
	private AttivitaService attivitaService;

	@Autowired
	private AttivitaValidator validator;

	@Autowired
	private AllievoService allievoService;

	@Autowired
	private CentroService centroService;

	/*Mappatura fra pannello funzioni e pagine ---------------------------------------*/

	// (UC2)
	@RequestMapping("/collegaAttivita")
	public String collegaAttivita(Model model, HttpSession sessione) {
		Responsabile responsabile = (Responsabile) sessione.getAttribute("responsabile");
		Centro centro = responsabile.getCentro();

		Long id = centro.getId();
		Centro centroTrovato = this.centroService.findById(id);
		model.addAttribute("attivitas", centroTrovato.getAttivita());
		return "responsabile/associazioneAttivita"; 
	}

	// (UC2)
	@RequestMapping(value="/mostraAllieviAttivita/{id}",method = RequestMethod.GET)
	public String mostraAttivita(@PathVariable("id") Long id, Model model) {
		model.addAttribute("attivita",this.attivitaService.findById(id));
		model.addAttribute("allievi", this.attivitaService.findById(id).getAllievi());
		return "responsabile/mostraAllieviAttivita";
	}

	// (UC4)
	@RequestMapping("/creaAttivita")
	public String creaAttivita(Model model) {
		model.addAttribute("attivita", new Attivita());
		return "responsabile/attivitaForm";
	}
	/*---------------------------------------------------------------------------------*/

	//(UC2)
	@RequestMapping(value="/linkAttivita", method = RequestMethod.GET)
	public String linkAttivita(@RequestParam("email")String email,
			@RequestParam("id") String idl, Model model, HttpSession session) {

		Responsabile responsabile = (Responsabile) session.getAttribute("responsabile");
		Centro centro = responsabile.getCentro();

		Long idc = centro.getId();
		Centro centroTrovato = this.centroService.findById(idc);

		/* Verifica formato id (nel blocco try deve essere inserito il pezzo di codice che potrebbe 
		 * generare un'eventuale eccezione e nel blocco catch 
		 * il codice di gestione dell'eccezione )*/
		try {
			Long id2 = Long.parseLong(idl);
		}
		catch(NumberFormatException e)  {
			model.addAttribute("formatoId", "ATTENZIONE! L'id deve essere un numero intero");
			return getListAttivita(model, session);
		} 
		/* Se si arriva fin qui, vuol dire che l'id è un numero (Long) e si converte la stringa
		che lo rapprsenta in un oggetto Long effettivo */
		Long id = Long.parseLong(idl);

		/*-------------------------------------------------------*/

		// Se il campo id ed il campo email sono vuoti 
		if(email.equals("") && id==null) {
			model.addAttribute("emailVuota","ATTENZIONE! Il campo email è obbligatorio! ");
			model.addAttribute("idVuoto","ATTENZIONE! Il campo id attività è obbligatorio ");
			return getListAttivita(model, session);	
		}

		// Se il campo email è vuoto
		if(email.equals("")) {
			model.addAttribute("emailVuota","ATTENZIONE! Il campo email è obbligatorio! ");
			return getListAttivita(model, session);	
		}

		// Se il campo id è vuoto
		if(id==null) {
			model.addAttribute("idVuoto","ATTENZIONE! Il campo id attività è obbligatorio ");
			return getListAttivita(model,session);	
		}

		// Se l'id passato non corrisponde a nessuna attività
		List <Attivita> listaAttivitaCentro = centroTrovato.getAttivita();
		boolean trovatoId = false; 

		for(Attivita a:listaAttivitaCentro) {
			if(a.getId() == id) {
				trovatoId = true;
				break;
			}
		}

		if(trovatoId == false) {
			model.addAttribute("idSconosciuto","ATTENZIONE! Non esistono attività con questo id");
			return getListAttivita(model,session);
		}

		// Se l'email immessa non corrisponde a nessun allievo -------------------------------------------------
		List <Allievo> listaAllieviCentro = centroTrovato.getAllievi();
		boolean trovatoEmail = false;

		for(Allievo a:listaAllieviCentro) {
			if(a.getEmail().equals(email)) {
				trovatoEmail = true;
				break;
			}
		}

		if(trovatoEmail==false) {
			model.addAttribute("emailSconosciuta","ATTENZIONE! Non esistono allievi con questa email");
			return getListAttivita(model,session);
		}

		// Se l'allievo è già iscritto all'attività
		for(Attivita a:listaAttivitaCentro) {
			if(a.getId() == id) {
				for(Allievo al: a.getAllievi()) {
					if(al.getEmail().equals(email)){
						model.addAttribute("allievoGiaIscritto","ATTENZIONE! L'allievo è già iscritto a questa attività");
						return getListAttivita(model,session);	
					}
				}
			}
		}

		//-----------------------------------------------------------------------------------------

		// FINE CONTROLLI===============================

		/* Se tutti i controlli precedenti vengono superati, viene aggiunto l'allievo
		alla lista di allievi iscritti all'attività */
		Allievo allievo = null;

		for(Allievo al: listaAllieviCentro) {
			if(al.getEmail().equals(email)) {
				allievo = al;
				break;
			}
		}

		Attivita attivita = null; 

		for(Attivita a: listaAttivitaCentro) {
			if(a.getId() == id) {
				attivita = a;
				a.getAllievi().add(allievo);
				this.attivitaService.save(a);
				break;
			}
		}

		for(Allievo al: listaAllieviCentro) {
			if(al.getEmail().equals(email)) {
				al.getAttivita().add(attivita);
				this.allievoService.save(al);
				break;
			}
		}
		model.addAttribute("nomeAt",attivita.getNome());
		model.addAttribute("allievi",attivita.getAllievi());
		model.addAttribute("lla",attivita.getAllievi().size());

		return "responsabile/associazioneSuccesso";
	}

	public String getListAttivita(Model model, HttpSession session) {

		Responsabile responsabile = (Responsabile) session.getAttribute("responsabile");
		Centro centro = responsabile.getCentro();
		Long id = centro.getId();

		Centro centroTrovato = this.centroService.findById(id);

		model.addAttribute("attivitas",centroTrovato.getAttivita());
		return "responsabile/associazioneAttivita";
	}

	// (UC4) Questo controller crea una nuova attività e 
	@RequestMapping(value="/registerAttivita",method = RequestMethod.POST)
	public String registerAttivita(@Valid @ModelAttribute("attivita") Attivita attivita,
			Model model, BindingResult bindingResult, HttpSession session) {

		//Restituisce responsabile in sessione
		Responsabile responsabile  = (Responsabile) session.getAttribute("responsabile");

		// Si prende il centro associato al responsabile in sessione
		Centro centro = responsabile.getCentro();
		Long id = centro.getId();

		Centro centroTrovato = this.centroService.findById(id);

		// Si prende la lista delle attività del centro ricavato
		List <Attivita> listaAttivitaCentro = centroTrovato.getAttivita();

		this.validator.validate(attivita, bindingResult);

		if(this.attivitaService.alreadyExists(attivita)) {
			model.addAttribute("esisteAttivita","ATTENZIONE! L'attivita già esiste! ");
			return "responsabile/attivitaForm";
		}
		else {
			if(!bindingResult.hasErrors()) {

				//Se non ci sono errori, si aggiunge la nuova attività alla lista di attività del centro
				listaAttivitaCentro.add(attivita);

				//Si salva l'attività all'interno sistema
				this.attivitaService.save(attivita);

				// si salva il centro con la nuova attività nella sua lista di attività
				this.centroService.save(centro);
				return "responsabile/creazioneSuccesso";
			}
		}
		model.addAttribute("erroreFormAttivita","ATTENZIONE! Inserire tutti i dati dell'attivita");
		return "responsabile/attivitaForm";
	}
}
