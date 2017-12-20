package pl.edu.pwste.goco.senior.Configuration;

import android.content.Context;
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

import RestClient.Entity.Senior;
import RestClient.Entity.User;

/**
 * Created by goco on 19.12.2017.
 */

public class DataManager {

    public static String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/data.txt";

    public static void saveData(Senior senior) {
        List<String> listSaveText = new ArrayList<>();
        listSaveText.add(senior.getUser().getLogin());
        listSaveText.add(senior.getUser().getPassword());
        listSaveText.add(Integer.toString(senior.getSafeDistance()));
        if (senior.getUser().getSecurityString() != null)
            listSaveText.add(senior.getUser().getSecurityString());
        else listSaveText.add(" ");

        //add empty record - no null pointer exception when new settings are saved
        for (int i=0; i<10; i++) listSaveText.add(" ");

        String[] saveText = new String[listSaveText.size()];
        saveText = listSaveText.toArray(saveText);
        save(saveText);
        Log.i("data", "Saved data - " + saveText);
    }

    public static void saveSecurityString(String securityString) {
        String[] textToSave = load();
        textToSave[3] = securityString;
        save(textToSave);

        Log.i("data", "Saved secStr - " + securityString);
    }

    public static User loadUserData() {
        String[] loadText = load();
        User user = new User();
        user.setLogin(loadText[0]);
        user.setPassword(loadText[1]);
        if (loadText[3] != null) user.setSecurityString(loadText[3]);

        Log.i("data", "Loaded data - " + loadText);
        return user;
    }

    public static String loadSecurityString() {
        String[] loadText = load();
        if (loadText[3] != null) {
            Log.i("data", "Loaded secStr - " + loadText[3]);
            return loadText[3];
        } else {
            Log.i("data", "Loaded secStr - Empty");
            return null;
        }
    }

    public static int loadSafeDistance() {
        String[] loadText = load();
        if (loadText[2] != null) {
            Log.i("data", "Loaded safe distance - " + loadText[2]);
            return Integer.parseInt(loadText[2]);
        } else {
            Log.i("data", "Loaded safe distance  - Empty");
            return 0;
        }
    }

    //todo to compleat
    public static boolean isSavedUser() {
        if (load() != null) return true;
        else return false;
    }

    //    File method
    public static void save(String[] data) {

        File file = new File(path);
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


    public static String[] load() {
        File file = new File(path);
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
}