package estore.order.controller;

import estore.order.dto.OrderDto;
import estore.order.dto.OrderLineDto;
import estore.order.dto.ProcessOrderCheckoutDto;
import estore.order.entity.Order;
import estore.order.repository.OrderRepository;
import estore.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;



    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable String id) {
        return ResponseEntity.ok(orderService.getOrderbyId(id));

    }

    @PostMapping("/add-to-cart")
    public ResponseEntity<?> addNewOrder(@RequestBody OrderDto orderDto) {
        return ResponseEntity.ok(orderService.addOrder(orderDto));
    }

    @DeleteMapping("/remove-item-from-cart/{cartId}")
    public ResponseEntity<?> removeFromCart(@PathVariable Long cartId) {
        return ResponseEntity.ok(orderService.removeItemFromCart(cartId));
    }

    @DeleteMapping("/remove-all-item-from-cart/{orderId}")
    public ResponseEntity<?> removeAllItemFromCart(@PathVariable String orderId) {
        orderService.removeAllItemFromCart(orderId);
        return ResponseEntity.ok("Successfully deleted");
    }


    @DeleteMapping("/payment-checkout")
    public ResponseEntity<?> paymentCheckout(@RequestBody ProcessOrderCheckoutDto processOrderCheckoutDto) {
        orderService.checkoutOrder(processOrderCheckoutDto);
        return ResponseEntity.ok("Successfully Checkout");
    }


}
