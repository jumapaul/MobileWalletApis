package com.comulynx.wallet.rest.api.service;

import com.comulynx.wallet.rest.api.dtos.MiniStatementRequest;
import com.comulynx.wallet.rest.api.dtos.TransactionRequest;
import com.comulynx.wallet.rest.api.dtos.TransferFundsResponse;
import com.comulynx.wallet.rest.api.dtos.TransferMoneyRequest;
import com.comulynx.wallet.rest.api.model.Transaction;

import java.util.List;
import java.util.Optional;

public interface TransactionService {
    List<Transaction> getAllTransactions();

    List<Transaction> getLast100TransactionsByCustomerId(TransactionRequest request);

    List<Transaction> getMiniStatementByCustomerIdAndAccountNo(MiniStatementRequest request);

    TransferFundsResponse doSendMoneyTransaction(TransferMoneyRequest request);

    List<Transaction> findTransactionsByCustomerId(String customerId);

    List<Transaction> findTransactionsByTransactionId(String transactionId);

    List<Transaction> findTransactionsByCustomerIdOrTransactionId(String transactionId, String customerId);
}
