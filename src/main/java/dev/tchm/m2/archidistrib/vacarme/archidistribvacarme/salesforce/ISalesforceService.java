package dev.tchm.m2.archidistrib.vacarme.archidistribvacarme.salesforce;

import java.util.List;

public interface ISalesforceService {
    List<SalesforceLead> getLeads() throws Exception;
}
