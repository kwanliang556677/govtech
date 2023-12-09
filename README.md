# govtech
This Application consist of Front-end and Back-end. 

For front end module, simply open the test.htm in Google chrome. While 

For back-end application it is written in Java 17 with Spring Boot 3.2 as its framework. Simply import the backend as maven in inteliJ and run the main method in DemoApplication.java.

===API Description (LunchSessionController)===
initiate-session -> This is to initiate a lunch session, it will return a UUID+timestamp that represent a sessionID
join-session -> This is to allow user to join the session 
add-location -> This allow joined user to submit a lunch location
end-session -> This allow the user that initialize the session to end the session so that other user cant join and submit thier restaurant location.
list-finalize-restaurant -> This is to display the finalize location.


Step to finalize a lunch location.
1. Open test.html file in chrome.
2. Click the Initiate Session -> there will be a link generated -> copy the link and open in a new tab.
3. Enter your name or employee ID in the new link and click "Join Session".
4. Enter your desired lunch location and click "Add location".
5. Go back to the initial tab and click "End Session".
6. Go to the tab where you submitted the recommended restaurant and click "List Restaurant"

End
