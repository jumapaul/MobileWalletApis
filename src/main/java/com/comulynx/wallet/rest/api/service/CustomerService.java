package com.comulynx.wallet.rest.api.service;

import com.comulynx.wallet.rest.api.dtos.*;
import com.comulynx.wallet.rest.api.model.Customer;
import com.google.gson.JsonObject;

import java.util.List;

public interface CustomerService {

    List<Customer> getAllCustomer();

    LoginResponse customerLogin(LoginRequest request);

    Customer createCustomer(CreateCustomerRequest request);

    ModificationResponse deleteCustomerByCustomerId(String customerId);

    ModificationResponse updateCustomerByCustomerId(UpdateCustomerRequest request);

    List<Customer> findAllCustomersWhoseEmailContainsGmail();
}
