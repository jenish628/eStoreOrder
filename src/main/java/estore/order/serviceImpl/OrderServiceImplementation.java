package estore.order.serviceImpl;

import estore.order.client.PaymentClient;
import estore.order.client.ProductClient;
import estore.order.dto.*;
import estore.order.entity.Order;
import estore.order.entity.OrderLine;
import estore.order.enumm.OrderStatus;
import estore.order.enumm.PurchaseStatus;
import estore.order.exception.OrderNotFoundException;
import estore.order.mapper.OrderMapper;
import estore.order.repository.OrderRepository;
import estore.order.service.OrderLineService;
import estore.order.service.OrderService;

import estore.order.util.ApplicationUtil;

import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class OrderServiceImplementation implements OrderService {


    private final PaymentClient paymentClient;
    private final OrderRepository orderRepository;

    private final OrderLineService orderLineService;

    private OrderMapper orderMapper;

    private ProductClient productClient;

    public OrderServiceImplementation(PaymentClient paymentClient, OrderRepository orderRepository, OrderLineService orderLineService, OrderMapper orderMapper, ProductClient productClient) {
        this.paymentClient = paymentClient;
        this.orderRepository = orderRepository;
        this.orderLineService = orderLineService;
        this.orderMapper = orderMapper;
        this.productClient = productClient;
    }

    @Override
    public List<OrderDto> getAllOrders() {
        return null;
    }

    @Override
    public OrderResponseDto getOrderbyId(String id) {
        Order order = findByOrderId(id);
        return orderMapper.convertOrderResponseEntity(order);
    }

    @Override
    public boolean deleteOrder(Long id) {
        return false;
    }

    @Override
    public OrderResponseDto addOrder(OrderDto orderDto) {

        Order order = findByOrderAndStatus();
        order.addOrderLine(createNewOrder(orderDto));
        orderRepository.save(order);
        return orderMapper.convertOrderResponseEntity(order);
    }

    @Override
    public PaymentResponseDto checkoutOrder(ProcessOrderCheckoutDto processOrderCheckoutDto) {

        Optional<Order> optionalOrder = orderRepository.findByUuidAndOrderStatus(processOrderCheckoutDto.getOrderId(),
                OrderStatus.PENDING);

        if (optionalOrder.isEmpty()) throw new OrderNotFoundException("Order not found");
        Order order = optionalOrder.get();

//        Order order = findByOrderId(processOrderCheckoutDto.getOrderId());
        order.setOrderStatus(OrderStatus.PROCESSING);
        order.setPaymentMethodId(processOrderCheckoutDto.getPaymentMethodId());
        orderRepository.saveAndFlush(order);

//        todo calling payment api


        ProcessPaymentDto processPaymentDto = ProcessPaymentDto.builder()
                .paymentMethodId(processOrderCheckoutDto.getPaymentMethodId())
                .orderId(order.getUuid())
                .amount(order.getTotalAmount()).build();

        PaymentResponseDto paymentResponseDto = paymentClient
                .processPurchase(processPaymentDto
                );

        order.setOrderStatus(paymentResponseDto.getPurchaseStatus() == PurchaseStatus.SUCCESS ?
                OrderStatus.COMPLETED :
                OrderStatus.CANCELLED);
        orderRepository.saveAndFlush(order);
        return paymentResponseDto;

    }

    Order findByOrderAndStatus() {
        Optional<Order> optionalOrder = orderRepository.findByCreatedByAndOrderStatus
                (ApplicationUtil.getCurrentUsername(), OrderStatus.PENDING);


        if (optionalOrder.isEmpty()) {
            Order order = new Order();
            order.setCreatedBy(ApplicationUtil.getCurrentUsername());
            return order;
        }
        return optionalOrder.get();
    }


    OrderLine checkOrderLineExistOrNot(OrderDto orderDto, Order order) {
        return orderLineService.findByProductIdAndOrder(orderDto.getProductId(), order);
    }


    private OrderLine createNewOrder(OrderDto orderDto) {
        OrderLine orderLine = orderMapper.convertToOrderLineEntity(orderDto);
        ProductDto productDto = productClient.getProduct(1l);
        orderLine.setTotalPrice(productDto.getPrice() * orderDto.getQuantity());
        orderLine.setPrice(productDto.getPrice());
        return orderLine;
    }


    @Override
    public OrderResponseDto removeItemFromCart(Long cartId) {

        OrderLine orderLine = orderLineService.getByOrderLineId(cartId);
        Order order = orderLine.getOrder();
        order.removeOrderLine(orderLine);
        return orderMapper.convertOrderResponseEntity(orderRepository.saveAndFlush(order));
    }

    @Override
    public void removeAllItemFromCart(String orderId) {
        Order order = findByOrderId(orderId);
        orderRepository.delete(order);
    }

    private Order findByOrderId(String id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isEmpty()) throw new OrderNotFoundException("Order not found");
        return optionalOrder.get();
    }


}
