package io.quadrant.locationsdkexample.java;

import android.app.Application;
import android.util.Log;

import com.quadrant.sdk.locationdata.core.Client;

public class BaseApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        setupMobileProducerSDK();
    }

    private void setupMobileProducerSDK() {
        try {
            // Please Replace with your integration key
            Client.getInstance().setup(this, true, "YOUR INTEGRATION KEY", new Client.ResultCallback() {
                @Override
                public void onSuccess(String result) {
                    Log.d("setupMobileProducerSDK", result);
                }

                @Override
                public void onError(String result) {
                    Log.i("setupMobileProducerSDK", "Error getting publisher's credential...");
                }
            });
        } catch (Exception e) {
            Log.e("setupMobileProducerSDK", "Error creating instance for SDK setup... " + e);
        }
    }
}
