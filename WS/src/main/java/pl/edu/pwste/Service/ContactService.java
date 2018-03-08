package pl.edu.pwste.Service;

import pl.edu.pwste.Entity.Contact;

import java.util.List;

public interface ContactService {

	void addContact(Contact contact, String seniorSecurityString);

	void addContact(Contact contact, Integer seniorId);

	List<Contact> getAllSeniorContact(String seniorLogin);

	void deleteContact(Integer id);

}
