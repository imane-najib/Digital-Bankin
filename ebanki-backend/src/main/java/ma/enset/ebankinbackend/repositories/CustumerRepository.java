package ma.enset.ebankinbackend.repositories;

import ma.enset.ebankinbackend.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustumerRepository extends JpaRepository<Customer,Long> {
    @Query("select c from Customer c where c.Name like :kw")
    List<Customer> searchCustomer(@Param("kw") String keyword);
}
