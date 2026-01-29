package com.comulynx.wallet.rest.api.service_impl;

import com.comulynx.wallet.rest.api.AppUtilities;
import com.comulynx.wallet.rest.api.dtos.MiniStatementRequest;
import com.comulynx.wallet.rest.api.dtos.TransactionRequest;
import com.comulynx.wallet.rest.api.dtos.TransferFundsResponse;
import com.comulynx.wallet.rest.api.dtos.TransferMoneyRequest;
import com.comulynx.wallet.rest.api.exception.BadRequestException;
import com.comulynx.wallet.rest.api.exception.InternalErrorException;
import com.comulynx.wallet.rest.api.exception.ResourceNotFoundException;
import com.comulynx.wallet.rest.api.exception.UnAuthorizedException;
import com.comulynx.wallet.rest.api.mappers.CustomerMapper;
import com.comulynx.wallet.rest.api.model.Account;
import com.comulynx.wallet.rest.api.model.Customer;
import com.comulynx.wallet.rest.api.model.Transaction;
import com.comulynx.wallet.rest.api.repository.AccountRepository;
import com.comulynx.wallet.rest.api.repository.CustomerRepository;
import com.comulynx.wallet.rest.api.repository.TransactionRepository;
import com.comulynx.wallet.rest.api.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class TransactionServiceImpl implements TransactionService {

    private static final Logger log = LoggerFactory.getLogger(TransactionServiceImpl.class);

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CustomerMapper mapper;

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    public List<Transaction> getLast100TransactionsByCustomerId(TransactionRequest request) {
        try {

            // TODO : Add login here to get Last 100 Transactions By CustomerId - Done
            Customer customer = customerRepository.findByCustomerId(request.customerId()).orElseThrow(() ->
                    new ResourceNotFoundException("Customer with id " + request.customerId() + " not found")
            );

            if (!Objects.equals(request.pin(), customer.getPin()))
                throw new UnAuthorizedException("Invalid credentials");

            List<Transaction> response = transactionRepository.findTop100ByCustomerIdOrderByIdDesc(request.customerId());
            log.info("--------------->Length is: " + response.size());
            return response;
        } catch (RuntimeException e) {
            log.info("Exception {}", AppUtilities.getExceptionStacktrace(e));
            throw new InternalErrorException(e.getMessage());
        }
    }

    @Override
    public TransferFundsResponse doSendMoneyTransaction(TransferMoneyRequest request) {
        try {
            Random rand = new Random();
            Account senderAccount = accountRepository.findAccountByAccountNo(request.accountFrom())
                    .orElseThrow(() -> new ResourceNotFoundException("Account " + request.accountFrom() + " NOT found for this"));

            Account beneficiaryAccount = accountRepository.findAccountByAccountNo(request.accountTo())
                    .orElseThrow(() -> new ResourceNotFoundException("Account " + request.accountTo() + " NOT found for this"));


            if (senderAccount.getBalance() < request.amount()) throw new BadRequestException("Insufficient balance");
            String debitTransactionId = "TRN" + rand.nextInt(100000) + 1000;
            String creditTransactionId = "TRN" + rand.nextInt(100000) + 1001;

            //Debit
            Transaction debitTransaction = mapper.toTransaction(
                    debitTransactionId,
                    request.customerId(),
                    senderAccount.getAccountNo(),
                    request.amount(),
                    senderAccount.getBalance() - request.amount(),
                    "Debit"
            );
            transactionRepository.save(debitTransaction);

            //Credit transaction
            Transaction creditTransaction = mapper.toTransaction(
                    creditTransactionId,
                    beneficiaryAccount.getCustomerId(),
                    beneficiaryAccount.getAccountNo(),
                    request.amount(),
                    beneficiaryAccount.getBalance() + request.amount(),
                    "Credit"
            );
            transactionRepository.save(creditTransaction);
            return new TransferFundsResponse(
                    HttpStatus.OK, "Transaction successful"
            );
        } catch (RuntimeException e) {
            log.info("Exception {}", AppUtilities.getExceptionStacktrace(e));
            throw new InternalErrorException(e.getMessage());
        }
    }

    @Override
    public List<Transaction> getMiniStatementByCustomerIdAndAccountNo(MiniStatementRequest request) {
        try {
            Customer customer = customerRepository.findByCustomerId(request.customerId()).orElseThrow(() ->
                    new ResourceNotFoundException("Customer with id " + request.customerId() + " not found")
            );

            if (!Objects.equals(request.pin(), customer.getPin()))
                throw new UnAuthorizedException("Invalid credentials");
            return transactionRepository.findTop5ByCustomerIdAndAccountNoOrderByIdDesc(request.customerId(),
                    request.accountNumber(), PageRequest.of(0, 5)).orElse(Collections.emptyList());
        } catch (RuntimeException exception) {
            log.info("Exception {}", AppUtilities.getExceptionStacktrace(exception));
            throw new InternalErrorException(exception.getMessage());
        }
    }

    @Override
    public List<Transaction> findTransactionsByCustomerId(String customerId) {
        try {
            return transactionRepository.findTransactionsByCustomerId(customerId).orElse(Collections.emptyList());
        } catch (RuntimeException exception) {
            log.info("Exception {}", AppUtilities.getExceptionStacktrace(exception));
            throw new InternalErrorException(exception.getMessage());
        }
    }

    @Override
    public List<Transaction> findTransactionsByTransactionId(String transactionId) {
        try {
            return transactionRepository.findTransactionsByTransactionId(transactionId).orElse(Collections.emptyList());
        } catch (RuntimeException exception) {
            log.info("Exception {}", AppUtilities.getExceptionStacktrace(exception));
            throw new InternalErrorException(exception.getMessage());
        }
    }

    @Override
    public List<Transaction> findTransactionsByCustomerIdOrTransactionId(String transactionId, String customerId) {
        try {
            return transactionRepository.findTransactionsByCustomerIdOrTransactionId(transactionId, customerId).orElse(Collections.emptyList());
        } catch (RuntimeException exception) {
            log.info("Exception {}", AppUtilities.getExceptionStacktrace(exception));
            throw new InternalErrorException(exception.getMessage());
        }
    }
}
