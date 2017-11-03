package pl.edu.pwste.Service;

import pl.edu.pwste.Entity.Medicine;
import pl.edu.pwste.Entity.TimeOfReceipt;

import java.util.List;

public interface MedicineService {

	Boolean addTimesOfReceiptForMedicine(String careAssistantSecurityString, int idMedicine,
			List<TimeOfReceipt> listTimeOfReceipt);

	Medicine addMedicineForSenior(String careAssistantSecurityString, String seniorLogin, Medicine medicine);

	List<Medicine> getCurrentMedicines(String seniorLogin);



}
