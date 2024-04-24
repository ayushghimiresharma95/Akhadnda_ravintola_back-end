package com.ayush.OrderService.OrderService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ayush.OrderService.OrderService.dto.OrderRequest;
import com.ayush.OrderService.OrderService.repository.OrderRepository;
import com.ayush.OrderService.OrderService.service.OrderService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {
    private OrderRepository orderRepository ;

    @Autowired
    private OrderService orderService ;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createOrder (@RequestBody OrderRequest orderRequest){
        log.info(orderRequest.toString());
        orderService.placeOrder(orderRequest);
        return "Order Placed Successfully" ;
    }
}
