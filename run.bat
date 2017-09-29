echo on
start java -cp h2-1.4.191.jar org.h2.tools.Server
timeout /t 1
gradlew --console plain wrapper bootRun
