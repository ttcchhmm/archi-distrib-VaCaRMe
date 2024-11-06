package dev.tchm.m2.archidistrib.vacarme.archidistribvacarme.openstreetmap;

import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Service
public class OSMNominatimService implements IOSMNominatimService {
    @Value("${osm.nominatim.url}")
    private String nominatimURL;

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
        
        @SuppressWarnings("unchecked")
        HashMap<String, String> first = (HashMap<String, String>) Objects.requireNonNull(restClient.get().uri(sb.toString()).retrieve().body(List.class)).getFirst();
        
        return new OSMNomatimResult(Double.parseDouble(first.get("lat")), Double.parseDouble(first.get("lon")));
    }
}
