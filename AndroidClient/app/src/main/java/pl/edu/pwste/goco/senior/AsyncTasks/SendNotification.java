package pl.edu.pwste.goco.senior.AsyncTasks;

import android.os.AsyncTask;
import android.util.Log;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import pl.edu.pwste.goco.senior.Configuration.RestConfiguration;
import pl.edu.pwste.goco.senior.Entity.Notification;

/**
 * Created by goco on 13.02.2018.
 */

public class SendNotification extends AsyncTask<Notification, String, ResponseEntity<String>> {

    private static List<Notification> notSentNotifications = new ArrayList<Notification>();
    private Notification notification;
    private String url;

    @Override
    protected void onPreExecute() {
        url = new RestConfiguration().getURLToSaveNotification();
    }

    @Override
    protected ResponseEntity<String> doInBackground(Notification... notifications) {
        try {
            this.notification = notifications[0];
            notSentNotifications.add(this.notification);
            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<Notification> request = new HttpEntity<Notification>(this.notification);

            ResponseEntity<String> response = restTemplate
                    .exchange(url, HttpMethod.POST, request, String.class);
            return response;

        } catch (ResourceAccessException exc) {
            Log.e("ATASK", exc.toString());
            return null;
        } catch (Exception ex) {
            Log.e("ATASK", ex.toString());
            return null;
        }
    }

    @Override
    protected void onPostExecute(ResponseEntity<String> stringResponseEntity) {
        if (stringResponseEntity != null && stringResponseEntity.getStatusCode().value() == 200) {
            Log.i("ATASK", "SendNotification>Notification saved");
            notSentNotifications.remove(this.notification);
            for (Notification notSentNotification : notSentNotifications) {
                doInBackground(notSentNotification);
            }
        } else if (stringResponseEntity != null && stringResponseEntity.getStatusCode().value() == 204) {
            Log.i("ATASK", "SendNotification>Notification not saved");
        }
    }
}
