package dev.tchm.m2.archidistrib.vacarme.archidistribvacarme.controllers;

import dev.tchm.m2.archidistrib.vacarme.archidistribvacarme.dto.LeadDTO;
import dev.tchm.m2.archidistrib.vacarme.archidistribvacarme.salesforce.ISalesforceService;
import dev.tchm.m2.archidistrib.vacarme.archidistribvacarme.thrift.ThriftService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@AllArgsConstructor
public class LeadController {
    private final ISalesforceService salesforceService;
    
    private final ThriftService thriftService;
    
    private final ModelMapper modelMapper;
    
    @GetMapping("/api/lead")
    public List<LeadDTO> getLeads(@RequestParam("lowRevenue") final double lowRevenue, @RequestParam("highRevenue") final double highRevenue, @RequestParam("state") final String state) throws Exception {
        var leads = new ArrayList<LeadDTO>();
        
        for(var lead : salesforceService.getLeads()) {
            var mappedLead = modelMapper.map(lead, LeadDTO.class);
            mappedLead.setSource("salesforce");
            leads.add(mappedLead);
        }
        
        thriftService.client(client -> {
            for(var lead : client.findLeads(lowRevenue, highRevenue, state)) {
                var mappedLead = modelMapper.map(lead, LeadDTO.class);
                mappedLead.setSource("legacy");
                
                var name = lead.getName().split(" ");
                
                if(name.length != 0) {
                    if(name.length == 1) {
                        mappedLead.setLastName(lead.getName());
                    } else {
                        mappedLead.setFirstName(name[0]);
                        
                        name = Arrays.copyOfRange(name, 1, name.length);
                        
                        mappedLead.setLastName(String.join(" ", name));
                    }
                }
                
                leads.add(mappedLead);
            }
            
            return null;
        });
        
        return leads;
    }
}
