package cit.edu.pacana_midterms_sia.controller;

import cit.edu.pacana_midterms_sia.Contact.Contact;
import cit.edu.pacana_midterms_sia.Contact.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class ViewController {
    
    @Autowired
    private ContactService contactService;
    
    @GetMapping("/")
    public String home() {
        return "components/home";
    }
    
    @GetMapping("/login")
    public String login() {
        return "components/home";
    }
    
    @GetMapping("/contacts")
    public String contacts(@RegisteredOAuth2AuthorizedClient("google") OAuth2AuthorizedClient authorizedClient, Model model) {
        String accessToken = authorizedClient.getAccessToken().getTokenValue();
        List<Contact> contacts = contactService.getAllContacts(accessToken);
        model.addAttribute("contacts", contacts);
        model.addAttribute("accessToken", accessToken);
        return "components/contacts";
    }
} 