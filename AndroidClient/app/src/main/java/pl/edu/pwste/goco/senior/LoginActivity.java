package pl.edu.pwste.goco.senior;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import pl.edu.pwste.goco.senior.AsyncTasks.ReceiveCareAssistants;
import pl.edu.pwste.goco.senior.Configuration.DataManager;
import pl.edu.pwste.goco.senior.Configuration.RestConfiguration;
import pl.edu.pwste.goco.senior.Entity.Senior;
import pl.edu.pwste.goco.senior.Entity.User;

public class LoginActivity extends AppCompatActivity {
    public static boolean isLogout = false;

    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    /**
     * Checks if the app has permission to write to device storage
     * <p>
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        verifyStoragePermissions(this);

        Senior senior = new Senior();
        senior.setUser(DataManager.loadUserData());

//      show activity after press Logout button
        if (isLogout) {
            if (senior.getUser().getLogin().length() > 0 && senior.getUser().getPassword().length() > 0) {
                EditText etLogin = (EditText) findViewById(R.id.etLogin);
                etLogin.setText(senior.getUser().getLogin());

                EditText etPassword = (EditText) findViewById(R.id.etPassword);
                etPassword.setText(senior.getUser().getPassword());
            }
        } else {
            try {
                //try login with saved info
                new RESTLogin().execute(senior);
            } catch (Exception e) {
                //error during connection with web service or invalid saved info
                Log.i("CON", "error during connection with web service or invalid saved info");
            }
        }

    }

    public void signInClick(View view) {

//get value from form
        EditText etLogin = (EditText) findViewById(R.id.etLogin);
        String login = etLogin.getText().toString();

        EditText etPassword = (EditText) findViewById(R.id.etPassword);
        String password = etPassword.getText().toString();

//prepare object
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        Senior senior = new Senior();
        senior.setUser(user);

//execute service
        DataManager dataManager = new DataManager();
        dataManager.saveData(senior);

        new RESTLogin().execute(senior);
    }

    public void registerClick(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    private void openMainActivity() {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    class RESTLogin extends AsyncTask<Senior, String, ResponseEntity<String>> {
        private String url = new RestConfiguration().getURLTologin();
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            String message = getResources().getString(R.string.logginInProgress);
            progressDialog = new ProgressDialog(LoginActivity.this);
            progressDialog.setMessage(message);
            progressDialog.show();
        }

        @Override
        protected ResponseEntity<String> doInBackground(Senior... params) {

            try {
                Senior senior = params[0];
                RestTemplate restTemplate = new RestTemplate();
                HttpEntity<Senior> request = new HttpEntity<Senior>(senior);

                ResponseEntity<String> response = restTemplate
                        .exchange(url, HttpMethod.POST, request, String.class);
                return response;

            } catch (ResourceAccessException exc) {
                Log.e("LOGIN", exc.toString());
                //showMessage(getResources().getString(R.string.internetConnectionError));
                return null;
            } catch (Exception ex) {
                Log.e("LOGIN", ex.toString());
                return null;
            }
        }

        @Override
        protected void onPostExecute(ResponseEntity<String> stringResponseEntity) {
            if (stringResponseEntity != null && stringResponseEntity.getStatusCode().value() == 200) {
                DataManager.saveSecurityString(stringResponseEntity.getBody().toString());
                new ReceiveCareAssistants().execute();
                openMainActivity();
            } else if (stringResponseEntity != null && stringResponseEntity.getStatusCode().value() == 204) {
                String infoMessage = getResources().getString(R.string.invalidLoginOrPassword);
                showMessage(infoMessage);
            } else {
                String infoMessage = getResources().getString(R.string.internetConnectionError);
                showMessage(infoMessage);
            }
            progressDialog.dismiss();
        }
    }

    public void showMessage(String message) {
        new showMessageAsync().execute(message);
    }

    private class showMessageAsync extends AsyncTask<String, Void, String> {
        AlertDialog.Builder builder;

        @Override
        protected String doInBackground(String... strings) {
            return strings[0];
        }

        protected void onPreExecute() {
            super.onPreExecute();
            builder = new AlertDialog.Builder(LoginActivity.this);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            builder.setMessage(result);

            // add a button
            builder.setPositiveButton("OK", null);

            // create and show the alert dialog
            AlertDialog dialog = builder.create();
            dialog.show();
        }

    }
}
