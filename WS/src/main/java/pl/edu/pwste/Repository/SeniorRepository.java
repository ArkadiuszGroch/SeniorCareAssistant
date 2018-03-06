package pl.edu.pwste.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pwste.Entity.Senior;
import pl.edu.pwste.Entity.User;

@Repository
public interface SeniorRepository extends CrudRepository<Senior, Long> {

    Senior findSeniorByUser(User user);

    Senior findSeniorById(int id);

}
