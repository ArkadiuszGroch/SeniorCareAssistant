package pl.edu.pwste.goco.senior;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import pl.edu.pwste.goco.senior.Configuration.DataManager;
import pl.edu.pwste.goco.senior.Configuration.RestConfiguration;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import pl.edu.pwste.goco.senior.Entity.SavedLocalization;
import pl.edu.pwste.goco.senior.Services.LocationService;

public class SaveLocationActivity extends AppCompatActivity {

    private String securityString;
    private double longitude;
    private double latitude;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_location);
        this.bundle = savedInstanceState;

        securityString = DataManager.loadSecurityString();

    }

    public void saveLocalization(View view) {
        EditText etFirstName = (EditText) findViewById(R.id.etNameOfLocalization);
        String nameOfLocalization = etFirstName.getText().toString();

        if (nameOfLocalization.length() == 0) {
            String messageTitle = getResources().getString(R.string.messageErrorTitle);
            String messageContext = getResources().getString(R.string.completeAllFields);

            this.showMessage(messageTitle, messageContext);
        } else {
            SavedLocalization savedLocalization = new SavedLocalization();
            savedLocalization.setName(nameOfLocalization);
            savedLocalization.setLatitude(LocationService.savedLat);
            savedLocalization.setLongitude(LocationService.savedLong);
            new RESTGetSavedLocations().execute(savedLocalization);
        }
    }


    private void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    //    ======================== SERVICES ===========================
    class RESTGetSavedLocations extends AsyncTask<SavedLocalization, String, ResponseEntity<String>> {

        String url = new RestConfiguration().getURLToSaveLocation();
        RestTemplate restTemplate = new RestTemplate();

        @Override
        protected ResponseEntity<String> doInBackground(SavedLocalization... params) {
            try {
                SavedLocalization savedLocalization = params[0];

                RestTemplate restTemplate = new RestTemplate();
                HttpEntity<SavedLocalization> request = new HttpEntity<SavedLocalization>(savedLocalization);

                ResponseEntity<String> response = restTemplate
                        .exchange(url, HttpMethod.POST, request, String.class);
                return response;
            } catch (Exception ex) {
                String infoTitle = getResources().getString(R.string.messageErrorTitle);
                String infoMessage = getResources().getString(R.string.connectionProblem);
                showMessage(infoTitle, infoMessage);
                return null;
            }
        }

        @Override
        protected void onPostExecute(ResponseEntity<String> stringResponseEntity) {
            if (stringResponseEntity != null && stringResponseEntity.getStatusCode().value() == 200) {
                Toast.makeText(getApplicationContext(), getString(R.string.locationSaved), Toast.LENGTH_LONG).show();
                openMainActivity();
            } else {
                Toast.makeText(getApplicationContext(), getString(R.string.errorDuringSaveLocation), Toast.LENGTH_LONG).show();
            }
        }
    }

    public void showMessage(String title, String message) {
        Log.i("LOCATION", title + " " + message);

    }


}
