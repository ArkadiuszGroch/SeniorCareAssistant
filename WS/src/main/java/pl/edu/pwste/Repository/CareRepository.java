package pl.edu.pwste.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pwste.Entity.Care;
import pl.edu.pwste.Entity.CareAssistant;
import pl.edu.pwste.Entity.Senior;

import java.util.List;
import java.util.Set;

@Repository
public interface CareRepository extends CrudRepository<Care, Long> {

    Set<Care> findByCareAssistant(CareAssistant careAssistant);

    List<Care> findBySenior(Senior senior);

    Care findByCareAssistantAndSenior(CareAssistant careAssistant, Senior senior);
}
