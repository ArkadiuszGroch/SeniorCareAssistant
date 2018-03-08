package pl.edu.pwste.Service;

import pl.edu.pwste.Entity.Care;
import pl.edu.pwste.Entity.Notification;

import java.util.List;

public interface NotificationService {
    List<Notification> getNotificationForUser(String userLogin, String seniorLogin) throws Exception;

    List<Notification> getNotificationForCare(Care care);

    void createNotification(Notification notification, String seniorSecurityString);

}
