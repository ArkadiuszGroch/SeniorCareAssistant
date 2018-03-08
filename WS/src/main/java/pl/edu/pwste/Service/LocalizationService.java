package pl.edu.pwste.Service;

import pl.edu.pwste.Entity.Localization;
import pl.edu.pwste.Entity.SavedLocalization;
import pl.edu.pwste.Entity.Senior;

import java.sql.Date;
import java.util.List;

public interface LocalizationService {

	void addCurrentLocation(Localization localization, String seniorSecurityString);

	void addSavedLocation(SavedLocalization savedLocalization, String seniorSecurityString);

	List<Localization> getLocalizationsOfDay(Date date, String seniorSecurityString);

	List<Localization> getLocalizationsForSenior(int seniorId);

	Double getDistanceFromHome(double latitudeHome, double longitudeHome, double latitudeCurrent, double longitudeCurrent);

	List<SavedLocalization> getSavedLocalizationsForSenior(int seniorId);

    SavedLocalization getHomeLocalizationForSenior(int seniorId);
}
