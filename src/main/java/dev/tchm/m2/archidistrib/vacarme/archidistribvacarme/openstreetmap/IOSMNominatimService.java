package dev.tchm.m2.archidistrib.vacarme.archidistribvacarme.openstreetmap;

import jakarta.annotation.Nullable;

public interface IOSMNominatimService {
    @Nullable
    OSMNomatimResult search(
            @Nullable String city,
            @Nullable String country,
            @Nullable String postalCode,
            @Nullable String street
    );
}
