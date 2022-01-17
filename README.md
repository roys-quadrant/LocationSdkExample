# Location SDK example
## implementation example
latest version 1.0.8

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

to implement
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
