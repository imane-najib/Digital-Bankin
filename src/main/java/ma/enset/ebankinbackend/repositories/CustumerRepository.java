package ma.enset.ebankinbackend.repositories;

import ma.enset.ebankinbackend.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustumerRepository extends JpaRepository<Customer,Long> {
}
