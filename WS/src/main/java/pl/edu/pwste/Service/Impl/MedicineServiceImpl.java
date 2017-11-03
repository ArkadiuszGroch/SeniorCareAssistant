package pl.edu.pwste.Service.Impl;

import org.springframework.stereotype.Service;
import pl.edu.pwste.Entity.Medicine;
import pl.edu.pwste.Entity.TimeOfReceipt;
import pl.edu.pwste.Service.MedicineService;

import java.util.List;

@Service
public class MedicineServiceImpl implements MedicineService {

	@Override
	public Boolean addTimesOfReceiptForMedicine(String careAssistantSecurityString, int idMedicine,
			List<TimeOfReceipt> listTimeOfReceipt) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Medicine addMedicineForSenior(String careAssistantSecurityString, String seniorLogin, Medicine medicine) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Medicine> getCurrentMedicines(String seniorLogin) {
		// TODO Auto-generated method stub
		return null;
	}

}
