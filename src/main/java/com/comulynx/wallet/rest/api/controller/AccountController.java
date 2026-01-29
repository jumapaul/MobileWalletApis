package com.comulynx.wallet.rest.api.controller;

import java.util.List;

import com.comulynx.wallet.rest.api.dtos.AccountBalanceRequest;
import com.comulynx.wallet.rest.api.dtos.AccountBalanceResponse;
import com.comulynx.wallet.rest.api.dtos.GetAccountRequest;
import com.comulynx.wallet.rest.api.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.comulynx.wallet.rest.api.AppUtilities;
import com.comulynx.wallet.rest.api.exception.ResourceNotFoundException;
import com.comulynx.wallet.rest.api.model.Account;
import com.comulynx.wallet.rest.api.repository.AccountRepository;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/")
    public List<Account> getAllAccount() {
        return accountService.getAllAccount();
    }


    @PostMapping("/balance")
    public ResponseEntity<AccountBalanceResponse> getAccountBalanceByCustomerIdAndAccountNo(
            @RequestBody AccountBalanceRequest request) {

        AccountBalanceResponse response = accountService.getAccountBalanceByCustomerIdAndAccountNo(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping("getByIdOrAccountNumber")
    public ResponseEntity<Account> findAccountByCustomerIdOrAccountNo(@RequestBody GetAccountRequest request) {

        return ResponseEntity.ok(accountService.findAccountByCustomerIdOrAccountNo(request));
    }

    @PostMapping("getByIdAndAccountNumber")
    public ResponseEntity<Account> findAccountByCustomerIdAndAccountNo(@RequestBody GetAccountRequest request) {

        return ResponseEntity.ok(accountService.findAccountByCustomerIdAndAccountNo(request));
    }
}
