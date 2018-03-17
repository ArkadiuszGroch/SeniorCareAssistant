package pl.edu.pwste.goco.senior;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
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

import pl.edu.pwste.goco.senior.Adapters.ContactsAdapter;
import pl.edu.pwste.goco.senior.Configuration.RestConfiguration;
import pl.edu.pwste.goco.senior.Entity.Contact;
import pl.edu.pwste.goco.senior.Entity.Senior;

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
        ContactsAdapter contactsAdapter = new ContactsAdapter(this, contactList);

        ListView contactsListButton = new ListView(this);
        setContentView(contactsListButton);
        contactsListButton.setAdapter(contactsAdapter);

        contactsListButton.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView,
                                    View view, int position, long rowId) {

                String phone = contactList.get((int) rowId).getPhone();

                Intent callIntent = new Intent(Intent.ACTION_CALL); //use ACTION_CALL class
                callIntent.setData(Uri.parse("tel:" + phone));    //this is the phone number calling
                //check permission
                //If the device is running Android 6.0 (API level 23) and the app's targetSdkVersion is 23 or higher,
                //the system asks the user to grant approval.
                if (ActivityCompat.checkSelfPermission(CallToActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    //request permission from user if the app hasn't got the required permission
                    ActivityCompat.requestPermissions(CallToActivity.this,
                            new String[]{Manifest.permission.CALL_PHONE},   //request specific permission from user
                            10);
                    return;
                } else {     //have got permission
                    try {
                        startActivity(callIntent);  //call activity and make phone call
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(getApplicationContext(), "yourActivity is not founded", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

    }

}
