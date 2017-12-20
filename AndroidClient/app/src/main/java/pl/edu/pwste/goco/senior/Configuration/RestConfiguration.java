package pl.edu.pwste.goco.senior.Configuration;

/**
 * Created by goco on 05.11.2017.
 */

public class RestConfiguration {

    public static String SENIOR_LOGIN ;
    public static String SECURITY_STRING;
    public static String PORT = "8090";
    public static String URL = "http://192.168.1.109" +  ":" + PORT;
    public static String REGISTER = URL + "/account/senior/register";
    public static String LOGIN = URL + "/account/senior/login";
    public static String ADD_CONTACT = URL + "/" + SECURITY_STRING + "/addContact";
    public static String GET_CONTACTS = URL + "/getAllContacts/" + SENIOR_LOGIN;
    public static String SEND_LOCATION = URL + "/localization/" + SECURITY_STRING + "/addCurrentLocalization";
    private static String SAVE_LOCATION = URL + "/localization/" + SECURITY_STRING + "/saveLocalization";
    public static String GET_CURRENT_MEDICINE = URL + "/getCurrentMedicine/" + SENIOR_LOGIN;

    public static String getURLToSaveLocation(String securityString){
        return  URL + "/localization/" + securityString + "/saveLocalization";
    }
    public static String getURLToSaveCurrentLocation(String securityString){
        return  URL + "/localization/" + securityString + "/addCurrentLocalization";
    }
}
