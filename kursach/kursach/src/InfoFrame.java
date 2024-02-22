import javax.swing.*;

public class InfoFrame extends JFrame {
    private JLabel label;

    public InfoFrame() {
        super("Информация о разработчиках");
        setBounds(275, 210, 450, 250);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);
        label = new JLabel("<html>Самарский университет" +
                "<br>Кафедра программных систем" +
                "<br><br>Курсовой проект по дисциплине" +
                "<br>«Объектная распределенная обработка»" +
                "<br><br>Тема проекта" +
                "<br>«Распределённое клиент-серверное приложение «Игра «Реверси»" +
                "<br><br>Разработчик группы 6413-020302D" +
                "<br>Гижевская Валерия" +
                "</html>");
        label.setBounds(10, 0, 400, 220);
        add(label);
        setVisible(true);
    }
}
