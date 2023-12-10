package bookstore.dto.orderdto;

import bookstore.dto.order.itemdto.OrderItemResponseDto;
import bookstore.entity.Order;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponseDto(
        Long id,
        Long userId,
        List<OrderItemResponseDto> orderItems,
        LocalDateTime orderDate,
        BigDecimal total,
        Order.OrderStatus status
) {
}
