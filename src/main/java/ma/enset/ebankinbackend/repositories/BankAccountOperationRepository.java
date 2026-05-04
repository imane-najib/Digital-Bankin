package ma.enset.ebankinbackend.repositories;

import ma.enset.ebankinbackend.entities.AccountOperation;
import ma.enset.ebankinbackend.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountOperationRepository extends JpaRepository<AccountOperation,Long> {
}
