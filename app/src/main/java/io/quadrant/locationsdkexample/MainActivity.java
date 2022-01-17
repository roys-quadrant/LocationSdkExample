package io.quadrant.locationsdkexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.quadrant.sdk.locationdata.core.Client;
import com.quadrant.sdk.locationdata.retrofit.GeneralCallback;

public class MainActivity extends AppCompatActivity {

    protected String[] mPermissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    private static final int PERMISSIONS_REQUEST = 5003;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupMobileProducerSDK();
    }

    private void setupMobileProducerSDK() {
        try {
            Client.getInstance().setup(getApplication().getApplicationContext(), true,false, "YOUR INTEGRATION KEY", new Client.ResultCallback() {
                @Override
                public void onSuccess(String result) {
                    checkPermissionsAndLaunch();
                    Log.d("SetupSdkSuccess", result);
                }

                @Override
                public void onError(String result) {
                    Log.d("SetupSdkError", result);
                }
            });
        } catch (Exception e) {
            Log.d("SetupSdkError", e.getMessage());
        }
    }

    protected void checkPermissionsAndLaunch() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!hasPermissions(this, mPermissions)) {
                ActivityCompat.requestPermissions(this, mPermissions, PERMISSIONS_REQUEST);
            } else {
                startTrackingLocation();
            }
        }
    }

    private static boolean hasPermissions(Activity activity, String... permissions) {
        if (activity != null && permissions != null) {
            for (String permission : permissions) {
                if ((ActivityCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) ||
                        (((ActivityCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_DENIED) && ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)))) {
                    return false;
                }
            }
        }
        return true;
    }

    private void startTrackingLocation() {
        try {
            Client.getInstance().startTrackingLocation(new GeneralCallback() {
                @Override
                public void onSuccess(String data) {
                    Toast.makeText(MainActivity.this, data, Toast.LENGTH_LONG).show();
                }

                @Override
                public void onError(String result) {
                    Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();

                }
            });
        } catch (Exception e) {
            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();

        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startTrackingLocation();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Client.getInstance().registerBroadcastReceiver(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Client.getInstance().unRegisterBroadcastReceiver(this);
    }
}