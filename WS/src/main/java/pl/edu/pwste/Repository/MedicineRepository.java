package pl.edu.pwste.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pwste.Entity.Medicine;
@Repository
public interface MedicineRepository extends CrudRepository<Medicine, Long> {

}
