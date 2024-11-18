package dev.tchm.m2.archidistrib.vacarme.archidistribvacarme.salesforce;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SalesforceService implements ISalesforceService {
    @Value("${sf.organization}")
    private String targetOrganization;
    
    public String getToken() throws Exception {
        if(System.getProperty("os.name").equalsIgnoreCase("Windows")) {
            throw new UnsupportedOperationException("Works only on UNIX platforms.");
        }
        
        var process = new ProcessBuilder().command("/usr/bin/sh", "-c", "sf org display --target-org " + targetOrganization + " 2> /dev/null | grep 'Access Token' | tr -s ' ' | cut -f 4 -d ' '").start();
        return new String(process.getInputStream().readAllBytes()).trim();
    }
}
