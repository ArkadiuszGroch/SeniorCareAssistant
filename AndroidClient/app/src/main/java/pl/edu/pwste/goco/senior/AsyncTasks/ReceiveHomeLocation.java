package pl.edu.pwste.goco.senior.AsyncTasks;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import pl.edu.pwste.goco.senior.Configuration.DataManager;
import pl.edu.pwste.goco.senior.Configuration.RestConfiguration;
import pl.edu.pwste.goco.senior.Entity.SavedLocalization;
import pl.edu.pwste.goco.senior.Entity.Senior;

/**
 * Created by goco on 07.03.2018.
 */

public class ReceiveHomeLocation extends AsyncTask<String, String, ResponseEntity<String>> {
    private String url;

    @Override
    protected void onPreExecute() {
        url = new RestConfiguration().getURLToGetHomeLocation();
    }

    @Override
    protected ResponseEntity<String> doInBackground(String... strings) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<Senior> request = null;

            ResponseEntity<String> response = restTemplate
                    .exchange(url, HttpMethod.GET, request, String.class);
            return response;
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.e("ATASK", "ReceiveHomeLocation>doInBackground exception");
            return null;
        }
    }

    @Override
    protected void onPostExecute(ResponseEntity<String> stringResponseEntity) {
        if (stringResponseEntity != null && stringResponseEntity.getStatusCode().value() == 200) {
            try {
                JSONObject jsonarray = new JSONObject(stringResponseEntity.getBody().toString());
                SavedLocalization savedLocalization = new SavedLocalization();
                savedLocalization.setName(jsonarray.getString("name"));
                savedLocalization.setLatitude(jsonarray.getDouble("latitude"));
                savedLocalization.setLongitude(jsonarray.getDouble("longitude"));

                DataManager.saveHomeLocation(savedLocalization);
            } catch (Exception e) {
                e.printStackTrace();

            }
        } else if (stringResponseEntity != null && stringResponseEntity.getStatusCode().value() == 404) {
            SavedLocalization savedLocalization = new SavedLocalization();
            savedLocalization.setLatitude(0);
            savedLocalization.setLongitude(0);

            DataManager.saveHomeLocation(savedLocalization);
        } else {
            Log.i("ATASK", "ReceiveHomeLocation>status code != 200");
        }
    }
}