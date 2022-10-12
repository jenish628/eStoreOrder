package estore.order.entity;

import estore.order.enumm.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.Random;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String productName;
    private Long referenceNumber;
    private int quantity;
    private Double price;
    private Double tax;
    private Double discount;

    private Date createdDate;
    private String createdBy;



    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private double getPrice(){
        return quantity * (price + tax - discount);

    }

    public Long getReferenceNumber() {
        Random random = new Random();
        return random.nextLong();
    }
}
