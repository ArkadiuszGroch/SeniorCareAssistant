package pl.edu.pwste.goco.senior;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;

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
import pl.edu.pwste.goco.senior.Services.LocationService;

public class NavigateToLocationActivity extends AppCompatActivity {

    public void loadLocations() {
        new RESTGetLocation().execute();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigate_to_location);
        new RESTGetLocation().execute();
    }

    @Override
    protected void onStart() {
        super.onStart();
        new RESTGetLocation().execute();
    }

    class RESTGetLocation extends AsyncTask<Void, String, ResponseEntity<String>> {
        private String url = new RestConfiguration().getURLToGetSavedLocations();
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            String message = getResources().getString(R.string.loadingLocation);
            progressDialog = new ProgressDialog(NavigateToLocationActivity.this);
            progressDialog.setMessage(message);
            progressDialog.show();
        }

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
            progressDialog.dismiss();

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
    private void addButtonsToLinearLayout(final List<SavedLocalization> savedLocalizationList) {

        ArrayAdapter<SavedLocalization> savedLocatSavedLocalizationArrayAdapter;
        ScrollView linearLayout = (ScrollView) findViewById(R.id.scrviewLocation);
        savedLocatSavedLocalizationArrayAdapter = new ArrayAdapter<SavedLocalization>(this,
                android.R.layout.simple_list_item_1, savedLocalizationList);

        ListView savedLocationListButton = new ListView(this);
        setContentView(savedLocationListButton);
        savedLocationListButton.setAdapter(savedLocatSavedLocalizationArrayAdapter);


        savedLocationListButton.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView,
                                    View view, int position, long rowId) {

                // Generate a message based on the position
                String message = "You clicked on " + savedLocalizationList.get((int) rowId).toString();

                double currentLattitude = LocationService.savedLat;
                double currentLongitude = LocationService.savedLong;

                double destinyLattitude = savedLocalizationList.get((int) rowId).getLatitude();
                double destinyLongitude = savedLocalizationList.get((int) rowId).getLongitude();


//                String uri = "http://maps.google.com/maps?saddr=" + currentLongitude + "," + currentLattitude + "&daddr=" + destinyLongitude + "," + destinyLattitude;
//                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
//                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
//                startActivity(intent);

                String packageName = "com.google.android.apps.maps";
                String query = "google.navigation:q=" + destinyLongitude + "," + destinyLattitude;

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(query));
                intent.setPackage(packageName);
                startActivity(intent);


            }


        });
    }
}
