package pl.edu.pwste.goco.senior.AsyncTasks;

import android.app.ProgressDialog;
import android.util.Log;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import pl.edu.pwste.goco.senior.Configuration.DataManager;
import pl.edu.pwste.goco.senior.Configuration.RestConfiguration;
import pl.edu.pwste.goco.senior.Entity.Senior;
import pl.edu.pwste.goco.senior.LoginActivity;
import pl.edu.pwste.goco.senior.R;

/**
 * Created by goco on 13.02.2018.
 */

public class SendNotification {
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
