package pl.edu.pwste.Service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pwste.Entity.CareAssistant;
import pl.edu.pwste.Entity.Senior;
import pl.edu.pwste.Entity.User;
import pl.edu.pwste.Repository.CareAssistantRepository;
import pl.edu.pwste.Repository.SeniorRepository;
import pl.edu.pwste.Repository.UserRepository;
import pl.edu.pwste.SecurityStringGenerator;
import pl.edu.pwste.Service.AccountService;

import javax.transaction.Transactional;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	SeniorRepository seniorRepository;

	@Autowired
	CareAssistantRepository careAssistantRepository;
	
	@Override
	@Transactional
	public void registerSenior(Senior senior) {
		User user = senior.getUser();
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
		generateSecurityStringForUser(user);

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

	
	private void generateSecurityStringForUser(User user){
		SecurityStringGenerator securityStringGenerator = new SecurityStringGenerator(30);
		String securityString = securityStringGenerator.generate();
		user.setSecurityString(securityString);		
	}
}
