package com.digitalbank.core_banking.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Metadata {
    private String processedBy;
    private String source;

    public Metadata() {}
    public Metadata(String processedBy, String source) {
        this.processedBy = processedBy;
        this.source = source;
    }

}
