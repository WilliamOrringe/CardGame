# Card Game
The game has n players, each numbered 1 to n (which for clarity in the illustration below are named player1, player2, ... , playern), with n being a positive integer, and n decks of cards, again, each numbered 1 to n (which for clarity in the illustration below are named deck1, deck2, ... , deckn). Each player will hold a hand of 4 cards. Both these hands and the decks will be drawn from a pack which contains 8n cards. Each card has a face value (denomination) of a non-negative integer.
At the start of the game, each player will be distributed four cards in a round-robin fashion, from the top of the pack, starting by giving one card to player1, then one card to player2, etc. After the hands have been distributed, the decks will then be filled from the remaining cards in the pack, again in a round-robin fashion.
To win the game, a player needs four cards of the same value in their hand.

### Prerequisites
What software to install

```
install jdk
```
```
install Java 8+
```

## Running the jar file
Running the main application
```
cd CardTest
```
```
java -jar cards.jar
```
The text files will be created in the same filepath as the jar file.

## Running the Junit Test
Running the test class
```
cd CardTest/src/
```
```
javac -cp .:lib/*  com/cards/fourofakind/test/CardTest.java 
```
```
cd java -cp .:lib/* org.junit.runner.JUnitCore com.cards.fourofakind.test.CardTest
```

## Deployment
Open the text files for each deck & player to see the program's gameplay.

## Built With
* Java

## Contributing
Please read [CONTRIBUTING.md](https://gist.github.com/PurpleBooth/b24679402957c63ec426) for details on our code of conduct, and the process for submitting pull requests to us.


## Authors
* **690064952**
* **690029129**

## License
This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details
