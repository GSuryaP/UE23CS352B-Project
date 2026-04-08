# OOAD Project : Personal Expense Tracker

## Initial Java and maven Setup

### Java Setup
- Check for your java version using the command: ```java --version```
  
  If your java version is 17.x, you can proceed. If it is not 17.x, you have to install Java17.x for springboot3.x+ to work, which has the features introduced in the modern springboot. The Java17.x can be installed using the following steps:
  
  - Install Java JDK 17:
    ```
    sudo apt update
    sudo apt install openjdk-18-jdk -y
    ```
  - Verification of the installed Java version can be done by the command: ```java --version```
  - Setting Java17 as the default Java version:
    ```
    sudo update-alternatives --config java
    ```

### Maven Setup
- Maven is used in the Personal Expense Tracker System to manage dependencies and automate the build, testing, and packaging process. It also provides a standard project structure and seamless integration with Spring Boot.

  - Install Maven:
    ```
    sudo apt install maven -y
    ```
  - Check installed Maven version: ```mvn --version```
    (should be 3.8 or above)

### SpringBoot Setup
- Go to Spring Initializr: ```https://start.spring.io/```
- Set the values of the fields as same as the one given below:

    - **H2 Database**: Requires no huge setup, easy demom in-memory DB
    - Click on **Generate** and download the zip file.
    - Extract the zip file and open it in terminal.
    - In the terminal, run the command: ``` mvn sping-boot:run```
        (What to expect: Success messages after a huge process execution. Success messages to expect: Starting ExpenseTrackerApplication using Java 17.0.18, Tomcat initialized with port 8080 (http))
    - For verification the proper execution of springboot without any error, you can go onto the url: ```http://localhost:8080``` and if no error related to Java, Port or Maven is shown, you are goot to go!!
- 
