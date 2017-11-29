package pl.edu.pwste.Service;

import pl.edu.pwste.Entity.Notification;

import java.util.List;

public interface NotificationService {
    public List<Notification> getNotificationForUser(String userLogin, String seniorLogin) throws Exception;

}
