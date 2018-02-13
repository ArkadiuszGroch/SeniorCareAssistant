package pl.edu.pwste.goco.senior;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import pl.edu.pwste.goco.senior.Configuration.DataManager;

import static pl.edu.pwste.goco.senior.Configuration.DataManager.loadCareAssistantsPhones;

public class SOSActivity extends AppCompatActivity {
    private long count = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sos);

        Timer T = new Timer();
        T.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView tvCounterSOS = (TextView) findViewById(R.id.tvCounterSOS);
                        tvCounterSOS.setText(String.valueOf(count));
                        count--;
                        if (count <= 0) {
                            sendSos();
                        }
                    }
                });
            }
        }, 0, 1000);
    }

    private void sendSos() {
        sendSMS();
    }

    private void sendSMS() {
        List<String> phoneList = DataManager.loadCareAssistantsPhones();
        String smsto = "smsto:";
        for (int i = 0; i < phoneList.size(); i++) {
            smsto += phoneList.get(i);
            if (i != phoneList.size() - 1) smsto += ";";
        }

        Intent smsIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse(smsto));
        smsIntent.putExtra("sms_body", "MESSAGE");
        startActivity(smsIntent);
    }

    public void sendSos(View view) {
        sendSos();
    }

    public void canclSos(View view) {
        this.finish();
    }
}
