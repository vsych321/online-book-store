package bookstore.dto.orderdto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateOrderRequestDto(@NotBlank @Size(max = 80) String shippingAddress) {
}
