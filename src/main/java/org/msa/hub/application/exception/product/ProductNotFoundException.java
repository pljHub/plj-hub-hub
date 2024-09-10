package org.msa.hub.application.exception.product;

import org.msa.hub.global.exception.PljHubException;
import org.msa.hub.global.exception.domain.ProductErrorCode;

public class ProductNotFoundException extends PljHubException {
    public ProductNotFoundException(){
        super(ProductErrorCode.PRODUCT_NOT_FOUND);
    }
}
