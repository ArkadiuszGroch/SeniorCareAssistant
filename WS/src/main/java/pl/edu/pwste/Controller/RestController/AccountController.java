package pl.edu.pwste.Controller.RestController;

import com.sun.media.sound.InvalidDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwste.Entity.CareAssistant;
import pl.edu.pwste.Entity.Senior;
import pl.edu.pwste.Service.AccountService;

@RequestMapping(value = "/account")
@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    // ====================================================================================================================
    /* Register senior
	 * 	Method: POST
	 *  URL: address: /account/senior/register
	 *  Input: JSON Senior
	 *  JSON Example: {
					  "locationUpdateFrequency": "00:01:00",
					  "user": {
					    "login": "Login",
					    "password": "Password",
					    "email":"jan@wp.pl",
					    "firstName": "FirstName",
					    "lastName": "LastName",
					    "phone": "100200300"
					  }
					}
	 *  Output: HTTP Status No_CONTENT (204) if register failed
	 *  		HTTP Status Ok (200) and JSON created Senior object if register successful
	 * */
    @RequestMapping(value = "/senior/register", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    ResponseEntity<String> registerSenior(@RequestBody Senior senior) {
        try {
            accountService.registerSenior(senior);
            return new ResponseEntity<String>(senior.getUser().getSecurityString(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
        }
    }


    /* Login senior
     * 	Method: POST
     *  URL: address: /account/senior/login
     *  Input: JSON Senior
     *  JSON Example: {
                      "user": {
                        "login": "Login",
                        "password": "Password"
                      }
                    }
     *  Output: HTTP Status No_CONTENT (204) if login failed
     *  		HTTP Status Ok (200) and JSON object Senior if login successful
     */
    @RequestMapping(value = "/senior/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<String> loginSenior(@RequestBody Senior senior) {
        try {
            String secString = accountService.loginSenior(senior);
            if (secString == null) throw new InvalidDataException();
            else return new ResponseEntity<String>(secString, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
        }
    }

    /* Register care assistant
     * 	Method: POST
     *  URL: address: /account/careAssistant/register
     *  Input: JSON CareAssistant
     *  JSON Example: {
                      "user": {
                        "login": "CareAssistantLogin2",
                        "password": "Password",
                        "email": "email@wp.pl",
                        "firstName": "First Name",
                        "lastName": "Last Name",
                        "phone": "300200100"
                      }
                    }
     *  Output: HTTP Status No_CONTENT (201) if login failed
     *  		HTTP Status Ok (200) and JSON object CareAssistant if register successful
     * */
    @RequestMapping(value = "/careAssistant/register", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<String> registerCareAssistant(@RequestBody CareAssistant careAssistant) {
        try {
            accountService.registerCareAssistant(careAssistant);
            return new ResponseEntity<String>(careAssistant.getUser().getSecurityString(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
        }
    }

    /* Login care assistant
     * 	Method: GET
     *  URL: address: /account/careAssistant/login
     *  Input: JSON Senior
     *  JSON Example: {
                  "user": {
                    "login": "CareAssistantLogin2",
                    "password": "Password"
                  }
                }
     *  Output: HTTP Status No_CONTENT (201) if login failed
     *  		HTTP Status Ok (200) and JSON object care assistant if login successful
     * */
    @RequestMapping(value = "/careAssistant/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<String> loginCareAssistant(@RequestBody CareAssistant careAssistant) {
        try {
            String secString = accountService.loginCareAssistant(careAssistant);
            return new ResponseEntity<String>(secString, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
        }
    }

}
