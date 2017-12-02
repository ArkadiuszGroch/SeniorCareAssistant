package Configuration;

/**
 * Created by goco on 05.11.2017.
 */

public class RestConfiguration {
    public static String SENIOR_LOGIN = null;
    public static String SECURITY_STRING = null;
    public static String URL = "http://192.168.1.103:8090";
    public static String REGISTER = URL + "/senior/register";
    public static String LOGIN = URL + "/senior/login";
    public static String ADD_CONTACT = URL + "/" + SECURITY_STRING + "/addContact";
    public static String GET_CONTACTS = URL + "/getAllContacts/" + SENIOR_LOGIN;
    public static String SEND_LOCATION = URL + "/" + SECURITY_STRING + "/addCurrentLocalization";
    public static String SAVE_LOCATION = URL + "/" + SECURITY_STRING + "/saveLocalization";
    public static String GET_CURRENT_MEDICINE = URL + "/getCurrentMedicine/" + SENIOR_LOGIN;
}
