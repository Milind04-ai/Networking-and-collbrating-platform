FROM tomcat:9.0-jdk8-temurin

RUN rm -rf /usr/local/tomcat/webapps/*

COPY target/Nexus-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/Nexus.war

EXPOSE 10000

CMD ["sh", "-c", "PORT=${PORT:-8080}; sed -i \"s/port=\\\"8080\\\"/port=\\\"${PORT}\\\"/\" /usr/local/tomcat/conf/server.xml; catalina.sh run"]