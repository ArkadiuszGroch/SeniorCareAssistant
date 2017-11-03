package pl.edu.pwste.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pwste.Entity.CareAssistant;
import pl.edu.pwste.Entity.User;

@Repository
public interface CareAssistantRepository extends CrudRepository<CareAssistant, Long> {
	
	public CareAssistant findCareAssistantByUser(User user);
	
}
