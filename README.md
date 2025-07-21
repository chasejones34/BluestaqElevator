# BluestaqElevator
Coding challenge: Simulate an elevator
(I tried downloading Docker and creating a Docker image, however I was running into a connection
issue that I could not solve after a couple hours of research. If you guys have experienced a similar issue
I would love to hear how it was solved)
I used VSCode to create this, so the instructions I am giving relate directly to that
(Im also assuming that most of the instructions I am giving are not needed, I just wanted to be sure)

#==========================================================================

To run:
1. Download all 5 java files from the repo
2. Open up the full folder in VSCode
3. Ensure that the java extension is active
4. Hit either the run button in the top right or type "java Main" in the terminal. Be sure to capitalize the M in Main
5. Be amazed at the awe of the elevator simulation

#==========================================================================

Unadded Features:
I ran into an issue early on with having multiple requests trigger at the same time.
What this would end up doing, is making the carriage almost lock in place as it tried to figure out 
which request had a higher priority. In order to alleviate the issue for the time being, I created
a method to disable all button presses (other than the FIRE button, Saftey First). What this did is 
prevent spamming of the buttons so that the elevator wouldnt get stuck. The FIRE button still works
when the others a disabled however. I would love to discuss this and come up with a possible solutuion for it.
