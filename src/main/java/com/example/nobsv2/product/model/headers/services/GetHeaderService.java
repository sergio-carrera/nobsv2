package com.example.nobsv2.product.model.headers.services;


import com.example.nobsv2.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class GetHeaderService implements Query<String, String> {
    @Override
    public ResponseEntity<String> execute(String region) {

        if (region.equals("US")) {
            return ResponseEntity.ok("BALD EAGLE FREEDOM");
        }
        if (region.equals("CAN")) {
            return ResponseEntity.ok("MAPLE SYRUP");
        }
        return ResponseEntity.badRequest().body("Region not supported");
    }
}
