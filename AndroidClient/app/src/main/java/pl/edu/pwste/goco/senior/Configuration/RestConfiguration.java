package pl.edu.pwste.goco.senior.Configuration;

/**
 * Created by goco on 05.11.2017.
 */

public class RestConfiguration {
    public RestConfiguration() {
    }

    public static String SENIOR_LOGIN;
    public static String SECURITY_STRING;
    public static String PORT = "8090";
    public static String URL = "http://192.168.1.107" + ":" + PORT;
//    public static String URL = "https://seniorservice.herokuapp.com" + ":" + PORT;

    public static String REGISTER = URL + "/account/senior/register";
    public static String LOGIN = URL + "/account/senior/login";

    private static String ADD_CONTACT = URL + "/" + SECURITY_STRING + "/addContact";
    private static String GET_CONTACTS = URL + "/getAllContacts/" + LOGIN;
    private static String GET_SAVED_LOCATIONS = URL + "/localization/" + SECURITY_STRING + "/getSavedLocations";
    private static String SEND_LOCATION = URL + "/localization/" + SECURITY_STRING + "/addCurrentLocalization";
    private static String SAVE_LOCATION = URL + "/localization/" + SECURITY_STRING + "/saveLocalization";
    private static String GET_CURRENT_MEDICINE = URL + "/getCurrentMedicine/" + SECURITY_STRING;

    private void loadSecurityString() {
        this.SECURITY_STRING = DataManager.loadSecurityString();
    }

    private void loadLogin() {
        this.LOGIN = DataManager.loadLogin();
    }


    public String getURLToAddContact() {
        loadSecurityString();
        return URL + "/" + SECURITY_STRING + "/addContact";
    }

    public String getURLToGetContacts() {
        loadLogin();
        return GET_CONTACTS = URL + "/contact/getAllContacts/" + LOGIN;
    }

    public String getURLToGetSavedLocations() {
        loadSecurityString();
        return URL + "/localization/" + SECURITY_STRING + "/getSavedLocations";
    }

    public String getURLSendLocation() {
        loadSecurityString();
        return URL + "/localization/" + SECURITY_STRING + "/addCurrentLocalization";
    }

    public String getURLToSaveLocation() {
        loadSecurityString();
        return URL + "/localization/" + SECURITY_STRING + "/saveLocalization";
    }


}
