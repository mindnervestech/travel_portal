name := "travel_portal"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
    "mysql" % "mysql-connector-java" % "5.1.6",	
    javaJdbc,
  	cache,
	javaJpa,
	"org.hibernate" % "hibernate-entitymanager" % "3.6.9.Final", 
	"org.json"%"org.json"%"chargebee-1.0"
)     

play.Project.playJavaSettings