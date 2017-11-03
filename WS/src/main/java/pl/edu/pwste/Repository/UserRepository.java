package pl.edu.pwste.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pwste.Entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

	public User findUserByLoginAndPassword(String login, String password);
	
	public User findUserByLogin(String login);
	
	public User findUserBySecurityString(String SecurityString);

    User findByEmail(String email);
}
