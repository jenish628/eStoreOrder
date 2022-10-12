package estore.order.service;




import estore.order.dto.OrderDto;
import estore.order.entity.Order;

import java.util.List;

public interface OrderService {

    public List<OrderDto> getAllOrders();
    public OrderDto getOrderbyId(Long id);
    public boolean deleteOrder(Long id);
    public void addOrder(OrderDto order);
    public String updateOrder(OrderDto orderDto, Long id);


}
