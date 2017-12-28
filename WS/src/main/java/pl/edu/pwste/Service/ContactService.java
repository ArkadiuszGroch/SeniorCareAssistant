package pl.edu.pwste.Service;

import pl.edu.pwste.Entity.Contact;

import java.util.List;

public interface ContactService {

	public void addContact(Contact contacts, String seniorSecurityString);

	public List<Contact> getAllSeniorContact(String seniorLogin);

	public void deleteContact(Integer id);

}
