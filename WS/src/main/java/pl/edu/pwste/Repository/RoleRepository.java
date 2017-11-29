package pl.edu.pwste.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pwste.Entity.Role;

@Repository()
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByRole(String role);

}