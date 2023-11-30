package bookstore.dto.cart.itemdto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateCartItemRequestDto(
        @NotNull
        @Positive
        Long bookId,

        @NotNull
        @Positive
        Integer quantity
) {
}
