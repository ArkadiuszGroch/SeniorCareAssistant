package pl.edu.pwste.Controller.RestController;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pwste.Service.CareService;



@RequestMapping(value = "/care")
@RestController
public class CareController {

	@Autowired
	private CareService careService;

	/*
	 * Adding senior to care assistant 
	 * Method: POST 
	 * URL: address: /care/(CareAssistantLogin)/addSenior/(SeniorLogin)
	 * Input: -
	 * Output: HTTP Status No_CONTENT (204) if adding failed 
	 * 			HTTP Status Ok (200) if adding successful
	 */
	@RequestMapping(value = "/{careAssistantLogin}/addSenior/{seniorLogin}", method = RequestMethod.POST)
	public ResponseEntity<String> addSeniorToCareAssistant(
			@PathVariable(value = "careAssistantLogin") String careAssistantLogin,
			@PathVariable(value = "seniorLogin") String seniorLogin) {
		try{
			careService.addSeniorToCareAssistant(seniorLogin, careAssistantLogin);
			return new ResponseEntity<String>(HttpStatus.OK);
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		}
	}	
}