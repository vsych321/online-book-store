package bookstore.repository.order;

import bookstore.entity.Order;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @EntityGraph(attributePaths = {"user", "orderItems.book", "orderItems", "orderItems.order"})
    List<Order> findAllByUserId(Long userId, Pageable pageable);

    @EntityGraph(attributePaths = {"user", "orderItems.book"})
    Optional<Order> findByIdAndUserId(Long orderId, Long userId);
}
