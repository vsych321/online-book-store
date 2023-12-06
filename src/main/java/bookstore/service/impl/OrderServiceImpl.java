package bookstore.service.impl;

import bookstore.dto.order.itemdto.OrderItemResponseDto;
import bookstore.dto.orderdto.CreateOrderRequestDto;
import bookstore.dto.orderdto.OrderResponseDto;
import bookstore.dto.orderdto.UpdateOrderStatusRequestDto;
import bookstore.entity.CartItem;
import bookstore.entity.Order;
import bookstore.entity.OrderItem;
import bookstore.entity.ShoppingCart;
import bookstore.entity.User;
import bookstore.exception.EntityNotFoundException;
import bookstore.mapper.OrderItemMapper;
import bookstore.mapper.OrderMapper;
import bookstore.repository.order.OrderRepository;
import bookstore.repository.order.item.OrderItemRepository;
import bookstore.repository.shopping.cart.ShoppingCartRepository;
import bookstore.security.CustomUserDetailsService;
import bookstore.service.OrderService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;
    private final ShoppingCartRepository shoppingCartRepository;
    private final CustomUserDetailsService userDetailsService;

    @Transactional
    @Override
    public OrderResponseDto createOrder(
            Authentication authentication, CreateOrderRequestDto requestDto) {
        User user = getUser(authentication);
        ShoppingCart cart = shoppingCartRepository.findByUserId(user.getId());
        Order order = create(requestDto, user, cart);
        order.setOrderItems(setItems(order, cart.getCartItems()));
        cart.clearCartItems();
        return orderMapper.toDto(orderRepository.save(order));
    }

    @Override
    public List<OrderResponseDto> findAll(Authentication authentication, Pageable pageable) {
        User user = getUser(authentication);
        return orderRepository.findAllByUserId(user.getId(), pageable)
                .stream()
                .map(orderMapper::toDto)
                .toList();
    }

    @Override
    public OrderResponseDto updateOrderStatus(Long id, UpdateOrderStatusRequestDto requestDto) {
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find order by id " + id));
        order.setStatus(requestDto.status());
        return orderMapper.toDto(orderRepository.save(order));
    }

    @Override
    public List<OrderItemResponseDto> getAllByOrderId(Long orderId, Authentication authentication) {
        User user = getUser(authentication);
        Order order = orderRepository.findByIdAndUserId(orderId, user.getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find order by id " + orderId));
        return order.getOrderItems().stream()
                .map(orderItemMapper::toDto)
                .toList();
    }

    @Override
    public OrderItemResponseDto getOrderItemById(
            Long orderId, Authentication authentication, Long orderItemId) {
        User user = getUser(authentication);
        Order order = orderRepository.findByIdAndUserId(orderId, user.getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find order by id " + orderId));
        OrderItem orderItem = orderItemRepository.findByIdAndOrderId(order.getId(), orderItemId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find item by id " + orderItemId));
        return orderItemMapper.toDto(orderItem);
    }

    private User getUser(Authentication authentication) {
        return (User) userDetailsService.loadUserByUsername(authentication.getName());
    }

    private Set<OrderItem> setItems(Order order, Set<CartItem> cartItems) {
        return cartItems.stream()
                .map(cartItem -> {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setBook(cartItem.getBook());
                    orderItem.setQuantity(cartItem.getQuantity());
                    orderItem.setPrice(cartItem.getBook().getPrice()
                            .multiply(BigDecimal.valueOf(cartItem.getQuantity())));
                    orderItem.setOrder(order);
                    return orderItem;
                })
                .collect(Collectors.toSet());
    }

    private Order create(CreateOrderRequestDto requestDto, User user, ShoppingCart cart) {
        Order order = new Order();
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(Order.OrderStatus.PENDING);
        order.setUser(user);
        order.setShippingAddress(requestDto.shippingAddress());
        Set<OrderItem> orderItems = setItems(order, cart.getCartItems());
        order.setTotal(calculateTotal(orderItems));
        order.setOrderItems(orderItems);
        return order;
    }

    private BigDecimal calculateTotal(Set<OrderItem> orderItems) {
        return orderItems.stream()
                .map(OrderItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
