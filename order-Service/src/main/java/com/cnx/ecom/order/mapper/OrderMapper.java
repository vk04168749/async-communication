package com.cnx.ecom.order.mapper;

import com.cnx.ecom.order.dto.CreateOrderRequest;
import com.cnx.ecom.order.dto.OrderDTO;
import com.cnx.ecom.order.dto.OrderItemDTO;
import com.cnx.ecom.order.entity.Order;
import com.cnx.ecom.order.entity.OrderItem;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(source = "customerId", target = "customerId")
    @Mapping(source = "items", target = "items")
    @Mapping(source = "billingAddress", target = "billingAddress")
    @Mapping(source = "shippingAddress", target = "shippingAddress")
    OrderDTO toDto(Order order);

    @Mapping(source = "productId", target = "productId")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "quantity", target = "quantity")
    @Mapping(source = "productName", target = "productName")
    OrderItemDTO toDto(OrderItem item);

    @Mapping(source = "customerId", target = "customerId")
    @Mapping(source = "billingAddress", target = "billingAddress")
    @Mapping(source = "shippingAddress", target = "shippingAddress")
    @Mapping(source = "items", target = "items")
    Order toEntity(CreateOrderRequest request);

    List<OrderItemDTO> toItemDtoList(List<OrderItem> items);
    
}
