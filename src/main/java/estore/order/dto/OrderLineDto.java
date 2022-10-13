package estore.order.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Column;

@Data
@NoArgsConstructor
public class OrderLineDto {

    private long id;
    private long productId;
    private long orderId;
    private Double price;
    private int quantity;
    private Double totalPrice;
}
