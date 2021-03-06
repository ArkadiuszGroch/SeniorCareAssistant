package pl.edu.pwste.goco.senior;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import pl.edu.pwste.goco.senior.Adapters.LocationsAdapter;
import pl.edu.pwste.goco.senior.Configuration.RestConfiguration;
import pl.edu.pwste.goco.senior.Entity.SavedLocalization;
import pl.edu.pwste.goco.senior.Entity.Senior;

public class NavigateToLocationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigate_to_location);
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
                Log.i("NavigateToLocation", "error during getting saved locations list");
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

        LocationsAdapter locationsAdapter = new LocationsAdapter(this, savedLocalizationList);

        ListView savedLocationListButton = new ListView(this);
        setContentView(savedLocationListButton);
        savedLocationListButton.setAdapter(locationsAdapter);

        savedLocationListButton.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView,
                                    View view, int position, long rowId) {

                Intent intent = new Intent(NavigateToLocationActivity.this, MapsActivity.class);
                startActivity(intent);

                double destinyLattitude = savedLocalizationList.get((int) rowId).getLatitude();
                double destinyLongitude = savedLocalizationList.get((int) rowId).getLongitude();

                String packageName = "com.google.android.apps.maps";
                String query = "google.navigation:q=" + destinyLongitude + "," + destinyLattitude;

                Intent intent2 = new Intent(Intent.ACTION_VIEW, Uri.parse(query));
                intent2.setPackage(packageName);
                startActivity(intent2);
            }
        });
    }
}