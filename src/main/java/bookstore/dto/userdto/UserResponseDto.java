package bookstore.dto.userdto;

public record UserResponseDto(
        Long id,

        String email,

        String firstName,

        String lastName,

        String shippingAddress
){
}
