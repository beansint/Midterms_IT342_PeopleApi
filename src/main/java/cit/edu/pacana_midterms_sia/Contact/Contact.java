package cit.edu.pacana_midterms_sia.Contact;

import com.google.api.services.people.v1.model.Person;
import com.google.api.services.people.v1.model.Name;

public class Contact {
    private String resourceName;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;

    // Getters and Setters
    public String getResourceName() {
        return resourceName;
    }
    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    // Convenience method to combine first and last names as a display name.
    public String getDisplayName() {
        if (firstName == null && lastName == null) {
            return "";
        }
        if (firstName == null) {
            return lastName;
        }
        if (lastName == null) {
            return firstName;
        }
        return firstName + " " + lastName;
    }

    // Create a Contact instance from a Person object returned by the People API.
    public static Contact fromPerson(Person person) {
        Contact contact = new Contact();
        contact.setResourceName(person.getResourceName());

        if (person.getNames() != null && !person.getNames().isEmpty()) {
            Name name = person.getNames().get(0);
            // Use givenName and familyName if available.
            if (name.getGivenName() != null || name.getFamilyName() != null) {
                contact.setFirstName(name.getGivenName());
                contact.setLastName(name.getFamilyName());
            } else if (name.getDisplayName() != null) {
                // Fallback: split displayName by space.
                String[] parts = name.getDisplayName().split(" ", 2);
                contact.setFirstName(parts[0]);
                if (parts.length > 1) {
                    contact.setLastName(parts[1]);
                }
            }
        }

        if (person.getEmailAddresses() != null && !person.getEmailAddresses().isEmpty()) {
            contact.setEmail(person.getEmailAddresses().get(0).getValue());
        }
        if (person.getPhoneNumbers() != null && !person.getPhoneNumbers().isEmpty()) {
            contact.setPhoneNumber(person.getPhoneNumbers().get(0).getValue());
        }

        return contact;
    }
}
