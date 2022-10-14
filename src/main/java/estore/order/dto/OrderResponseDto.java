package estore.order.dto;


import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Builder
@Data
public class OrderResponseDto {

    private String orderId;
    private double totalAmount;
    private Set<OrderLineDto> orders;
}
