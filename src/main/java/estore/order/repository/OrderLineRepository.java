package estore.order.repository;

import estore.order.entity.Order;
import estore.order.entity.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderLineRepository extends JpaRepository<OrderLine,Long> {

    OrderLine findByProductIdAndOrder(Long productId, Order order);

}
