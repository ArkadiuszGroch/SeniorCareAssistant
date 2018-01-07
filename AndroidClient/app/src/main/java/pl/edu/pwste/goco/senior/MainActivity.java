package pl.edu.pwste.goco.senior;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import pl.edu.pwste.goco.senior.Services.LocationService;

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

    public void btnLogoutClick(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        LoginActivity.isLogout = true;
        startActivity(intent);
    }

    public void showNavigateToPlace(View view) {
        Intent intent = new Intent(this, NavigateToLocationActivity.class);
        LoginActivity.isLogout = true;
        startActivity(intent);

    }
}
