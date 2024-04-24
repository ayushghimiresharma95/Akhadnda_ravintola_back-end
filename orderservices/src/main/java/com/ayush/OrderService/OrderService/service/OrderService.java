package com.ayush.OrderService.OrderService.service;

import java.util.UUID;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Service;

import com.ayush.OrderService.OrderService.dto.InventoryResponse;
import com.ayush.OrderService.OrderService.dto.OrderLineItemsDto;
import com.ayush.OrderService.OrderService.dto.OrderRequest;
import com.ayush.OrderService.OrderService.model.Order;
import com.ayush.OrderService.OrderService.model.OrderLineItems;
import com.ayush.OrderService.OrderService.repository.OrderRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderService {

    private final  OrderRepository orderRepository ;
    private final WebClient.Builder webClientBuilder ;

    public void placeOrder(OrderRequest orderRequest){
        Order orders = new Order() ;
        orders.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems =  orderRequest.getOrderLineItemsList().stream().map(orderLineItemsDto -> MaptoGetCreatOrder(orderLineItemsDto)).toList();
        orders.setOrderLineItems(orderLineItems);

        List<String> barcodes = orders.getOrderLineItems().stream().map(order -> order.getBarcode()).toList() ;
        log.info(barcodes.toString());
        InventoryResponse[] inventoryResponses = webClientBuilder.build().get()
            .uri("http://INVENTORY-SERVICE/api/inventory",uriBuilder -> uriBuilder.queryParam("barcodes", barcodes).build())
            .retrieve()
            .bodyToMono(InventoryResponse[].class).block();

        boolean allItemsIn = Arrays.stream(inventoryResponses).allMatch(items -> items.getIsInstock());
        if (allItemsIn){
            orderRepository.save(orders) ;
        }
        else{
            throw new IllegalArgumentException("Product is not in Stock, please try again later") ;
        } 

        
    }

    public OrderLineItems MaptoGetCreatOrder(OrderLineItemsDto orderLineItemsDto){
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setName(orderLineItems.getName());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setBarcode(orderLineItemsDto.getBarcode());
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        return orderLineItems ;
        
}
}