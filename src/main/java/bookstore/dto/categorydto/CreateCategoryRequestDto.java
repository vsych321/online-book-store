package bookstore.dto.categorydto;

import jakarta.validation.constraints.NotBlank;

public record CreateCategoryRequestDto(
        @NotBlank
        String name,
        @NotBlank
        String description
) {
}
