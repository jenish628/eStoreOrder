package estore.order.controller;

import estore.order.dto.OrderDto;
import estore.order.dto.OrderLineDto;
import estore.order.entity.Order;
import estore.order.repository.OrderRepository;
import estore.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import eye2web.modelmapper.ModelMapper;

import java.util.List;


@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/")
    public List<OrderDto> getAll(){
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public OrderDto getById(@PathVariable Long id){
        return orderService.getOrderbyId(id);
    }
    @PostMapping("/")
    public void addPost(@RequestBody OrderDto orderDto){
        orderService.addOrder(orderDto);
    }

    @DeleteMapping("/{id}")
    public boolean deleteOrder(@PathVariable Long id){
        return orderService.deleteOrder(id);
    }

    @PutMapping("/{id}")
    public String editOrders(@RequestBody OrderDto orderDto,
                             @PathVariable Long id){
        return orderService.updateOrder(orderDto,id);
    }


}
