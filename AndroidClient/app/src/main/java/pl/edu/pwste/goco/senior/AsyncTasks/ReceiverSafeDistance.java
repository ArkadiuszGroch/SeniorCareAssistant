package pl.edu.pwste.goco.senior.AsyncTasks;

import android.os.AsyncTask;
import android.util.Log;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import pl.edu.pwste.goco.senior.Configuration.DataManager;
import pl.edu.pwste.goco.senior.Configuration.RestConfiguration;
import pl.edu.pwste.goco.senior.Entity.Senior;

/**
 * Created by goco on 08.03.2018.
 */

public class ReceiverSafeDistance extends AsyncTask<String, String, ResponseEntity<String>> {
    private String url;

    @Override
    protected void onPreExecute() {
        url = new RestConfiguration().getURLToGetSafeDistance();
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
            Log.e("ATASK", "ReceiverSafeDistance>doInBackground exception");
            return null;
        }
    }

    @Override
    protected void onPostExecute(ResponseEntity<String> stringResponseEntity) {
        if (stringResponseEntity != null && stringResponseEntity.getStatusCode().value() == 200) {
            try {
                int safeDistance = Integer.parseInt(stringResponseEntity.getBody());

                DataManager.saveSafeDistance(safeDistance);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Log.i("ATASK", "ReceiverSafeDistance>status code != 200");
        }
    }
}