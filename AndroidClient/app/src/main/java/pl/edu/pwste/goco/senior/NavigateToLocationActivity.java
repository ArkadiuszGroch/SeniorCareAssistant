package pl.edu.pwste.goco.senior;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import RestClient.Entity.SavedLocalization;
import RestClient.Entity.Senior;
import pl.edu.pwste.goco.senior.Configuration.DataManager;
import pl.edu.pwste.goco.senior.Configuration.RestConfiguration;

public class NavigateToLocationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigate_to_location);
        new RESTGetLocation().execute();
    }

    class RESTGetLocation extends AsyncTask<Void, String, ResponseEntity<String>> {
        String url = new RestConfiguration().getURLToGetSavedLocations();

        @Override
        protected ResponseEntity<String> doInBackground(Void... voids) {
            try {
                RestTemplate restTemplate = new RestTemplate();
                HttpEntity<Senior> request = null;

                ResponseEntity<String> response = restTemplate
                        .exchange(url, HttpMethod.GET, request, String.class);
                return response;
            } catch (Exception ex) {
                String infoTitle = getResources().getString(R.string.messageErrorTitle);
                String infoMessage = getResources().getString(R.string.connectionProblem);
                return null;
            }
        }

        @Override
        protected void onPostExecute(ResponseEntity<String> stringResponseEntity) {
            if (stringResponseEntity != null && stringResponseEntity.getStatusCode().value() == 200) {
                DataManager.saveSecurityString(stringResponseEntity.getBody().toString());
                try {
                    List<SavedLocalization> savedLocalizationList = convertJsonStringToList(stringResponseEntity.getBody().toString());

                    addButtonsToLinearLayout(savedLocalizationList);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                String infoTitle = getResources().getString(R.string.messageErrorTitle);
                String infoMessage = getResources().getString(R.string.invalidLoginOrPassword);
            }
        }

        private List<SavedLocalization> convertJsonStringToList(String jsonStr) throws JSONException {
            JSONArray jsonarray = new JSONArray(jsonStr);
            List<SavedLocalization> savedLocalizationsList = new ArrayList<>();
            for (int i = 0; i < jsonarray.length(); i++) {
                JSONObject jsonobject = jsonarray.getJSONObject(i);
                String name = jsonobject.getString("name");
                String latitude = jsonobject.getString("latitude");
                String longitude = jsonobject.getString("longitude");

                SavedLocalization savedLocalization = new SavedLocalization();
                savedLocalization.setName(name);
                savedLocalization.setLongitude(Double.parseDouble(longitude));
                savedLocalization.setLatitude(Double.parseDouble(latitude));

                savedLocalizationsList.add(savedLocalization);
            }
            return savedLocalizationsList;
        }
    }

    @SuppressLint("ResourceAsColor")
    private void addButtonsToLinearLayout(List<SavedLocalization> savedLocalizationList) {

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linlayLocationList);
        for (SavedLocalization savedLocalization : savedLocalizationList) {
            Button button = new Button(this);
            button.setBackgroundColor(R.color.buttonsBackground);
            button.setTextColor(R.color.buttonsText);
            button.setText(savedLocalization.getName());
            linearLayout.addView(button);
        }
    }
}
