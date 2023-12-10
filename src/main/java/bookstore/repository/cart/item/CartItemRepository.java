package bookstore.repository.cart.item;

import bookstore.entity.CartItem;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findCartItemByBookIdAndShoppingCartId(Long bookId, Long shoppingCardId);

    Optional<CartItem> findCartItemByIdAndShoppingCartId(Long userId, Long shoppingCardId);
}
