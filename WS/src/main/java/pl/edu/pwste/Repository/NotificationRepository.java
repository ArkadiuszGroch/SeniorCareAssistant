package pl.edu.pwste.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pwste.Entity.Care;
import pl.edu.pwste.Entity.Notification;

import java.util.List;
@Repository
public interface NotificationRepository extends CrudRepository<Notification, Long> {
    List<Notification> getNotificationsByCare(Care care);

}
