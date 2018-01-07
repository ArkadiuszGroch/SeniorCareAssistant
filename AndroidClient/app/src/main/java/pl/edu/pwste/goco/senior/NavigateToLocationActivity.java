package pl.edu.pwste.goco.senior;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import RestClient.Entity.Senior;
import pl.edu.pwste.goco.senior.Configuration.DataManager;
import pl.edu.pwste.goco.senior.Configuration.RestConfiguration;

public class NavigateToLocationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigate_to_location);


    }

    class RESTGetLocation extends AsyncTask<Senior, String, ResponseEntity<String>> {
        String url = new RestConfiguration().getURLToGetSavedLocations();

        @Override
        protected ResponseEntity<String> doInBackground(Senior... params) {
            try {
                Senior senior = params[0];
                RestTemplate restTemplate = new RestTemplate();
                HttpEntity<Senior> request = new HttpEntity<Senior>(senior);

                ResponseEntity<String> response = restTemplate
                        .exchange(url, HttpMethod.POST, request, String.class);
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

            } else {
                String infoTitle = getResources().getString(R.string.messageErrorTitle);
                String infoMessage = getResources().getString(R.string.invalidLoginOrPassword);
            }
        }
    }
}
