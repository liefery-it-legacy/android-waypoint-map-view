lazy val root = project.in( file( "." ) )
    .enablePlugins( AndroidLib )
    .settings( Settings.common )
    .settings(
        libraryDependencies ++=
            "com.github.liefery" % "android-icon-badge" % "1.1.1" ::
            "com.google.android.gms" % "play-services-maps" % "11.0.1" ::
            "com.google.maps.android" % "android-maps-utils" % "0.5" ::
            Nil,
        name := "waypoint-map-view",
        publishArtifact in ( Compile, packageDoc ) := false,
        resolvers += "jitpack" at "https://jitpack.io"
    )

lazy val sample = project
    .enablePlugins( AndroidApp )
    .settings( Settings.common )
    .settings(
        organization := organization.value + ".waypoint_map_view.sample",
        run := ( run in Android ).evaluated
    )
    .dependsOn( root )