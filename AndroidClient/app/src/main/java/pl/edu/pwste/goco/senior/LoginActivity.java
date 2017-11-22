package pl.edu.pwste.goco.senior;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import Configuration.RestConfiguration;
import RestClient.AnswerTemplate;
import RestClient.Entity.Senior;
import RestClient.Entity.User;
import RestClient.Service.LoginService;

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
        //new RESTLogin().execute(senior);
        executeLogin(senior);
    }

    private void executeLogin(Senior senior) {
        RestService restService = new RestService();
        restService.doInBackground(senior);

        Thread thread = new Thread() {
            public void run() {
                LoginService loginService = new LoginService(senior);
                AnswerTemplate answer = loginService.execute();
                if(answer.getErrorCode() != AnswerTemplate.ErrorCode.CONNECTION_ERROR){
                    if(answer.getCode() == 200)
                    {
                        RestConfiguration.SECURITY_STRING = (String) answer.getObject();
                        openMainActivity();
                    }
                    else{
                        String infoTitle = getResources().getString(R.string.messageErrorTitle);
                        String infoMessage = getResources().getString(R.string.invalidLoginOrPassword);
                        showMessage(infoTitle, infoMessage);
                    }
                }
                else {
                    String infoTitle = getResources().getString(R.string.messageErrorTitle);
                    String infoMessage = getResources().getString(R.string.connectionProblem);
                    showMessage(infoTitle, infoMessage);
                }
            }
        };
    }
    class RestService extends AsyncTask<Senior, AnswerTemplate, AnswerTemplate> {

        @Override
        protected AnswerTemplate doInBackground(Senior... seniors) {
            LoginService loginService = new LoginService(seniors[0]);
            return loginService.execute();
        }
    }

    public void registerClick(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);

    }

    private void openMainActivity()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


/*    ======================== SERVICES ===========================
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
            if (stringResponseEntity.getStatusCode().value() == 204) {
                String infoTitle = getResources().getString(R.string.messageErrorTitle);
                String infoMessage = getResources().getString(R.string.invalidLoginOrPassword);
                showMessage(infoTitle, infoMessage);
            } else {
                RestConfiguration.SECURITY_STRING = stringResponseEntity.getBody().toString();
                openMainActivity();
            }
        }
    }
*/
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
