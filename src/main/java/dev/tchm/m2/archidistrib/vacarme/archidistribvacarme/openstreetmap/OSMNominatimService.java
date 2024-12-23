package dev.tchm.m2.archidistrib.vacarme.archidistribvacarme.openstreetmap;

import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class OSMNominatimService implements IOSMNominatimService {
    @Value("${osm.nominatim.url}")
    private String nominatimURL;
    
    private Instant lastRequest = null;

    @Nullable
    @Override
    public OSMNomatimResult search(@Nullable String city, @Nullable String country, @Nullable String postalCode, @Nullable String street) {
        StringBuilder sb = new StringBuilder();
        sb.append("/search?format=json&limit=1");

        if(city != null) {
            sb.append("&city=").append(city);
        }

        if(country != null) {
            sb.append("&country=").append(country);
        }
        
        if(postalCode != null) {
            sb.append("&postalcode=").append(postalCode);
        }

        if(street != null) {
            sb.append("&street=").append(street);
        }
        
        RestClient restClient = RestClient.builder()
                .baseUrl(nominatimURL)
                .defaultHeader("User-Agent", "VaCaRMe/1.0")
                .build();
        
        // Implement rate limiting
        // https://operations.osmfoundation.org/policies/nominatim/
        if(lastRequest == null) {
            lastRequest = Instant.now();
        } else {
            if(lastRequest.plus(1, ChronoUnit.SECONDS).isAfter(Instant.now())) {
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException ignored) {}
                
                lastRequest = Instant.now();
            }
        }
        
        try {
            @SuppressWarnings("unchecked")
            HashMap<String, String> first = (HashMap<String, String>) Objects.requireNonNull(restClient.get().uri(sb.toString()).retrieve().body(List.class)).getFirst();

            return new OSMNomatimResult(Double.parseDouble(first.get("lat")), Double.parseDouble(first.get("lon")));
        } catch (NoSuchElementException ignored) {
            return null;
        }
    }
}
