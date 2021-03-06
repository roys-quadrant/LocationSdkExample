package io.quadrant.locationsdkexample.java;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.quadrant.sdk.locationdata.core.Client;
import com.quadrant.sdk.locationdata.core.Constants;
import com.quadrant.sdk.locationdata.retrofit.GeneralCallback;
import com.quadrant.sdk.locationdata.util.PublisherCompliance;

import io.quadrant.locationsdkexample.R;

public class MainActivity extends AppCompatActivity {

    private TextView tvLogTracking;
    private Button btnStart, btnStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvLogTracking = findViewById(R.id.tvLogTracking);
        btnStart = findViewById(R.id.btnStart);
        btnStop = findViewById(R.id.btnStop);

        btnStart.setOnClickListener(view -> {
            startTrackingLocation();
        });
        btnStop.setOnClickListener(view -> {
           Client.getInstance().stopTrackingLocation();
        });
    }

    private void startTrackingLocation() {
        try {
            // USE Constans.PRIORITY_HIGH_ACCURACY: to request the most accurate locations available.
            // This will return the finest location available.

            // OR Constans.PRIORITY_BALANCED_POWER_ACCURACY: to request "block" level accuracy.
            //Block level accuracy is considered to be about 100 meter accuracy. Using a coarse accuracy such as this often consumes less power.
            Client.getInstance().startTrackingLocation(this, true, "YOUR KEY", getActivityResultRegistry(), Constants.PRIORITY_BALANCED_POWER_ACCURACY, PublisherCompliance.yes,new GeneralCallback() {
                @Override
                public void onSuccess(String data) {
                    String log;
                    if(tvLogTracking.getText().toString().isEmpty()){
                        log = GetDate.getNow()+" "+"Tracking location success: "+data;
                    }else{
                        log = tvLogTracking.getText()+"\n"+GetDate.getNow()+" "+"Tracking location success: "+data;
                    }
                    tvLogTracking.setText(log);
                }

                @Override
                public void onError(String result) {
                    String log = tvLogTracking.getText()+"\n"+GetDate.getNow()+" "+"Tracking location error: "+result;
                    tvLogTracking.setText(log);
                }
            });
        } catch (Exception e) {
            String log = tvLogTracking.getText()+"\n"+GetDate.getNow()+" "+"Tracking location error: "+e.getMessage();
            tvLogTracking.setText(log);
        }
    }
}