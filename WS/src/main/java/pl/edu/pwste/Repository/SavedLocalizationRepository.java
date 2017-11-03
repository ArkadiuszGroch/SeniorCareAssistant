package pl.edu.pwste.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pwste.Entity.SavedLocalization;
@Repository
public interface SavedLocalizationRepository extends CrudRepository<SavedLocalization, Long> {

}
