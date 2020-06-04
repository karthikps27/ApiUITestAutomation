###### ApiUITestAutomation
Test project for running both UI and API testcases.

### Setup & Tools
* Install intellij (optional):
  https://www.jetbrains.com/idea/download/
* Install docker desktop (optional): 
  https://www.docker.com/products/docker-desktop    
* Java JDK 1.8 from Oracle: https://www.oracle.com/java/technologies/javase-jdk8-downloads.html
* Download and extract Gradle: 
  https://gradle.org/next-steps/?version=6.4&format=bin
* Download and Extract Allure commandline:  
  https://repo.maven.apache.org/maven2/io/qameta/allure/allure-commandline/2.13.3/allure-commandline-2.13.3.zip
* Set Environment variables      
    * JAVA_HOME: Pointing to the Java SDK folder
    * GRADLE_HOME: Pointing to Gradle directory
    * ALLURE_HOME: Pointing to allure directory
    * PATH: Paths of bin folder of all three should be added to PATH variable

### Build and Run
To build and run, execute below commands in the mentioned order.

```$xslt
$ gradle clean build
$ gradle task runTests
$ gradle allureReport
$ gradle allureServe
```

To disable headless mode, src/test/resources/testng.xml has the parameter "headless". Set its value to false.

### Run on Docker Containers
This is an optional step, but a good practice as Docker Containers provides easier and more cleaned approach for running testcases.
To do this step, docker desktop should be running.

Spin up docker containers using docker-compose via command line
```$xslt
$ docker-compose up -d
```
Above step could take a while during the first time around. 

Once the execution is done, you will have access to jenkins where the jobs are preconfigured. Go to job ApiUIAutomation and build it. Reports will be accessible once the job is complete.


