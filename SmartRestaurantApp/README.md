# Smart Restaurant Mobile App

**Important notice:**
- **The Smart Restaurant Mobile App can only run on devices running Android Jelly Bean 4.2.x (API level 17) or later.**
- **Given that the Smart Restaurant Mobile App uses the device's camera to locate the customer inside the restaurant through QR code scanning, the app cannot be run on an Android emulator.**

--

### Simply run the release APK

1. Copy the file SmartRestaurantApp/app-release.apk to your phone and install it
   - Note that this APK has been digitally signed and is ready for release
2. Open the Smart Restaurant app from your phone's app drawer

### Build the debug APK 

1. Download and install Android Studio from [https://developer.android.com/studio/index.html](https://developer.android.com/studio/index.html)
2. Make sure to install Android SDK for API 23 (Android 6.0 Marshmallow)
3. Open the project on Android Studio 
     - Open Android Studio
     - Select "Open an existing Android Studio project"
     - Navigate to the project folder and select the SmartRestaurantApp folder
     - Click OK
4. Wait for gradle build to finish
5. Go to Build > Build APK
     - Note that this builds the debug APK, which is signed with an auto-generated certificate and is **not** ready for release
6. Copy the file SmartRestaurantApp/app/build/outputs/apk/app-debug.apk to your phone and install it
7. Open the Smart Restaurant app from your phone's app drawer
     


------
This app uses <a href="http://github.com/andreasschrade/android-design-template">Andreas Schrade's template</a> as base.





 

