package pl.edu.pwste.goco.senior;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.sql.Timestamp;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import pl.edu.pwste.goco.senior.AsyncTasks.SendNotification;
import pl.edu.pwste.goco.senior.Configuration.DataManager;
import pl.edu.pwste.goco.senior.Entity.Notification;
import pl.edu.pwste.goco.senior.Services.LocationService;

public class SOSActivity extends AppCompatActivity {
    private long counter = 10;
    private boolean isSend = false;
    MediaPlayer alarmSoundMP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sos);

        alarmSoundMP = MediaPlayer.create(this, R.raw.alarm);
        alarmSoundMP.setLooping(true);
        alarmSoundMP.start();

        Timer T = new Timer();
        T.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView tvCounterSOS = (TextView) findViewById(R.id.tvCounterSOS);
                        if(counter >= 0)
                        {
                            tvCounterSOS.setText(String.valueOf(counter));
                        }
                        
                        if (isSend) {
                            sendSos();
                            isSend = false;
                        } else {
                            if (counter-- == 1) {
                                isSend = true;
                            }
                        }
                    }
                });
            }
        }, 0, 1000);
    }

    private void sendSos() {
        String message = prepareMessage();
        sendSMS(message);
        sendNotification(message);
    }

    private String prepareMessage() {
        String locationName = LocationService.locationName;
        String message = "I NEED HELP! ";
        if (locationName != null)
            message += "I'm near the " + locationName;
        return message;
    }

    private void sendNotification(String message) {
        Notification notification = new Notification();
        notification.setContent(message);
        notification.setName("WARNING");
        notification.setStatus("Not read");
        new SendNotification().execute(notification);
    }

    private void sendSMS(String message) {
        List<String> phoneList = DataManager.loadCareAssistantsPhones();
        String smsto = "smsto:";
        for (int i = 0; i < phoneList.size(); i++) {
            smsto += phoneList.get(i);
            if (i != phoneList.size() - 1) smsto += ";";
        }

        Intent smsIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse(smsto));
        smsIntent.putExtra("sms_body", message);
        startActivity(smsIntent);
    }

    public void sendSos(View view) {
        sendSos();
    }

    public void canclSos(View view) {
        alarmSoundMP.stop();
        this.finish();
    }
}
