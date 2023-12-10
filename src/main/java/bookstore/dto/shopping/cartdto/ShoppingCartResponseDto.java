package bookstore.dto.shopping.cartdto;

import bookstore.dto.cart.itemdto.CartItemResponseDto;
import java.util.List;

public record ShoppingCartResponseDto(
        Long id,
        Long userId,
        List<CartItemResponseDto> cartItems
) {
}
