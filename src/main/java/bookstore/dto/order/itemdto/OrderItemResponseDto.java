package bookstore.dto.order.itemdto;

public record OrderItemResponseDto(
        Long id,
        Long bookId,
        Integer quantity
) {
}
