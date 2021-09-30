package com.raissapp.estore.ecommerce.service;

import java.util.Set;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.raissapp.estore.ecommerce.dao.CustomerRepository;
import com.raissapp.estore.ecommerce.dto.Purchase;
import com.raissapp.estore.ecommerce.dto.PurchaseResponse;
import com.raissapp.estore.ecommerce.entity.Customer;
import com.raissapp.estore.ecommerce.entity.Order;
import com.raissapp.estore.ecommerce.entity.OrderItem;


@Service
public class CheckoutServiceImpl implements CheckoutService {

	private CustomerRepository customerRepository;
	
	
	@Autowired
	public CheckoutServiceImpl(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}



	@Override
	@Transactional
	public PurchaseResponse placeOrder(Purchase purchase) {
		
		// retrieve the order info from dto
		Order order = purchase.getOrder();
		
		// generate tracking number
		String orderTrackingNumber = generateOrderTrackingNumber();
		order.setOrderTrackingNumber(orderTrackingNumber);
		
		// populate ordedr with orderItems
		Set<OrderItem> orderItems = purchase.getOrderItems();
		orderItems.forEach(item -> order.add(item));
		
		// populate order with billingAddress
		order.setBillingAddress(purchase.getBillingAddress());
		order.setShippingAddress(purchase.getShippingAddress());
		
		// populate customer with order
		Customer customer = purchase.getCustomer();
		
		//check if this is an existing customer
		String theEmail = customer.getEmail(); 
		Customer customerFromDB = customerRepository.findByEmail(theEmail);
		if(customerFromDB != null) {
			//we found them ... let's assign them accordingly
			customer = customerFromDB;
			
		}
		customer.add(order);
		
		// save  to the database
		customerRepository.save(customer);
		
		//return a reponse
		return new PurchaseResponse(orderTrackingNumber);
		
	}



	private String generateOrderTrackingNumber() {
		// generate a random UUID number (UUID version-4)
		
		return UUID.randomUUID().toString();
	}

	
	
}
