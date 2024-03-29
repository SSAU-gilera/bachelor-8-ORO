import java.util.ArrayList;

public class ReversiLogic {
    private int rows = 8;
    private int cols = 8;
    private int computerCount = 0;
    private int playerCount = 0;
    public Cell gameCells[][];

    public ReversiLogic() {
        int mid;
        mid = rows / 2;
        gameCells = new Cell[8][8];
        for (int i = 0; i < rows; ++i)
            gameCells[i] = new Cell[8];
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                gameCells[i][j] = new Cell();
                char c = (char) (97 + j);
                if ((i == mid - 1) && (j == mid - 1)) {
                    gameCells[i][j].setPosition(c, 'X', i + 1);
                } else if ((i == mid - 1) && (j == mid)) {
                    gameCells[i][j].setPosition(c, 'O', i + 1);
                } else if ((i == mid) && (j == mid - 1)) {
                    gameCells[i][j].setPosition(c, 'O', i + 1);
                } else if ((i == mid) && (j == mid)) {
                    gameCells[i][j].setPosition(c, 'X', i + 1);
                } else {
                    gameCells[i][j].setPosition(c, '.', i + 1);
                }
            }
        }
    }

    public ReversiLogic(ReversiLogic reversi) {
        int y;
        char c, x;
        gameCells = new Cell[8][8];
        for (int i = 0; i < rows; ++i)
            gameCells[i] = new Cell[8];
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                gameCells[i][j] = new Cell();
                c = reversi.gameCells[i][j].getCh();
                y = reversi.gameCells[i][j].getCorY();
                x = reversi.gameCells[i][j].getCorX();
                gameCells[i][j].setPosition(x, c, y);
            }
        }
    }

    public void findLegalMove(ArrayList<Integer> legalMoves) {
        int change;
        change = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (gameCells[i][j].getCh() == '.') {
                    int numberOfMoves[] = new int[1];
                    move(i, j, change, 'X', 'O', numberOfMoves);
                    if (numberOfMoves[0] != 0) {
                        legalMoves.add(i);
                        legalMoves.add(j);
                    }
                }
            }
        }
    }

    public int gamePlay() {
        int change, max = 0, mX = 0, mY = 0;
        change = 0;
        int numberOfMoves[] = new int[1];
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                if (gameCells[i][j].getCh() == '.') {
                    move(i, j, change, 'O', 'X', numberOfMoves);
                    if (max < numberOfMoves[0]) {
                        max = numberOfMoves[0];
                        mX = i;
                        mY = j;
                    }
                }
            }
        }
        computerCount = max;
        if (computerCount == 0) {
            computerCount = -1;
            return -1;
        }
        if (computerCount != 0) {
            change = 1;
            move(mX, mY, change, 'O', 'X', numberOfMoves);
        }
        return 0;
    }

    public int gamePlay(int xCor, int yCor) {
        int status;
        int change = 0, max = 0;
        int numberOfMoves[] = new int[1];
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                if (gameCells[i][j].getCh() == '.') {
                    move(i, j, change, 'X', 'O', numberOfMoves);
                    if (max < numberOfMoves[0])
                        max = numberOfMoves[0];
                }
            }
        }
        playerCount = max;
        if (playerCount == 0) {
            playerCount = -1;
            return -1;
        }
        if (playerCount != 0) {
            change = 1;
            if (!(gameCells[xCor][yCor].getCh() == '.'))
                return 1;
            status = move(xCor, yCor, change, 'X', 'O', numberOfMoves);
            if (status == -1)
                return 1;
        }
        return 0;
    }

    public int endOfGame() {
        int[] arr = new int[3];
        int cross, circular, point;
        fillingFieldWithControls(arr);
        cross = arr[0];
        circular = arr[1];
        point = arr[2];

        if ((playerCount == -1 && computerCount == -1) || point == 0) {
            if (playerCount == -1 && computerCount == -1)
                return 0;
            if (circular > cross)
                return 1;
            else if (cross > circular)
                return 2;
            else if (cross == 0)
                return 3;
            else if (circular == 0)
                return 4;
            else
                return 5;
        }
        return -1;
    }

    public void fillingFieldWithControls(int[] controls) {
        int cross = 0, point = 0, circular = 0;
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                if (gameCells[i][j].getCh() == 'X')
                    cross++;
                else if (gameCells[i][j].getCh() == 'O')
                    circular++;
                else if (gameCells[i][j].getCh() == '.')
                    point++;
            }
        }
        controls[0] = cross;
        controls[1] = circular;
        controls[2] = point;
    }

    int move(int xCor, int yCor, int change, char char1, char char2, int[] numberOfMoves) {
        int cont, st2 = 0, st = 0;
        int status = -1, corX, corY, temp;
        char str;
        int ix, y, x;
        x = xCor;
        y = yCor;
        numberOfMoves[0] = 0;
        if ((x + 1 < rows) && (gameCells[x + 1][y].getCh() == char2)) {
            cont = x;
            while ((cont < rows) && (st2 != -1) && (st != 2)) {
                cont++;
                if (cont < rows) {
                    if (gameCells[cont][y].getCh() == char2)
                        st = 1;
                    else if (gameCells[cont][y].getCh() == char1)
                        st = 2;
                    else
                        st2 = -1;
                }
            }
            if (st == 2) {
                temp = cont - x - 1;
                numberOfMoves[0] += temp;
            }
            if (st == 2 && change == 1) {
                for (int i = x; i < cont; ++i) {
                    str = gameCells[i][y].getCorX();
                    ix = gameCells[i][y].getCorY();
                    gameCells[i][y].setPosition(str, char1, ix);
                }
                status = 0;
            }
            st = 0;
            st2 = 0;
        }
        if ((x - 1 >= 0) && (gameCells[x - 1][y].getCh() == char2)) {
            cont = x;
            while ((cont >= 0) && (st2 != -1) && (st != 2)) {
                cont--;
                if (cont >= 0) {
                    if (gameCells[cont][y].getCh() == char2)
                        st = 1;
                    else if (gameCells[cont][y].getCh() == char1)
                        st = 2;
                    else
                        st2 = -1;
                }
            }
            if (st == 2) {
                temp = x - cont - 1;
                numberOfMoves[0] += temp;
            }
            if (st == 2 && change == 1) {
                for (int i = cont; i <= x; ++i) {
                    str = gameCells[i][y].getCorX();
                    ix = gameCells[i][y].getCorY();
                    gameCells[i][y].setPosition(str, char1, ix);
                }
                status = 0;
            }
            st = 0;
            st2 = 0;
        }
        if ((y + 1 < cols) && (gameCells[x][y + 1].getCh() == char2)) {
            cont = y;
            while ((cont < cols) && (st2 != -1) && (st != 2)) {
                cont++;
                if (cont < cols) {
                    if (gameCells[x][cont].getCh() == char2)
                        st = 1;
                    else if (gameCells[x][cont].getCh() == char1)
                        st = 2;
                    else
                        st2 = -1;
                }
            }
            if (st == 2) {
                temp = cont - y - 1;
                numberOfMoves[0] += temp;
            }
            if (st == 2 && change == 1) {
                for (int i = y; i < cont; ++i) {
                    str = gameCells[x][i].getCorX();
                    ix = gameCells[x][i].getCorY();
                    gameCells[x][i].setPosition(str, char1, ix);
                }
                status = 0;
            }
            st = 0;
            st2 = 0;
        }
        if ((y - 1 >= 0) && (gameCells[x][y - 1].getCh() == char2)) {
            cont = y;
            while ((cont >= 0) && (st2 != -1) && (st != 2)) {
                cont--;
                if (cont >= 0) {
                    if (gameCells[x][cont].getCh() == char2)
                        st = 1;
                    else if (gameCells[x][cont].getCh() == char1)

                        st = 2;
                    else
                        st2 = -1;
                }
            }
            if (st == 2) {
                temp = y - cont - 1;
                numberOfMoves[0] += temp;
            }
            if (st == 2 && change == 1) {
                for (int i = cont; i <= y; ++i) {
                    str = gameCells[x][i].getCorX();
                    ix = gameCells[x][i].getCorY();
                    gameCells[x][i].setPosition(str, char1, ix);
                }
                status = 0;
            }
            st = 0;
            st2 = 0;
        }
        if ((x - 1 >= 0) && (y + 1 < cols) && (gameCells[x - 1][y + 1].getCh() == char2)) {
            corY = y;
            corX = x;
            while ((corX >= 0) && (corY < cols) && (st2 != -1) && (st != 2)) {
                corX--;
                corY++;
                if ((corX >= 0) && (corY < cols)) {
                    if (gameCells[corX][corY].getCh() == char2)
                        st = 1;
                    else if (gameCells[corX][corY].getCh() == char1)
                        st = 2;
                    else
                        st2 = -1;
                }
            }
            if (st == 2) {
                temp = x - corX - 1;
                numberOfMoves[0] += temp;
            }
            if (st == 2 && change == 1) {
                while ((corX <= x) && (y < corY)) {
                    corX++;
                    corY--;
                    if ((corX <= x) && (y <= corY)) {
                        str = gameCells[corX][corY].getCorX();
                        ix = gameCells[corX][corY].getCorY();
                        gameCells[corX][corY].setPosition(str, char1, ix);
                    }
                }
                status = 0;
            }
            st = 0;
            st2 = 0;
        }
        if ((x - 1 >= 0) && (y - 1 >= 0) && (gameCells[x - 1][y - 1].getCh() == char2)) {
            corY = y;
            corX = x;
            while ((corX >= 0) && (corY >= 0) && (st2 != -1) && (st != 2)) {
                corX--;
                corY--;
                if ((corX >= 0) && (corY >= 0)) {
                    if (gameCells[corX][corY].getCh() == char2)
                        st = 1;
                    else if (gameCells[corX][corY].getCh() == char1)
                        st = 2;
                    else
                        st2 = -1;
                }
            }
            if (st == 2) {
                temp = x - corX - 1;
                numberOfMoves[0] += temp;
            }
            if (st == 2 && change == 1) {
                while ((corX <= x) && (corY <= y)) {
                    corX++;
                    corY++;
                    if ((corX <= x) && (corY <= y)) {
                        str = gameCells[corX][corY].getCorX();
                        ix = gameCells[corX][corY].getCorY();
                        gameCells[corX][corY].setPosition(str, char1, ix);
                    }
                }
                status = 0;
            }
            st = 0;
            st2 = 0;
        }
        if ((x + 1 < rows) && (y + 1 < cols) && (gameCells[x + 1][y + 1].getCh() == char2)) {
            corY = y;
            corX = x;
            while ((corX < rows) && (corY < cols) && (st2 != -1) && (st != 2)) {
                corX++;
                corY++;
                if ((corX < rows) && (corY < cols)) {
                    if (gameCells[corX][corY].getCh() == char2)
                        st = 1;
                    else if (gameCells[corX][corY].getCh() == char1)
                        st = 2;
                    else
                        st2 = -1;
                }
            }
            if (st == 2) {
                temp = corX - x - 1;
                numberOfMoves[0] += temp;
            }
            if (st == 2 && change == 1) {
                while ((corX >= x) && (corY >= y)) {
                    corX--;
                    corY--;
                    if ((corX >= x) && (corY >= y)) {
                        str = gameCells[corX][corY].getCorX();
                        ix = gameCells[corX][corY].getCorY();
                        gameCells[corX][corY].setPosition(str, char1, ix);
                    }
                }
                status = 0;
            }
            st = 0;
            st2 = 0;
        }
        if ((x + 1 < rows) && (y - 1 >= 0) && (gameCells[x + 1][y - 1].getCh() == char2)) {
            corY = y;
            corX = x;
            while ((corX < rows) && (corY >= 0) && (st2 != -1) && (st != 2)) {
                corX++;
                corY--;
                if ((corX < rows) && (corY >= 0)) {
                    if (gameCells[corX][corY].getCh() == char2)
                        st = 1;
                    else if (gameCells[corX][corY].getCh() == char1)
                        st = 2;
                    else
                        st2 = -1;
                }
            }
            if (st == 2) {
                temp = corX - x - 1;
                numberOfMoves[0] += temp;
            }
            if (st == 2 && change == 1) {
                while ((corX >= x) && (corY <= y)) {
                    corX--;
                    corY++;
                    if ((corX >= x) && (corY <= y)) {
                        str = gameCells[corX][corY].getCorX();
                        ix = gameCells[corX][corY].getCorY();
                        gameCells[corX][corY].setPosition(str, char1, ix);
                    }
                }
                status = 0;
            }
            st = 0;
            st2 = 0;
        }
        if (status == 0)
            return 0;
        else
            return -1;
    }
}
