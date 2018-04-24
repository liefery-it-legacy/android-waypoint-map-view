import android.Keys._
import sbt._
import sbt.Keys._

object Settings {
    val common = Def.settings(
        autoScalaLibrary := false,
        javacOptions ++=
            "-source" :: "1.7" ::
            "-target" :: "1.7" ::
            Nil,
        minSdkVersion := "14",
        organization := "com.liefery.android",
        platformTarget := "android-27",
        resolvers ++=
            ( "Google Maven" at "https://maven.google.com" ) ::
            ( "jitpack" at "https://jitpack.io" ) ::
            Nil,
        scalaVersion := "2.11.12",
        targetSdkVersion := "27"
    )
}