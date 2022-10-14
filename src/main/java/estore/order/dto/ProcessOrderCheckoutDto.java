package estore.order.dto;


import lombok.Data;

@Data
public class ProcessOrderCheckoutDto {

    private String orderId;
    private Long paymentMethodId;

}
