# Morra Game - _CS342 Spring 2022_

This program is a game called morra, a hand game that dates back to ancient 
times. This version of morra is played on a client, with different buttons for 
your game options. The game is played between 2 clients using TCP for connection
and the game state communication. The project is done in Java, with fxml and
CSS elements for styling. The project is built and run with maven for both server
and client programs. Also included is a UML pdf with a project outline.

## *Client Program*

The client program is located in MorraClient. The client program connects to a
server using an IP address and a port number. Once a client is connected, they
must click ready. Once two clients are connected and ready, the game will start.
Each player chooses a number of fingers to play, and guesses the number the other
player has played.

## *Server Program*

The server program is located in MorraServer. The server program allows the user
to choose a port to start the server on. The server allows for 2 clients to
connect, and facilitates the game. Clients send a game state object that the
server then uses and returns.
