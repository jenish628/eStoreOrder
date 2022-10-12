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
    private Long id;
    private String name;
    private int quantity;
    private Date createdDate;
    private String createdBy;
    private Long referenceNumber;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
}
