# syntax=docker/dockerfile:1
FROM ubuntu
user root

ENV http_proxy http://proxy.conexus.svc.local:3128
ENV https_proxy http://proxy.conexus.svc.local:3128
ENV NO_PROXY "vault.azure.net,.vaultcore.azure.net,.svc.local,.internal.cloudapp.net,localhost,127.0.0.1,.az.3pc.att.com,.azurecr.io,.jboss.org"

RUN perl -pi.bak -e 's/archive.ubuntu.com/azure.archive.ubuntu.com/g' /etc/apt/sources.list

# ssh
ENV SSH_PASSWD "root:Docker!"
RUN apt-get update \
        && apt-get update \
        && apt-get install -y --no-install-recommends openssh-server \
        && apt-get install -y --no-install-recommends lsof \
        && apt-get install -y --no-install-recommends net-tools

#ENV JAVA_HOME /usr/bin/java
RUN apt-get install -y --no-install-recommends openjdk-8-jdk

RUN apt-get install -y --no-install-recommends curl

VOLUME /tmp
ADD target/DockerJava-MessiFinal30.jar app.jar
COPY init.sh   /bin/init.sh
ENTRYPOINT ["/bin/init.sh"]