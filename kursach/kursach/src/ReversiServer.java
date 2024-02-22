import java.awt.Color;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;


public class ReversiServer extends JPanel {

    JPanel boardPanel;
    static JLabel score;
    static JButton[] cell;
    static ReversiLogic board;
    static ArrayList<ReversiLogic> arrReversi = new ArrayList<>();
    static JButton newGame;
    static JButton rules;
    static JButton info;
    static public int userScore = 2;
    static public int pcScore = 2;
    private static ReversiLogic start;
    private static int rows = 8;
    private static int cols = 8;

    public Image imgL, imgD;

    {
        try {
            imgL = ImageIO.read(getClass().getResource("images/computer.png"));
            imgD = ImageIO.read(getClass().getResource("images/player.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ImageIcon imgLight = new ImageIcon(imgL);
    public ImageIcon imgDark = new ImageIcon(imgD);

    public Color darkGray = new Color(80, 80, 80);
    public Color lightGray = new Color(238, 238, 238);
    public Color green = new Color(152, 251, 152);

    Font font55 = new Font("Courier", Font.BOLD, 65);
    Font font15 = new Font("Courier", Font.TRUETYPE_FONT, 15);

    public Color ColorCheck(int col, int row) {
        Color color = darkGray;
        if (((col % 2 == 0) && (row % 2 == 0)) || ((col % 2 == 1) && (row % 2 == 1))) {
            color = lightGray;
        }
        return color;
    }

    public ReversiServer(Socket socket) {
        super(new BorderLayout());
        setPreferredSize(new Dimension(1000, 700));
        setLocation(0, 0);
        board = new ReversiLogic();
        start = board;
        arrReversi.add(new ReversiLogic(board));

        newGame = new JButton("Новая игра");
        newGame.setFont(font15);
        newGame.setPreferredSize(new Dimension(350, 40));
        newGame.addActionListener(new Action());
        rules = new JButton("Правила");
        rules.setFont(font15);
        rules.setPreferredSize(new Dimension(100, 40));
        rules.addActionListener(new Action());
        info = new JButton("Информация о разработчиках");
        info.setFont(font15);
        info.setPreferredSize(new Dimension(250, 40));
        info.addActionListener(new Action());

        boardPanel = new JPanel(new GridLayout(8, 8));
        boardPanel.setBounds(0, 300, 750, 750);
        cell = new JButton[64];
        int k = 0;
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                cell[k] = new JButton();
                cell[k].setSize(50, 50);
                cell[k].setBackground(ColorCheck(col, row));
                if (board.gameCells[row][col].getCh() == 'X') {
                    cell[k].setIcon(imgDark);
                } else if (board.gameCells[row][col].getCh() == 'O') {
                    cell[k].setIcon(imgLight);
                } else if (row == 2 && col == 4 || row == 3 && col == 5 || row == 4 && col == 2 || row == 5 && col == 3) {
                    cell[k].setBackground(green);
                }
                cell[k].addActionListener(new Action());
                boardPanel.add(cell[k]);
                k++;
            }
        }
        add(boardPanel);

        JLabel dark = new JLabel();
        dark.setIcon(imgDark);
        JLabel light = new JLabel();
        light.setIcon(imgLight);

        JPanel gamePanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        JPanel infoPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c1 = new GridBagConstraints();
        JPanel scorePanel = new JPanel(new GridBagLayout());
        GridBagConstraints c2 = new GridBagConstraints();

        c.weightx = 300;
        c.weighty = 800;
        c1.gridx = 0;
        c1.gridy = 0;
        infoPanel.add(rules, c1);
        c1.gridx = 1;
        c1.gridy = 0;
        infoPanel.add(info, c1);
        c.gridx = 0;
        c.gridy = 1;
        gamePanel.add(infoPanel, c);
        score = new JLabel();
        score.setText(userScore + " : " + pcScore);
        score.setFont(font55);
        JLabel players = new JLabel();
        players.setText("   Вы                    Компьютер");
        players.setFont(font15);
        c2.gridx = 0;
        c2.gridy = 0;
        scorePanel.add(players, c2);
        c2.gridx = 0;
        c2.gridy = 1;
        scorePanel.add(score, c2);
        c.gridx = 0;
        c.gridy = 2;
        gamePanel.add(scorePanel, c);
        c.gridx = 0;
        c.gridy = 3;
        gamePanel.add(newGame, c);
        add(gamePanel, BorderLayout.EAST);
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(1025);
        System.out.println("Игра \"Реверси\" запущена...");
        PrintWriter out = null;
        BufferedReader in = null;
        Socket socket = null;
        while (true) {
            try {
                socket = serverSocket.accept();
                out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
            new ReversiServer(socket);
            try {
                out.println();
                System.out.println("Клиент успешно подключён...\n");
            } catch (Exception e) {
                System.err.println("Ошибка");
            }
            out.close();
            in.close();
            socket.close();
        }
    }


    class Action implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == newGame) {
                arrReversi.clear();
                board = new ReversiLogic();
                arrReversi.add(new ReversiLogic(board));
                int k = 0;
                for (int row = 0; row < rows; row++) {
                    for (int colum = 0; colum < cols; colum++) {
                        cell[k].setIcon(null);
                        cell[k].setBackground(ColorCheck(colum, row));
                        if (board.gameCells[row][colum].getCh() == 'X') {
                            cell[k].setIcon(imgDark);
                        } else if (board.gameCells[row][colum].getCh() == 'O') {
                            cell[k].setIcon(imgLight);
                        }
                        if (row == 2 && colum == 4 || row == 3 && colum == 5 || row == 4 && colum == 2 || row == 5 && colum == 3) {
                            cell[k].setBackground(green);
                        }
                        ++k;
                    }
                }
                userScore = 2;
                pcScore = 2;
                score.setText(userScore + " : " + pcScore);
            } else if (e.getSource() == rules) {
                new RulesFrame();
            } else if (e.getSource() == info) {
                new InfoFrame();
            } else {
                for (int i = 0; i < 64; i++) {
                    if (e.getSource() == cell[i]) {
                        int xCor, yCor, ct, point;
                        int arr[] = new int[3];
                        if (i == 0) {
                            xCor = 0;
                            yCor = 0;
                        } else {
                            yCor = i % 8;
                            xCor = i / 8;
                        }
                        ct = board.gamePlay(xCor, yCor);
                        System.out.printf("Ход игрока: (%d;%d)\n", xCor, yCor);
                        if (ct == 0) {
                            arrReversi.add(new ReversiLogic(board));
                            int k = 0;
                            for (int row = 0; row < rows; row++) {
                                for (int colum = 0; colum < cols; colum++) {
                                    if (board.gameCells[row][colum].getCh() == 'X') {
                                        cell[k].setIcon(imgDark);
                                        cell[k].setBackground(ColorCheck(colum, row));
                                    } else if (board.gameCells[row][colum].getCh() == 'O') {
                                        cell[k].setIcon(imgLight);
                                        cell[k].setBackground(ColorCheck(colum, row));
                                    }
                                    k++;
                                }
                            }
                            board.fillingFieldWithControls(arr);
                            userScore = arr[0];
                            pcScore = arr[1];
                            point = arr[2];
                            score.setText(userScore + " : " + pcScore);
                        }
                        if (ct == 0 || ct == -1) {
                            board.gamePlay();
                            arrReversi.add(new ReversiLogic(board));
                            ArrayList<Integer> arrList = new ArrayList<Integer>();
                            int k = 0;
                            for (int row = 0; row < rows; row++) {
                                for (int colum = 0; colum < cols; colum++) {
                                    if (board.gameCells[row][colum].getCh() == 'X') {
                                        cell[k].setIcon(imgDark);
                                    } else if (board.gameCells[row][colum].getCh() == 'O') {
                                        cell[k].setIcon(imgLight);
                                    } else if (board.gameCells[row][colum].getCh() == '.') {
                                        cell[k].setIcon(null);
                                    }
                                    cell[k].setBackground(ColorCheck(colum, row));
                                    k++;
                                }
                            }
                            board.findLegalMove(arrList);
                            for (int j = 0; j < arrList.size(); j += 2) {
                                cell[arrList.get(j) * rows + arrList.get(j + 1)].setBackground(green);
                            }
                            board.fillingFieldWithControls(arr);
                            userScore = arr[0];
                            pcScore = arr[1];
                            point = arr[2];
                            score.setText(userScore + " : " + pcScore);
                        }
                    }
                }
                int st = board.endOfGame();
                if (st == 0) {
                    if (userScore > pcScore)
                        JOptionPane.showMessageDialog(null, "Вы выиграли!\nВозможных ходов нет.", "Конец игры", JOptionPane.PLAIN_MESSAGE);
                    else
                        JOptionPane.showMessageDialog(null, "Вы проиграли!\nВозможных ходов нет.", "Конец игры", JOptionPane.PLAIN_MESSAGE);
                } else if (st == 1 || st == 3) {
                    JOptionPane.showMessageDialog(null, "Вы проиграли!", "Конец игры", JOptionPane.PLAIN_MESSAGE);
                } else if (st == 2 || st == 4) {
                    JOptionPane.showMessageDialog(null, "Вы выиграли!", "Конец игры", JOptionPane.PLAIN_MESSAGE);
                } else if (st == 5) {
                    JOptionPane.showMessageDialog(null, "Ничья!", "Конец игры", JOptionPane.PLAIN_MESSAGE);
                }
            }
        }
    }
}
