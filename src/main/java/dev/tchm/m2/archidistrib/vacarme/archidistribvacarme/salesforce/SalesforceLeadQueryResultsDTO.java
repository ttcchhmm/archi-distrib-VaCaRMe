package dev.tchm.m2.archidistrib.vacarme.archidistribvacarme.salesforce;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SalesforceLeadQueryResultsDTO {
    private int totalSize;
    private boolean done;
    private List<SalesforceLeadDTO> records;
}
