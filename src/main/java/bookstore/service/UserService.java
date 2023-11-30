package bookstore.service;

import bookstore.dto.userdto.UserRegistrationRequestDto;
import bookstore.dto.userdto.UserResponseDto;

public interface UserService {

    UserResponseDto register(UserRegistrationRequestDto request);
}
