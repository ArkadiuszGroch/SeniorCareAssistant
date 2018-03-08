package pl.edu.pwste.Service;

import pl.edu.pwste.Entity.CareAssistant;
import pl.edu.pwste.Entity.Senior;

public interface AccountService {

	void registerSenior(Senior senior);

	String loginSenior(Senior senior);

	void registerCareAssistant(CareAssistant careAssistant);

	String loginCareAssistant(CareAssistant careAssistant);

	CareAssistant findCareAssistantByLoginOrPassword(String login, String password);

	CareAssistant findCareAssistantByLoginOrEmail(String login, String email);

	void updateSenior(Senior senior);
}
