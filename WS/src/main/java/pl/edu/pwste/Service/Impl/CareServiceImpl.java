package pl.edu.pwste.Service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pwste.Entity.*;
import pl.edu.pwste.Repository.*;
import pl.edu.pwste.Service.CareService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class CareServiceImpl implements CareService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SeniorRepository seniorRepository;

    @Autowired
    private CareAssistantRepository careAssistantRepository;

    @Autowired
    private CareRepository careRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public void addSeniorToCareAssistant(String seniorLogin, String careAssistantLogin) {
        User seniorUser = userRepository.findUserByLogin(seniorLogin);
        Senior s = seniorRepository.findSeniorByUser(seniorUser);

        User careAssistantUser = userRepository.findUserByLogin(careAssistantLogin);
        CareAssistant ca = careAssistantRepository.findCareAssistantByUser(careAssistantUser);

        Care care = new Care();
        care.setSenior(s);
        care.setCareAssistant(ca);

        careRepository.save(care);

    }

    @Override
    public List<Senior> findAllSeniorsForCareAssistant(CareAssistant careAssistant) {
        Set result = careRepository.findByCareAssistant(careAssistant);
        List<Care> listOfCare = new ArrayList<>();
        listOfCare.addAll(result);
        List<Senior> listOfSeniors = new ArrayList<>();
        for (Care care : listOfCare) {
            listOfSeniors.add(care.getSenior());
        }
        hideSensitiveData(listOfSeniors);
        return listOfSeniors;
    }

    @Override
    public Care findCareBySeniorAndCareAssistant(Senior senior, CareAssistant careAssistant) {
        return careRepository.findByCareAssistantAndSenior(careAssistant,senior);
    }

    @Override
    public void removeCare(Care care) {
        List<Notification> listOfNotification = new ArrayList<>();
        listOfNotification.addAll(care.getNotification());
        for (Notification notification:listOfNotification) {
            notificationRepository.delete(notification);
        }
        careRepository.delete(care);
    }

    private void hideSensitiveData(List<Senior> listOfSeniors) {
        for (Senior senior : listOfSeniors) {
            if (senior.getUser().getPassword() != null) senior.getUser().setPassword(null);
            if (senior.getUser().getSecurityString() != null) senior.getUser().setSecurityString(null);
            if (senior.getUser().getEmail() != null) senior.getUser().setEmail(null);
            if (senior.getUser().getRoles() != null) senior.getUser().setRoles(null);
            if (senior.getUser().getPhone() != null) senior.getUser().setPhone(null);
            if (senior.getUser().getCareAssistant() != null) senior.getUser().setCareAssistant(null);
            if (senior.getUser().getSenior() != null) senior.getUser().setSenior(null);
            if (senior.getCare() != null) senior.setCare(null);
            if (senior.getContacts() != null) senior.setContacts(null);
            if (senior.getLastModification() != null) senior.setLastModification(null);
            if (senior.getLocalization() != null) senior.setLocalization(null);
            if (senior.getLocationUpdateFrequency() != null) senior.setLocationUpdateFrequency(null);
            if (senior.getMedicine() != null) senior.setMedicine(null);
            if (senior.getSavedLocalization() != null) senior.setSavedLocalization(null);
            if (senior.getLastSynchronization() != null) senior.setLastSynchronization(null);
        }
    }

}
