package pl.edu.pwste.goco.senior;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import pl.edu.pwste.goco.senior.Configuration.DataManager;
import pl.edu.pwste.goco.senior.Configuration.RestConfiguration;
import pl.edu.pwste.goco.senior.Entity.Senior;
import pl.edu.pwste.goco.senior.Entity.User;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void registerClick(View view) {
        Senior senior = validAndGetSeniorFromForm();
        if (senior != null) {
            new RESTRegister().execute(senior);
            DataManager.saveData(senior);
        }
    }

    public void startMainActivity() {
        //open main activities
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private Senior validAndGetSeniorFromForm() {
//       get value from form
        EditText etLogin = (EditText) findViewById(R.id.etLogin);
        String login = etLogin.getText().toString();

        EditText etPassword = (EditText) findViewById(R.id.etPassword);
        String password = etPassword.getText().toString();

        EditText etConfirmPassword = (EditText) findViewById(R.id.etConfirmPassword);
        String confirmPassword = etConfirmPassword.getText().toString();

        EditText etFirstName = (EditText) findViewById(R.id.etFirstName);
        String firstName = etFirstName.getText().toString();

        EditText etLastName = (EditText) findViewById(R.id.etLastName);
        String lastName = etLastName.getText().toString();

        EditText etPhone = (EditText) findViewById(R.id.etPhone);
        String phone = etPhone.getText().toString();

        EditText etEmail = (EditText) findViewById(R.id.etEmail);
        String email = etEmail.getText().toString();

        if (login.length() > 0 && password.length() > 0
                && confirmPassword.length() > 0
                && firstName.length() > 0
                && lastName.length() > 0
                && phone.length() > 0
                && email.length() > 0) {
            if (password.equals(confirmPassword)) {
                //prepare object
                User user = new User();
                user.setLogin(login);
                user.setPassword(password);
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setEmail(email);
                user.setPhone(phone);

                Senior senior = new Senior();
                senior.setUser(user);

                return senior;
            } else {
                //password field and confirm password field is different
                String infoTitle = getResources().getString(R.string.messageErrorTitle);
                String infoMessage = getResources().getString(R.string.passwordNotMatch);
                showMessage(infoTitle, infoMessage);
                return null;
            }
        } else {
            String infoTitle = getResources().getString(R.string.messageErrorTitle);
            String infoMessage = getResources().getString(R.string.completeAllFields);
            showMessage(infoTitle, infoMessage);
            return null;
        }
    }

    //    ======================== SERVICES ===========================
    class RESTRegister extends AsyncTask<Senior, String, ResponseEntity<String>> {
        String url = RestConfiguration.getURLToRegister();

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
            if (stringResponseEntity.getStatusCode().value() == 200) {
                String infoTitle = getResources().getString(R.string.doneRegister);
                String infoMessage = stringResponseEntity.getBody();
                DataManager.saveSecurityString(stringResponseEntity.getBody());
                Log.i("REG", "Register completed");
                startMainActivity();
            } else {
                String infoTitle = getResources().getString(R.string.messageErrorTitle);
                String infoMessage = getResources().getString(R.string.failedRegister);
                showMessage(infoTitle, infoMessage);
                Log.i("REG", "Register failed");
            }
        }
    }

    public void showMessage(String title, String message) {
        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
        dlgAlert.setMessage(message);
        dlgAlert.setTitle(title);
        dlgAlert.setPositiveButton("OK", null);
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();
        dlgAlert.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //dismiss the dialog
                    }
                });
    }
}
