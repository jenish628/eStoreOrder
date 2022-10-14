package estore.order.dto;

import estore.order.enumm.PurchaseStatus;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class PaymentResponseDto {
    private String orderId;
    private double paymentAmount;
    private String failureReason;
    private PurchaseStatus purchaseStatus;

}
