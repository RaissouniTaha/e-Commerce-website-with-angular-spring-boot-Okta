package com.raissapp.estore.ecommerce.dto;

import java.util.Set;

import com.raissapp.estore.ecommerce.entity.Address;
import com.raissapp.estore.ecommerce.entity.Customer;
import com.raissapp.estore.ecommerce.entity.Order;
import com.raissapp.estore.ecommerce.entity.OrderItem;

import lombok.Data;

@Data
public class Purchase {

	private Customer customer;
	
	private Address shippingAddress;
	
	private Address billingAddress;
	
	private Order order;
	
	private Set<OrderItem> orderItems;
	
}
