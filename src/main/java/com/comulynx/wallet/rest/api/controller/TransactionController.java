package com.comulynx.wallet.rest.api.controller;

import java.util.List;

import com.comulynx.wallet.rest.api.dtos.MiniStatementRequest;
import com.comulynx.wallet.rest.api.dtos.TransactionRequest;
import com.comulynx.wallet.rest.api.dtos.TransferFundsResponse;
import com.comulynx.wallet.rest.api.dtos.TransferMoneyRequest;
import com.comulynx.wallet.rest.api.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.comulynx.wallet.rest.api.model.Transaction;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @GetMapping("/")
    public List<Transaction> getAllTransaction() {
        return transactionService.getAllTransactions();
    }


    @PostMapping(value = "/last-100-transactions")
    public ResponseEntity<List<Transaction>> getLast100TransactionsByCustomerId(@RequestBody TransactionRequest request) {
        return ResponseEntity.ok(transactionService.getLast100TransactionsByCustomerId(request));
    }

    @PostMapping(value = "/send-money")
    public ResponseEntity<TransferFundsResponse> doSendMoneyTransaction(@RequestBody @Valid TransferMoneyRequest request) {
        return ResponseEntity.ok(transactionService.doSendMoneyTransaction(request));
    }

    @PostMapping(value = "/mini-statement")
    public ResponseEntity<List<Transaction>> getMiniStatementByCustomerIdAndAccountNo(@RequestBody @Valid MiniStatementRequest request) {

        return ResponseEntity.ok(transactionService.getMiniStatementByCustomerIdAndAccountNo(request));
    }

    @GetMapping("/byCustomerId")
    public ResponseEntity<List<Transaction>> findTransactionsByCustomerId(
            @RequestParam(name = "customerId") String customerId) {
        return ResponseEntity.ok(transactionService.findTransactionsByCustomerId(customerId));
    }

    @GetMapping("/byTransactionId")
    public ResponseEntity<List<Transaction>> findTransactionsByTransactionId(
            @RequestParam(name = "transactionId") String transactionId) {
        return ResponseEntity.ok(transactionService.findTransactionsByTransactionId(transactionId));
    }

    @GetMapping("/byCustomerIdOrTransactionId")
    public ResponseEntity<List<Transaction>> findTransactionsByCustomerIdOrTransactionId(
            @RequestParam String transactionId,
            @RequestParam String customerId) {
        return ResponseEntity.ok(transactionService.findTransactionsByCustomerIdOrTransactionId(transactionId, customerId));
    }

}
