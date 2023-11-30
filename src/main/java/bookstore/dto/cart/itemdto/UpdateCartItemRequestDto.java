package bookstore.dto.cart.itemdto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record UpdateCartItemRequestDto(@NotNull @PositiveOrZero Integer quantity) {
}
