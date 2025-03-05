package cit.edu.pacana_midterms_sia.Contact;

import com.google.api.services.people.v1.model.Person;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Contact {
    private String resourceName;
    private String firstName;
    private String lastName;
    private List<String> emails;
    private List<String> phoneNumbers;
    
    public Contact() {
        this.emails = new ArrayList<>();
        this.phoneNumbers = new ArrayList<>();
    }
    
    // Getters and Setters
    public String getResourceName() { return resourceName; }
    public void setResourceName(String resourceName) { this.resourceName = resourceName; }
    
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    
    public String getDisplayName() { 
        return lastName != null && !lastName.trim().isEmpty() 
            ? firstName + " " + lastName.trim()
            : firstName;
    }
    
    public List<String> getEmails() { return emails; }
    public void setEmails(List<String> emails) { this.emails = emails != null ? emails : new ArrayList<>(); }
    
    public List<String> getPhoneNumbers() { return phoneNumbers; }
    public void setPhoneNumbers(List<String> phoneNumbers) { this.phoneNumbers = phoneNumbers != null ? phoneNumbers : new ArrayList<>(); }
    
    public static Contact fromPerson(Person person) {
        Contact contact = new Contact();
        contact.setResourceName(person.getResourceName());
        
        if (person.getNames() != null && !person.getNames().isEmpty()) {
            contact.setFirstName(person.getNames().get(0).getGivenName());
            contact.setLastName(person.getNames().get(0).getFamilyName());
        }
        
        if (person.getEmailAddresses() != null && !person.getEmailAddresses().isEmpty()) {
            contact.setEmails(person.getEmailAddresses().stream()
                .map(email -> email.getValue())
                .collect(Collectors.toList()));
        }
        
        if (person.getPhoneNumbers() != null && !person.getPhoneNumbers().isEmpty()) {
            contact.setPhoneNumbers(person.getPhoneNumbers().stream()
                .map(phone -> phone.getValue())
                .collect(Collectors.toList()));
        }
        
        return contact;
    }
}
