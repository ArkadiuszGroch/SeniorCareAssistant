package pl.edu.pwste.Service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pwste.Entity.Care;
import pl.edu.pwste.Entity.CareAssistant;
import pl.edu.pwste.Entity.Senior;
import pl.edu.pwste.Entity.User;
import pl.edu.pwste.Repository.CareAssistantRepository;
import pl.edu.pwste.Repository.CareRepository;
import pl.edu.pwste.Repository.SeniorRepository;
import pl.edu.pwste.Repository.UserRepository;
import pl.edu.pwste.Service.CareService;

@Service
public class CareServiceImpl implements CareService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	SeniorRepository seniorRepository;

	@Autowired
	CareAssistantRepository careAssistantRepository;

	@Autowired
	CareRepository careRepository;

	@Override
	public void addSeniorToCareAssistant(String seniorLogin, String careAssistantLogin) {
		User seniorUser = userRepository.findUserByLogin(seniorLogin);
		Senior s = seniorRepository.findSeniorByUser(seniorUser);
		
		User careAssistantUser = userRepository.findUserByLogin(careAssistantLogin);
		CareAssistant ca = careAssistantRepository.findCareAssistantByUser(careAssistantUser);

		Care care = new Care();
		care.setSenior(s);
		care.setCareAssistant(ca);

		careRepository.save(care);

	}

}
