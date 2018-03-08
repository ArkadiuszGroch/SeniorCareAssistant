package pl.edu.pwste.Service;

import pl.edu.pwste.Entity.CareAssistant;
import pl.edu.pwste.Entity.Senior;
import pl.edu.pwste.Entity.User;

public interface UserService {
    User findUserByEmail(String email);

    User findUserByEmailOrLogin(String email, String login);

    User findUserByLogin(String login);

    Senior findSeniorByLogin(String login);

    Senior findSeniorById(int id);

    CareAssistant findCareAssistantByLogin(String login);

    Senior findSeniorByEmail(String email);

    Senior findSeniorBySecStr(String securityString);
}
