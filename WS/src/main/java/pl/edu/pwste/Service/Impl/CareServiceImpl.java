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

    @Override
    public List<String> findCareAssistantsBySenior(String seniorLogin) {
        Senior senior = seniorRepository.findSeniorByUser(userRepository.findUserByLogin(seniorLogin));
        List<Care> listOfCare = careRepository.findBySenior(senior);
        List<String> careAssistantList = new ArrayList<>();
        for (Care care : listOfCare) {
            CareAssistant careAssistant = care.getCareAssistant();
            careAssistantList.add(careAssistant.getUser().getPhone());
        }
        return careAssistantList;
    }

    private void hideSensitiveDataForUser(User user)
    {
        if (user.getPassword() != null)user.setPassword(null);
        if (user.getSecurityString() != null) user.setSecurityString(null);
        if (user.getEmail() != null) user.setEmail(null);
        if (user.getRoles() != null) user.setRoles(null);
        if (user.getCareAssistant() != null) user.setCareAssistant(null);
        if (user.getSenior() != null) user.setSenior(null);
    }
    private void hideSensitiveData(List<Senior> listOfSeniors) {
        for (Senior senior : listOfSeniors) {
            hideSensitiveDataForUser(senior.getUser());

            if (senior.getUser().getPhone() != null) senior.getUser().setPhone(null);
            if (senior.getCare() != null) senior.setCare(null);
            if (senior.getContacts() != null) senior.setContacts(null);
            if (senior.getLastModification() != null) senior.setLastModification(null);
            if (senior.getLocalization() != null) senior.setLocalization(null);
            if (senior.getMedicine() != null) senior.setMedicine(null);
            if (senior.getSavedLocalization() != null) senior.setSavedLocalization(null);
            if (senior.getLastSynchronization() != null) senior.setLastSynchronization(null);
        }
    }

}
