package ma.enset.ebankinbackend.services;

import ma.enset.ebankinbackend.entities.BankAccount;
import ma.enset.ebankinbackend.entities.CurrentAccount;
import ma.enset.ebankinbackend.entities.Customer;
import ma.enset.ebankinbackend.entities.SavingAccount;
import ma.enset.ebankinbackend.exceptions.BalanceNotSufficientException;
import ma.enset.ebankinbackend.exceptions.BankAccountNotFoundException;
import ma.enset.ebankinbackend.exceptions.CustomerNotFoundException;

import java.util.List;

public interface BankAccountService {
     Customer saveCustomer(Customer customer);
     CurrentAccount saveCurrentBankAccount(double intialBalance, double overDraft, Long customerId) throws CustomerNotFoundException;
     SavingAccount saveSavingBankAccount(double intialBalance, double interestRate, Long customerId) throws CustomerNotFoundException;
     List<Customer> listCustomers();
     BankAccount getBankAccounts(String accountId) throws BankAccountNotFoundException;
     void debit(String accountId,double amount,String description) throws BankAccountNotFoundException, BalanceNotSufficientException;
     void credit(String accountId,double amount,String description) throws BankAccountNotFoundException;
     void transfer(String accountIdSource,String accountIdDestination,double amount) throws BankAccountNotFoundException, BalanceNotSufficientException;
}
