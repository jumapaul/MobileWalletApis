package com.comulynx.wallet.rest.api.service_impl;

import com.comulynx.wallet.rest.api.AppUtilities;
import com.comulynx.wallet.rest.api.dtos.*;
import com.comulynx.wallet.rest.api.exception.BadRequestException;
import com.comulynx.wallet.rest.api.exception.InternalErrorException;
import com.comulynx.wallet.rest.api.exception.ResourceNotFoundException;
import com.comulynx.wallet.rest.api.exception.UnAuthorizedException;
import com.comulynx.wallet.rest.api.mappers.CustomerMapper;
import com.comulynx.wallet.rest.api.model.Account;
import com.comulynx.wallet.rest.api.model.Customer;
import com.comulynx.wallet.rest.api.repository.AccountRepository;
import com.comulynx.wallet.rest.api.repository.CustomerRepository;
import com.comulynx.wallet.rest.api.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@Service
public class CustomerServiceImpl implements CustomerService {

    private static final Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public List<Customer> getAllCustomer() {
        return customerRepository.findAll();
    }

    @Override
    public LoginResponse customerLogin(LoginRequest request) {

        // TODO : Add Customer login logic here. Login using customerId and
        // PIN
        // NB: We are using plain text password for testing Customer login
        // If customerId doesn't exists throw an error "Customer does not exist"
        // If password do not match throw an error "Invalid credentials"

        //TODO : Return a JSON object with the following after successful login
        //Customer Name, Customer ID, email and Customer Account

        try {
            Customer customer = customerRepository.findByCustomerId(request.customerId()).orElseThrow(() ->
                    new ResourceNotFoundException("Customer does not exist")
            );

            if (!Objects.equals(request.pin(), customer.getPin()))
                throw new UnAuthorizedException("Invalid credentials");

            Account account = accountRepository.findAccountByCustomerId(request.customerId()).orElseThrow(() ->
                    new ResourceNotFoundException("User account not found")
            );

            String customerName = customer.getFirstName() + " " + customer.getLastName();
            return new LoginResponse(
                    customerName,
                    customer.getCustomerId(),
                    customer.getEmail(),
                    account.getAccountNo()
            );
        } catch (Exception exception) {
            log.info("Exception {}", AppUtilities.getExceptionStacktrace(exception));
            throw new InternalErrorException(exception.getMessage());
        }
    }

    @Transactional
    @Override
    public Customer createCustomer(CreateCustomerRequest request) {
        // TODO : Add logic to Hash Customer PIN here
        //  : Add logic to check if Customer with provided email, or
        // customerId exists. If exists, throw a Customer with [?] exists
        // Exception.
        try {
            boolean exists = customerRepository.existsByCustomerIdOrEmail(request.customerId(), request.email());

            if (exists) throw new BadRequestException("CustomerId or email exists");

            String hashedPin = encodePin(request.pin());
            Customer customer = customerMapper.toCustomer(request, hashedPin);

            String accountNo = generateAccountNo(request.customerId());

            Account account = customerMapper.toAccount(accountNo, request.customerId());

            accountRepository.save(account);
            customerRepository.save(customer);
            return null;
        } catch (Exception e) {
            log.info("Exception {}", AppUtilities.getExceptionStacktrace(e));
            throw new InternalErrorException(e.getMessage());
        }
    }

    @Override
    public ModificationResponse deleteCustomerByCustomerId(String customerId) {
        try {
            customerRepository.findByCustomerId(customerId).orElseThrow(() ->
                    new ResourceNotFoundException("Customer not found")
            );
            int response = customerRepository.deleteCustomerByCustomerId(customerId);
            String message;

            if (response == 1) {
                message = "Customer successfully deleted";
            } else {
                message = "Customer deletion failed";
            }
            return new ModificationResponse(
                    message
            );
        } catch (RuntimeException e) {
            log.info("Exception {}", AppUtilities.getExceptionStacktrace(e));
            throw new InternalErrorException(e.getMessage());
        }
    }

    @Override
    public ModificationResponse updateCustomerByCustomerId(UpdateCustomerRequest request) {
        try {
            customerRepository.findByCustomerId(request.customerId()).orElseThrow(() ->
                    new ResourceNotFoundException("Customer not found")
            );
            int response = customerRepository.updateCustomerByCustomerId(request.firstName(), request.customerId());

            String message;

            if (response == 1) {
                message = "Customer firstname successfully deleted";
            } else {
                message = "Customer deletion failed";
            }
            return new ModificationResponse(
                    message
            );
        } catch (RuntimeException exception) {
            log.info("Exception {}", AppUtilities.getExceptionStacktrace(exception));
            throw new InternalErrorException(exception.getMessage());
        }
    }

    @Override
    public List<Customer> findAllCustomersWhoseEmailContainsGmail() {
        return customerRepository.findAllCustomersWhoseEmailContainsGmail();
    }

    private String encodePin(String pin) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(pin.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing pin", e);
        }
    }

    /**
     * Add required functionality
     * <p>
     * generate a random but unique Account No (NB: Account No should be unique
     * in your accounts table)
     */

    //Use timestamp and random number between 0-1000000 and hashedId
    private String generateAccountNo(String customerId) {
        // TODO : Add logic here - generate a random but unique Account No (NB:
        // Account No should be unique in the accounts table)
        Random random = new Random();
        String accountNumber;

        do {
            long timestamp = System.currentTimeMillis() % 100000; //5digits
            int randomPart = random.nextInt(1000); // 3 digits
            int hashedId = customerId.hashCode() % 1000; //2 digits
            accountNumber = String.format("%05d%03d%02d", timestamp, randomPart, hashedId);
        } while (accountRepository.existsByAccountNo(accountNumber));

        return accountNumber;
    }
}
