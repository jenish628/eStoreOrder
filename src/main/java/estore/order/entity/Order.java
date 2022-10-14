package estore.order.entity;

import estore.order.enumm.OrderStatus;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;


import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_table")
@Builder
public class Order {
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private String uuid;
    private int itemsCount;
    private double totalAmount;
    @Column(updatable = false)
    private LocalDate createdDate = LocalDate.now();

    private String createdBy;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus = OrderStatus.PENDING;

    private Long paymentMethodId;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "order")
    @org.hibernate.annotations.OrderBy(clause = "id asc")
    private Set<OrderLine> orderLines;


    @UpdateTimestamp
    private LocalDate updatedDate;


    @PrePersist
    public void prePersist() {
        if (this.uuid == null) {
            this.uuid = UUID.randomUUID().toString();
        }
        if (this.createdDate == null) this.createdDate = LocalDate.now();
    }


    public void addOrderLine(OrderLine orderLine) {
        if (this.orderLines == null) this.orderLines = new HashSet<>();
        this.orderLines.add(orderLine);
        this.totalAmount += orderLine.getTotalPrice();
        orderLine.setOrder(this);
    }


    public void removeOrderLine(OrderLine orderLine) {
        this.orderLines.remove(orderLine);
        this.totalAmount-= orderLine.getTotalPrice();
        orderLine.setOrder(null);
    }
}
