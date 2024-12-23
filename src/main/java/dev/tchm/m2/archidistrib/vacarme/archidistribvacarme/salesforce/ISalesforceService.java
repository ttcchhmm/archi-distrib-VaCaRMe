package dev.tchm.m2.archidistrib.vacarme.archidistribvacarme.salesforce;

import java.util.List;

public interface ISalesforceService {
    default List<SalesforceLead> getLeads() throws Exception {
        return getLeads(null, null, null);
    }
    
    List<SalesforceLead> getLeads(Integer minRevenue, Integer maxRevenue, String state) throws Exception;
}
