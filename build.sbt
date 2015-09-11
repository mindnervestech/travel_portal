name := "travel_portal"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
"net.coobird" % "thumbnailator" % "0.4.3",
    "mysql" % "mysql-connector-java" % "5.1.6",	
    javaJdbc,
  	cache,
	javaJpa,
	"org.hibernate" % "hibernate-entitymanager" % "3.6.9.Final", 
	"org.json"%"org.json"%"chargebee-1.0",
	"com.itextpdf" % "itextpdf" % "5.5.4",
    "com.itextpdf.tool" % "xmlworker" % "1.0.0",
	"commons-io" % "commons-io" % "2.1",
	"org.javers" % "javers-core" % "1.1.1",
	"org.apache.velocity" % "velocity" % "1.7",
	"org.apache.velocity" % "velocity-tools" % "2.0"
)     

play.Project.playJavaSettings