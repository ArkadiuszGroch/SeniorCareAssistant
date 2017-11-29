package pl.edu.pwste.Service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pwste.Entity.CareAssistant;
import pl.edu.pwste.Entity.Role;
import pl.edu.pwste.Entity.Senior;
import pl.edu.pwste.Entity.User;
import pl.edu.pwste.Repository.CareAssistantRepository;
import pl.edu.pwste.Repository.RoleRepository;
import pl.edu.pwste.Repository.SeniorRepository;
import pl.edu.pwste.Repository.UserRepository;
import pl.edu.pwste.SecurityStringGenerator;
import pl.edu.pwste.Service.AccountService;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SeniorRepository seniorRepository;

    @Autowired
    private CareAssistantRepository careAssistantRepository;

    @Autowired
    RoleRepository roleRepository;

    @Override
    @Transactional
    public void registerSenior(Senior senior) {

        User user = senior.getUser();
        setRole(user, "SENIOR");
        generateSecurityStringForUser(user);

        userRepository.save(user);
        seniorRepository.save(senior);
    }


    @Override
    public String loginSenior(Senior senior) {
        User user = userRepository.findUserByLoginAndPassword(senior.getUser().getLogin(),
                senior.getUser().getPassword());
        generateSecurityStringForUser(user);
        userRepository.save(user);
        return seniorRepository.findSeniorByUser(user).getUser().getSecurityString();
    }


    @Override
    @Transactional
    public void registerCareAssistant(CareAssistant careAssistant) {
        User user = careAssistant.getUser();
        setRole(user, "CAREASSISTANT");

        userRepository.save(user);
        careAssistantRepository.save(careAssistant);
    }


    @Override
    public String loginCareAssistant(CareAssistant careAssistant) {
        User user = userRepository.findUserByLoginAndPassword(careAssistant.getUser().getLogin(),
                careAssistant.getUser().getPassword());
        generateSecurityStringForUser(user);
        userRepository.save(user);
        return careAssistantRepository.findCareAssistantByUser(user).getUser().getSecurityString();
    }

    @Override
    public CareAssistant findCareAssistantByLoginOrPassword(String login, String password) {
        User user = userRepository.findUserByLoginAndPassword(login, password);
        CareAssistant careAssistant = null;
        if(user != null)
            careAssistant = careAssistantRepository.findCareAssistantByUser(user);
        return careAssistant;
    }

    @Override
    public CareAssistant findCareAssistantByLoginOrEmail(String login, String email) {
        User user = userRepository.findUserByLoginOrEmail(login, email);
        CareAssistant careAssistant = null;
        if(user != null)
            careAssistant = careAssistantRepository.findCareAssistantByUser(user);
        return careAssistant;
    }

    private void generateSecurityStringForUser(User user) {
        SecurityStringGenerator securityStringGenerator = new SecurityStringGenerator(30);
        String securityString = securityStringGenerator.generate();
        user.setSecurityString(securityString);
    }

    public void setRole(User user, String roleName) {
        Role role = roleRepository.findByRole(roleName);
        if (role == null) {
            role = new Role();
            role.setRole(roleName);
            roleRepository.save(role);
        }

        Set<Role> roles = user.getRoles();
        if (roles == null)
            roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);
    }
}
