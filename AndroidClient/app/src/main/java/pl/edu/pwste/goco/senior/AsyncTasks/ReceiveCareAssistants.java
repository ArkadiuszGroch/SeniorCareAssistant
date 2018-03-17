package pl.edu.pwste.goco.senior.AsyncTasks;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import pl.edu.pwste.goco.senior.Configuration.DataManager;
import pl.edu.pwste.goco.senior.Configuration.RestConfiguration;
import pl.edu.pwste.goco.senior.Entity.Senior;

/**
 * Created by goco on 12.02.2018.
 */

public class ReceiveCareAssistants extends AsyncTask<String, String, ResponseEntity<String>> {
    private String url;

    @Override
    protected void onPreExecute() {
        url = new RestConfiguration().getURLToGetCareAssistantsPhones();
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
            Log.e("ATASK", "ReceiveCareAssistants>doInBackground exception");
            return null;
        }
    }

    @Override
    protected void onPostExecute(ResponseEntity<String> stringResponseEntity) {
        if (stringResponseEntity != null && stringResponseEntity.getStatusCode().value() == 200) {

            try {
                List<String> contactsList = convertJsonStringToList(stringResponseEntity.getBody().toString());
                DataManager.saveCareAssistantsPhoneNumers(contactsList);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else {
            Log.i("ATASK", "ReceiveCareAssistants>status code != 200");
        }
    }

    private List<String> convertJsonStringToList(String jsonStr) throws JSONException {
        JSONArray jsonarray = new JSONArray(jsonStr);

        List<String> phoneList = new ArrayList<>();
        for (int i = 0; i < jsonarray.length(); i++) {
            String tmp = (String) jsonarray.get(i);
            tmp.replace("[\"", "").replace("\"]", "").replace(",", "");
            phoneList.add(tmp);
        }
        return phoneList;
    }
}