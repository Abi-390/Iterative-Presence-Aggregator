name := """student-management-system"""
organization := "com.sms"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.18"

libraryDependencies ++= Seq(
  guice,                                                        // DI — always include
  jdbc,                                                         //By adding jdbc, Play automatically integrates HikariCP, which is a high-performance JDBC connection pool
  "io.getquill"  %% "quill-jdbc-zio" % "4.8.0",                 // Quill ORM
  "mysql"         % "mysql-connector-java" % "8.0.33",          // MySQL JDBC driver
  "com.zaxxer"    % "HikariCP"             % "5.0.1" ,          // Connection pool
  "com.auth0"     % "java-jwt"             % "4.4.0",           //JWT
  "org.mindrot"   % "jbcrypt"                % "0.4",           //jbcrypt
)
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "7.0.2" % Test

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.sms.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.sms.binders._"
