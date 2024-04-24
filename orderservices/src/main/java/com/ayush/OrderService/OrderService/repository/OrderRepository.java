package com.ayush.OrderService.OrderService.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ayush.OrderService.OrderService.model.Order;

public interface OrderRepository extends JpaRepository<Order,Long> {
    
}
