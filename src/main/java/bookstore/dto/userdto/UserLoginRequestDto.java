package bookstore.dto.userdto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record UserLoginRequestDto(
        @NotBlank
        @Size(min = 8, max = 100)
        @Email
        String email,

        @NotEmpty
        @Size(min = 8, max = 100)
        String password
) {
}
