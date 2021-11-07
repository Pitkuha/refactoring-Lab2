import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Pitkuha
 */
public class TestRunnableClientTester implements Runnable{
    static Socket socket;

    public TestRunnableClientTester() {
        try {
            socket = new Socket("localhost", 3345);
            System.out.println("Client connected to socket");
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            DataOutputStream oos = new DataOutputStream(socket.getOutputStream());
            DataInputStream ois = new DataInputStream(socket.getInputStream());
            System.out.println("Client oos & ois initialized");
            Scanner scanner = new Scanner(System.in);
            while (true) {
                String command = scanner.nextLine();
                oos.writeUTF(command);
                oos.flush();
                System.out.println();
                String in = ois.readUTF();
                System.out.println(in);
//                Thread.sleep(2000);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ExecutorService exec = Executors.newFixedThreadPool(10);
        exec.execute(new TestRunnableClientTester());
    }
}
