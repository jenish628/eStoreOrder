package estore.order.service;




import estore.order.dto.*;
import estore.order.entity.Order;

import java.util.List;

public interface OrderService {

    public List<OrderDto> getAllOrders();
    public OrderResponseDto getOrderbyId(String id);
    public boolean deleteOrder(Long id);
    public OrderResponseDto addOrder(OrderDto order);

    OrderResponseDto removeItemFromCart(Long cartId);
    public void removeAllItemFromCart(String orderId);
    public PaymentResponseDto checkoutOrder(ProcessOrderCheckoutDto processOrderCheckoutDto);



}
