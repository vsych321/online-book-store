package bookstore.repository.order;

import bookstore.entity.Order;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @EntityGraph(attributePaths = {"user", "orderItems.book", "orderItems", "orderItems.order"})
    List<Order> findAllByUserId(Long userId);

    @EntityGraph(attributePaths = {"user", "orderItems.book"})
    Optional<Order> findByIdAndUserId(Long orderId, Long userId);
}
