import sbt._
import java.io.File
import org.apache.commons.io.FileUtils
import sbt.Keys._

object RestSeverDemoBuild extends Build {

  lazy val accTest  = config("at") extend Test
  lazy val unitTest = config("ut") extend Test
  lazy val systemTest = config("st") extend Test

  lazy val VMaxRealtimeMonitorProject = Project(
    id = "rest-sever-demo",
    base = file("."),
    settings = Defaults.defaultSettings ++ Seq(
      sourcesInBase in Test := false,
      unmanagedSourceDirectories in Test += baseDirectory.value / "src" / "ut" / "java",
      unmanagedSourceDirectories in Test += baseDirectory.value / "src" / "ut" / "scala",
      unmanagedResourceDirectories in Test += baseDirectory.value / "src" / "ut" / "resources",
      unmanagedSourceDirectories in Test += baseDirectory.value / "src" / "at" / "java",
      unmanagedSourceDirectories in Test += baseDirectory.value / "src" / "at" / "scala",
      unmanagedResourceDirectories in Test += baseDirectory.value / "src" / "at" / "resources",
      unmanagedSourceDirectories in Test += baseDirectory.value / "src" / "st" / "java",
      unmanagedSourceDirectories in Test += baseDirectory.value / "src" / "st" / "scala",
      unmanagedResourceDirectories in Test += baseDirectory.value / "src" / "st" / "resources"
    )
  ).configs( accTest )
   .settings( inConfig(accTest)(Defaults.testSettings) : _*)
   .settings(testOptions in accTest := Seq(Tests.Argument("-l","SlowAT")))
   .configs( unitTest )
   .settings( inConfig(unitTest)(Defaults.testSettings) : _*)
   .configs( systemTest )
   .settings( inConfig(systemTest)(Defaults.testSettings) : _*)

  def removeAllSvn(base: File): Unit = {
    for (x <- base.listFiles) {
      if (x.isDirectory) {
        if (x.getName == ".svn") FileUtils.deleteDirectory(x)
        else removeAllSvn(x)
      }
    }
  }

}

