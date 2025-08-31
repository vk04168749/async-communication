package com.cnx.ecom.order.service;

import com.cnx.ecom.order.dto.CreateOrderRequest;
import com.cnx.ecom.order.dto.InventoryProductDTO;
import com.cnx.ecom.order.dto.OrderDTO;
import com.cnx.ecom.order.entity.Order;
import com.cnx.ecom.order.entity.OrderItem;
import com.cnx.ecom.order.exception.OrderException;
import com.cnx.ecom.order.kafka.OrderProducer;
import com.cnx.ecom.order.mapper.OrderMapper;
import com.cnx.ecom.order.repository.OrderRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cnx.ecom.order.client.InventoryClient;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepo;

    private final OrderMapper orderMapper;

    private final OrderProducer orderProducer;

    private final InventoryClient inventoryClient;

    @Transactional
    public OrderDTO createOrder(CreateOrderRequest request) {

        // Fetch product details from Inventory
        List<Long> productIds = request.getItems().stream().map(CreateOrderRequest.OrderItemRequest::getProductId).toList();
        List<InventoryProductDTO> products = inventoryClient.getProductsByIds(productIds);

        BigDecimal total = BigDecimal.ZERO;
        List<OrderItem> orderItems = new ArrayList<>();

        Order order = orderMapper.toEntity(request);
        order.setStatus("PENDING");

        for (CreateOrderRequest.OrderItemRequest itemReq : request.getItems()) {
            InventoryProductDTO product = products.stream()
                    .filter(p -> p.getId().equals(itemReq.getProductId()))
                    .findFirst()
                    .orElseThrow(() -> new OrderException("Product not found: " + itemReq.getProductId()));

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProductId(product.getId());
            orderItem.setProductName(product.getName());
            orderItem.setQuantity(itemReq.getQuantity());
            orderItem.setPrice(product.getPrice());

            orderItems.add(orderItem);
            total = total.add(product.getPrice().multiply(BigDecimal.valueOf(itemReq.getQuantity())));
        }

        order.setTotalAmount(total);
        order.setItems(orderItems);

        Order saved = orderRepo.save(order);

        return orderMapper.toDto(saved);
    }


    @Transactional(readOnly = true)
    public OrderDTO getOrder(Long orderId) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new OrderException("Order not found: " + orderId));
        return orderMapper.toDto(order);
    }

    @Transactional
    public OrderDTO updateStatus(Long orderId, String status) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new OrderException("Order not found: " + orderId));
        order.setStatus(status);
        OrderDTO dto = orderMapper.toDto(order);
        orderProducer.sendOrder(dto);
        return dto;
    }
}
