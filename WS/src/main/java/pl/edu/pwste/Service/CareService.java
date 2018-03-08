package pl.edu.pwste.Service;

import pl.edu.pwste.Entity.Care;
import pl.edu.pwste.Entity.CareAssistant;
import pl.edu.pwste.Entity.Senior;

import java.util.List;

public interface CareService {

	void addSeniorToCareAssistant(String seniorLogin, String careAssistantLogin);

	List<Senior> findAllSeniorsForCareAssistant(CareAssistant careAssistant);

	Care findCareBySeniorAndCareAssistant(Senior senior, CareAssistant careAssistant);

	void removeCare(Care care);

	List<String> findCareAssistantsBySenior(String seniorLogin);
}
