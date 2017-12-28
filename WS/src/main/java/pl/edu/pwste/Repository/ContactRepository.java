package pl.edu.pwste.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pwste.Entity.Contact;
import pl.edu.pwste.Entity.Senior;

import java.util.List;

@Repository
public interface ContactRepository extends CrudRepository<Contact, Long> {
	
	public List<Contact> findBySenior(Senior senior);

	public Contact findById(Integer id);

}
