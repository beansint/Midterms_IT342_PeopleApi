    package cit.edu.pacana_midterms_sia.Contact;

    import com.google.api.services.people.v1.PeopleService;
    import com.google.api.services.people.v1.model.Person;
    import com.google.api.services.people.v1.model.ListConnectionsResponse;
    import com.google.api.services.people.v1.model.Name;
    import com.google.api.services.people.v1.model.EmailAddress;
    import com.google.api.services.people.v1.model.PhoneNumber;
    import com.google.api.services.people.v1.model.FieldMetadata;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;
    import org.springframework.http.HttpStatus;
    import org.springframework.web.server.ResponseStatusException;

    import java.util.List;
    import java.util.stream.Collectors;

    @Service
    public class ContactService {

        private final PeopleService peopleService;

        @Autowired
        public ContactService(PeopleService peopleService) {
            this.peopleService = peopleService;
        }

        public List<Contact> getAllContacts(String accessToken) {
            try {
                ListConnectionsResponse response = peopleService.people().connections()
                        .list("people/me")
                        .setPageSize(100)
                        .setPersonFields("names,emailAddresses,phoneNumbers")
                        .setAccessToken(accessToken)
                        .execute();
                return response.getConnections().stream()
                        .map(Contact::fromPerson)
                        .collect(Collectors.toList());
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Failed to fetch contacts: " + e.getMessage());
            }
        }

        public Contact createContact(String accessToken, Contact contact) {
            try {
                Person person = new Person();

                // Set name
                Name name = new Name();
                name.setGivenName(contact.getFirstName());
                name.setFamilyName(contact.getLastName());
                name.setDisplayName(contact.getDisplayName());
                person.setNames(List.of(name));

                // Set emails
                List<EmailAddress> emailAddresses = contact.getEmails().stream()
                    .map(email -> {
                        EmailAddress emailAddress = new EmailAddress();
                        emailAddress.setValue(email);
                        return emailAddress;
                    })
                    .collect(Collectors.toList());
                person.setEmailAddresses(emailAddresses);

                // Set phone
                List<PhoneNumber> phoneNumbers = contact.getPhoneNumbers().stream()
                    .map(phone -> {
                        PhoneNumber phoneNumber = new PhoneNumber();
                        phoneNumber.setValue(phone);
                        return phoneNumber;
                    })
                    .collect(Collectors.toList());
                person.setPhoneNumbers(phoneNumbers);

                Person createdPerson = peopleService.people().createContact(person)
                        .setAccessToken(accessToken)
                        .execute();
                return Contact.fromPerson(createdPerson);
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Failed to create contact: " + e.getMessage());
            }
        }

        public Contact updateContact(String accessToken, String resourceName, Contact contact) {
            try {
                // Fetch existing contact to get ETag
                Person existingPerson = peopleService.people().get(resourceName)
                        .setPersonFields("names,emailAddresses,phoneNumbers")
                        .setAccessToken(accessToken)
                        .execute();

                Person person = new Person();
                person.setEtag(existingPerson.getEtag());

                // Set name
                Name name = new Name();
                name.setGivenName(contact.getFirstName());
                name.setFamilyName(contact.getLastName());
                name.setDisplayName(contact.getDisplayName());
                person.setNames(List.of(name));

                // Set emails
                List<EmailAddress> emailAddresses = contact.getEmails().stream()
                    .map(email -> {
                        EmailAddress emailAddress = new EmailAddress();
                        emailAddress.setValue(email);
                        return emailAddress;
                    })
                    .collect(Collectors.toList());
                person.setEmailAddresses(emailAddresses);

                // Set phone
                List<PhoneNumber> phoneNumbers = contact.getPhoneNumbers().stream()
                    .map(phone -> {
                        PhoneNumber phoneNumber = new PhoneNumber();
                        phoneNumber.setValue(phone);
                        return phoneNumber;
                    })
                    .collect(Collectors.toList());
                person.setPhoneNumbers(phoneNumbers);

                Person updatedPerson = peopleService.people().updateContact(resourceName, person)
                        .setUpdatePersonFields("names,emailAddresses,phoneNumbers")
                        .setAccessToken(accessToken)
                        .execute();

                return Contact.fromPerson(updatedPerson);
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Failed to update contact: " + e.getMessage());
            }
        }

        public void deleteContact(String accessToken, String resourceName) {
            try {
                System.out.println("Access token for delete: " + accessToken);
                System.out.println("Deleting contact with resource name: " + resourceName);
                peopleService.people().deleteContact(resourceName)
                        .setAccessToken(accessToken)
                        .execute();
            } catch (Exception e) {
                e.printStackTrace();
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                        "Failed to delete contact: " + e.getMessage());
            }
        }
    }