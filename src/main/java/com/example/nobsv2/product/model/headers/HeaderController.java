package com.example.nobsv2.product.model.headers;

import com.example.nobsv2.product.model.Product;
import com.example.nobsv2.product.model.headers.services.GetHeaderService;
import com.example.nobsv2.product.model.headers.services.GetProductByHeaderService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HeaderController {

    private final GetHeaderService getHeaderService;
    private final GetProductByHeaderService getProductByHeaderService;

    public HeaderController(GetHeaderService getHeaderService, GetProductByHeaderService getProductByHeaderService) {
        this.getHeaderService = getHeaderService;
        this.getProductByHeaderService = getProductByHeaderService;
    }

    @GetMapping("/header")
    public ResponseEntity<String> getRegionalResponse(@RequestHeader(required = false, defaultValue = "US") String region) {
        return getHeaderService.execute(region);
    }

    @GetMapping(value = "/header/product", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Product> getProduct() {
        return getProductByHeaderService.execute(null);
    }
}
