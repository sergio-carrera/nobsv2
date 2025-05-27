package com.example.nobsv2.mappings.services;

import com.example.nobsv2.Query;
import com.example.nobsv2.mappings.exceptions.CustomerNotFoundException;
import com.example.nobsv2.mappings.model.Customer;
import com.example.nobsv2.mappings.repository.CustomerRepository;
import com.example.nobsv2.product.services.GetProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GetCustomerService implements Query<Integer, Customer> {

    private final CustomerRepository customerRepository;

    private static final Logger logger = LoggerFactory.getLogger(GetProductService.class);

    public GetCustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public ResponseEntity<Customer> execute(Integer id) {
        logger.info("Executing " + getClass() + " id: " + id);
        Optional<Customer> response = customerRepository.findById(id);
        if (response.isPresent()) {
            return ResponseEntity.ok(response.get());
        }
        throw new CustomerNotFoundException();
    }
}
