package Game;

import java.awt.*;
import java.applet.*;

// Расширяем класс апплета
public class ChessBoard extends Applet {
    static int N = 8;
    // Используем метод paint ()
    public void paint(Graphics g) {
        int x, y;
        int size = 80;
        for (int row = 0; row < N; row++) {
            for (int col = 0; col < N; col++) {
                // Устанавливаем координаты х прямоугольника
                // в 20 раз
                x = row * size;
                // Устанавливаем координаты у прямоугольника
                // в 20 раз
                y = col * size;
                // Проверяем, есть ли строка и столбец
                // в четном положении
                // Если это правда, установить черный цвет
                if ((row % 2 == 0) == (col % 2 == 0))
                    g.setColor(Color.GRAY);
                else
                    g.setColor(Color.LIGHT_GRAY);
                // Создать прямоугольник с
                // длина и ширина 20
                g.fillRect(x, y, size, size);
            }
        }
    }
}