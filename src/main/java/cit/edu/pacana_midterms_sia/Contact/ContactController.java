package cit.edu.pacana_midterms_sia.Contact;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contacts")
@CrossOrigin(origins = "*")
public class ContactController {
    
    private final ContactService contactService;
    
    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }
    
    @GetMapping
    public ResponseEntity<List<Contact>> getAllContacts(@RequestHeader("Authorization") String accessToken) {
        try {
            List<Contact> contacts = contactService.getAllContacts(accessToken);
            return ResponseEntity.ok(contacts);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @PostMapping
    public ResponseEntity<Contact> createContact(
            @RequestHeader("Authorization") String accessToken,
            @RequestBody Contact contact) {
        try {
            Contact createdContact = contactService.createContact(accessToken, contact);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdContact);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @PutMapping("/{resourceName}")
    public ResponseEntity<Contact> updateContact(
            @RequestHeader("Authorization") String accessToken,
            @PathVariable String resourceName,
            @RequestBody Contact contact) {
        try {
            Contact updatedContact = contactService.updateContact(accessToken, resourceName, contact);
            return ResponseEntity.ok(updatedContact);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @DeleteMapping("/{resourceName}")
    public ResponseEntity<Void> deleteContact(
            @RequestHeader("Authorization") String accessToken,
            @PathVariable String resourceName) {
        try {
            contactService.deleteContact(accessToken, resourceName);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
} 