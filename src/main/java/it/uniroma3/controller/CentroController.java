package it.uniroma3.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import it.uniroma3.controller.validator.CentroValidator;
import it.uniroma3.model.Allievo;
import it.uniroma3.model.Attivita;
import it.uniroma3.model.Azienda;
import it.uniroma3.model.Centro;
import it.uniroma3.service.AziendaService;
import it.uniroma3.service.CentroService;

@Controller
public class CentroController {

	@Autowired
	private CentroService centroService;

	@Autowired
	private CentroValidator validator;

	@Autowired
	private AziendaService aziendaService;

	/*Mappatura fra pannello funzioni e pagine ---------------------------------------*/
	// (UC5)
	@RequestMapping("/ricercaCentro")
	public String ricercaCentro(Model model, String email){
		return "direttore/trovaCentro";
	}

	// (UC6)
	@RequestMapping("/aggiungiCentro")
	public String aggiungiCentro(Model model) {
		model.addAttribute("centro", new Centro());
		return "direttore/centroForm";
	}
	/*-----------------------------------------------------------------------------------*/

	// (UC5)
	@RequestMapping(value="/findCentro", method = RequestMethod.GET)
	public String findCentro(Model model,@RequestParam("email") String email) {

		Centro centro = this.centroService.findByEmail(email);
		
		if(email.equals("")) {
			model.addAttribute("campoVuotoCentro", "ATTENZIONE! Inserire un email da ricercare");
			return "direttore/trovaCentro";
		}	

		else if(centro == null) {
			model.addAttribute("nonEsisteCentro", "ATTENZIONE! Non esiste nessun centro registrato con questa email!");
			return "direttore/trovaCentro";
		}
		else {

			List <Allievo> listaAllieviCentro = centro.getAllievi();
			List <Attivita> listaAttivitaCentro = centro.getAttivita();

			model.addAttribute("centro", centro);
			model.addAttribute("allieviCentro",listaAllieviCentro);
			model.addAttribute("attivitaCentro",listaAttivitaCentro);

			return "direttore/centroTrovato";
		}
	}

	// (UC3) Questo controller registra il nuovo allievo nel database
	@RequestMapping(value="/addCentro",method = RequestMethod.POST)
	public String addCentro(@Valid @ModelAttribute("centro") Centro centro,
			Model model, BindingResult bindingResult) {

		this.validator.validate(centro, bindingResult);

		// Controllo del numero di telefono
		try {
			String s = centro.getTelefono();
			Integer telefono = Integer.parseInt(s);
		}
		catch(NumberFormatException e) {
			model.addAttribute("erroreTel","ATTENZIONE! Il telefono deve essere composto da numeri");
			return "direttore/centroForm";
		}

		// Controllo del numero massimo di allievi
		try {
			String s = centro.getMaxA();
			Integer maxA = Integer.parseInt(s);
		}
		catch(NumberFormatException e) {
			model.addAttribute("erroreMax","ATTENZIONE! Il numero massimo di allievi deve essere un numero intero");
			return "direttore/centroForm";
		}

		// Se il centro già esiste
		if(this.centroService.alreadyExists(centro)) {
			model.addAttribute("esisteCentro","ATTENZIONE! Il centro già esiste! ");
			return "direttore/centroForm";
		}

		else {
			if(!bindingResult.hasErrors()) {
				this.centroService.save(centro);
				Azienda azienda = this.aziendaService.findByNome("FERS");
				List <Centro> listaCentriAzienda = azienda.getCentri();
				listaCentriAzienda.add(centro);
				this.aziendaService.save(azienda);

				return "direttore/aggiuntaCentroSuccesso";
			}
		} 
		model.addAttribute("erroreFormCentro","*ATTENZIONE! Inserire tutti i dati del centro");
		return "direttore/centroForm";
	}
}
