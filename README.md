
This backend service built on 
- Java 8
- Spring boot - 2.4.5
- Spring Data
- H2 in-memory rdbms database

Local installation requires
- JDK 1.8
- Maven 3.8.1
- Editor like STS, eclipse
- Github

Open command prompt ensure maven and java in the environment path.

Run below command for respective item
- To cleanup locally
   mvn clean 
- To run the local test
   mvn test 
- To resolve the local resolve dependencies and compilation
   mvn install
- To package the application and it creates usermgmt-0.0.1-SNAPSHOT.jar
   mvn package
- To run the application as a standalone
   mvn spring-boot:run
- To run the application as a service(as mentioned above in package)
   java -jar  <PATH TO application jar file> 
- To launch service in a docker
   docker build --build-arg JAR_FILE=<PATH TO myapp.jar> .

How to launch H2 database through browser
 - Once you start the application by mvn spring-boot:run browse through the url to access database: http://localhost:8080/h2-console
  
 We can use test it through either postman or using below curl command
 - GET: All users
       curl --location --request GET 'http://localhost:8080/users' --header 'Content-Type: application/json'

 - GET: All user by salary search criteria and sort by Id
       curl --location --request GET 'http://localhost:8080/users/1/100' --header 'Content-Type: application/json'
 
 - GET: A user by Id
       curl --location --request GET 'http://localhost:8080/users/e0004' --header 'Content-Type: application/json'
       
 - POST: createUser
       curl --location --request POST 'http://localhost:8080/users' \
			--header 'Content-Type: application/json' \
			--data-raw '        {
			            "id": "E00021",
			            "login": "rwesley",
			            "name": "Ron Weasley",
			            "salary": 19234.5,
			            "startdate": "2001-11-16"
			        }'

 - PUT: updateUser
			curl --location --request PUT 'http://localhost:8080/users/E00021' \
			--header 'Content-Type: application/json' \
			--data-raw '    {
			        "id": "E00021",
			        "login": "21rhagrid",
			        "name": "21Rubeus Hagrid",
			        "salary": 0.1,
			        "startdate": "2002-11-16"
			    }'

 - DELETE: deleteUser
      curl --location --request DELETE 'http://localhost:8080/users/E0004'
      
 - BULK Upload user using multipart file
       curl --location --request POST 'http://localhost:8080/users/upload' \
            --form 'file=@"/D:/userfile_data.txt"'

 Code analysis and code coverage
  - Can be identified through various maven plug-ins line pmd, checkstyle, jacoco, hammurapi and sonar.
  - The pom.xml file has relevant dependencies can be enabled for pmd to run mvn:site.
  - Once sonar server target is available, get token to execute below command for getting the reports: 
    mvn sonar:sonar -Dsonar.host.url=<<http://localhost:9000>> -Dsonar.login=<<the-generated-token>>
  
   
 Continuous Integration/Continuous Deployment:
  - Herewith provided a Jenkinsfile which can be used for Continuous integration and deployment.
  - Please refer to the Jenkins file, docker file
 
 Bonus, improvements and suggestions:
  - can use profile to maintain multiple application profiles to deploy into different environment such as dev,test,ust and prod
  - we can explore below spring packages
  	- spring-security to control api access by specific role. 
    - spring-boot-actuator package to moniter application health for production ready features.

Note: Future release will be upgraded with the latest java environment and enhance application by adding,upgrading,enhancing testcases into all layers and fix specific findings.