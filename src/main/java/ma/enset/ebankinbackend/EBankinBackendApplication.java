package ma.enset.ebankinbackend;

import com.fasterxml.jackson.databind.ser.impl.StringArraySerializer;
import ma.enset.ebankinbackend.dtos.BankAccountDTO;
import ma.enset.ebankinbackend.dtos.CurrentBankAccountDTO;
import ma.enset.ebankinbackend.dtos.CustomerDTO;
import ma.enset.ebankinbackend.dtos.SavingBankAccountDTO;
import ma.enset.ebankinbackend.entities.*;
import ma.enset.ebankinbackend.enums.*;
import ma.enset.ebankinbackend.exceptions.CustomerNotFoundException;
import ma.enset.ebankinbackend.repositories.*;
import ma.enset.ebankinbackend.services.BankAccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class EBankinBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(EBankinBackendApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(BankAccountService  bankAccountService) {
        return args -> {
            Stream.of("Hassan", "Imane", "Mohamed").forEach(name -> {
                CustomerDTO customerDTO = new CustomerDTO();
                customerDTO.setName(name);
                customerDTO.setEmail(name + "@gmail.com");
                bankAccountService.saveCustomer(customerDTO);
            });

            bankAccountService.listCustomers().forEach(customer -> {
                try {
                    bankAccountService. saveCurrentBankAccount(Math.random()*9000,9000, customer.getId());
                    bankAccountService.saveSavingBankAccount(Math.random()*120000,5.5, customer.getId());


                } catch (CustomerNotFoundException e) {
                    e.printStackTrace();
                }
            });
            List<BankAccountDTO> bankAccounts = bankAccountService.bankAccountList();

            for(BankAccountDTO bankAccount : bankAccounts){
                for(int i = 0 ; i < 10 ; i++){
                    String accountId;
                    if(bankAccount instanceof SavingBankAccountDTO){
                        accountId = ((SavingBankAccountDTO) bankAccount).getId();
                    }else {
                        accountId = ((CurrentBankAccountDTO) bankAccount).getId();
                    }
                    bankAccountService.credit(accountId,10000+Math.random()*120000,"Credit");
                    bankAccountService.debit(accountId,1000+Math.random()*9000,"Debit");
                }

            }


        };
    }


    // @Bean
    CommandLineRunner start (CustumerRepository customerRepository, // C’est un outil Spring Boot qui s’exécute automatiquement au démarrage de l’application️ ---> donc ce code tourne une seule fois au lancement
                             BankAccountRepository bankAccountRepository, // Les paramètres (Injection automatique)
                             BankAccountOperationRepository accountOperationRepository) {
        return args -> {

            Stream.of("Hassan", "Yassine", "Aicha").forEach(name->{ // créer une liste des noms
                Customer customer=new Customer();
                customer.setName(name);
                customer.setEmail(name+"@gmail.com");
                customerRepository.save(customer);
            });

            customerRepository.findAll().forEach(cust->{
                CurrentAccount currentAccount = new CurrentAccount();
                currentAccount.setId (UUID.randomUUID().toString());
                currentAccount.setBalance (Math.random()*90000);
                currentAccount.setCreatedAt (new Date());
                currentAccount.setStatus (AccountStatus.CREATED);
                currentAccount.setCustomer (cust);
                currentAccount.setOverDraft (9000);
                bankAccountRepository.save(currentAccount);

                SavingAccount savingAccount = new SavingAccount();
                savingAccount.setId (UUID.randomUUID().toString());
                savingAccount.setBalance (Math.random()*90000);
                savingAccount.setCreatedAt (new Date());
                savingAccount.setStatus (AccountStatus.CREATED);
                savingAccount.setCustomer (cust);
                savingAccount.setInterestRate(5.5);
                bankAccountRepository.save(savingAccount);
            });

            bankAccountRepository.findAll().forEach( acc->{
                for(int i=0; i<5; i++){
                    AccountOperation accountOperation = new AccountOperation();
                    accountOperation.setOperationDate(new Date());
                    accountOperation.setAmount(Math.random()*120000);
                    accountOperation.setType(Math.random()>0.5?OperationType.DEBIT: OperationType.CREDIT);
                    accountOperation.setBankAccount(acc);
                    accountOperationRepository.save(accountOperation);
                }
            });

        };
    }

}
