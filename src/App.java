import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.Scanner;

public class App {

    private static final String HOST = "127.0.0.1";
    private static final String NODE_NAME = "/znode_testowy";

    public static void main(final String[] args) throws InterruptedException, IOException {
        Scanner reader = new Scanner(System.in);
        int port;
        String exec;
        String line;

        if (args.length != 2) {
            port = Integer.parseInt(reader.nextLine().trim());
            exec = reader.nextLine();
        } else {
            port = Integer.parseInt(args[0]);
            exec = args[1];
        }
        Executor executor = new Executor(HOST, port, exec, NODE_NAME);
        executor.start();

        loop:
        while ((line = reader.nextLine()) != null) {
            switch (line) {
                case "show":
                    executor.printNodeFamily();
                    break;
                case "quit":
                    executor.stop();
                    break loop;
                default:
                    System.out.println("You can only show or quit");
            }
        }
    }

}
