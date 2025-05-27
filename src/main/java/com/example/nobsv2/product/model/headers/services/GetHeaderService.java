package com.example.nobsv2.product.model.headers.services;


import com.example.nobsv2.Query;
import com.example.nobsv2.product.services.GetProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class GetHeaderService implements Query<String, String> {

    private static final Logger logger = LoggerFactory.getLogger(GetProductService.class);

    @Override
    public ResponseEntity<String> execute(String region) {

        logger.info("Executing " + getClass() + " region: " + region);

        if (region.equals("US")) {
            return ResponseEntity.ok("BALD EAGLE FREEDOM");
        }
        if (region.equals("CAN")) {
            return ResponseEntity.ok("MAPLE SYRUP");
        }
        return ResponseEntity.badRequest().body("Region not supported");
    }
}
