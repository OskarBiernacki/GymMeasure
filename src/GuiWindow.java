import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;

public class GuiWindow extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private TrainingHistory trainingHistory;
    private TableModelListener tableModelListener;
    private JComboBox<String> comboBox;

    // Konstruktor klasy GuiWindow
    public GuiWindow(ExerciseCategory[] exerciseCategories, TrainingHistory trainingHistory) {
        this.trainingHistory = trainingHistory;

        setTitle("GymMeasure");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Ciemny motyw
        //setDarkTheme();

        // Tworzenie głównego panelu
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Tworzenie panelu na przyciski
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());

        String[] columnNames = {"Ćwiczenie", "Waga [kg]", "Powtórzenia", "Waga [kg]", "Powtórzenia", "Waga [kg]", "Powtórzenia", "Waga [kg]", "Powtórzenia"};

        String[] exercises = new String[exerciseCategories.length];
        for (int i = 0; i < exerciseCategories.length; i++) {
            exercises[i] = exerciseCategories[i].exerciseCategoryName;
        }
        comboBox = new JComboBox<>(exercises);
        Object[][] data = new Object[exerciseCategories[0].exercisesNames.length][9];
        for (int i = 0; i < exerciseCategories[0].exercisesNames.length; i++) {
            data[i][0] = exerciseCategories[0].exercisesNames[i];
        }

        // Zmiana kategorii
        comboBox.addActionListener(e -> {
            onCategoryChange(exerciseCategories, columnNames);
        });

        topPanel.add(comboBox, BorderLayout.WEST);

        // Tworzenie przycisków strzałek
        JButton leftArrowButton = new JButton("←");
        JButton rightArrowButton = new JButton("→");

        // Dodawanie przycisków do panelu
        buttonPanel.add(leftArrowButton);
        buttonPanel.add(rightArrowButton);

        // Tworzenie listy po lewej stronie
        String[] items = {"Adrian", "Oskar", "Michał", "Obama"};
        JList<String> list = new JList<>(items);
        JScrollPane listScrollPane = new JScrollPane(list);

        // Tworzenie tabelki na środku z modelem tabeli
        tableModel = new DefaultTableModel(data, columnNames);

        tableModelListener = new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                try {
                    if (e.getLastRow() == -1) return;
                    int row = e.getFirstRow();
                    if (e.getType() == TableModelEvent.UPDATE) {
                        Object firstCellValue = tableModel.getValueAt(row, 0);
                        Object[] newValues = new Object[8];
                        for (int i = 0; i < 8; i++) {
                            if (tableModel.getValueAt(row, i + 1) != null)
                                newValues[i] = tableModel.getValueAt(row, i + 1).toString();
                            else
                                newValues[i] = null;
                        }
                        trainingHistory.getCurrentTraining().setSeriesOf(firstCellValue.toString(), newValues);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };

        tableModel.addTableModelListener(tableModelListener);

        table = new JTable(tableModel);

        // Zablokowanie przesuwania kolumn
        table.getTableHeader().setReorderingAllowed(false);

        // Ustawianie szerokości kolumn
        TableColumn exerciseColumn = table.getColumnModel().getColumn(0); // Kolumna z ćwiczeniami
        exerciseColumn.setPreferredWidth(200);  // Szersza kolumna

        for (int i = 1; i < table.getColumnCount(); i++) {
            TableColumn otherColumn = table.getColumnModel().getColumn(i);
            otherColumn.setPreferredWidth(50);  // Węższe kolumny
        }

        JScrollPane tableScrollPane = new JScrollPane(table);

        // Dodawanie elementów do głównego panelu
        topPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(topPanel, BorderLayout.NORTH); // Panel z przyciskami na górze
        mainPanel.add(listScrollPane, BorderLayout.WEST); // Lista po lewej stronie
        mainPanel.add(tableScrollPane, BorderLayout.CENTER); // Tabela na środku

        // Aby uniknąć martwego stanu
        onCategoryChange(exerciseCategories, columnNames);
        // Dodawanie głównego panelu do okna
        add(mainPanel);
        this.setBackground(Color.BLACK);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    // Funkcja show(), aby wyświetlić okno
    public void showWindow() {
        setVisible(true);
    }

    private void onCategoryChange(ExerciseCategory[] exerciseCategories, String[] columnNames) {
        String selectedExercise = (String) comboBox.getSelectedItem();
        for (ExerciseCategory category : exerciseCategories) {
            if (category.exerciseCategoryName.compareTo(selectedExercise) == 0) {
                // Aktualizacja danych tabeli
                Object[][] newData = new Object[category.exercisesNames.length][9];
                for (int i = 0; i < category.exercisesNames.length; i++) {
                    newData[i][0] = category.exercisesNames[i];

                    Object[] seriesData = trainingHistory.getCurrentTraining().getSeriesOf(category.exercisesNames[i], category.exerciseCategoryName);
                    for (int x = 1; x < 9; x++) newData[i][x] = seriesData[x - 1];
                }

                // Zastąpienie starych danych nowymi
                tableModel.setDataVector(newData, columnNames);
                TableColumn exerciseColumn = table.getColumnModel().getColumn(0);
                exerciseColumn.setPreferredWidth(200);

                for (int i = 1; i < table.getColumnCount(); i++) {
                    TableColumn otherColumn = table.getColumnModel().getColumn(i);
                    otherColumn.setPreferredWidth(50);
                }
            }
        }
    }

    // Funkcja ustawiająca ciemny motyw
    @SuppressWarnings("unused")
    private void setDarkTheme() {
        // Kolory tła i tekstu
        UIManager.put("Panel.background", Color.DARK_GRAY);
        UIManager.put("Table.background", Color.BLACK);
        UIManager.put("Table.foreground", Color.WHITE);
        UIManager.put("Table.gridColor", Color.GRAY);
        UIManager.put("TableHeader.background", Color.BLACK);
        UIManager.put("TableHeader.foreground", Color.WHITE);
        UIManager.put("Button.background", Color.DARK_GRAY);
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("ComboBox.background", Color.DARK_GRAY);
        UIManager.put("ComboBox.foreground", Color.WHITE);
        UIManager.put("List.background", Color.BLACK);
        UIManager.put("List.foreground", Color.WHITE);
        UIManager.put("ScrollPane.background", Color.DARK_GRAY);
    }
}
