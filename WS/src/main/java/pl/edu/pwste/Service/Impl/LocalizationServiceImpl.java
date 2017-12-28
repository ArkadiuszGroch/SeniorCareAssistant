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
	private SavedLocalizationRepository savedLocalizationRepository;

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
		savedLocalizationRepository.save(savedLocalization);

	}

	@Override
	public List<Localization> getLocalizationsOfDay(Date date, String seniorSecurityString) {
		User user = userRepository.findUserBySecurityString(seniorSecurityString);
		if (user == null)
			throw new NullPointerException();

		List<Localization> localizationList = localizationRepository.getLocalizationByDateAndUser(date, user);

		return localizationList;
	}

	@Override
	public List<Localization> getLocalizationsForSenior(int seniorId) {
		Senior senior = seniorRepository.findSeniorById(seniorId);
		return localizationRepository.getLocalizationBySenior(senior);
	}

	@Override
	public Double getDistanceFromHome(double latitudeHome, double longitudeHome, double latitudeCurrent, double longitudeCurrent) {
		double R = 6378.137; // Radius of earth in KM
		double dLat = latitudeCurrent * Math.PI / 180 - latitudeHome * Math.PI / 180;
		double dLon = longitudeCurrent * Math.PI / 180 - longitudeHome * Math.PI / 180;
		double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
				Math.cos(latitudeHome * Math.PI / 180) * Math.cos(latitudeCurrent * Math.PI / 180) *
						Math.sin(dLon/2) * Math.sin(dLon/2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		double d = R * c;
		return d * 1000; // meters
	}

	@Override
	public List<SavedLocalization> getSavedLocalizationsForSenior(int seniorId) {
		Senior senior = seniorRepository.findSeniorById(seniorId);
		return savedLocalizationRepository.getSavedLocalizationBySenior(senior);
	}
}
