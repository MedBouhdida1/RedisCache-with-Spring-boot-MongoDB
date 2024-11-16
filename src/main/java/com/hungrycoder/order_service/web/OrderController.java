package com.hungrycoder.order_service.web;

import com.hungrycoder.order_service.model.Order;
import com.hungrycoder.order_service.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // Retrieve an order by ID
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable String id) throws Exception {
        return new ResponseEntity<>(orderService.getOrderById(id), HttpStatus.OK);
    }

    // Retrieve all orders
    @GetMapping("/all")
    public ResponseEntity<List<Order>> getOrders() throws Exception {
        List<Order> orders = orderService.getOrders(); // Get the orders from the service
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    // Create a new order
    @PostMapping("/create")
    public ResponseEntity<String> createOrder(@RequestBody Order order) {
        return new ResponseEntity<>(orderService.createOrder(order), HttpStatus.CREATED);
    }

    // Update an existing order
    @PutMapping("/update/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable String id, @RequestBody Order updatedOrder) throws Exception {
        Order order = orderService.updateOrder(id, updatedOrder);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    // Delete an order
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable String id) throws Exception {
        orderService.deleteOrder(id); // Deletes the order from the repository
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Returns a 204 No Content response
    }
}
