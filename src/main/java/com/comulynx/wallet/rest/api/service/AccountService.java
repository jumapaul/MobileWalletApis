package com.comulynx.wallet.rest.api.service;

import com.comulynx.wallet.rest.api.dtos.AccountBalanceRequest;
import com.comulynx.wallet.rest.api.dtos.AccountBalanceResponse;
import com.comulynx.wallet.rest.api.dtos.GetAccountRequest;
import com.comulynx.wallet.rest.api.exception.InternalErrorException;
import com.comulynx.wallet.rest.api.exception.ResourceNotFoundException;
import com.comulynx.wallet.rest.api.model.Account;
import com.google.gson.JsonObject;

import java.util.List;
import java.util.Optional;

public interface AccountService {

    List<Account> getAllAccount();

    AccountBalanceResponse getAccountBalanceByCustomerIdAndAccountNo(AccountBalanceRequest request);

    Account findAccountByCustomerIdOrAccountNo(GetAccountRequest request);

    Account findAccountByCustomerIdAndAccountNo(GetAccountRequest request);
}
