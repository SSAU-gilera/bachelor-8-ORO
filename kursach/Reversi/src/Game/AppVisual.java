package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

public class AppVisual {

    public AppVisual() {
        JFrame frame = new JFrame("Reversi");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        /*panel.setSize(320,320);*/
        ChessBoard chessBoard = new ChessBoard();
        /*frame.getContentPane().add(BorderLayout.CENTER, chessBoard);*/
        panel.setLayout(new BorderLayout());
        panel.setBounds(10,10,640,640);
        //panel.setSize(640,640);
        panel.add(chessBoard,BorderLayout.CENTER);
        frame.add(panel);
        //frame.repaint();
        frame.setSize(1000, 700);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        AppVisual app = new AppVisual();
    }
}
