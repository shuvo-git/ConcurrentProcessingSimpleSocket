Java Developer Assignment
=========================

This document outlines the specification of a TCP/IP socket server and client. Your task is to develop a simple TCP/IP
socket server based on specifications and also a client to send subsequent requests to the server.

TCP/IP Socket Server
--------------------
A socket is one endpoint of a two-way communication link between two programs running on the network. The socket is
bound to a port number so that the TCP layer can identify the application that data is destined to be sent to.

You can find an example of Java Socket Server [here](app/src/main/java/socket/server/App.java).

In server application, there is a class
called [`PrimeCalculationManager`](app/src/main/java/socket/server/manager/PrimeCalculationManager.java) which returns
the count of prime numbers less or equal to the given input n. There might be lots of other manager classes like this.

Our desired socket server should be able to handle subsequent requests from clients. So one specification of the socket
server would be, it must be able to handle incoming requests in parallel **for every socket connection**.

TCP/IP Socket Client
--------------------
A client can establish a socket connection to the server and send/receive messages. Here is an example of a TCP/IP
socket client.

Our desired client will send requests to the server with
the [`RequestObject`](app/src/main/java/socket/server/io/RequestObject.java).

For example, if I want to get the count of prime numbers less than or equal to 100, it should have request object like
following:

```
{
    "method":"findPrimes",
    "managerName":"PrimeCalculationManager",
    "args":{"n":"100"}
}
```

Tasks
-----
1. Implement the socket server which will receive incoming requests from clients. The incoming
requests object will have 3 parameters.

 - **managerName**: The server will instantiate an instance of the manager class from the managerName. Here we have a
  constraint. We have to instantiate the manager class using reflection.
 - **method**: It denotes which method we need to call for that specific manager. Here we will also have the same
  constraint. We will get the method using reflection and invoke later.
 - **args**: Denotes the arguments need to invoke the method.

 So the server will execute the requested method with provided arguments and will return the result to the client.

2. Implement the socket client which will send subsequent requests to server with appropriate request object. **Important:** we can use one socket connection to send multiple requests.  

For example if we send the following 4 numbers squentially in the same socket connection:-
```
10, 1000000, 200, 50000
```
Then we are expecting response in the following order:-
```
4 // n = 10
46 // n = 200
5133 // n = 50000
78498 // n = 1000000
```

Assignment Instructions
------------------------
1. Please create a new repository in your Github by forking this repository and then make the necessary changes in your own repository as per instruction.
2. Commit to GitHub from the initial setup, commit early and often. Don’t commit everything after finishing the assignment. By looking into your commit message, we will try to get an idea how you approached the problem. 
3. Provide a readme file which will contain how to run and package your application.
4. Try to make sure, your implemented functionalities are as complete as possible based on the provided use cases including any corner cases.
5. You are free to use any third-party library if you want. Just make sure that you have given the instructions on the readme file regarding how to install those.
6. When you are done, please make sure to provide access to ['cefalolab'](https://github.com/cefalolab) user to your repository so that we can review your source code.

