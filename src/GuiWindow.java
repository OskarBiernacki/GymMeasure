import javax.swing.*;
import java.awt.*;

public class GuiWindow extends JFrame {

    // Konstruktor klasy MyWindow
    public GuiWindow() {
        setTitle("GymMeasure");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Tworzenie głównego panelu
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Tworzenie panelu na przyciski
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        // Tworzenie przycisków strzałek
        JButton leftArrowButton = new JButton("←");
        JButton rightArrowButton = new JButton("→");

        // Dodawanie przycisków do panelu
        buttonPanel.add(leftArrowButton);
        buttonPanel.add(rightArrowButton);

        // Tworzenie listy po lewej stronie
        String[] items = {"Element 1", "Element 2", "Element 3", "Element 4"};
        JList<String> list = new JList<>(items);
        JScrollPane listScrollPane = new JScrollPane(list);

        // Tworzenie tabelki na środku
        String[] columnNames = {"ćwiczenie", "waga", "powtórzenia"};
        Object[][] data = {
                {"Dane 1", "Dane 2", "Dane 3"},
                {"Dane 4", "Dane 5", "Dane 6"},
                {"Dane 7", "Dane 8", "Dane 9"}
        };
        JTable table = new JTable(data, columnNames);
        JScrollPane tableScrollPane = new JScrollPane(table);

        // Dodawanie elementów do głównego panelu
        mainPanel.add(buttonPanel, BorderLayout.NORTH); // Panel z przyciskami na górze
        mainPanel.add(listScrollPane, BorderLayout.WEST); // Lista po lewej stronie
        mainPanel.add(tableScrollPane, BorderLayout.CENTER); // Tabela na środku

        // Dodawanie głównego panelu do okna
        add(mainPanel);
    }

    // Funkcja show(), aby wyświetlić okno
    public void showWindow() {
        setVisible(true);
    }
}
