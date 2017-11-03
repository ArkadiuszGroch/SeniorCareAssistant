package pl.edu.pwste.Service;

import pl.edu.pwste.Entity.User;

/**
 * Created by goco on 26.10.2017.
 */
public interface UserService {
    public User findUserByEmail(String email);
    public void saveUser(User user);
}
