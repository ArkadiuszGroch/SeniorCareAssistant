package pl.edu.pwste.goco.senior.Configuration;

/**
 * Created by goco on 05.11.2017.
 */

public class RestConfiguration {

    public RestConfiguration() {
    }

    private static String LOGIN;
    private static String SECURITY_STRING;
    private static String PORT = "8090";
    //    public static String URL = "https://seniorservice.herokuapp.com";
    private static String URL = "http://192.168.1.12" + ":" + PORT;


    //Load

    private void loadSecurityString() {
        this.SECURITY_STRING = DataManager.loadSecurityString();
    }

    private void loadLogin() {
        this.LOGIN = DataManager.loadLogin();
    }

    //get url methods
    public static String getURLToRegister() {
        return URL + "/account/senior/register";
    }

    public String getURLTologin() {
        return URL + "/account/senior/login";
    }

    public String getURLToGetHomeLocation() {
        loadSecurityString();
        return URL + "/localization/" + SECURITY_STRING + "/getHomeLocation";
    }

    public String getURLToGetSafeDistance() {
        loadSecurityString();
        return URL + "/account/" + SECURITY_STRING + "/getSafeDistance";
    }

    public String getURLToGetLocationUpdateFrequency() {
        loadSecurityString();
        return URL + "/account/" + SECURITY_STRING + "/getLocationUpdateFrequency";
    }

    public String getURLToAddContact() {
        loadSecurityString();
        return URL + "/" + SECURITY_STRING + "/addContact";
    }

    public String getURLToGetContacts() {
        loadLogin();
        return URL + "/contact/getAllContacts/" + LOGIN;
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

    public String getURLToGetCareAssistantsPhones() {
        loadLogin();
        return URL + "/care/" + LOGIN + "/getCareAssistants";
    }

    public String getURLToSaveNotification() {
        loadSecurityString();
        return URL + "/notification/" + SECURITY_STRING + "/createNotification";
    }


}
