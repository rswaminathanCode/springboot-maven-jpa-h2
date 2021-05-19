
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

Go to command prompt ensure maven and java in the environment path.

Run below command for respective item
- To cleanup locally
   mvn clean 
- To run the local test
   mvn test 
- To resolve the local resolve dependencies and compilation
   mvn install
- To package the application
   mvn package
- To run the application as a standalone
   mvn spring-boot:run
  
 We can use test it through either postman or using below curl command
 - GET: All users
       curl --location --request GET 'http://localhost:8080/users'

 - GET: All user by salary search criteria 
       curl --location --request GET 'http://localhost:8080/users/1/4000
 
 - GET: A user by Id
       curl --location --request GET 'http://localhost:8080/users/E0004'
       
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
       curl --location --request PUT 'http://localhost:8080/users/E0004' \
			--header 'Content-Type: application/json' \
			--data-raw '    {
			        "id": "E0004",
			        "login": "1rhagrid",
			        "name": "1Rubeus Hagrid",
			        "salary": 0.1,
			        "startdate": "2002-11-16"
			    }'

 - DELETE: deleteUser
      curl --location --request DELETE 'http://localhost:8080/users/E0004'
      
 - BULK Upload user using multipart file
       curl --location --request POST 'http://localhost:8080/users/upload' \
            --form 'file=@"/D:/userfile_data.txt"'

 
 Continuous Integration/Continuous Deployment:
  - Herewith provided a Jenkinsfile which can be used for Continuous integration and deployment with or without docker container.
  - Please refer to the Jenkins file, docker file
 