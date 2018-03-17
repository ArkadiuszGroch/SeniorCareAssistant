package pl.edu.pwste.goco.senior.Configuration;

import android.os.Environment;
import android.util.Log;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import pl.edu.pwste.goco.senior.Entity.SavedLocalization;
import pl.edu.pwste.goco.senior.Entity.Senior;
import pl.edu.pwste.goco.senior.Entity.User;

/**
 * Created by goco on 19.12.2017.
 */

public class DataManager {

    /**
     * File where value should be saved
     */
    private static String fileName = "/data.txt";


    private static int LOGIN_ROW = 0;
    private static int PASSWORD_ROW = 1;
    private static int SECURITY_DISTANCE_ROW = 2;
    private static int SECURITY_STRING_ROW = 3;
    private static int CARE_ASSISTANTS_ROW = 4;
    private static int HOME_LOCATION_ROW = 5;
    private static int LOCATION_UPDATE_FREQ_ROW = 6;

    /**
     * Method saves data to file
     *
     * @param senior
     */
    public static void saveData(Senior senior) {
        List<String> listSaveText = new ArrayList<>();
        listSaveText.add(senior.getUser().getLogin());
        listSaveText.add(senior.getUser().getPassword());
        listSaveText.add(Integer.toString(senior.getSafeDistance()));
        if (senior.getUser().getSecurityString() != null)
            listSaveText.add(senior.getUser().getSecurityString());
        else listSaveText.add(" ");

        //add empty record - no null pointer exception when new settings are saved
        for (int i = 0; i < 10; i++) listSaveText.add(" ");

        String[] saveText = new String[listSaveText.size()];
        saveText = listSaveText.toArray(saveText);
        save(saveText);
        Log.i("data", "Saved data - " + saveText);
    }

    /**
     * Method saves security string to file
     *
     * @param securityString
     */
    public static void saveSecurityString(String securityString) {
        String[] textToSave = load();
        textToSave[SECURITY_STRING_ROW] = securityString;
        save(textToSave);

        Log.i("data", "Saved secStr - " + securityString);
    }

    /**
     * Method loads object user from file
     *
     * @return User object
     */
    public static User loadUserData() {
        try {
            String[] loadText = load();
            User user = new User();
            user.setLogin(loadText[LOGIN_ROW]);
            user.setPassword(loadText[PASSWORD_ROW]);
            if (loadText[SECURITY_STRING_ROW] != null) user.setSecurityString(loadText[3]);

            Log.i("data", "Loaded data - " + loadText);
            return user;
        } catch (Exception e) {
            Log.e("data", "error during load user data");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Method loads security string from file
     *
     * @return security string
     */
    public static String loadSecurityString() {
        String[] loadText = load();
        if (loadText[SECURITY_STRING_ROW] != null) {
            Log.i("data", "Loaded secStr - " + loadText[3]);
            return loadText[SECURITY_STRING_ROW];
        } else {
            Log.i("data", "Loaded secStr - Empty");
            return null;
        }
    }

    /**
     * Methods saves home location to file
     *
     * @param savedLocalization
     */
    public static void saveHomeLocation(SavedLocalization savedLocalization) {
        String[] textToSave = load();
        String savedLocationString = savedLocalization.getLatitude() + "n" + savedLocalization.getLongitude();
        textToSave[HOME_LOCATION_ROW] = savedLocationString;
        save(textToSave);

        Log.i("data", "Saved home location - " + savedLocationString);
    }

    /**
     * Method load home coordinates from file
     *
     * @return SavedLocalization
     */
    public static SavedLocalization loadHomeLocation() {
        String[] dataFromFile = load();
        String savedLocationString = dataFromFile[HOME_LOCATION_ROW];
        String[] points = savedLocationString.split("n");

        SavedLocalization savedLocalization = new SavedLocalization();
        savedLocalization.setLatitude(Double.parseDouble(points[0]));
        savedLocalization.setLongitude(Double.parseDouble(points[1]));
        savedLocalization.setName("Home");
        return savedLocalization;
    }


    /**
     * Method load safe distance from file
     *
     * @return safe distance
     */
    public static int loadSafeDistance() {
        String[] loadText = load();
        if (loadText[SECURITY_DISTANCE_ROW] != null) {
            Log.i("data", "Loaded safe distance - " + loadText[SECURITY_DISTANCE_ROW]);
            return Integer.parseInt(loadText[SECURITY_DISTANCE_ROW]);
        } else {
            Log.i("data", "Loaded safe distance  - Empty");
            return 0;
        }
    }

    /**
     * Method save safe distance to file
     *
     * @param safeDistance
     */
    public static void saveSafeDistance(int safeDistance) {
        String[] textToSave = load();
        textToSave[SECURITY_DISTANCE_ROW] = safeDistance + "";
        save(textToSave);

        Log.i("data", "Saved safe distance to file - " + safeDistance);
    }


    /**
     * Method load locationUpdateFreq
     *
     * @return slocationUpdateFreq
     */
    public static int loadLocationUpdateFreq() {
        String[] loadText = load();
        int ret = 0;
        try {
            ret = Integer.parseInt(loadText[LOCATION_UPDATE_FREQ_ROW]);
            Log.i("data", "Loaded locationUpdateFreq - " + ret);
        } catch (Exception e) {
            Log.i("data", "Loaded locationUpdateFreq  - Empty");
        }
        return ret;
    }

    /**
     * Method save locationUpdateFreq
     *
     * @param locationUpdateFreq
     */
    public static void saveLocationUpdateFreq(int locationUpdateFreq) {
        String[] textToSave = load();
        textToSave[LOCATION_UPDATE_FREQ_ROW] = locationUpdateFreq + "";
        save(textToSave);

        Log.i("data", "Saved locationUpdateFreq - " + locationUpdateFreq);
    }

    //todo to compleat
    public static boolean isSavedUser() {
        if (load() != null) return true;
        else return false;
    }

    /**
     * Method to save data to file
     *
     * @param data to save to file
     */
    private static void save(String[] data) {

        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), fileName);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            try {
                for (int i = 0; i < data.length; i++) {
                    fos.write(data[i].getBytes());
                    if (i < data.length - 1) {
                        fos.write("\n".getBytes());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Method load data from file
     *
     * @return content of file as string array
     */
    public static String[] load() {
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e) {


            e.printStackTrace();
        }
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr);

        String test;
        int anzahl = 0;
        try {
            while ((test = br.readLine()) != null) {
                anzahl++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            fis.getChannel().position(0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] array = new String[anzahl];

        String line;
        int i = 0;
        try {
            while ((line = br.readLine()) != null) {
                array[i] = line;
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return array;
    }

    public static String loadLogin() {
        String[] loadText = load();
        if (loadText[LOGIN_ROW] != null) {
            Log.i("data", "Loaded login - " + loadText[0]);
            return loadText[LOGIN_ROW];
        } else {
            Log.i("data", "Loaded login - Empty");
            return null;
        }
    }

    public static void saveCareAssistantsPhoneNumers(List<String> careAssistantsPhoneNumbers) {
        if (careAssistantsPhoneNumbers != null || careAssistantsPhoneNumbers.size() == 0) {
            String[] dataToSave = load();
            String careAssistantsContactsToSave = new String();

            for (int i = 0; i < careAssistantsPhoneNumbers.size(); i++) {
                careAssistantsContactsToSave += careAssistantsPhoneNumbers.get(i);
                if (i != careAssistantsPhoneNumbers.size() - 1) careAssistantsContactsToSave += "c";
            }

            //dont save the same value
            if (!dataToSave[CARE_ASSISTANTS_ROW].equals(careAssistantsContactsToSave)) {
                dataToSave[CARE_ASSISTANTS_ROW] = careAssistantsContactsToSave;
                save(dataToSave);

                Log.i("data", "Saved care assistants list - " + careAssistantsContactsToSave);
            }
        } else {
            Log.i("data", "Care assistants list not saved - empty or null");
        }
    }

    public static List<String> loadCareAssistantsPhones() {
        String[] dataToSave = load();
        String careAssistantsList = dataToSave[CARE_ASSISTANTS_ROW];
        String[] careAssistantArray = careAssistantsList.split("c");
        List<String> careAssistantList = new ArrayList<String>();

        for (String s : careAssistantArray) {
            careAssistantList.add(s);
        }
        return careAssistantList;
    }

}
