package com.digitalbank.core_banking.model.customer;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class BusinessCustomer extends Customer {

    private String businessNumber;

}
