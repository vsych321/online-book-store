package bookstore.repository.role;

import bookstore.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findRoleByName(Role.RoleName roleName);
}
