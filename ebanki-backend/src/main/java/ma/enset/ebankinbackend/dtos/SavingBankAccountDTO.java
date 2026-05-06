package ma.enset.ebankinbackend.dtos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.enset.ebankinbackend.entities.AccountOperation;
import ma.enset.ebankinbackend.entities.Customer;
import ma.enset.ebankinbackend.enums.AccountStatus;

import java.util.Date;
import java.util.List;


@Data
public  class SavingBankAccountDTO extends  BankAccountDTO{

    private String id;
    private double balance;
    private Date createdAt;
    private AccountStatus status;

    private CustomerDTO customerDTO;
    private double interestRate;


}
