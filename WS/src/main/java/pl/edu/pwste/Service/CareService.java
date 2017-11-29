package pl.edu.pwste.Service;

import pl.edu.pwste.Entity.Care;
import pl.edu.pwste.Entity.CareAssistant;
import pl.edu.pwste.Entity.Senior;

import java.util.List;

public interface CareService {

	public void addSeniorToCareAssistant(String seniorLogin, String careAssistantLogin);

	public List<Senior> findAllSeniorsForCareAssistant(CareAssistant careAssistant);

}
