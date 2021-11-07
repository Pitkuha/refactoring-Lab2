import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Pitkuha
 */
public class MultiThreadServer {
    static ExecutorService executeIt = Executors.newFixedThreadPool(2);

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(3345);
             BufferedReader br = new BufferedReader(new InputStreamReader(System.in))){
            System.out.println("Server socket created, command console reader for listen to server commands");
            while (!server.isClosed()){
                if (br.ready()){
                    System.out.println("Main Server found any message in channel, lets look at them.");

                    String serverCommand = br.readLine();
                    if (serverCommand.equalsIgnoreCase("quit")){
                        System.out.println("Main server initiate exiting...");
                        server.close();
                        break;
                    }
                }

                Socket client = server.accept();

                executeIt.execute(new MonoThreadClientHandler(client));
                System.out.println("Connection accepted");
            }

            executeIt.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
