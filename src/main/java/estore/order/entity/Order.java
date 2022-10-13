package estore.order.entity;

import estore.order.enumm.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;

import javax.persistence.*;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, updatable = false,nullable = false)
    private UUID id;
    private int itemsCount;
    private Double totalAmount;
    private Date createdDate;
    private String createdBy;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

}
