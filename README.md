Project Summary:

This project is for exercise purposes. "countmeup" represents a poll for candidates and voters who vote for one or more candidates to be selected.

Application Code And Classes Explained Briefly:
All the classes and methods are documented inside the code, however this part is added to provide a brief introduction about the application classes.

AppServer.java: The class wrapping our jetty web server and the necessary methods to run, terminate and query the current condition of the server.
RestHandler.java: The class responsible for handling every rest request. Just processes the post request input and delegates the necessary code logic to the Poll class.
Poll.java: All the necessary logic for voting or result calculation is implemented here.
ResultObject.java: This object represents the result for one single candidate as a candidateName-voteCount pair.
Vote.java: This is the necessary encapsulation of the voter and the candidateName that the voter vote for.

AppTest.java: Unit-test class for testing the necessary cases.
TestPostClient: The post client class which can be used to cerate vote and result rest call without an external tool like SOAP-UI. 

A short description of the motivation behind the creation and maintenance of the project. This should explain **why** the project exists.

INSTALLATION - HOW TO RUN:
There is no installation step necessary for the project. Just run the AppServer clas main method and the jetty server will wait for any incoming request with local url:
http://localhost:8181/countmeup/
For vote: http://localhost:8181/countmeup/vote
For results: http://localhost:8181/countmeup/result
There is a post client to test the Rest server methods. You can use the test client inside the unit test class AppTest of the project or in a custom class you provide.

TESTS:
Test scenarios are grouped into two as voteTest and resultTests. For simplicity the app server necessary for this applications is started up in setUp method of the test class and the server is shot down after all test cases run.

ASSUMPTIONS:
vote requests and getResult requests can be formed in our AppTest class. However if it is necessary to call it from outside like SOAP-UI tool, the json input String for the vote request would be like 
{"voterID":"user1","candidateName":"A"}. The getResult method returns a JSon String will be like: [{"candidateName":"A","voteCount":"8"},{"candidateName":"B","voteCount":"5"},{"candidateName":"C","voteCount":"3"},{"candidateName":"D","voteCount":"1"}]
