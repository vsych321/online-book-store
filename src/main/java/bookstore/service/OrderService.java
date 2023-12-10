package bookstore.service;

import bookstore.dto.order.itemdto.OrderItemResponseDto;
import bookstore.dto.orderdto.CreateOrderRequestDto;
import bookstore.dto.orderdto.OrderResponseDto;
import bookstore.dto.orderdto.UpdateOrderStatusRequestDto;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

public interface OrderService {
    OrderResponseDto createOrder(
            Authentication authentication, CreateOrderRequestDto requestDto);

    List<OrderResponseDto> findAll(Authentication authentication, Pageable pageable);

    OrderResponseDto updateOrderStatus(Long id, UpdateOrderStatusRequestDto requestDto);

    List<OrderItemResponseDto> getAllByOrderId(Long orderId, Authentication authentication);

    OrderItemResponseDto getOrderItemById(
            Long orderId, Authentication authentication, Long orderItemId);
}
