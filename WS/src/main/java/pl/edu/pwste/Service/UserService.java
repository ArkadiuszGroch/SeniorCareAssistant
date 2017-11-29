package pl.edu.pwste.Service;

import pl.edu.pwste.Entity.CareAssistant;
import pl.edu.pwste.Entity.Senior;
import pl.edu.pwste.Entity.User;

public interface UserService {
    public User findUserByEmail(String email);
    public User findUserByEmailOrLogin(String email, String login);
    public void saveUser(User user);
    public User findUserByLogin(String login);
    public Senior findSeniorByLogin(String login);
    public CareAssistant findCareAssistantByLogin(String login);
}
