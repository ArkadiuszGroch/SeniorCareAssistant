package pl.edu.pwste.Service;

import pl.edu.pwste.Entity.CareAssistant;
import pl.edu.pwste.Entity.Senior;

public interface AccountService {

	public void registerSenior(Senior senior);

	public String loginSenior(Senior senior);

	public void registerCareAssistant(CareAssistant careAssistant);

	public String loginCareAssistant(CareAssistant careAssistant);

	public CareAssistant findCareAssistantByLoginOrPassword(String login, String password);

	public CareAssistant findCareAssistantByLoginOrEmail(String login, String email);

	public void updateSenior(Senior senior);
}
