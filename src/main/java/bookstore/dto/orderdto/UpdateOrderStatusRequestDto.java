package bookstore.dto.orderdto;

import bookstore.entity.Order;

public record UpdateOrderStatusRequestDto(Order.OrderStatus status) {
}
