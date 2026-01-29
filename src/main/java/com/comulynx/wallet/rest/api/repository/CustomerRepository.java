package com.comulynx.wallet.rest.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.comulynx.wallet.rest.api.model.Customer;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByCustomerId(String customerId);

    boolean existsByCustomerIdOrEmail(String customerId, String email);

    // TODO : Implement the query and function below to delete a customer using Customer Id
    @Transactional
     @Modifying
     @Query("delete from Customer t where t.customerId = :customerId")
     int deleteCustomerByCustomerId(@Param("customerId") String customerId);

    // TODO : Implement the query and function below to update customer firstName using Customer Id
    @Transactional
     @Modifying
     @Query("update Customer c set c.firstName = :firstName where c.customerId = :customerId")
     int updateCustomerByCustomerId(
             @Param("firstName") String firstName,
             @Param("customerId") String customerId);

    // TODO : Implement the query and function below and to return all customers whose Email contains  'gmail'
     @Query("select c from Customer c where c.email like '%gmail%'")
     List<Customer> findAllCustomersWhoseEmailContainsGmail();
}
