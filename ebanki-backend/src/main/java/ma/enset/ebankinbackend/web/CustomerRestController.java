package ma.enset.ebankinbackend.web;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.enset.ebankinbackend.dtos.CustomerDTO;
import ma.enset.ebankinbackend.entities.Customer;
import ma.enset.ebankinbackend.exceptions.CustomerNotFoundException;
import ma.enset.ebankinbackend.services.BankAccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin("*")
public class CustomerRestController {
    private BankAccountService bankAccountService;

    @GetMapping("/customers")
    public List<CustomerDTO> Customers() {
        return bankAccountService.listCustomers();
    }

    @GetMapping("/customers/search")
    public List<CustomerDTO> SearchCustomers(@RequestParam(name = "keyword",defaultValue = "")String keyword) {
        return bankAccountService.SearchCustomers("%"+keyword+"%");
    }

    @GetMapping("/customers/{id}")
    public CustomerDTO getCustomer(@PathVariable(name = "id") Long customerId) throws CustomerNotFoundException {
        return bankAccountService.getCustomer(customerId);
    }

    @PostMapping("/customers")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO) {
        return bankAccountService.saveCustomer(customerDTO);
    }

    @PutMapping("/customers/{id}")
    public CustomerDTO updateCustomer(@PathVariable(name = "id") Long customerId, @RequestBody CustomerDTO customerDTO) {
        customerDTO.setId(customerId);
        return  bankAccountService.updateCustomer(customerDTO);
    }
    @DeleteMapping("/customers/{idCustomer}")
    public void deleteCustomer(@PathVariable Long idCustomer){
        bankAccountService.deleteCustomer(idCustomer);
    }
}
