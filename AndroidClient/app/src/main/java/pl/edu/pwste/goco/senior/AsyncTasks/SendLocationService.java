package pl.edu.pwste.goco.senior.AsyncTasks;

import android.os.AsyncTask;
import android.util.Log;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import pl.edu.pwste.goco.senior.Configuration.RestConfiguration;
import pl.edu.pwste.goco.senior.Entity.Localization;

/**
 * Created by goco on 13.03.2018.
 */

public class SendLocationService extends AsyncTask<Double, String, ResponseEntity<String>> {
    private static List<Localization> notSavedLocationList = new ArrayList<>();
    private static Localization currentLocationToSave;

    @Override
    protected ResponseEntity<String> doInBackground(Double... params) {
        try {
            //get url with sec str
            String url = new RestConfiguration().getURLSendLocation();
            Double longitude = params[0];
            Double latitude = params[1];

            currentLocationToSave = new Localization();
            currentLocationToSave.setLatitude(latitude);
            currentLocationToSave.setLongitude(longitude);

            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<Localization> request = new HttpEntity<Localization>(currentLocationToSave);

            ResponseEntity<String> response = restTemplate
                    .exchange(url, HttpMethod.POST, request, String.class);
            return response;
        } catch (Exception ex) {
            Log.i("loc", "error - " + ex.toString());
            return null;
        }
    }

    @Override
    protected void onPostExecute(ResponseEntity<String> stringResponseEntity) {
        if (stringResponseEntity != null && stringResponseEntity.getStatusCode().value() == 200) {
            Log.i("loc", "Location saved! ");
        } else {
            Log.i("loc", "Location not saved! ");
            notSavedLocationList.add(currentLocationToSave);
            for (Localization localization : notSavedLocationList) {
                this.execute(localization.getLongitude(), localization.getLatitude());
            }
        }
    }
}