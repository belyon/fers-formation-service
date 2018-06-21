package it.uniroma3.controller;

import it.uniroma3.model.Responsabile;
import it.uniroma3.service.ResponsabileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private ResponsabileService responsabileService;


    @RequestMapping("/login")
    public String login(Model model) {
        model.addAttribute("responsabile", new Responsabile());
        return "login";
    }

    @RequestMapping("/role")
    public String loginRole(HttpSession session, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().toString();
        Responsabile responsabile = this.responsabileService.findByUsername(auth.getName());
        String targetUrl = "";
        
        if(role.contains("RESPONSABILE")) {
            session.setAttribute("responsabile", responsabile);
            model.addAttribute("username",responsabile.getUsername());
            targetUrl = "responsabile/PannelloResponsabile";
        } else if(role.contains("DIRETTORE")) {
            session.setAttribute("responsabile", responsabile);
            model.addAttribute("username", responsabile.getUsername());
            targetUrl = "direttore/PannelloAzienda";
        }
        
        return targetUrl;
    }
 }
