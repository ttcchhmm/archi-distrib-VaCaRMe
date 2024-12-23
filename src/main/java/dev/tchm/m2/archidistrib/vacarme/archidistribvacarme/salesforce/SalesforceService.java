package dev.tchm.m2.archidistrib.vacarme.archidistribvacarme.salesforce;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class SalesforceService implements ISalesforceService {
    @Value("${sf.organization}")
    private String targetOrganization;
    
    @Value("${sf.host}")
    private String sfHost;
    
    private final ModelMapper modelMapper;

    public SalesforceService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    private String getToken() throws Exception {
        if(System.getProperty("os.name").equalsIgnoreCase("Windows")) {
            throw new UnsupportedOperationException("Works only on UNIX platforms.");
        }
        
        var process = new ProcessBuilder().command("/bin/sh", "-c", "sf org display --json --target-org " + targetOrganization + " | jq -r .result.accessToken").start();
        process.waitFor();
        return new String(process.getInputStream().readAllBytes()).trim();
    }

    @Override
    public List<SalesforceLead> getLeads(Integer minRevenue, Integer maxRevenue, String state) throws Exception {
        var restClient = RestClient.builder()
                .baseUrl(sfHost)
                .defaultHeader("User-Agent", "VaCaRMe/1.0")
                .defaultHeader("Authorization", "Bearer " + getToken())
                .defaultHeader("Accept", "application.json")
                .build();
        
        var query = new StringBuilder("SELECT Phone,FirstName,LastName,Street,PostalCode,City,Country,Company,State,AnnualRevenue,CreationDate__c,ConvertedAccountId FROM Lead");
        
        var isFirst = true;
        if(minRevenue != null || maxRevenue != null || state != null) {
            query.append(" WHERE");
        }
        
        if(minRevenue != null) {
            query.append(" AnnualRevenue > ").append(minRevenue.intValue());
            isFirst = false;
        }
        
        if(maxRevenue != null) {
            if(!isFirst) {
                query.append(" AND");
            }
            
            query.append(" AnnualRevenue < ").append(maxRevenue.intValue());
            isFirst = false;
        }
        
        if(state != null) {
            if(!isFirst) {
                query.append(" AND");
            }
            
            query.append(" State = '").append(state).append("'");
            isFirst = false;
        }

        System.out.println(query);
        
        var resultsDTO = restClient.get().uri("/services/data/v45.0/query?q=" + query.toString().replace(' ', '+')).retrieve().body(SalesforceLeadQueryResultsDTO.class);

        assert resultsDTO != null;
        return modelMapper.map(resultsDTO.getRecords(), new TypeToken<List<SalesforceLead>>() {}.getType());
    }
}
