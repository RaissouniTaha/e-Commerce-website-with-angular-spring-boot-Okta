package com.raissapp.estore.ecommerce.service;

import com.raissapp.estore.ecommerce.dto.Purchase;
import com.raissapp.estore.ecommerce.dto.PurchaseResponse;

public interface CheckoutService {

	PurchaseResponse placeOrder(Purchase purchase);
	
}
