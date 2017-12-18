package pl.edu.pwste.goco.senior;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startService(new Intent(this, LocationService.class));

    }

    public void saveCurrentLocation(View view) {
        Intent intent = new Intent(this, SaveLocationActivity.class);
        startActivity(intent);
    }
}
