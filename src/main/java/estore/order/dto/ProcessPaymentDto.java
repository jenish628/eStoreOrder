package estore.order.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProcessPaymentDto {
    private String orderId;
    private Long paymentMethodId;
    private double amount;
}
