                                           TestNG ReadMe

This file contains the details of TestNG java program.  This program enables integrating Selenium and PySerial scripts. This program can be invoked from a Jenkins job and result in TestNG format is archived in the Jenkins job.  This Jenkins job can be called from within ReleaseIQ pipeline stages.

The Java program can be exetended for Libre specific automation tests and used within ReleaseIQ pipeline.

DIRECTORY STRUCTURE of libre-riq folder.
  1. src : Contains 2 java files.  One for Selenium and other for PySerial
  2. jars: Contains all dependent jar files
  3. classes: Contains compiled output of Java files
  4. test-output: TestNG Test reports of test run is placed here
  5. chromedriver required for Selenium

chmod 777 /home/libreadmin/eclipse-workspace/LibreAuromation/test-output/*

COMPILE TestNG JAVA SOURCE FILES:
  1. Run command below to compile all Java files in src folder.
     
    javac -cp .:jars/testng-7.3.0.jar:jars/jython-standalone-2.7.2.jar:jars/selenium-server-standalone-3.141.59.jar:jars/sikulixide-2.0.4.jar -d ./classes src/*.java
    
  2. All class files (Output of Java compilation) is placed in Classes folder

java -cp bin:jars/* -DserverPath=http://172.16.2.103/LSBuilds_QA/ManualBuilds/LS9_Alexa_Main/20210119_165825_Alexa_LS9AD_NB6061_p5 org.testng.TestNG FwDownload.xml

RUN TestNG:
  1. Run command below for Selenium (Deploy Test)
     java -cp bin:jars/*  org.testng.TestNG DeployTest.xml

  2. Run command below for PySerial Test (Automation Test)
     java -cp classes/fwUpdate:jars/*  org.testng.TestNG PySerialTest.xml

  3. Check the test reports in test-output folder


JENKINS JOB:
  1. LS9Autotest job in Jenkins (http://172.16.2.177:8080/job/LS9Autotest/)
     Runs the TestNG programs and results are Archived
  2. LS9Autotest can be used in the ReleaseIQ pipeline. 


javac -cp .:jars/testng.jar:jars/selenium-server-standalone.jar:jars/log4j.jar -d ./classes src/main/*.java

  java -cp bin:jars/* org.testng.TestNG DeployTest.xml FwDownload.xml

  java -cp bin:jars/* -DserverPaath = http://172.16.2.103/LSBuilds_QA/ManualBuilds/LS9_Alexa_Main/20210119_165825_Alexa_LS9AD_NB6061_p5 org.testng.TestNG FwDownload.xml
