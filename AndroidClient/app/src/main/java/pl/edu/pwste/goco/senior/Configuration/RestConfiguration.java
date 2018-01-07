package pl.edu.pwste.goco.senior.Configuration;

/**
 * Created by goco on 05.11.2017.
 */

public class RestConfiguration {
    public RestConfiguration() {
        this.SECURITY_STRING = DataManager.loadSecurityString();
    }

    public static String SENIOR_LOGIN;
    public static String SECURITY_STRING;
    public static String PORT = "8090";
    public static String URL = "http://192.168.1.109" + ":" + PORT;
    public static String REGISTER = URL + "/account/senior/register";
    public static String LOGIN = URL + "/account/senior/login";

    private static String ADD_CONTACT = URL + "/" + SECURITY_STRING + "/addContact";
    private static String GET_CONTACTS = URL + "/getAllContacts/" + SECURITY_STRING;
    private static String GET_SAVED_LOCATIONS = URL + "/localization/" + SECURITY_STRING + "/getSavedLocations";
    private static String SEND_LOCATION = URL + "/localization/" + SECURITY_STRING + "/addCurrentLocalization";
    private static String SAVE_LOCATION = URL + "/localization/" + SECURITY_STRING + "/saveLocalization";
    private static String GET_CURRENT_MEDICINE = URL + "/getCurrentMedicine/" + SECURITY_STRING;

    private void loadSecurityString() {
        this.SECURITY_STRING = DataManager.loadSecurityString();
    }


    public String getURLToAddContact() {
        loadSecurityString();
        return ADD_CONTACT;
    }

    public String getURLToGetContacts() {
        loadSecurityString();
        return GET_CONTACTS;
    }

    public String getURLToGetSavedLocations() {
        loadSecurityString();
        return GET_SAVED_LOCATIONS;
    }

    public String getURLSendLocation() {
        loadSecurityString();
        return SEND_LOCATION;
    }

    public String getURLToSaveLocation() {
        loadSecurityString();
        return SAVE_LOCATION;
    }


}
