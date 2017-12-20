package pl.edu.pwste.goco.senior;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import pl.edu.pwste.goco.senior.Configuration.DataManager;
import pl.edu.pwste.goco.senior.Configuration.RestConfiguration;
import RestClient.Entity.Senior;
import RestClient.Entity.User;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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
        String url = RestConfiguration.LOGIN;

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
                showMessage(infoTitle, infoMessage);
                return null;
            }
        }

        @Override
        protected void onPostExecute(ResponseEntity<String> stringResponseEntity) {
            if (stringResponseEntity != null && stringResponseEntity.getStatusCode().value() == 200) {
                DataManager.saveSecurityString(stringResponseEntity.getBody().toString());
                openMainActivity();

            } else {
                String infoTitle = getResources().getString(R.string.messageErrorTitle);
                String infoMessage = getResources().getString(R.string.invalidLoginOrPassword);
                //showMessage(infoTitle, infoMessage);
            }
        }
    }

    public void showMessage(String title, String message) {
        final Context mContext = this;
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

        //Setting Dialog Title
        alertDialog.setTitle(title);

        //Setting Dialog Message
        alertDialog.setMessage(message);

        //On Pressing Setting button
        alertDialog.setPositiveButton(R.string.action_settings, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(intent);
            }
        });

        //On pressing cancel button
        alertDialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialog.show();
        Log.i("CON", title + ") " + message)
        ;
    }
}
