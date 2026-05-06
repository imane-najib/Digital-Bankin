package ma.enset.ebankinbackend.services;

import ma.enset.ebankinbackend.dtos.*;
import ma.enset.ebankinbackend.entities.BankAccount;
import ma.enset.ebankinbackend.entities.CurrentAccount;
import ma.enset.ebankinbackend.entities.Customer;
import ma.enset.ebankinbackend.entities.SavingAccount;
import ma.enset.ebankinbackend.exceptions.BalanceNotSufficientException;
import ma.enset.ebankinbackend.exceptions.BankAccountNotFoundException;
import ma.enset.ebankinbackend.exceptions.CustomerNotFoundException;

import java.util.List;

public interface BankAccountService {
     CustomerDTO saveCustomer(CustomerDTO customerDTO);
     CurrentBankAccountDTO saveCurrentBankAccount(double intialBalance, double overDraft, Long customerId) throws CustomerNotFoundException;
     SavingBankAccountDTO saveSavingBankAccount(double intialBalance, double interestRate, Long customerId) throws CustomerNotFoundException;
     List<CustomerDTO> listCustomers();
     BankAccountDTO getBankAccounts(String accountId) throws BankAccountNotFoundException;
     void debit(String accountId,double amount,String description) throws BankAccountNotFoundException, BalanceNotSufficientException;
     void credit(String accountId,double amount,String description) throws BankAccountNotFoundException;
     void transfer(String accountIdSource,String accountIdDestination,double amount) throws BankAccountNotFoundException, BalanceNotSufficientException;

     List<BankAccountDTO> bankAccountList();

     CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException;

     CustomerDTO updateCustomer(CustomerDTO customerDTO);

     void deleteCustomer(Long custumerId);

     List<AccountOperationDTO> accountHistory(String accountId);

     AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException;


    List<CustomerDTO> SearchCustomers(String keyword);
}
