package estore.order.serviceImpl;

import estore.order.dto.OrderDto;
import estore.order.dto.OrderLineDto;
import estore.order.entity.OrderLine;
import estore.order.exception.OperationUnsucessfullException;
import estore.order.repository.OrderLineRepository;
import estore.order.service.OrderLineService;
import eye2web.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderLineServiceImplementation implements OrderLineService {

    @Autowired
    private OrderLineRepository orderLineRepository;

    @Autowired
    private ModelMapper mapper;
    @Override
    public List<OrderLineDto> getAll() {
        // order -> orderline dto

//      return   orderLineRepository
//                .findAll()
//                .stream()
//                .map(orderLine -> {
//                    OrderLineDto orderLineDto = new OrderLineDto();
//                    orderLineDto.setOrderId(orderLine.getOrderId());
//                    return orderLineDto;
//                }).collect(Collectors.toList());

        return orderLineRepository.findAll().stream().map(a-> mapper.map(a, OrderLineDto.class)).collect(Collectors.toList());
    }

    @Override
    public OrderLineDto getByOrderLineId(Long id) {
        return mapper.map(orderLineRepository.findAll(),OrderLineDto.class);
    }

    @Override
    public boolean delete(Long id) {
        try {
            orderLineRepository.deleteById(id);
            return true;
        }catch (Exception e){
            throw new OperationUnsucessfullException();
        }
    }

    @Override
    public String Update(OrderLineDto orderLineDto, Long id) {
        try {
            OrderLine orderLine = orderLineRepository.findById(id).get();
            orderLine.setId(orderLineDto.getId());

            orderLineRepository.save(orderLine);
            return "Update Sucessfull";
        }catch (Exception e){
            throw new OperationUnsucessfullException();
        }
    }

    @Override
    public void add(OrderLineDto orderLineDto) {
        orderLineRepository.save(mapper.map(orderLineDto, OrderLine.class));
    }
}
