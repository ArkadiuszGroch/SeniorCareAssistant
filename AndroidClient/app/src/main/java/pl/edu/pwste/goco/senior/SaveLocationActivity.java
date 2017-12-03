package pl.edu.pwste.goco.senior;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import pl.edu.pwste.goco.senior.Configuration.RestConfiguration;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import RestClient.Entity.SavedLocalization;

public class SaveLocationActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private String securityString;
    private double longitude;
    private double latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_location);

        // Shared preference to get sec str
        sharedPreferences = this.getSharedPreferences("pl.edu.pwste.goco.senior", Context.MODE_PRIVATE);
        securityString = sharedPreferences.getString("secStr","");

        // check if GPS enabled
        GPSTracker gpsTracker = new GPSTracker(this);

        if (gpsTracker.getIsGPSTrackingEnabled()) {
            latitude = gpsTracker.latitude;
            longitude = gpsTracker.longitude;

//            String stringLatitude = String.valueOf(gpsTracker.latitude);
//            textview = (TextView)findViewById(R.id.fieldLatitude);
//            textview.setText(stringLatitude);
//
//            String stringLongitude = String.valueOf(gpsTracker.longitude);
//            textview = (TextView)findViewById(R.id.fieldLongitude);
//            textview.setText(stringLongitude);
//
//            String country = gpsTracker.getCountryName(this);
//            textview = (TextView)findViewById(R.id.fieldCountry);
//            textview.setText(country);
//
//            String city = gpsTracker.getLocality(this);
//            textview = (TextView)findViewById(R.id.fieldCity);
//            textview.setText(city);
//
//            String postalCode = gpsTracker.getPostalCode(this);
//            textview = (TextView)findViewById(R.id.fieldPostalCode);
//            textview.setText(postalCode);
//
//            String addressLine = gpsTracker.getAddressLine(this);
//            textview = (TextView)findViewById(R.id.fieldAddressLine);
//            textview.setText(addressLine);
        } else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gpsTracker.showSettingsAlert();
        }
    }

    public void saveLocalization(View view) {
        EditText etFirstName = (EditText) findViewById(R.id.etNameOfLocalization);
        String nameOfLocalization = etFirstName.getText().toString();

        if (nameOfLocalization.length() == 0) {
            String messageTitle = getResources().getString(R.string.messageErrorTitle);
            String messageContext = getResources().getString(R.string.compleatAllFields);

            this.showMessage(messageTitle, messageTitle);
        } else {

            SavedLocalization savedLocalization = new SavedLocalization();
            savedLocalization.setName(nameOfLocalization);
            savedLocalization.setLatitude(latitude);
            savedLocalization.setLongitude(longitude);
            new SaveLocationActivity.RESTRegister().execute(savedLocalization);
        }
    }


    private void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    //    ======================== SERVICES ===========================
    class RESTRegister extends AsyncTask<SavedLocalization, String, ResponseEntity<String>> {

        String url = RestConfiguration.getURLToSaveLocation(securityString);
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
                openMainActivity();

                String infoTitle = getResources().getString(R.string.locationSaved);
                String infoMessage = stringResponseEntity.getBody().toString();
                showMessage(infoTitle, infoMessage);


            } else {
                String infoTitle = getResources().getString(R.string.messageErrorTitle);
                String infoMessage = getResources().getString(R.string.errorDuringSaveLocation);
                showMessage(infoTitle, infoMessage);
            }
        }
    }

    public void showMessage(String title, String message) {
        Log.i("LOCATION", title + " " + message);
//        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
//        dlgAlert.setMessage(message);
//        dlgAlert.setTitle(title);
//        dlgAlert.setPositiveButton("OK", null);
//        dlgAlert.setCancelable(true);
//        dlgAlert.create().show();
//        dlgAlert.setPositiveButton("Ok",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        //dismiss the dialog
//                    }
//                });
    }
}
