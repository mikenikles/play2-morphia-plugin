import sbt._
import Keys._

object Play2MorphiaPluginBuild extends Build {

  import Resolvers._
  import Dependencies._
  import BuildSettings._

  lazy val Play2MorphiaPlugin = Project(
    "play2-morphia-plugin",
    file("."),
    settings = buildSettings ++ Seq(
      libraryDependencies := runtime ++ test,
      publishMavenStyle := true,
      publishTo := Some(dropboxRepository),
      scalacOptions ++= Seq("-Xlint", "-deprecation", "-unchecked", "-encoding", "utf8"),
      javacOptions ++= Seq("-source", "1.6", "-encoding", "utf8"),
      resolvers ++= Seq(DefaultMavenRepository, Resolvers.typesafeRepository),
      checksums := Nil // To prevent proxyToys downloding fails https://github.com/leodagdag/play2-morphia-plugin/issues/11
    )
  ).settings()

  object Resolvers {
    val githubRepository = Resolver.file("GitHub Repository", Path.userHome / "dev" / "leodagdag.github.com" / "repository" asFile)(Resolver.ivyStylePatterns)
    val dropboxRepository = Resolver.file("Dropbox Repository", Path.userHome / "Dropbox" / "Public" / "repository" asFile)
    val typesafeRepository = "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"
  }

  object Dependencies {
    val runtime = Seq(
      "com.github.jmkgreen.morphia" % "morphia" % "1.2.2",
      ("com.github.jmkgreen.morphia" % "morphia-logging-slf4j" % "1.2.2" % "compile" notTransitive())
        .exclude("org.slf4j", "slf4j-simple")
        .exclude("org.slf4j", "slf4j-jdk14"),
      ("com.github.jmkgreen.morphia" % "morphia-validation" % "1.2.2" % "compile" notTransitive())
        .exclude("org.slf4j", "slf4j-simple")
        .exclude("org.slf4j", "slf4j-jdk14"),

      "play" %% "play-java" % "2.1.0" % "provided"
    )
    val test = Seq(
      "play" %% "play-test" % "2.1.0" % "test"
    )
  }

  object BuildSettings {
    val buildOrganization = "leodagdag"
    val buildVersion = "0.0.15-SNAPSHOT"
    val buildScalaVersion = "2.10.0"
    val buildSbtVersion = "0.12.2"
    val buildSettings = Defaults.defaultSettings ++ Seq(
      organization := buildOrganization,
      version := buildVersion,
      scalaVersion := buildScalaVersion
    )
  }
}
