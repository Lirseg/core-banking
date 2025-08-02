package com.digitalbank.core_banking.model.customer;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class IndividualCustomer extends Customer{

    private String personalId;
    private String cellNum;
    private java.time.LocalDate birthDate;


}
