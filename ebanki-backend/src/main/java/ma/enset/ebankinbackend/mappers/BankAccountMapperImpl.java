package ma.enset.ebankinbackend.mappers;

import ma.enset.ebankinbackend.dtos.AccountOperationDTO;
import ma.enset.ebankinbackend.dtos.CurrentBankAccountDTO;
import ma.enset.ebankinbackend.dtos.CustomerDTO;
import ma.enset.ebankinbackend.dtos.SavingBankAccountDTO;
import ma.enset.ebankinbackend.entities.AccountOperation;
import ma.enset.ebankinbackend.entities.CurrentAccount;
import ma.enset.ebankinbackend.entities.Customer;
import ma.enset.ebankinbackend.entities.SavingAccount;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;


@Service
public class BankAccountMapperImpl {

    public CustomerDTO fromCustomer(Customer customer){
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);
        return customerDTO;
    }

    public Customer fromCustomer(CustomerDTO customerDTO){
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
        return customer;
    }
    public SavingBankAccountDTO fromSavingAccount (SavingAccount savingAccount) {
        SavingBankAccountDTO savingAccountDTO=new SavingBankAccountDTO();
        BeanUtils.copyProperties (savingAccount, savingAccountDTO);
        savingAccountDTO.setCustomerDTO (fromCustomer (savingAccount.getCustomer()));
        savingAccountDTO.setType(savingAccount.getClass().getSimpleName());
        return savingAccountDTO;
    }
    public SavingAccount fromSavingAccountDTO (SavingBankAccountDTO savingAccountDTO) {
        SavingAccount savingAccount=new SavingAccount();
        BeanUtils.copyProperties (savingAccountDTO, savingAccount);
        savingAccount.setCustomer (fromCustomer (savingAccountDTO.getCustomerDTO()));
        return savingAccount;
    }
    public CurrentBankAccountDTO fromCurrentAccount (CurrentAccount currentAccount){
        CurrentBankAccountDTO currentAccountDTO=new CurrentBankAccountDTO();
        BeanUtils.copyProperties (currentAccount, currentAccountDTO);
        currentAccountDTO.setCustomerDTO (fromCustomer (currentAccount.getCustomer()));
        currentAccountDTO.setType(currentAccount.getClass().getSimpleName());
        return currentAccountDTO;
    }
    public CurrentAccount fromCurrentAccountDTO (CurrentBankAccountDTO currentAccountDTO){
        CurrentAccount currentAccount=new CurrentAccount();
        BeanUtils.copyProperties (currentAccountDTO, currentAccount);
        currentAccount.setCustomer (fromCustomer (currentAccountDTO.getCustomerDTO()));
        return currentAccount;
    }
    public AccountOperation fromAccountOperationDTO(AccountOperationDTO accountOperationDTO){

        AccountOperation accountOperation = new AccountOperation();
        BeanUtils.copyProperties(accountOperationDTO, accountOperation);
        return accountOperation;
    }
    public AccountOperationDTO fromAccountOperation(AccountOperation accountOperation){

        AccountOperationDTO accountOperationDTO = new AccountOperationDTO();
        BeanUtils.copyProperties(accountOperation, accountOperationDTO);
        return accountOperationDTO;
    }
}
