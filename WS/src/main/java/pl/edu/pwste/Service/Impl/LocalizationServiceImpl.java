package pl.edu.pwste.Service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pwste.Entity.Localization;
import pl.edu.pwste.Entity.SavedLocalization;
import pl.edu.pwste.Entity.Senior;
import pl.edu.pwste.Entity.User;
import pl.edu.pwste.Repository.LocalizationRepository;
import pl.edu.pwste.Repository.SavedLocalizationRepository;
import pl.edu.pwste.Repository.SeniorRepository;
import pl.edu.pwste.Repository.UserRepository;
import pl.edu.pwste.Service.LocalizationService;

import java.sql.Date;
import java.util.List;

@Service
public class LocalizationServiceImpl implements LocalizationService {

	@Autowired
	private SeniorRepository seniorRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private LocalizationRepository localizationRepository;

	@Autowired
	private SavedLocalizationRepository savedlocalizationRepository;

	@Override
	public void addCurrentLocation(Localization localization, String seniorSecurityString) {
		User user = userRepository.findUserBySecurityString(seniorSecurityString);
		if (user == null)
			throw new NullPointerException();

		Senior senior = seniorRepository.findSeniorByUser(user);

		localization.setSenior(senior);
		localizationRepository.save(localization);

	}

	@Override
	public void addSavedLocation(SavedLocalization savedLocalization, String seniorSecurityString) {
		User user = userRepository.findUserBySecurityString(seniorSecurityString);
		if (user == null)
			throw new NullPointerException();

		Senior senior = seniorRepository.findSeniorByUser(user);

		savedLocalization.setSenior(senior);
		savedlocalizationRepository.save(savedLocalization);

	}

	@Override
	public List<Localization> getLocalizationsOfDay(Date date, String seniorSecurityString) {
		User user = userRepository.findUserBySecurityString(seniorSecurityString);
		if (user == null)
			throw new NullPointerException();

		List<Localization> localizationList = localizationRepository.getLocalizationByDateAndUser(date, user);

		return localizationList;
	}

}
