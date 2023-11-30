package bookstore.dto.cart.itemdto;

public record CartItemResponseDto(
        Long id,
        Long bookId,
        String bookTitle,
        Integer quantity
) {
}
