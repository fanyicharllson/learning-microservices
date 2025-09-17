package org.charllson.orderservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.charllson.orderservice.dto.InventoryResponse;
import org.charllson.orderservice.dto.OrderLineItemsDto;
import org.charllson.orderservice.dto.OrderRequest;
import org.charllson.orderservice.model.Order;
import org.charllson.orderservice.model.OrderLineItems;
import org.charllson.orderservice.respository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final WebClient webClient;


    public void placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList().stream()
                .map(this::mapToDto).toList();

        order.setOrderLineItemsList(orderLineItems);

        //get skuCodes
        List<String> skuCodes = order.getOrderLineItemsList().stream()
                .map(OrderLineItems::getSkucode)
                .toList();

        //Make a call to the Inventory Service
        InventoryResponse[] inventoryResponseArray = webClient.get()
                .uri("http://localhost:8082/api/inventory", uriBuilder -> uriBuilder.queryParam("skuCodes", skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block(); //ensure its synchronious

        //check if all product is in stock
        boolean allProductInStock = Arrays.stream(inventoryResponseArray).allMatch(InventoryResponse::isInStock);

        if (allProductInStock) {
            orderRepository.save(order);
        } else {
            throw new IllegalArgumentException("Order Not Found! Please try again!");
        }
    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();

        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkucode(orderLineItemsDto.getSkucode());
        return orderLineItems;

    }
}

