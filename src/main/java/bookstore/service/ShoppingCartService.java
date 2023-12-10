package bookstore.service;

import bookstore.dto.cart.itemdto.CartItemResponseDto;
import bookstore.dto.cart.itemdto.CreateCartItemRequestDto;
import bookstore.dto.cart.itemdto.UpdateCartItemRequestDto;
import bookstore.dto.shopping.cartdto.ShoppingCartResponseDto;
import org.springframework.security.core.Authentication;

public interface ShoppingCartService {
    ShoppingCartResponseDto getCartByUser(Authentication authentication);

    CartItemResponseDto addCartItem(CreateCartItemRequestDto requestDto,
                                    Authentication authentication);

    CartItemResponseDto updateQuantityOfCartItem(Long id, UpdateCartItemRequestDto requestDto);

    void deleteCartItem(Authentication authentication, Long id);
}
