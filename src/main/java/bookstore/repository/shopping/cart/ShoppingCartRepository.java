package bookstore.repository.shopping.cart;

import bookstore.entity.ShoppingCart;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    @EntityGraph(attributePaths = {"user", "cartItems", "cartItems.book"})
    ShoppingCart findByUserId(Long userId);

    //Optional<ShoppingCart> findByUserEmail(String email);
}
