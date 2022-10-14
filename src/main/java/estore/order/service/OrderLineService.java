package estore.order.service;


import estore.order.dto.OrderDto;
import estore.order.dto.OrderLineDto;
import estore.order.entity.Order;
import estore.order.entity.OrderLine;
import org.hibernate.sql.Update;

import java.util.List;

public interface OrderLineService {

    public List<OrderLineDto> getAll();
    public OrderLine getByOrderLineId(Long id);
    public boolean delete(Long id);
    public String Update (OrderLineDto orderLineDto, Long id);
    public void  add (OrderLineDto orderLineDto);
    OrderLine findByProductIdAndOrder(Long productId, Order order);
}
