package bookstore.controller;

import bookstore.dto.cart.itemdto.CartItemResponseDto;
import bookstore.dto.cart.itemdto.CreateCartItemRequestDto;
import bookstore.dto.cart.itemdto.UpdateCartItemRequestDto;
import bookstore.dto.shopping.cartdto.ShoppingCartResponseDto;
import bookstore.service.ShoppingCartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Cart management", description = "Endpoints for managing carts")
@RequiredArgsConstructor
@RestController
@Validated
@RequestMapping("/cart")
public class ShoppingCartController {
    private final ShoppingCartService cartService;

    @Operation(summary = "Get user's shopping cart",
            description = "Storing all the items within the cart")
    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ShoppingCartResponseDto getShoppingCart(Authentication authentication) {
        return cartService.getCartByUser(authentication);
    }

    @Operation(summary = "add book to shopping cart",
            description = "add specified book to the shopping cart")
    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public CartItemResponseDto addBookToShoppingCart(
            @RequestBody CreateCartItemRequestDto request, Authentication authentication) {
        return cartService.addCartItem(request, authentication);
    }

    @Operation(summary = "update quantity of book",
            description = "update the quantity of available books")
    @PutMapping("/cart-items/{cartItemId}")
    @PreAuthorize("hasRole('USER')")
    public CartItemResponseDto updateQuantityOfCartItem(
            @PathVariable @Positive Long cartItemId,
            @Valid @RequestBody UpdateCartItemRequestDto request) {
        return cartService.updateQuantityOfCartItem(cartItemId, request);
    }

    @Operation(summary = "delete book",
            description = "delete book from shopping cart")
    @DeleteMapping("/cart-items/{cartItemId}")
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCartItem(Authentication authentication,
                               @PathVariable @Positive Long cartItemId) {
        cartService.deleteCartItem(authentication, cartItemId);
    }
}
