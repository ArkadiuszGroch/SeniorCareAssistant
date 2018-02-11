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
import pl.edu.pwste.Service.AccountService;
import pl.edu.pwste.Service.UserService;

import java.util.Arrays;
import java.util.HashSet;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AccountService accountService;
    @Autowired
    private CareAssistantRepository careAssistantRepository;
    @Autowired
    private SeniorRepository seniorRepository;


    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findUserByEmailOrLogin(String email, String login) {
        return null;
    }

    @Override
    public void saveUser(User user) {
        Role userRole = roleRepository.findByRole("ADMIN");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        userRepository.save(user);
    }

    @Override
    public User findUserByLogin(String login) {
        return userRepository.findUserByLogin(login);
    }

    @Override
    public Senior findSeniorByLogin(String login) {
        User user = findUserByLogin(login);
        Senior senior = null;
        if (user != null)
            senior = (Senior) findAccountByLogin(login);
        return senior;
    }

    @Override
    public Senior findSeniorById(int id) {
        return seniorRepository.findSeniorById(id);
    }

    @Override
    public CareAssistant findCareAssistantByLogin(String login) {
        User user = findUserByLogin(login);
        CareAssistant careAssistant = null;
        if (user != null)
            careAssistant = (CareAssistant) findAccountByLogin(login);
        return careAssistant;
    }

    @Override
    public Senior findSeniorByEmail(String email) {
        User user = findUserByEmail(email);
        Senior senior = null;
        if (user != null)
            senior = (Senior) findAccountByEmail(email);
        return senior;
    }

    @Override
    public Senior findSeniorBySecStr(String securityString) {
        User user = userRepository.findUserBySecurityString(securityString);
        return seniorRepository.findSeniorByUser(user);
    }

    private Object findAccountByLogin(String login) {
        Object object = null;
        User user = userRepository.findUserByLogin(login);
        if (user != null) {
            object = careAssistantRepository.findCareAssistantByUser(user);
            if(object==null)
            {
                object=seniorRepository.findSeniorByUser(user);
            }
        }
        return object;
    }

    private Object findAccountByEmail(String email) {
        Object object = null;
        User user = userRepository.findByEmail(email);
        if (user != null) {
            object = careAssistantRepository.findCareAssistantByUser(user);
            if(object==null)
            {
                object=seniorRepository.findSeniorByUser(user);
            }
        }
        return object;
    }
}
