package pl.edu.pwste.Service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.edu.pwste.Entity.Role;
import pl.edu.pwste.Entity.User;
import pl.edu.pwste.Repository.RoleRepository;
import pl.edu.pwste.Repository.UserRepository;
import pl.edu.pwste.Service.UserService;

import java.util.Arrays;
import java.util.HashSet;

@Service("userService")
public class  UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;


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
}
