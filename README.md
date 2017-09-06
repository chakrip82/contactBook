# contactBookApp

This contactBookApp allows the endUsers/Customers to store/manage their Contacts.

Please see the complete problem statement in the Design Document.

The following are the steps needed for executing the project.

The following are the dependencies for executing this project
Java7/Java8, Maven3, MongoDB 3.0.5

The following are the contents of this Repo


Contact Book App Design Document.pdf	README.md				contactBookApp.postman_collection	mvnw.cmd				src
Procfile				contactBook.iml				mvnw					pom.xml					target


Contact Book App Design Document contains the design considerations for the contactBook App.
contactBookApp.postman_collection contains whole postman collection for verifying the contactBook App.


Steps for building the Contact Book App

mvn clean install

The above command will clean, compile, run the test cases(both unit tests and integration tests), generate output artifact which in this case is contactBook-0.0.1-SNAPSHOT.jar in target folder.

Steps for Executing the Contact Book App

Pre Requisite : Please have the local mongodb running and all contacts will be stored in DB(contacts) and collection(contacts)

java -jar target/contactBook-0.0.1-SNAPSHOT.jar





