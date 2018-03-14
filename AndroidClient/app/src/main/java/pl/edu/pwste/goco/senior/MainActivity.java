package pl.edu.pwste.goco.senior;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.security.Policy;

import pl.edu.pwste.goco.senior.Services.LocationService;

public class MainActivity extends AppCompatActivity {
    private boolean isTurnedOnTorchLight = false;
    public static Camera cam = null;// has to be static, otherwise onDestroy() destroys it

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
        startActivity(intent);

    }

    public void callTo(View view) {
        Intent intent = new Intent(this, CallToActivity.class);
        startActivity(intent);
    }

    public void switchTorchLight(View view) {
        //todo
    }

    public void sendSOS(View view) {
        Intent intent = new Intent(this, SOSActivity.class);
        startActivity(intent);
    }
}
