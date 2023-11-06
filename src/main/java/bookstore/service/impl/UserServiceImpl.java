package bookstore.service.impl;

import bookstore.dto.user.UserRegistrationRequestDto;
import bookstore.dto.user.UserResponseDto;
import bookstore.entity.Role;
import bookstore.entity.User;
import bookstore.exception.RegistrationException;
import bookstore.mapper.UserMapper;
import bookstore.repository.role.RoleRepository;
import bookstore.repository.user.UserRepository;
import bookstore.service.UserService;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new RegistrationException("Unable to complete registration");
        }
        User user = userMapper.toModel(request);
        user.setRoles(Collections.singleton(roleRepository
                .findRoleByName(Role.RoleName.ROLE_USER)));
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setEmail(request.email());
        User savedUser = userRepository.save(user);
        return userMapper.toUserResponse(savedUser);
    }
}

