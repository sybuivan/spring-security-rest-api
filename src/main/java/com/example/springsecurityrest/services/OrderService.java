package com.example.springsecurityrest.services;

import com.example.springsecurityrest.dto.OrderDto;
import com.example.springsecurityrest.dto.OrderItemRequestDto;
import com.example.springsecurityrest.exception.ResourceNotFoundException;
import com.example.springsecurityrest.interfaces.IOrder;
import com.example.springsecurityrest.models.Order;
import com.example.springsecurityrest.models.OrderItem;
import com.example.springsecurityrest.models.Product;
import com.example.springsecurityrest.models.User;
import com.example.springsecurityrest.repositories.OrderRepository;
import com.example.springsecurityrest.repositories.ProductRepository;
import com.example.springsecurityrest.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService implements IOrder {

    @Autowired
    private OrderRepository orderRepository;

//    @Autowired
//    OrderItemRepository orderItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;


    @Override
    public void orderProduct(OrderDto orderDto) {
        int totalPrice = 0;
        Order order = new Order();
        List<OrderItem> orderItemList = new ArrayList<>();

        User user = userRepository.findById(orderDto.getUserId()).orElse(null);
        List<OrderItemRequestDto> orderItems = orderDto.getOrderItems();

        if (orderItems != null) {
            for (OrderItemRequestDto item : orderItems) {
                OrderItem orderItem = new OrderItem();

                Optional<Product> productOptional = productRepository.findById(item.getProductId());
                if (productOptional.isPresent()) {
                    Product product = productOptional.get();
                    totalPrice += product.getPrice() * item.getQuantity();
                    orderItem.setProduct(product);
                    orderItem.setQuantity(item.getQuantity());
                    orderItem.setOrder(order);
                    orderItemList.add(orderItem);
                } else {
                    throw new ResourceNotFoundException("Product not found with ID: " + item.getProductId());
                }
            }
        } else {
            System.out.println("Order item list is null.");
        }


        order.setOrderDate(new Date());
        order.setOrderItems(orderItemList);
        order.setTotalPrice(totalPrice);
        order.setUser(user);

        orderRepository.save(order);
    }
}
