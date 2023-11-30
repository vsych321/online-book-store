package bookstore.service.impl;

import bookstore.dto.cart.itemdto.CartItemResponseDto;
import bookstore.dto.cart.itemdto.CreateCartItemRequestDto;
import bookstore.dto.cart.itemdto.UpdateCartItemRequestDto;
import bookstore.dto.shopping.cartdto.ShoppingCartResponseDto;
import bookstore.entity.CartItem;
import bookstore.entity.ShoppingCart;
import bookstore.entity.User;
import bookstore.exception.EntityNotFoundException;
import bookstore.mapper.CartItemMapper;
import bookstore.mapper.ShoppingCartMapper;
import bookstore.repository.book.BookRepository;
import bookstore.repository.cart.item.CartItemRepository;
import bookstore.repository.shopping.cart.ShoppingCartRepository;
import bookstore.repository.user.UserRepository;
import bookstore.security.CustomUserDetailsService;
import bookstore.service.ShoppingCartService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final CartItemMapper cartItemMapper;
    private final CustomUserDetailsService userDetailsService;

    @Transactional
    @Override
    public ShoppingCartResponseDto getCartByUser(Authentication authentication) {
        User user = (User) userDetailsService.loadUserByUsername(authentication.getName());
        ShoppingCart cart = shoppingCartRepository.findByUserId(user.getId());
        return shoppingCartMapper.toDto(cart);
    }

    @Transactional
    @Override
    public CartItemResponseDto addCartItem(
            CreateCartItemRequestDto requestDto, Authentication authentication) {
        User user = (User) userDetailsService.loadUserByUsername(authentication.getName());
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(user.getId());
        Optional<CartItem> itemFromDb =
                cartItemRepository.findCartItemByBookIdAndShoppingCartId(
                        requestDto.bookId(), shoppingCart.getId());
        if (itemFromDb.isPresent()) {
            CartItem cartItem = itemFromDb.get();
            cartItem.setQuantity(cartItem.getQuantity() + requestDto.quantity());
            return cartItemMapper.toDto(cartItemRepository.save(cartItem));
        }
        CartItem newCartItem = new CartItem();
        newCartItem.setShoppingCart(shoppingCart);
        newCartItem.setBook(bookRepository.getReferenceById(requestDto.bookId()));
        newCartItem.setQuantity(requestDto.quantity());
        return cartItemMapper.toDto(cartItemRepository.save(newCartItem));
    }

    @Transactional
    @Override
    public CartItemResponseDto updateQuantityOfCartItem(
            Long id, UpdateCartItemRequestDto requestDto) {
        CartItem cartItem = cartItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find cartItem by id " + id));
        cartItem.setQuantity(requestDto.quantity());
        return cartItemMapper.toDto(cartItemRepository.save(cartItem));
    }

    @Override
    public void deleteCartItem(Authentication authentication, Long id) {
        User user = (User) userDetailsService.loadUserByUsername(authentication.getName());
        CartItem cartItem = cartItemRepository
                .findCartItemByIdAndShoppingCartId(id, user.getShoppingCart().getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find item in your shopping cart by id " + id));
        cartItemRepository.delete(cartItem);

    }
}
