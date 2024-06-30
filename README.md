# GeoCubed

Navigation application (Google Maps like) using augmented reality (Work in Progress)

## Table of Contents

- [Description](#description)
- [Usage](#usage)
- [Author](#author)

## Description

The goal of this project is to create a navigation application (Google Maps & Apple Maps like) in augmented reality.

The application will allow the user to search for a destination and then display the route to follow.
Using the mobile device's camera, the application will show the user the way to go to the chosen destination.

Note that the application is still in development and due to an increasing complexity and a lack of time, AR features are **NOT** yet implemented.

### APIs used

- [Maps SDK for Android](https://developers.google.com/maps/documentation/android-sdk/overview) (?)   ⇒ Map display
- [Openrouteservice](https://openrouteservice.org/) (free)                                            ⇒ Get location name and directions
- [ARCore](https://developers.google.com/ar) (free/paid)                                              ⇒ AR usage (not yet implemented)

### Architecture

GeoCubed is based on the MVVM (Model View ViewModel) model.

!https://upload.wikimedia.org/wikipedia/commons/thumb/8/87/MVVMPattern.png/500px-MVVMPattern.png

## Usage

Simply clone the repository and open the project in Android Studio. 
You can also install the APK file on your Android device.

### API Keys
You may need a Google Maps API key to use the Maps SDK for Android.
Feel free to create a new project on the Google Cloud Platform and enable the Maps SDK for Android.

To add the API key to the project, you can add it in the `local.properties` file like this:

```
MAPS_API_KEY=<your_maps_api_key>
```

### What you can do

- Search for a destination
- Follow the directions using the bottom sheet menu 
- Or cancel the search and go back to the map
- Change language to get directions in your own language

- **NOT YET IMPLEMENTED** : Use your camera to display the route in augmented reality

## Author

Adrien RAIMBAULT <3
