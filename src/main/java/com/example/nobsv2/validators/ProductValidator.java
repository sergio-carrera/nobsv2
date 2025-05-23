package com.example.nobsv2.validators;

import com.example.nobsv2.product.exceptions.ErrorMessages;
import com.example.nobsv2.product.exceptions.ProductNotValidException;
import com.example.nobsv2.product.model.Product;
import com.mysql.cj.util.StringUtils;

public class ProductValidator {
    private ProductValidator() {
    }

    public static void execute(Product product) {
        if (StringUtils.isNullOrEmpty(product.getName())) {
            throw new ProductNotValidException(ErrorMessages.NAME_REQUIRED.getMessage());
        }

        if (product.getDescription().length() < 20) {
            throw new ProductNotValidException(ErrorMessages.DESCRIPTION_LENGTH.getMessage());
        }

        if (product.getPrice() == null || product.getPrice() < 0.00) {
            throw new ProductNotValidException(ErrorMessages.PRICE_CANNOT_BE_NEGATIVE.getMessage());
        }
    }
}
