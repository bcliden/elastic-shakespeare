FROM tomcat:7.0.96-jdk8-adoptopenjdk-hotspot
LABEL maintainer="benjamin.c.liden@gmail.com"
ADD ./target/shakespeare.war /usr/local/tomcat/webapps/
EXPOSE 8080
CMD ["catalina.sh", "run"]