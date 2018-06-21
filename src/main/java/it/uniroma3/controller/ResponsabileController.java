package it.uniroma3.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import it.uniroma3.model.Responsabile;

@Controller
public class ResponsabileController {
    
    @RequestMapping("/pannelloResponsabile")
    public String entro(HttpSession sessione, Model model){ 
    	Responsabile responsabile = (Responsabile) sessione.getAttribute("responsabile");
    	model.addAttribute("username",responsabile.getUsername());
    	return "responsabile/PannelloResponsabile";
    }
    
    @RequestMapping("/logoutResp")
    public String esco() { 
        return "index";
    }
}
