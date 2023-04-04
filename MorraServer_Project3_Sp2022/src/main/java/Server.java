import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Set;
import java.util.function.Consumer;

public class Server {
    public int serverCount = 1;
    private int port = 0;
    private final Consumer<Serializable> callback;

    HashMap<ClientThread, MorraInfo> playerClients = new HashMap<>();
    TheServer serverConnection;

    Server(Consumer<Serializable> call) {
        callback = call;
        serverConnection = new TheServer();
    }

    public void startServer(String portNum) {
        port = Integer.parseInt(portNum);
        if (port != 0) {
            serverConnection.start();
        } else {
            System.out.println("Server failed to start! Invalid port!");
        }
    }

    public class TheServer extends Thread{
        public void run() {
            try (ServerSocket mysocket = new ServerSocket(port)) {
                System.out.println("Server is waiting for a client!");

                while (true) {
                    ClientThread c = new ClientThread(mysocket.accept(), serverCount);
                    callback.accept("client has connected to server: " + "client #" + serverCount);
                    playerClients.put(c, new MorraInfo(serverCount));
                    c.start();
                    serverCount++;
                }
            }
            catch (Exception e) {
                callback.accept("Server socket did not launch");
            }
        }
    }

    class ClientThread extends Thread{
        Socket connection;
        int clientCount;
        ObjectInputStream in;
        ObjectOutputStream out;

        ClientThread(Socket s, int count){
            this.connection = s;
            this.clientCount = count;
        }

        public void messageClients(String message) {
            Set<ClientThread> clients = playerClients.keySet();
            for (ClientThread t : clients) {
                try {
                    MorraInfo i = playerClients.get(t);
                    i.message = message;
                    t.out.writeObject(i);
                    t.out.reset();
                } catch (Exception e) {
                    callback.accept("Lost client connection...");
                }
            }
        }

        public void updateOneClient(ClientThread client, MorraInfo data) {
            try {
                client.out.writeObject(data);
                client.out.reset();
            } catch (Exception e) {
                callback.accept("Lost client connection...");
            }
        }

        public void startGame(ClientThread client, MorraInfo clientData) {
            int clientGuess = 0;
            int clientPlay = 0;

            clientData.message = "Game will start when all players are ready!";
            updateOneClient(client, clientData);
            if (playerClients.size() == 2) {
                messageClients("Two players connected and ready, game start !");
                while (true) {
                    try {
                        if (clientGuess == 0 && clientPlay == 0) {
                            messageClients("Chose a number, and make a guess at the opponents number.");
                            MorraInfo data = (MorraInfo) in.readObject();
                            clientGuess = data.guess;
                            clientPlay = data.play;
                            callback.accept("Client #" + clientCount + " played " + clientGuess + " and guessed " + clientGuess);
                        } else {
                            MorraInfo data = (MorraInfo) in.readObject();
                            callback.accept("Client #" + clientCount + " sent morra info");
                        }

                    }
                    catch (Exception e) {

                    }
                }
            }
        }

        public void run() {
            try {
                in = new ObjectInputStream(connection.getInputStream());
                out = new ObjectOutputStream(connection.getOutputStream());
                connection.setTcpNoDelay(true);
            } catch (Exception e) {
                System.out.println("Streams not open");
            }

            if (playerClients.size() <= 2) {
                MorraInfo i = playerClients.get(this);  // get current client morra info
                System.out.println(playerClients.size());
                i.message = "Connected to server as client #" + clientCount;
                updateOneClient(this,i);
                while (true) {
                    try {
                        messageClients("Waiting for players...");
                        MorraInfo data = (MorraInfo) in.readObject();
                        if (data.ready) {
                            callback.accept("Client: " + clientCount + " is ready!");
                            messageClients("Client: " + clientCount + " is ready!");
                            startGame(this, data);
                        }
                        // default assumes message
                        callback.accept("client: " + clientCount + " sent MorraInfo object " + data.message);
//                        messageClients("client: " + clientCount + " sent MorraInfo object " + data.message);
                    } catch (Exception e) {
                        callback.accept("OOOPPs...Something wrong with the socket from client: " + clientCount + "....closing down!");
                        messageClients("Client #" + clientCount + " has left the server!");
                        playerClients.remove(this);
                        clientCount--;
                        break;
                    }
                }
            } else if (playerClients.size() > 2) {
                MorraInfo i = playerClients.get(this);
                i.message = "Cannot connect! Maximum number of players!";
                updateOneClient(this, i);
                try {
                    connection.close();
                    callback.accept("Client #" + clientCount + " denied connection to the server!");
                    playerClients.remove(this);
                    serverCount--;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } //end of run
    } //end of client thread
}
