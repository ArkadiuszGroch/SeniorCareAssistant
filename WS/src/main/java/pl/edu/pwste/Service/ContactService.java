package pl.edu.pwste.Service;

import pl.edu.pwste.Entity.Contact;

import java.util.List;

public interface ContactService {

	public void addContact(Contact contact, String seniorSecurityString);

	public void addContact(Contact contact, Integer seniorId);

	public List<Contact> getAllSeniorContact(String seniorLogin);

	public void deleteContact(Integer id);

}
