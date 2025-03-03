package cit.edu.pacana_midterms_sia.GoogleContacts;

import com.google.api.services.people.v1.PeopleService;
import com.google.api.services.people.v1.model.Person;
import com.google.api.services.people.v1.model.ListConnectionsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class GoogleContactsService {
    
    @Autowired
    private PeopleService peopleService;
    
    public List<Person> getAllContacts(String accessToken) throws IOException {
        ListConnectionsResponse response = peopleService.people().connections()
                .list("people/me")
                .setPageSize(100)
                .setAccessToken(accessToken)
                .execute();
        return response.getConnections();
    }
    
    public Person createContact(String accessToken, Person contact) throws IOException {
        return peopleService.people().createContact(contact)
                .setAccessToken(accessToken)
                .execute();
    }
    
    public Person updateContact(String accessToken, String resourceName, Person contact) throws IOException {
        return peopleService.people().updateContact(resourceName, contact)
                .setAccessToken(accessToken)
                .execute();
    }
    
    public void deleteContact(String accessToken, String resourceName) throws IOException {
        peopleService.people().deleteContact(resourceName)
                .setAccessToken(accessToken)
                .execute();
    }
} 