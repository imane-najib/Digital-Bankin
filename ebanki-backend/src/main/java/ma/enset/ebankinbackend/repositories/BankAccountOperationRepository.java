package ma.enset.ebankinbackend.repositories;

import ma.enset.ebankinbackend.dtos.AccountOperationDTO;
import ma.enset.ebankinbackend.entities.AccountOperation;
import ma.enset.ebankinbackend.entities.BankAccount;
import ma.enset.ebankinbackend.entities.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BankAccountOperationRepository extends JpaRepository<AccountOperation,Long> {
    List<AccountOperation> findByBankAccountId(String accountId);
    Page<AccountOperation> findByBankAccountId(String accountId, Pageable pageable);
}
