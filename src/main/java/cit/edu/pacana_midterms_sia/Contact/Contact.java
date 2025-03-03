package cit.edu.pacana_midterms_sia.Contact;

import com.google.api.services.people.v1.model.Person;

public class Contact {
    private String resourceName;
    private String displayName;
    private String email;
    private String phoneNumber;
    
    // Getters and Setters
    public String getResourceName() { return resourceName; }
    public void setResourceName(String resourceName) { this.resourceName = resourceName; }
    
    public String getDisplayName() { return displayName; }
    public void setDisplayName(String displayName) { this.displayName = displayName; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    
    public static Contact fromPerson(Person person) {
        Contact contact = new Contact();
        contact.setResourceName(person.getResourceName());
        contact.setDisplayName(person.getNames() != null && !person.getNames().isEmpty() 
            ? person.getNames().get(0).getDisplayName() : "");
        contact.setEmail(person.getEmailAddresses() != null && !person.getEmailAddresses().isEmpty() 
            ? person.getEmailAddresses().get(0).getValue() : "");
        contact.setPhoneNumber(person.getPhoneNumbers() != null && !person.getPhoneNumbers().isEmpty() 
            ? person.getPhoneNumbers().get(0).getValue() : "");
        return contact;
    }
} 