package estore.order.repository;

import estore.order.entity.Order;
import estore.order.enumm.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {


    Optional<Order> findByCreatedByAndOrderStatus(String username, OrderStatus orderStatus);

    Optional<Order> findByUuidAndOrderStatus(String uuid,OrderStatus orderStatus);
}
