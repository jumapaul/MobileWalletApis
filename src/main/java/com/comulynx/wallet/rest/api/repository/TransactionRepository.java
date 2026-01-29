package com.comulynx.wallet.rest.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.comulynx.wallet.rest.api.model.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findTop100ByCustomerIdOrderByIdDesc(String customerId);

    Optional<List<Transaction>> findTransactionsByCustomerId(String customerId);

    Optional<List<Transaction>> findTransactionsByTransactionId(String transactionId);

    Optional<List<Transaction>> findTransactionsByCustomerIdOrTransactionId(String transactionId, String customerId);

    // TODO : Change below Query to return the last 5 transactions
    // TODO : Change below Query to use Named Parameters instead of indexed
    // parameters
    // TODO : Change below function to return Optional<List<Transaction>>


    @Query("select t from Transaction t where t.customerId = :customerId and t.accountNo = :accountNo order by t.id desc")
    Optional<List<Transaction>> findTop5ByCustomerIdAndAccountNoOrderByIdDesc(
            @Param("customerId") String customerId,
            @Param("accountNo") String accountNo,
            Pageable pageable
    );
}
