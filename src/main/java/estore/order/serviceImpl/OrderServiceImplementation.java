package estore.order.serviceImpl;

import estore.order.dto.OrderDto;
import estore.order.entity.Order;
import estore.order.exception.OperationUnsucessfullException;
import estore.order.repository.OrderRepository;
import estore.order.service.OrderService;
import eye2web.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImplementation implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public List<OrderDto> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(a-> modelMapper.map(a, OrderDto.class)).collect(Collectors.toList());
    }

    @Override
    public OrderDto getOrderbyId(Long id) {

        return modelMapper.map(orderRepository.findById(id).get(), OrderDto.class);
    }

    @Override
    public boolean deleteOrder(Long id) {
        try {
            orderRepository.deleteById(id);
            return true;
        }catch (Exception e){
            throw new OperationUnsucessfullException();
        }

    }

    @Override
    public void addOrder(OrderDto order) {
            orderRepository.save(modelMapper.map(order,Order.class));
    }

    @Override
    public String updateOrder(OrderDto orderDto, Long id) {
        try {
            Order order = orderRepository.findById(id).get();
            orderRepository.save(order);
            return "UPDTAE SUCESSFULL";
        } catch (Exception e){
            throw new OperationUnsucessfullException();
        }


    }
}
