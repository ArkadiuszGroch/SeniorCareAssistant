package pl.edu.pwste.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.edu.pwste.Entity.Localization;
import pl.edu.pwste.Entity.Senior;
import pl.edu.pwste.Entity.User;

import java.sql.Date;
import java.util.List;

@Repository
public interface LocalizationRepository extends CrudRepository<Localization, Long> {

	@Query("SELECT l FROM Localization l"
			+ " WHERE year(l.time as date) = year(:date)"
			+ " AND month(l.time as date) = month(:date)"
			+ " AND day(l.time as date) = day(:date)"
			+ " AND l.senior.user = :user")
	public List<Localization> getLocalizationByDateAndUser(	@Param("date") Date date,
															@Param("user") User user);

	public List<Localization> getLocalizationBySenior(Senior senior);
}
