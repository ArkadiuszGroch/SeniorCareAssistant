package pl.edu.pwste.Controller.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwste.Entity.Notification;
import pl.edu.pwste.Service.NotificationService;

@RequestMapping(value = "/notification")
@RestController

public class NotificationController {
    @Autowired
    private NotificationService notificationService;


    /* Add new notification
     * 	Method: POST
     *  URL: address: /notification/(SeniorSecurityString)/createNotification
     *  Input: JSON Notification
     *  JSON Example: {
     *  //todo
              "name": "Name of contact",
              "phone": "600500400"
            }
     *  Output: HTTP Status No_CONTENT (204) if not added
     *  		HTTP Status Ok (200) if added
     */
    @RequestMapping(value = "/{SeniorSecurityString}/createNotification", method = RequestMethod.POST)
    public ResponseEntity<String> createNotification(@PathVariable(value = "SeniorSecurityString") String seniorSecurityString,
                                                     @RequestBody Notification notification) {
        try {
            notificationService.createNotification(notification, seniorSecurityString);
            return new ResponseEntity<String>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
        }
    }
}
