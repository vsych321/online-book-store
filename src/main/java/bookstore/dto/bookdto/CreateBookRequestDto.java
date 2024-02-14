package bookstore.dto.bookdto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.util.List;

public record CreateBookRequestDto(
         @NotBlank
         String title,
         @NotBlank
         String author,
         @NotBlank
         String isbn,
         @NotNull
         @PositiveOrZero
         BigDecimal price,
         @NotBlank
         String description,
         @NotBlank
         String coverImage,
         List<Long> categoryIds
){
}
