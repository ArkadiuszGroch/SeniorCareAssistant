package pl.edu.pwste.Service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pwste.Entity.Care;
import pl.edu.pwste.Entity.CareAssistant;
import pl.edu.pwste.Entity.Senior;
import pl.edu.pwste.Entity.User;
import pl.edu.pwste.Repository.CareAssistantRepository;
import pl.edu.pwste.Repository.CareRepository;
import pl.edu.pwste.Repository.SeniorRepository;
import pl.edu.pwste.Repository.UserRepository;
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
        return listOfSeniors;
    }

}
