package io.quadrant.locationsdkexample.kotlin

import android.app.Application
import android.util.Log
import com.quadrant.sdk.locationdata.core.Client

class BaseApp: Application() {
    override fun onCreate() {
        super.onCreate()

        setupMobileProducerSDK()
    }

    private fun setupMobileProducerSDK() {
        try {
            // Please Replace with your integration key
            Client.getInstance()
                .setup(this, true, "YOUR INTEGRATION KEY", object : Client.ResultCallback {
                    override fun onSuccess(result: String) {
                        Log.d("setupMobileProducerSDK", result)
                    }

                    override fun onError(result: String) {
                        Log.d("setupMobileProducerSDK", "Error getting publisher's credential...")
                    }
                })
        } catch (e: Exception) {
            Log.d("setupMobileProducerSDK", "Error creating instance for SDK setup... $e")
        }
    }
}