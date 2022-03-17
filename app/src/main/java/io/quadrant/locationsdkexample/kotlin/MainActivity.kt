package io.quadrant.locationsdkexample.kotlin

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.quadrant.sdk.locationdata.core.Client
import com.quadrant.sdk.locationdata.core.Constants
import com.quadrant.sdk.locationdata.retrofit.GeneralCallback
import io.quadrant.locationsdkexample.R
import io.quadrant.locationsdkexample.java.GetDate

class MainActivity:AppCompatActivity() {
    private lateinit var tvLogTracking: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvLogTracking = findViewById(R.id.tvLogTracking)

    }

    private fun startTrackingLocation() {
        try {
            // USE Constans.PRIORITY_HIGH_ACCURACY: to request the most accurate locations available.
            // This will return the finest location available.

            // OR Constans.PRIORITY_BALANCED_POWER_ACCURACY: to request "block" level accuracy.
            //Block level accuracy is considered to be about 100 meter accuracy. Using a coarse accuracy such as this often consumes less power.
            Client.getInstance().startTrackingLocation(this,
                true,
                "YOUR KEY",
                activityResultRegistry,
                Constants.PRIORITY_BALANCED_POWER_ACCURACY,
                object : GeneralCallback {
                    override fun onSuccess(data: String) {
                        val log:String
                        if(TextUtils.isEmpty(tvLogTracking.text)){
                            log = GetDate.getNow()+" "+"Tracking location success: "+data
                        }else{
                            log = tvLogTracking.text.toString()+"\n"+GetDate.getNow()+" "+"Tracking location success: "+data
                        }
                        tvLogTracking.text = log
                    }

                    override fun onError(result: String) {
                        val log:String = tvLogTracking.text.toString()+"\n"+GetDate.getNow()+" "+"Tracking location error: "+result
                        tvLogTracking.text = log
                    }
                })
        } catch (e: Exception) {
            val log:String = tvLogTracking.text.toString()+"\n"+GetDate.getNow()+" "+"Tracking location error: "+e
            tvLogTracking.text = log
        }
    }
}