# Android Waypoint MapView

> Efficient tour rendering

[![](https://jitpack.io/v/liefery/android-waypoint-map-view.svg)](https://jitpack.io/#liefery/android-stop-badge)

![Sample app screenshot](https://liefery.github.io/android-waypoint-map-view/screenshot.png)

## Installation

### sbt

```scala
resolvers += "jitpack" at "https://jitpack.io"

libraryDependencies += "com.github.liefery" % "android-waypoint-map-view" % "1.0.0"
```

### Gradle

```groovy
allprojects {
    repositories {
        ...
        maven { url "https://jitpack.io" }
    }
}

dependencies {
    compile 'com.github.liefery:android-waypoint-map-view:1.0.0'
}
```

## Usage

Please have a look at the sample application for usage details.

To run the sample, first insert a [Google Maps Api Key][1] into [`google_maps_api_key.xml`][2].

[1]: https://developers.google.com/maps/documentation/android-api/signup
[2]: https://github.com/liefery/android-waypoint-map-view/blob/master/sample/src/main/res/values/google_maps_api_key.xml