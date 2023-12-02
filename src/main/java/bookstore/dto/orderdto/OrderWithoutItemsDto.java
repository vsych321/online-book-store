package bookstore.dto.orderdto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderWithoutItemsDto(
        Long orderId,
        String status,
        BigDecimal total,
        LocalDateTime orderDate,
        String shippingAddress
) {
}
