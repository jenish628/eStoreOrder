package estore.order.dto;

import estore.order.enumm.OrderStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;

@Data
@NoArgsConstructor
public class OrderDto {

    private int quantity;
    private Long productId;

}
