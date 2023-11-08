package bookstore.dto.user;

import bookstore.validation.FieldMatch;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@FieldMatch(fields = {"password", "repeatedPassword"},
        message = "passwords must be matched")
public record UserRegistrationRequestDto(

        @Email
        @NotBlank
        @Size(min = 4, max = 50)
        String email,

        @NotBlank
        @Size(min = 8, max = 80)
        String password,

        @NotBlank
        @Size(min = 8, max = 80)
        String repeatPassword,

        @NotBlank
        @Size(min = 2, max = 100)
        String firstName,

        @NotBlank
        @Size(min = 2, max = 100)
        String lastName,

        @NotBlank
        String shippingAddress
){
}
