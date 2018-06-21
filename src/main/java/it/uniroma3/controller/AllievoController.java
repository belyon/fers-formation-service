package it.uniroma3.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import it.uniroma3.controller.validator.AllievoValidator;
import it.uniroma3.model.Allievo;
import it.uniroma3.model.Azienda;
import it.uniroma3.model.Centro;
import it.uniroma3.model.Responsabile;
import it.uniroma3.service.AllievoService;
import it.uniroma3.service.AziendaService;
import it.uniroma3.service.CentroService;

@Controller
public class AllievoController {

	@Autowired
	private AllievoService allievoService;

	@Autowired
	private AllievoValidator validator;

	@Autowired
	private AziendaService aziendaService;

	@Autowired
	private CentroService centroService;

	/*Mappatura fra pannello funzioni e pagine ---------------------------------------*/
	// (UC1)
	@RequestMapping("/ricercaAllievo")
	public String ricercaAllievo(Model model, String email){
		return "responsabile/trovaAllievo";
	}

	// (UC3)
	@RequestMapping("/registraAllievo")
	public String registraAllievo(Model model) {
		model.addAttribute("allievo", new Allievo());
		return "responsabile/allievoForm";
	}
	/*-----------------------------------------------------------------------------------*/

	// (UC1)
	@RequestMapping(value="/findAllievo", method = RequestMethod.GET)
	public String findAllievo(Model model,@RequestParam("email") String email, HttpSession session) {

		Responsabile responsabile = (Responsabile) session.getAttribute("responsabile");
		Centro centro = responsabile.getCentro();

		Long id = centro.getId();
		Centro centroTrovato = this.centroService.findById(id);
		List <Allievo> listaAllieviCentro = centroTrovato.getAllievi();


		if(email.equals("")) {
			model.addAttribute("campoVuoto", "ATTENZIONE! Inserire un email da ricercare");
			return "responsabile/trovaAllievo";
		}	

		for(Allievo a:listaAllieviCentro) {
			if(a.getEmail().equals(email)) {
				model.addAttribute("attivitas",a.getAttivita());
				model.addAttribute("allievo",a);
				return "responsabile/allievoTrovato";
			}
		}

		model.addAttribute("nonEsiste", "ATTENZIONE! Non esiste nessun allievo registrato con questa email!");
		return "responsabile/trovaAllievo";
	}



	// (UC3) Questo controller registra il nuovo allievo nel database
	@RequestMapping(value="/registerAllievo",method = RequestMethod.POST)
	public String registerAllievo(@Valid @ModelAttribute("allievo") Allievo allievo,
			Model model, BindingResult bindingResult, HttpSession session) {
		
		this.validator.validate(allievo, bindingResult);

		Responsabile responsabile = (Responsabile) session.getAttribute("responsabile");
		Centro centro = responsabile.getCentro();

		Long id = centro.getId();
		Centro centroTrovato = this.centroService.findById(id);

		//Il numero massimo di allievi è una stringa. Prima di confrontarlo, va convertito in integer
		Integer numeroAllieviCentro = centroTrovato.getAllievi().size();
		String numeroMassimoAllieviCentro = centroTrovato.getMaxA();
		Integer maxA = Integer.parseInt(numeroMassimoAllieviCentro);

		// Si verifica quanti utenti sono iscritti al centro
		if(numeroAllieviCentro+1 > maxA) {
			model.addAttribute("troppiAllievi","ATTENZIONE! Non è possibile iscrivere ulteriori allievi al centro."
					+ "Il numero massimo di allievi di questo centro è "+ numeroMassimoAllieviCentro);
			return "responsabile/allievoForm";
		}

		// Si verifica se l'allievo esista all'interno del centro
		if(this.allievoService.alreadyExists(allievo, id)) {
			model.addAttribute("esisteAllievo","ATTENZIONE! L'allievo già esiste! ");
			return "responsabile/allievoForm";
		}

		else {
			if(!bindingResult.hasErrors()) {
				allievo.setCentro(centroTrovato);
				this.allievoService.save(allievo);

				// Si prende la lista di allievi del centro corrente e si aggiunge il nuovo allievo a questa lista
				List <Allievo> listaAllieviCentro = centroTrovato.getAllievi();
				listaAllieviCentro.add(allievo);

				// Si salva il centro
				this.centroService.save(centroTrovato);

				// Si salva l'allievo all'interno della lista di allievi dell'azienda
				Azienda azienda = this.aziendaService.findByNome("FERS");
				List <Allievo> listaAllieviAzienda = azienda.getAllievi();
				listaAllieviAzienda.add(allievo);
				this.aziendaService.save(azienda);

				System.out.println("Numero di allievi:" +listaAllieviCentro.size());
				for(Allievo a: listaAllieviCentro)
					System.out.println("Numero di allievi:" +a.getNome());

				return "responsabile/registrazioneSuccesso";
			}
		}
		model.addAttribute("erroreFormAllievo","ATTENZIONE! Inserire tutti i dati dell'allievo");
		return "responsabile/allievoForm";
	}
}
