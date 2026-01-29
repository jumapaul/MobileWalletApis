package com.comulynx.wallet.rest.api.service_impl;

import com.comulynx.wallet.rest.api.AppUtilities;
import com.comulynx.wallet.rest.api.dtos.AccountBalanceRequest;
import com.comulynx.wallet.rest.api.dtos.AccountBalanceResponse;
import com.comulynx.wallet.rest.api.dtos.GetAccountRequest;
import com.comulynx.wallet.rest.api.exception.InternalErrorException;
import com.comulynx.wallet.rest.api.exception.ResourceNotFoundException;
import com.comulynx.wallet.rest.api.model.Account;
import com.comulynx.wallet.rest.api.repository.AccountRepository;
import com.comulynx.wallet.rest.api.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public List<Account> getAllAccount() {
        return accountRepository.findAll();
    }

    @Override
    public AccountBalanceResponse getAccountBalanceByCustomerIdAndAccountNo(AccountBalanceRequest request) throws InternalErrorException {
        try {
            // TODO : Add logic to find Account balance by CustomerId - Done
            Account account = accountRepository.findAccountByCustomerId(request.customerId()).orElseThrow(() ->
                    new ResourceNotFoundException("Customer with id " + request.customerId() + " not found")
            );

            return new AccountBalanceResponse(
                    account.getBalance(),
                    account.getAccountNo()
            );
        } catch (Exception exception) {
            logger.error("Exception {}", AppUtilities.getExceptionStacktrace(exception));
            throw new InternalErrorException(exception.getMessage());
        }
    }

    @Override
    public Account findAccountByCustomerIdOrAccountNo(GetAccountRequest request) {
        try {
            return accountRepository.findAccountByCustomerIdOrAccountNo(request.customerId(), request.accountNo()).orElseThrow(() ->
                    new ResourceNotFoundException("Account not found")
            );
        } catch (RuntimeException e) {
            logger.error("Exception {}", AppUtilities.getExceptionStacktrace(e));
            throw new InternalErrorException(e.getMessage());
        }
    }

    @Override
    public Account findAccountByCustomerIdAndAccountNo(GetAccountRequest request) {
        try {
            return accountRepository.findAccountByCustomerIdAndAccountNo(request.customerId(), request.accountNo()).orElseThrow(() ->
                    new ResourceNotFoundException("Account not found")
            );
        } catch (RuntimeException e) {
            logger.error("Exception {}", AppUtilities.getExceptionStacktrace(e));
            throw new InternalErrorException(e.getMessage());
        }
    }
}
