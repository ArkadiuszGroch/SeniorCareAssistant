package pl.edu.pwste.Controller.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwste.Entity.Contact;
import pl.edu.pwste.Service.ContactService;

import java.util.List;


@RequestMapping(value = "/contact")
@RestController
public class ContactController {

	@Autowired 
	ContactService contactService;

	
	/* Add contact for senior 
	 * 	Method: POST
	 *  URL: address: /contact/(SeniorSecurityString)/addContact
	 *  Input: JSON Contacts
	 *  JSON Example: {
			  "name": "Name of contact",
			  "phone": "600500400"
			}
	 *  Output: HTTP Status No_CONTENT (204) if not added
	 *  		HTTP Status Ok (200) if added
	 */
	@RequestMapping(value = "/{SeniorSecurityString}/addContact", method = RequestMethod.POST)
	public ResponseEntity<String> addContact(@PathVariable(value = "SeniorSecurityString") String SeniorSecurityString,
			@RequestBody Contact contact) {		
		try {
			contactService.addContact(contact, SeniorSecurityString);
			return new ResponseEntity<String>(HttpStatus.OK); 
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		}
	}
	
	
	/*
	 * Get all contacts for selected senior 
	 * Method: GET
	 * URL: address: /contact/getAllContacts/(SeniorLogin)
	 * Input: -
	 * Output: HTTP Status No_CONTENT (204) if error
	 * 		HTTP Status Ok (200) if getting successful and List of Contacts
	 */
	@RequestMapping(value = "/getAllContacts/{seniorLogin}", method = RequestMethod.GET)
	public ResponseEntity<List<Contact>> getNotifications(
			@PathVariable(value = "seniorLogin") String seniorLogin) {		
		try {
			List<Contact> listOfContacts = contactService.getAllSeniorContact(seniorLogin);
			return new ResponseEntity<List<Contact>>(listOfContacts, HttpStatus.OK); 
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<Contact>>(HttpStatus.NO_CONTENT);
		}
	}
	
}