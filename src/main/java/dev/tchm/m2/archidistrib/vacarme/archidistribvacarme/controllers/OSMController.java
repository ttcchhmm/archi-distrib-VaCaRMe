package dev.tchm.m2.archidistrib.vacarme.archidistribvacarme.controllers;

import dev.tchm.m2.archidistrib.vacarme.archidistribvacarme.dto.OSMNominatimResultDTO;
import dev.tchm.m2.archidistrib.vacarme.archidistribvacarme.openstreetmap.IOSMNominatimService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OSMController {
    private final IOSMNominatimService nominatimService;
    
    private final ModelMapper modelMapper;
    
    @Autowired
    public OSMController(IOSMNominatimService service, ModelMapper modelMapper) {
        this.nominatimService = service;
        this.modelMapper = modelMapper;
    }
    
    @GetMapping("/api/osm/search")
    public OSMNominatimResultDTO searchOpenStreetMap() {
        return modelMapper.map(nominatimService.search("Angers", "France", "49100", "Boulevard Lavoisier"), OSMNominatimResultDTO.class);
    }
}
