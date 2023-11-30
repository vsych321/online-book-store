package bookstore.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Getter
@Setter
@EqualsAndHashCode(exclude = {"shoppingCart", "book"})
@ToString(exclude = {"shoppingCart", "book"})
@Entity
@SQLDelete(sql = "UPDATE cart_items SET is_deleted = true WHERE id = ?")
@Where(clause = "is_deleted=false")
@Table(name = "cart_items")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "shopping_cart_id", referencedColumnName = "id", nullable = false)
    private ShoppingCart shoppingCart;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "book_id", referencedColumnName = "id", nullable = false)
    private Book book;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;
}
