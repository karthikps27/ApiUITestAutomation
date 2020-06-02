FROM ubuntu:focal-20200423

RUN apt-get -o Acquire::Check-Valid-Until=false update

RUN apt-get install -y openjdk-8-jdk wget zip unzip curl

ENV GRADLE_VERSION 6.4.1
ENV ALLURE_VERSION 2.13.3

WORKDIR /tests

# Install Gradle
RUN wget https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip && \
    unzip gradle-${GRADLE_VERSION}-bin.zip && \
    mv gradle-${GRADLE_VERSION} /opt/ && \
    rm gradle-${GRADLE_VERSION}-bin.zip
ENV GRADLE_HOME /opt/gradle-${GRADLE_VERSION}
ENV PATH $PATH:$GRADLE_HOME/bin


ENV JAVA_HOME /usr/lib/jvm/java-8-openjdk-amd64/jre
ENV PATH $JAVA_HOME/bin:$PATH


# Install allure
RUN curl -o allure-commandline-${ALLURE_VERSION}.tgz -Ls https://dl.bintray.com/qameta/maven/io/qameta/allure/allure-commandline/${ALLURE_VERSION}/allure-commandline-${ALLURE_VERSION}.tgz && \
    tar -zxvf allure-commandline-${ALLURE_VERSION}.tgz -C /opt/ && ln -s /opt/allure-${ALLURE_VERSION}/bin/allure /usr/bin/allure && allure --version

# Install Chrome
RUN wget https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb
RUN dpkg -i google-chrome-stable_current_amd64.deb; apt-get -fy install

#Install chrome driver
RUN export chromeDriverLatestVersion=`curl https://chromedriver.storage.googleapis.com/LATEST_RELEASE` && \
    wget https://chromedriver.storage.googleapis.com/${chromeDriverLatestVersion}/chromedriver_linux64.zip
RUN unzip chromedriver_linux64.zip
ENV CHROMEDRIVER_PATH /tests/chromedriver

USER root

#COPY . .
#RUN gradle clean build
#RUN gradle task runTests
#CMD sleep 100
