import javax.swing.*;
import java.awt.*;
import java.net.Socket;

public class ReversiClient extends JFrame{

    private static JPanel panel;

    public ReversiClient() {
        super("Реверси");
        setLayout(new BorderLayout());
        Socket socket = null;
        try {
            socket = new Socket("127.0.0.1", 1025);
        } catch (Exception e) {
            return;
        }

        panel = new ReversiServer(socket);
        add(panel, BorderLayout.CENTER);

        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    public static void main(String[] args) {
        new ReversiClient();
    }
}
