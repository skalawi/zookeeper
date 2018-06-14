package testapp;

import java.io.IOException;

public class ChildrenApp {
    private static final String HOST = "127.0.0.1";
    private static final String NODE_NAME = "/children";

    public static void main(final String[] args) throws InterruptedException, IOException {
        ChildrenGUI gui = new ChildrenGUI();
        ChildrenExecutor executor = new ChildrenExecutor(HOST, 2182, NODE_NAME, gui);
        executor.start();
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                gui.setVisible(true);
            }
        });
    }

}
