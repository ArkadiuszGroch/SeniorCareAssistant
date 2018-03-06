package pl.edu.pwste.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pwste.Entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

	User findUserByLoginAndPassword(String login, String password);
	
	User findUserByLogin(String login);
	
	User findUserBySecurityString(String SecurityString);

    User findByEmail(String email);

	User findUserByLoginOrEmail(String login, String email);

}
