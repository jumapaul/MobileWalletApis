package com.comulynx.wallet.rest.api.mappers;

import com.comulynx.wallet.rest.api.dtos.CreateCustomerRequest;
import com.comulynx.wallet.rest.api.dtos.TransferMoneyRequest;
import com.comulynx.wallet.rest.api.model.Account;
import com.comulynx.wallet.rest.api.model.Customer;
import com.comulynx.wallet.rest.api.model.Transaction;
import org.springframework.stereotype.Service;

@Service
public class CustomerMapper {

    public Customer toCustomer(CreateCustomerRequest request, String hashedPin) {
        Customer customer = new Customer();
        customer.setCustomerId(request.customerId());
        customer.setFirstName(request.firstName());
        customer.setLastName(request.lastName());
        customer.setEmail(request.email());
        customer.setPin(hashedPin);
        return customer;
    }

    public Account toAccount(String accountNumber, String customerId) {
        Account account = new Account();

        account.setAccountNo(accountNumber);
        account.setCustomerId(customerId);
        account.setBalance(0.0);
        return account;
    }

    public Transaction toTransaction(
            String transactionId,
            String customerId,
            String accountNumber,
            double amount,
            double accountBalance,
            String type
    ) {
        Transaction transaction = new Transaction();
        transaction.setTransactionId(transactionId);
        transaction.setCustomerId(customerId);
        transaction.setAccountNo(accountNumber);
        transaction.setAmount(amount);
        transaction.setBalance(accountBalance);
        transaction.setTransactionType("FT");
        transaction.setDebitOrCredit(type);

        return transaction;
    }
}
