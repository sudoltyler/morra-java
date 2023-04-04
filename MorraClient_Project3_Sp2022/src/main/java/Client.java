import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.function.Consumer;

public class Client extends Thread {
    Socket socketClient;
    ObjectOutputStream out;
    ObjectInputStream in;

    private Consumer<Serializable> callback;
    private String ipAddr;
    private int portNum;
    private MorraInfo playerInfo;
    int guess;
    int play;

    Client(Consumer<Serializable> call) {
        callback = call;
    }

    public void run() {
        try {
            socketClient = new Socket(ipAddr,portNum);
            out = new ObjectOutputStream(socketClient.getOutputStream());
            in = new ObjectInputStream(socketClient.getInputStream());
            socketClient.setTcpNoDelay(true);
        }
        catch (Exception e) {
            callback.accept("Client failed to connect, no server found.");
        }

        while(true) {
            try {
                playerInfo = (MorraInfo) in.readObject();
                System.out.println("Recieved info object!");
                callback.accept(playerInfo.message);
            }
            catch(Exception e) {
                callback.accept("Failed to read from input stream... closing socket");
                try {
                    socketClient.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                break;
            }
        }
    }

    public void setReady() {
        try {
            playerInfo.ready = true;
            out.writeObject(playerInfo);
            out.reset();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send() {
        try {
            playerInfo.play = this.play;
            playerInfo.guess = this.guess;
            out.writeObject(playerInfo);
            out.reset();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setPortIp(int port, String ip) {
        portNum = port;
        ipAddr = ip;
    }

}
