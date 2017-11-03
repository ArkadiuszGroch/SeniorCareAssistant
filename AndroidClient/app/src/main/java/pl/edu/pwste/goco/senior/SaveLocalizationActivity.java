package pl.edu.pwste.goco.senior;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import RestClient.Entity.SavedLocalization;
import RestClient.Entity.Senior;

public class SaveLocalizationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_localization);
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
            new SaveLocalizationActivity.RESTRegister().execute(savedLocalization);
        }
    }

    private void openMainActivity()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    //    ======================== SERVICES ===========================
    class RESTRegister extends AsyncTask<SavedLocalization, String, ResponseEntity<String>> {
        String url = getResources().getString(R.string.urlService) + "account/senior/register";
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
            if (stringResponseEntity.getStatusCode().value() == 204) {
                String infoTitle = getResources().getString(R.string.messageErrorTitle);
                String infoMessage = getResources().getString(R.string.failedRegister);
                showMessage(infoTitle, infoMessage);
            } else {

                openMainActivity();

                String infoTitle = getResources().getString(R.string.doneRegister);
                String infoMessage = stringResponseEntity.getBody().toString();
                showMessage(infoTitle, infoMessage);
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
