package pl.edu.pwste.goco.senior;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ScrollView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import RestClient.Entity.Contact;
import RestClient.Entity.SavedLocalization;
import RestClient.Entity.Senior;
import pl.edu.pwste.goco.senior.Configuration.RestConfiguration;
import pl.edu.pwste.goco.senior.Services.LocationService;

public class CallToActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_to);
        new RESTGetContacts().execute();
    }

    class RESTGetContacts extends AsyncTask<Void, String, ResponseEntity<String>> {
        private String url;
        private ProgressDialog progressDialog;

        RESTGetContacts() {
            url = new RestConfiguration().getURLToGetContacts();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            String message = getResources().getString(R.string.loadingContacts);
            progressDialog = new ProgressDialog(CallToActivity.this);
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
                    List<Contact> contactsList = convertJsonStringToList(stringResponseEntity.getBody().toString());
                    addButtonsToLinearLayout(contactsList);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                String infoTitle = getResources().getString(R.string.messageErrorTitle);
                String infoMessage = getResources().getString(R.string.invalidLoginOrPassword);
            }
            progressDialog.dismiss();

        }

        private List<Contact> convertJsonStringToList(String jsonStr) throws JSONException {
            JSONArray jsonarray = new JSONArray(jsonStr);
            List<Contact> contactsList = new ArrayList<>();
            for (int i = 0; i < jsonarray.length(); i++) {
                JSONObject jsonobject = jsonarray.getJSONObject(i);
                String name = jsonobject.getString("name");
                String number = jsonobject.getString("phone");

                Contact contact = new Contact();
                contact.setName(name);
                contact.setPhone(number);

                contactsList.add(contact);
            }
            return contactsList;
        }
    }

    @SuppressLint("ResourceAsColor")
    private void addButtonsToLinearLayout(final List<Contact> contactList) {

        final ArrayAdapter<Contact> contactAdapter;
        ScrollView linearLayout = (ScrollView) findViewById(R.id.contactScrollView);
        contactAdapter = new ArrayAdapter<Contact>(this,
                android.R.layout.simple_list_item_1, contactList);

        ListView savedLocationListButton = new ListView(this);
        setContentView(savedLocationListButton);
        savedLocationListButton.setAdapter(contactAdapter);


        savedLocationListButton.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView,
                                    View view, int position, long rowId) {

                String message = "You clicked on " + contactList.get((int) rowId).toString();

                String phoneNumber = contactList.get((int) rowId).getPhone();

                String uri = "tel:" + phoneNumber;
//                Intent intent = new Intent(Intent.ACTION_DIAL);
//                intent.setData(Uri.parse(uri));
//                startActivity(intent);

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse(uri));
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(callIntent);
            }

        });
    }
}
