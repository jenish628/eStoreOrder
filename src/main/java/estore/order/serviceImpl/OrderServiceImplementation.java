package estore.order.serviceImpl;

import estore.order.client.AddressClient;
import estore.order.client.PaymentClient;
import estore.order.client.ProductClient;
import estore.order.dto.*;
import estore.order.entity.Order;
import estore.order.entity.OrderLine;
import estore.order.enumm.OrderStatus;
import estore.order.enumm.ProductEnum;
import estore.order.enumm.PurchaseStatus;
import estore.order.exception.AddressNotFoundException;
import estore.order.exception.OrderNotFoundException;
import estore.order.mapper.OrderMapper;
import estore.order.repository.OrderRepository;
import estore.order.service.OrderLineService;
import estore.order.service.OrderService;

import estore.order.util.ApplicationUtil;

import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class OrderServiceImplementation implements OrderService {

    @Autowired
    private PaymentClient paymentClient;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderLineService orderLineService;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private ProductClient productClient;
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private AddressClient addressClient;

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

        ProductDto productDto = productClient.changeProductUnit(
                request.getHeader("Authorization"),
                ProductChangeUnitDto.builder()
                        .productId(orderDto.getProductId())
                        .status(ProductEnum.SOLD)
                        .quantity(orderDto.getQuantity()).build()
        );


        order.addOrderLine(createNewOrder(orderDto, productDto));
        orderRepository.save(order);
        return orderMapper.convertOrderResponseEntity(order);
    }

    @Override
    public PaymentResponseDto checkoutOrder(ProcessOrderCheckoutDto processOrderCheckoutDto) {
        try{

            Optional<Order> optionalOrder = orderRepository.findByUuidAndOrderStatus(processOrderCheckoutDto.getOrderId(),
                    OrderStatus.PENDING);

            if (optionalOrder.isEmpty()) throw new OrderNotFoundException("Order not found");

            AddressDto addressDto = addressClient.findAddressById(request.getHeader("Authorization"),
                    processOrderCheckoutDto.getAddressId()
            );


            if(addressDto == null) throw new  AddressNotFoundException("Address not found");
            if(!addressDto.isShippingAddress()) throw new AddressNotFoundException("Shipping address not found");

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
                    .processPurchase(request.getHeader("Authorization"), processPaymentDto
                    );

            order.setOrderStatus(paymentResponseDto.getPurchaseStatus() == PurchaseStatus.SUCCESS ?
                    OrderStatus.COMPLETED :
                    OrderStatus.CANCELLED);
            orderRepository.saveAndFlush(order);
            return paymentResponseDto;
        }catch (Exception ex) {
            throw new OrderNotFoundException(ex.getMessage());
        }

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


    private OrderLine createNewOrder(OrderDto orderDto, ProductDto productDto) {
        OrderLine orderLine = orderMapper.convertToOrderLineEntity(orderDto);
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
