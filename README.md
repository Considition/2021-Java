# Java Starterkit Considition 2021
This is the StarterKit for Considition 2021, a help to get going as quickly as possible with the competition. There are four parts of the kit.

- **The Main Program:** This is where we run the main parts. There is an example solver implemented, greedysolver, which you can try out of the box.
- **The Game Layer:** A wrapper between the API and the Main Program. Helps you with formatting the input to the API.
- **The API:** A representation of the REST-API that the game is played with. Can be used directly or through the Game Layer.
- **GreedySolver** This is the out of the box solver you can change, take inspiration from or just replace with your own solver.

Each part us described in greater detail below. The competition itself is also described on [Considition.com/rules](considition.com/rules).

# Installation and running
Install package *com.google.gson.Gson* and run *Main.java*.

# Main Program
The Main Program is a simple loop. Each run of the program does the following:
 - Fetches the desired map
 - Creates a solution with selected solver
	- This is where you can implement your solution. Optimize the solver to maximize your final score.
 - Submits the score to be validated and if approved, evaluated an posted for the competition.
 - Prints the final score, game id to keep track of your best attempts and a link to that games visualization. 
 
 # Game Layer
 The game layer has all the functions you need to play the game.
 
 **The Game**
 - **New Game** Gets the properties of the selected map in a *GameResponse*,  such as its packages and the vehicle dimensions.
 - **Submit Game** Submits your game for validation and evaluation. If solution is valid it returns a *SubmitResponse* with the scoring of your game.
  
  # API 
 The definition of the API and what it returns can be found on game.Considition.com/swagger, or on Considition.com/rules. 
 To see the visualization of your solution you can either follow the link in the gameresponse or go to visualizer.considition.com
  
