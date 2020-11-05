Files/classes in this Project --->

Server:
>Not used in any way other than as a sort of reference for simplest socket implementation.

ThreadedServer:
>Our server with one room by default. we can probably figure out a way to make multiple rooms later if we want to. one example is replacing current Room object in this class with a mutable list of Room objects.

ServerThread:
>using this class, a thread is created for every new connection to ThreadedServer. 
>check out getArrayFromMessage function for help on how can we extract array out of string type messages.

Room:
>all the functions needed to be performed in Room. this includes adding user, keeping the board's state etc.

User:
>user state

In Test directory:

>made a test directory to mock client side. 

Client.java:
>simplest version of client. used some static variables here which are identical to ones in ThreadedServer on server side. we can change these in actual project however it suits just remember to make same changes on server side

ClientTest:
>not actual tests. just sort of simulating two scenarios. one for the polling, other for a user action


*** to run tests first run ThreadedServer.java
*** we can run both tests at same time if we wanna test user actions are actually reflected in polling.
