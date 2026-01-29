package com.comulynx.wallet.rest.api.controller;

import java.util.List;

import com.comulynx.wallet.rest.api.dtos.*;
import com.comulynx.wallet.rest.api.service.CustomerService;
import jakarta.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.comulynx.wallet.rest.api.AppUtilities;
import com.comulynx.wallet.rest.api.model.Account;
import com.comulynx.wallet.rest.api.model.Customer;
import com.comulynx.wallet.rest.api.repository.AccountRepository;
import com.comulynx.wallet.rest.api.repository.CustomerRepository;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping("/")
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomer();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> customerLogin(@RequestBody LoginRequest request) {

        LoginResponse response = customerService.customerLogin(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/")
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody CreateCustomerRequest request) {
        return ResponseEntity.ok(customerService.createCustomer(request));
    }

    @DeleteMapping
    public ResponseEntity<ModificationResponse> deleteCustomerByCustomerId(
            @RequestParam("customerId") String customerId) {
        return ResponseEntity.ok(customerService.deleteCustomerByCustomerId(customerId));
    }

    @PatchMapping("/update")
    public ResponseEntity<ModificationResponse> updateCustomerByCustomerId(
            @RequestBody UpdateCustomerRequest request) {
        return ResponseEntity.ok(customerService.updateCustomerByCustomerId(request));
    }

    @GetMapping("/gmail")
    public ResponseEntity<List<Customer>> findAllCustomersWhoseEmailContainsGmail() {
        return ResponseEntity.ok(customerService.findAllCustomersWhoseEmailContainsGmail());
    }
}
