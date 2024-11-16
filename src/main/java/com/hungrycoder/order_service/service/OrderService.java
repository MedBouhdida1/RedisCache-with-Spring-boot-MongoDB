package com.hungrycoder.order_service.service;

import com.hungrycoder.order_service.model.Order;
import com.hungrycoder.order_service.model.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    public OrderRepo orderRepo;

    // Retrieve an order by ID and cache the result
    @Cacheable(value = "order", key = "#id")
    public Order getOrderById(String id) throws Exception {
        System.out.println("Fetching getOrderById from MongoDB");
        return orderRepo.findById(id).orElseThrow(() -> new Exception("Order Not Found!"));
    }

    // Retrieve all orders and cache the result
    @Cacheable(value = "orders")
    public List<Order> getOrders() throws Exception {
        System.out.println("Fetching getOrders from MongoDB");
        return orderRepo.findAll();
    }

    // Create a new order and evict the cache for orders
    @CacheEvict(cacheNames = "orders", allEntries = true) // Clears all cached orders
    public String createOrder(Order order) {
        Order newOrder = new Order();
        newOrder.setDate(order.getDate());
        newOrder.setProductId(order.getProductId());
        Order savedOrder = orderRepo.save(newOrder);
        return savedOrder.getId();
    }

    // Update an existing order and manage cache eviction
    @CacheEvict(cacheNames = {"order", "orders"},
            allEntries = true, key = "#id") // Evicts the specific order and clears all orders from the cache
    public Order updateOrder(String id, Order updatedOrder) throws Exception {
        Order existingOrder = orderRepo.findById(id)
                .orElseThrow(() -> new Exception("Order Not Found!"));

        existingOrder.setDate(updatedOrder.getDate());
        existingOrder.setProductId(updatedOrder.getProductId());
        return orderRepo.save(existingOrder);
    }

    // Delete an order and manage cache eviction
    @CacheEvict(cacheNames = {"order", "orders"},
            allEntries = true, key = "#id") // Evicts the specific order and clears all orders from the cache
    public void deleteOrder(String id) throws Exception {
        if (!orderRepo.existsById(id)) {
            throw new Exception("Order Not Found!");
        }
        orderRepo.deleteById(id);
    }
}
