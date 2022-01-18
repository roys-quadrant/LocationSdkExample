package io.quadrant.locationsdkexample.kotlin

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.quadrant.sdk.locationdata.core.Client
import com.quadrant.sdk.locationdata.retrofit.GeneralCallback
import io.quadrant.locationsdkexample.R

class MainActivity:AppCompatActivity() {

    protected var mPermissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )
    private val PERMISSIONS_REQUEST = 5003

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupMobileProducerSDK()
    }

    private fun setupMobileProducerSDK() {
        try {
            Client.getInstance().setup(
                application.applicationContext,
                true,
                false,
                "YOUR INTEGRATION KEY",
                object : Client.ResultCallback {
                    override fun onSuccess(result: String) {
                        checkPermissionsAndLaunch()
                        Log.d("SetupSdkSuccess", result)
                    }

                    override fun onError(result: String) {
                        Log.d("SetupSdkError", result)
                    }
                })
        } catch (e: Exception) {
            Log.d("SetupSdkError", e.message!!)
        }
    }

    private fun checkPermissionsAndLaunch() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!hasPermissions(this, *mPermissions)) {
                ActivityCompat.requestPermissions(
                    this,
                    mPermissions,
                    PERMISSIONS_REQUEST
                )
            } else {
                startTrackingLocation()
            }
        }
    }

    private fun hasPermissions(activity: Activity?, vararg permissions: String): Boolean {
        if (activity != null && permissions != null) {
            for (permission in permissions) {
                if (ActivityCompat.checkSelfPermission(
                        activity,
                        permission
                    ) != PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.checkSelfPermission(
                        activity,
                        permission
                    ) == PackageManager.PERMISSION_DENIED && ActivityCompat.shouldShowRequestPermissionRationale(
                        activity,
                        permission
                    )
                ) {
                    return false
                }
            }
        }
        return true
    }

    private fun startTrackingLocation() {
        try {
            Client.getInstance().startTrackingLocation(object : GeneralCallback {
                override fun onSuccess(data: String) {
                    Toast.makeText(this@MainActivity, data, Toast.LENGTH_LONG).show()
                }

                override fun onError(result: String) {
                    Toast.makeText(this@MainActivity, result, Toast.LENGTH_LONG).show()
                }
            })
        } catch (e: Exception) {
            Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_LONG).show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSIONS_REQUEST) {
            if (grantResults.size > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                startTrackingLocation()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        Client.getInstance().registerBroadcastReceiver(this)
    }

    override fun onPause() {
        super.onPause()
        Client.getInstance().unRegisterBroadcastReceiver(this)
    }
}