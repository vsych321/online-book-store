package bookstore.service.impl;

import bookstore.dto.userdto.UserRegistrationRequestDto;
import bookstore.dto.userdto.UserResponseDto;
import bookstore.entity.Role;
import bookstore.entity.ShoppingCart;
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
        User user = userMapper.toUser(request);
        user.setRoles(Collections.singleton(roleRepository
                .findRoleByName(Role.RoleName.ROLE_USER)));
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        createShoppingCart(user);
        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    private void createShoppingCart(User user) {
        ShoppingCart cart = new ShoppingCart();
        cart.setUser(user);
        user.setShoppingCart(cart);
    }
}
