package pl.edu.pwste.Controller.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwste.Entity.Medicine;
import pl.edu.pwste.Entity.TimeOfReceipt;
import pl.edu.pwste.Service.MedicineService;

import java.util.List;


@RequestMapping(value = "/medicine")
@RestController
public class MedicineController {

	@Autowired
	MedicineService medicineService;
	
	/* Add medicine for senior 
	 * 	Method: POST
	 *  URL: address: /medicine/(careAssistantSecurityString)/addMedicine/(seniorLogin)
	 *  Input: JSON Medicine
	 *  JSON Example: {"name":"name of medicine",
				"description":"description of medicine",
				"startDate":1493642463910,
				"endDate":1493642466910
				}
	 *  Output: HTTP Status No_CONTENT (204) if not added
	 *  		HTTP Status Ok (200) if added
	 */
	@RequestMapping(value = "/{careAssistantSecurityString}/addMedicine/{seniorLogin}", method = RequestMethod.POST)
	public ResponseEntity<Medicine> addMedicine(@PathVariable(value = "careAssistantSecurityString") String careAssistantSecurityString,
			@PathVariable(value = "seniorLogin") String seniorLogin, @RequestBody Medicine medicine) {
		Medicine newMedicine = medicineService.addMedicineForSenior(careAssistantSecurityString, seniorLogin, medicine);
		if (newMedicine == null) {
			return new ResponseEntity<Medicine>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<Medicine>(newMedicine, HttpStatus.OK);
		}
	}
	
	
	/* Add times of receipt for medicine
	 * 	Method: POST
	 *  URL: address: /medicine/(careAssistantSecurityString)/addTimesOfReceipt/(seniorLogin)/medicine/(IdOfMedicine)
	 *  Input: JSON List of TimesOfReceipt
	 *  JSON Example: [{"time":"10:00:00","dosege":"30mg"},
						{"time":"12:30:00","dosege":"30mg"},
						{"time":"17:45:00","dosege":"30mg"}]
	 *  Output: HTTP Status No_CONTENT (204) if not added
	 *  		HTTP Status Ok (200) if added
	 */
	@RequestMapping(value = "/{careAssistantLogin}/addTimesOfReceipt/{seniorLogin}/medicine/{idMedicine}", method = RequestMethod.POST)
	public ResponseEntity<List<TimeOfReceipt>> addTimeOfReceiptToMedicine(@PathVariable(value = "careAssistantSecurityString") String careAssistantSecurityString,
			@PathVariable(value = "seniorLogin") String seniorLogin,@PathVariable(value = "idMedicine") int idMedicine, @RequestBody List<TimeOfReceipt> listTimeOfReceipt) {
		Boolean addedTimesOfReceipt = medicineService.addTimesOfReceiptForMedicine(careAssistantSecurityString, idMedicine, listTimeOfReceipt);
		if (!addedTimesOfReceipt) {
			return new ResponseEntity<List<TimeOfReceipt>>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<List<TimeOfReceipt>>(HttpStatus.OK);
		}
	}
	
	
	/* Get all current medicines for selected senior 
	 * Method: GET
	 * URL: address: /medicine/getCurrentMedicine/(SeniorLogin)
	 * Input: -
	 * Output: HTTP Status No_CONTENT (204) if error
	 * 		HTTP Status Ok (200) if getting successful and List of Medicine
	 */
	@RequestMapping(value = "/getCurrentMedicine/{seniorLogin}", method = RequestMethod.GET)
	public ResponseEntity<List<Medicine>> getCurrentMedicine(
			@PathVariable(value = "seniorLogin") String seniorLogin) {

		List<Medicine> medicines = medicineService.getCurrentMedicines(seniorLogin);
		if (medicines == null) {
			return new ResponseEntity<List<Medicine>>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<List<Medicine>>(medicines, HttpStatus.OK);
		}
	}
}