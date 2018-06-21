package it.uniroma3.controller;


import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import it.uniroma3.model.Responsabile;

@Controller

public class AziendaController {
    
    @RequestMapping("/pannelloAzienda")
    public String entro(HttpSession sessione, Model model) { 
    	Responsabile direttore = (Responsabile) sessione.getAttribute("responsabile");
    	model.addAttribute("username",direttore.getUsername());
        return "direttore/PannelloAzienda";
    }
    
}
