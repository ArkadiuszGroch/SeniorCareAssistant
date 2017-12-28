package pl.edu.pwste.Service;

import pl.edu.pwste.Entity.Localization;
import pl.edu.pwste.Entity.SavedLocalization;
import pl.edu.pwste.Entity.Senior;

import java.sql.Date;
import java.util.List;

public interface LocalizationService {

	public void addCurrentLocation(Localization localization, String seniorSecurityString);

	public void addSavedLocation(SavedLocalization savedLocalization, String seniorSecurityString);

	public List<Localization> getLocalizationsOfDay(Date date, String seniorSecurityString);

	public List<Localization> getLocalizationsForSenior(int seniorId);

	public Double getDistanceFromHome(double latitudeHome, double longitudeHome,double latitudeCurrent,double longitudeCurrent);

	public List<SavedLocalization> getSavedLocalizationsForSenior(int seniorId);
}
