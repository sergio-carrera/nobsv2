package com.example.nobsv2.mappings.services;

import com.example.nobsv2.Query;
import com.example.nobsv2.mappings.exceptions.CustomerNotFoundException;
import com.example.nobsv2.mappings.model.Customer;
import com.example.nobsv2.mappings.repository.CustomerRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GetCustomerService implements Query<Integer, Customer> {

    private CustomerRepository customerRepository;

    public GetCustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public ResponseEntity<Customer> execute(Integer id) {
        Optional<Customer> response = customerRepository.findById(id);
        if (response.isPresent()) {
            return ResponseEntity.ok(response.get());
        }
        throw new CustomerNotFoundException();
    }
}
