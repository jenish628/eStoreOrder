package estore.order.serviceImpl;

import estore.order.dto.OrderDto;
import estore.order.dto.OrderLineDto;
import estore.order.entity.Order;
import estore.order.entity.OrderLine;
import estore.order.exception.OrderLineNotFoundException;
import estore.order.repository.OrderLineRepository;
import estore.order.service.OrderLineService;
import estore.order.service.OrderService;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderLineServiceImplementation implements OrderLineService {



    private OrderLineRepository orderLineRepository;

    public OrderLineServiceImplementation(OrderLineRepository orderLineRepository) {
        this.orderLineRepository = orderLineRepository;
    }

    @Override
    public List<OrderLineDto> getAll() {
        return null;
    }

    @Override
    public OrderLine getByOrderLineId(Long id) {

        Optional<OrderLine> orderLine = orderLineRepository.findById(id);
        if(orderLine.isEmpty()) throw new OrderLineNotFoundException("Item not found");
        return orderLine.get();
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public String Update(OrderLineDto orderLineDto, Long id) {
        return null;
    }

    @Override
    public void add(OrderLineDto orderLineDto) {

    }

    @Override
    public OrderLine findByProductIdAndOrder(Long productId, Order order){
        return orderLineRepository.findByProductIdAndOrder(productId, order);
    }
}
