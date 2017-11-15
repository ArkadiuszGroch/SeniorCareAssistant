package pl.edu.pwste.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwste.Entity.Localization;
import pl.edu.pwste.Entity.SavedLocalization;
import pl.edu.pwste.Service.LocalizationService;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;



@RequestMapping(value = "/localization")
@RestController
public class LocalizationController {

	@Autowired
	LocalizationService localizationService;

	/* Save current localization for senior 
	 * 	Method: POST
	 *  URL: address: /localization/(SeniorSecurityString)/addCurrentLocalization
	 *  Input: JSON Localization
	 *  JSON Example: {
			  "latitude": 200,
			  "longitude": 100,
			}
	 *  Output: HTTP Status No_CONTENT (204) if not added
	 *  		HTTP Status Ok (200) if added
	 * */
	@RequestMapping(value = "/{SeniorSecurityString}/addCurrentLocalization", method = RequestMethod.POST)
	public ResponseEntity<Localization> addCurrentLocalization(@PathVariable(value = "SeniorSecurityString") String seniorSecurityString,
			@RequestBody Localization localization) {
		try {
			localizationService.addCurrentLocation(localization, seniorSecurityString);
			return new ResponseEntity<Localization>(HttpStatus.OK); 
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Localization>(HttpStatus.NO_CONTENT);
		}
	}

	/* Save localization for senior 
	 * 	Method: POST
	 *  URL: address: /localization/(seniorSecurityString)/saveLocalization
	 *  Input: JSON SavedLocalization
	 *  JSON Example: {
			  "name": "Name of place",
			  "latitude": 1,
			  "longitude": 2
			}
	 *  Output: HTTP Status No_CONTENT (204) if not added
	 *  		HTTP Status Ok (200) if added
	 */
	@RequestMapping(value = "/{seniorSecurityString}/saveLocalization", method = RequestMethod.POST)
	public ResponseEntity<SavedLocalization> saveLocalization(@PathVariable(value = "seniorSecurityString") String seniorSecurityString,
			@RequestBody SavedLocalization savedLocalization) {
		try {
			localizationService.addSavedLocation(savedLocalization, seniorSecurityString);
			return new ResponseEntity<SavedLocalization>(HttpStatus.OK); 
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<SavedLocalization>(HttpStatus.NO_CONTENT);
		}
	}
	
	/* Get localizations of selected day
	 * 	Method: GET
	 *  URL: address: /localization/(seniorSecurityString)/getLocalizations/(date format YYYY:mm:dd)
	 *  Input: -
	 *  Output: HTTP Status No_CONTENT (204) if error
	 *  		HTTP Status Ok (200) if selected
	 */
	@RequestMapping(value = "/{seniorSecurityString}/getLocalizations/{date}", method = RequestMethod.GET)
	public ResponseEntity<List<Localization>> getLocalizationsOfDay(@PathVariable(value = "seniorSecurityString") String seniorSecurityString,
			@PathVariable(value = "date") String stringDate) {
		try {
			//TODO repair format date - 1970 years and -1 month

			SimpleDateFormat sdf1 = new SimpleDateFormat("YYYY:mm:dd");
			java.util.Date date = sdf1.parse(stringDate);
			java.sql.Date sqlStartDate = new Date(date.getTime()); 
			System.out.println(sqlStartDate);
			List<Localization> listOfLocalization =  localizationService.getLocalizationsOfDay(sqlStartDate, seniorSecurityString);
			return new ResponseEntity<List<Localization>>(listOfLocalization, HttpStatus.OK); 
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<Localization>>(HttpStatus.NO_CONTENT);
		}
	}
	
}