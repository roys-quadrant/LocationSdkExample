# Location SDK example
## implementation example
### latest version 1.0.8


setting.gradle file
```sh
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        jcenter() // Warning: this repository is going to shut down soon
        maven {
            url "https://quadrantsdk2.jfrog.io/artifactory/quadrant-sdk/"
        }
    }
}
```


build.gradle(:app)
```sh
defaultConfig {
        ....
        multiDexEnabled true

        ....
    }
dependencies {
    ....
    implementation 'io.quadrant.sdk.locationdata:data-acquisition-sdk:1.0.8'
    implementation "androidx.multidex:multidex:2.0.1"
}
```


### to implement
#### java
```sh
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
```
```sh
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
```
```sh
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
```
#### kotlin
```sh
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
```
```sh
Client.getInstance().startTrackingLocation(object : GeneralCallback {
                override fun onSuccess(data: String) {
                    Toast.makeText(this@MainActivity, data, Toast.LENGTH_LONG).show()
                }

                override fun onError(result: String) {
                    Toast.makeText(this@MainActivity, result, Toast.LENGTH_LONG).show()
                }
            })
```
```sh
override fun onResume() {
        super.onResume()
        Client.getInstance().registerBroadcastReceiver(this)
    }

    override fun onPause() {
        super.onPause()
        Client.getInstance().unRegisterBroadcastReceiver(this)
    }
```
