package cit.edu.pacana_midterms_sia.config;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.people.v1.PeopleService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Configuration
public class GoogleConfig {
    
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    
    @Bean
    public NetHttpTransport httpTransport() throws GeneralSecurityException, IOException {
        return GoogleNetHttpTransport.newTrustedTransport();
    }
    
    @Bean
    public PeopleService peopleService(NetHttpTransport httpTransport) {
        return new PeopleService.Builder(httpTransport, JSON_FACTORY, null)
                .setApplicationName("Google Contacts API")
                .build();
    }
} 