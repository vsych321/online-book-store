package bookstore.mapper;

import bookstore.dto.userdto.UserRegistrationRequestDto;
import bookstore.dto.userdto.UserResponseDto;
import bookstore.entity.User;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        implementationPackage = "<PACKAGE_NAME>.impl"
)
public interface UserMapper {
    UserResponseDto toDto(User user);

    User toUser(UserRegistrationRequestDto requestDto);
}
