package ma.enset.ebankinbackend.repositories;

import ma.enset.ebankinbackend.entities.BankAccount;
import ma.enset.ebankinbackend.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount,String> {
}
