# Location SDK example
## implementation example
### latest version 1.0.12


setting.gradle or build.gradle(project level) file
```sh
    repositories {
        google()
        mavenCentral()
        jcenter() // Warning: this repository is going to shut down soon
        maven {
            url "https://quadrantsdk2.jfrog.io/artifactory/quadrant-sdk/"
        }
    }
```


build.gradle(app level)
```sh
defaultConfig {
        ....
        multiDexEnabled true

        ....
    }
dependencies {
    ....
    implementation 'io.quadrant.sdk.locationdata:data-acquisition-sdk:1.0.12'
    implementation "androidx.multidex:multidex:2.0.1"
}
```


### to implement
#### java

to implement setup and tracking
```sh
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startTrackingLocation()
    }
    
    private void startTrackingLocation() {
        try {
            // USE Constans.PRIORITY_HIGH_ACCURACY: to request the most accurate locations available.
            // This will return the finest location available.

            // OR Constans.PRIORITY_BALANCED_POWER_ACCURACY: to request "block" level accuracy.
            //Block level accuracy is considered to be about 100 meter accuracy. Using a coarse accuracy such as this often consumes less power.
            Client.getInstance().startTrackingLocation(this, true, "YOUR KEY" getActivityResultRegistry(), Constants.PRIORITY_HIGH_ACCURACY,new GeneralCallback() {
                @Override
                public void onSuccess(String data) {
                }

                @Override
                public void onError(String result) {
                }
            });
        } catch (Exception e) {
        }
    }
    
```
#### kotlin
```sh
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
     
        startTrackingLocation()
    }

    private fun startTrackingLocation() {
        try {
            // USE Constans.PRIORITY_HIGH_ACCURACY: to request the most accurate locations available.
            // This will return the finest location available.

            // OR Constans.PRIORITY_BALANCED_POWER_ACCURACY: to request "block" level accuracy.
            //Block level accuracy is considered to be about 100 meter accuracy. Using a coarse accuracy such as this often consumes less power.
            Client.getInstance().startTrackingLocation(this,
                true,
                "YOUR KEY"
                activityResultRegistry,
                Constants.PRIORITY_BALANCED_POWER_ACCURACY,
                object : GeneralCallback {
                    override fun onSuccess(data: String) {
                        
                    }

                    override fun onError(result: String) {
                        
                    }
                })
        } catch (e: Exception) {
            
        }
    }
```

#### Proguard
if you want to implement obfuscate in your release app, add this on your proguard rules file
```sh
-keep class com.quadrant.sdk.locationdata.** {*;}
```



-----------------------------------------------------------------------

## Non-Transitive
### latest version 1.0.3
On some special case, when your gradle library clash with ours regarding version issues, please use our non-transitive SDK.
The different is on on build.gradle on app level. The rest is similar with the above.

build.gradle(app level)
```sh
defaultConfig {
        ....
        multiDexEnabled true

        ....
    }
dependencies {
    ....
    //THIS OUR SDK LIBRARY
    implementation 'io.quadrant.sdk.locationdata:data-acquisition-sdk-non-transitive:1.0.3'
    
    //THIS LIBRARY NEEDED BY OUR SDK.
    //Please replace library version number with your need
    implementation 'com.squareup.retrofit2:retrofit:x.x.x'
    implementation 'com.squareup.retrofit2:converter-gson:x.x.x'
    implementation 'com.squareup.retrofit2:converter-scalars:x.x.x'
    implementation 'com.squareup.okhttp3:logging-interceptor:x.x.x'
    implementation 'com.google.android.gms:play-services-base:x.x.x'
    implementation 'com.google.android.gms:play-services-location:x.x.x'
    implementation 'com.google.android.gms:play-services-ads:x.x.x'
    implementation 'com.google.android.gms:play-services-safetynet:x.x.x'
    implementation "androidx.multidex:multidex:x.x.x"
    implementation "androidx.lifecycle:lifecycle-common-java8:x.x.x"
    implementation 'com.google.guava:guava:xx.x-android'
    
    //KOTLIN
    implementation "androidx.lifecycle:lifecycle-runtime-ktx:x.x.x"
    implementation "androidx.work:work-runtime-ktx:x.x.x"
    //JAVA
    implementation "androidx.work:work-runtime:x.x.x"
    
}
```
