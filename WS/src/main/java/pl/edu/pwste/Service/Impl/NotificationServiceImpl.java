package pl.edu.pwste.Service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pwste.Entity.Care;
import pl.edu.pwste.Entity.CareAssistant;
import pl.edu.pwste.Entity.Notification;
import pl.edu.pwste.Entity.Senior;
import pl.edu.pwste.Repository.CareRepository;
import pl.edu.pwste.Repository.NotificationRepository;
import pl.edu.pwste.Service.NotificationService;
import pl.edu.pwste.Service.UserService;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private CareRepository careRepository;


    @Override
    public List<Notification> getNotificationForUser(String userLogin, String seniorLogin) throws Exception {
        CareAssistant careAssistant = userService.findCareAssistantByLogin(userLogin);
        Senior senior = userService.findSeniorByLogin(seniorLogin);
        List<Notification> listOfNotification;
        if (senior == null || careAssistant == null) {
            throw new Exception();
        } else {
            Care care = careRepository.findByCareAssistantAndSenior(careAssistant, senior);
            listOfNotification = notificationRepository.getNotificationsByCare(care);
            return listOfNotification;
        }
    }

    @Override
    public List<Notification> getNotificationForCare(Care care) {
        List<Notification> listOfNotifications = notificationRepository.getNotificationsByCare(care);
        return listOfNotifications;
    }

    @Override
    public void createNotification(Notification notification, String seniorSecurityString) {
        Senior senior = userService.findSeniorBySecStr(seniorSecurityString);
        List<Care> careList = careRepository.findBySenior(senior);
        for (Care care : careList) {
            notification.setCare(care);
            notificationRepository.save(notification);
        }
    }
}