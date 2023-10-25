package bookstore.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

public record CreateBookRequestDto(
         @NotNull
         String title,
         @NotNull
         String author,
         @NotNull
         String isbn,
         @NotNull
         @PositiveOrZero
         BigDecimal price,
         String description,
         String coverImage
){
}
