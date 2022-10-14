package estore.order.mapper;


import estore.order.dto.OrderDto;
import estore.order.dto.OrderLineDto;
import estore.order.dto.OrderResponseDto;
import estore.order.dto.PaymentResponseDto;
import estore.order.entity.Order;
import estore.order.entity.OrderLine;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderMapper {


    Order convertToEntity(OrderDto orderDto);
    OrderLine convertToOrderLineEntity(OrderDto orderLineDto);

    Order convertToDto(Order order);
    OrderLineDto convertToOrderLineDto(OrderLine orderLine);

    @Mapping(source = "uuid",target = "orderId")
    @Mapping(source = "orderLines", target = "orders")
    OrderResponseDto convertOrderResponseEntity(Order order);
    PaymentResponseDto checkoutResponse(Order order);


}
