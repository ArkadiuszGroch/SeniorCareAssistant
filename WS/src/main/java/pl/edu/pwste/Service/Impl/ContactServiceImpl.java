package pl.edu.pwste.Service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pwste.Entity.Contact;
import pl.edu.pwste.Entity.Senior;
import pl.edu.pwste.Entity.User;
import pl.edu.pwste.Repository.ContactRepository;
import pl.edu.pwste.Repository.SeniorRepository;
import pl.edu.pwste.Repository.UserRepository;
import pl.edu.pwste.Service.ContactService;

import java.util.List;

@Service
public class ContactServiceImpl implements ContactService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private SeniorRepository seniorRepository;
	
	@Autowired
	private ContactRepository contactRepository;
	
	@Override
	public void addContact(Contact contact, String seniorSecurityString) {
		User user = userRepository.findUserBySecurityString(seniorSecurityString);
		Senior senior = seniorRepository.findSeniorByUser(user);
		
		contact.setSenior(senior);
		contactRepository.save(contact);
	}

	@Override
	public List<Contact> getAllSeniorContact(String seniorLogin) {
		User user = userRepository.findUserByLogin(seniorLogin);		
		if(user == null) throw new NullPointerException();
		
		Senior senior = seniorRepository.findSeniorByUser(user);
		
		List<Contact> listOfContacts = contactRepository.findBySenior(senior);

		return listOfContacts;
	}

	@Override
	public void deleteContact(Integer id) {
		Contact contact = contactRepository.findById(id);
		contactRepository.delete(contact);
	}

}
