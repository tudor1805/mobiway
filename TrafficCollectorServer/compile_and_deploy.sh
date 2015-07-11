#!/bin/bash

if [ "$EUID" != "0" ] ; then
	echo "Please run the script as root !"
	exit 1
fi

mvn compile && mvn package

if [ "$?" -ne "0" ] ; then
	echo "Failed to generate war file !"
	exit 1
fi

# My tomcat webapps are found at /var/lib/tomcat7/webapps
# The application I wish to deploy is the main (ROOT) application
webapps_dir=/var/lib/tomcat7/webapps

service tomcat7 stop
sleep 10

# Remove existing assets (if any)
rm -rf $webapps_dir/TrafficCollectorServer

cp ./target/TrafficCollectorServer-1.0.0-BUILD-SNAPSHOT.war TrafficCollectorServer.war

# Copy WAR file into place
mv TrafficCollectorServer.war $webapps_dir

# Restart tomcat
service tomcat7 restart

echo "Done"
