package pl.edu.pwste.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pwste.Entity.Care;

@Repository
public interface CareRepository extends CrudRepository<Care, Long> {

}
