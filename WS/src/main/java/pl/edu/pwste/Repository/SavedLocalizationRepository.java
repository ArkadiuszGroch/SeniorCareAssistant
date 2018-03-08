package pl.edu.pwste.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.edu.pwste.Entity.SavedLocalization;
import pl.edu.pwste.Entity.Senior;

import java.util.List;

@Repository
public interface SavedLocalizationRepository extends CrudRepository<SavedLocalization, Long> {

    List<SavedLocalization> getSavedLocalizationBySenior(Senior senior);

    @Query("SELECT sl FROM SavedLocalization sl WHERE sl.name like 'Home' AND sl.senior.id = ?1")
    SavedLocalization getHomeLocalizationBySenior(int senior);
}
