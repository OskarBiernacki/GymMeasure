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

    // Konstruktor klasy MyWindow
    public GuiWindow(ExerciseCategpry[] exerciseCategpries, TrainingHistory trainingHistory) {
        this.trainingHistory = trainingHistory;

        setTitle("GymMeasure");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Tworzenie głównego panelu
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Tworzenie panelu na przyciski
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());

        String[] columnNames = {"Ćwiczenie", "Waga [kg]", "Powtórzenia", "Waga [kg]", "Powtórzenia", "Waga [kg]", "Powtórzenia", "Waga [kg]", "Powtórzenia"};

        String[] exercises = new String[exerciseCategpries.length];
        for (int i = 0; i < exerciseCategpries.length; i++) {
            exercises[i] = exerciseCategpries[i].exerciseCategoryName;
        }
        JComboBox<String> comboBox = new JComboBox<>(exercises);
        Object[][] data = new Object[exerciseCategpries[0].exercisesNames.length][9];
        for (int i = 0; i < exerciseCategpries[0].exercisesNames.length; i++) {
            data[i][0] = exerciseCategpries[0].exercisesNames[i];
        }

        comboBox.addActionListener(e -> {
            String selectedExercise = (String) comboBox.getSelectedItem();
            System.out.println("Wybrane ćwiczenie: " + selectedExercise);

            for (ExerciseCategpry category : exerciseCategpries) {
                if (category.exerciseCategoryName.compareTo(selectedExercise) == 0) {
                    // Aktualizacja danych tabeli
                    Object[][] newData = new Object[category.exercisesNames.length][9];
                    for (int i = 0; i < category.exercisesNames.length; i++) {
                        newData[i][0] = category.exercisesNames[i];

                        Object[] seriesData = trainingHistory.getCurrentTraining().getSeriesOf(category.exercisesNames[i], category.exerciseCategoryName);
                        for(int x=1;x<9;x++)newData[i][x]=seriesData[x-1];
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
        table = new JTable(tableModel);

        tableModelListener=new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                try{
                    int row = e.getFirstRow();  // Uzyskanie wiersza, w którym zaszła zmiana
                    int column = e.getColumn();  // Uzyskanie kolumny, w której zaszła zmiana
                    if (e.getType() == TableModelEvent.UPDATE) {
                        Object changedValue = tableModel.getValueAt(row, column);  // Nowa wartość w komórce
                        Object firstCellValue = tableModel.getValueAt(row, 0);  // Wartość z pierwszej kolumny tego wiersza

                        // Wypisywanie informacji o zmianie
                        System.out.println("Zmieniono wartość w wierszu: " + row + ", kolumna: " + column);
                        System.out.println("Nowa wartość: " + changedValue);
                        System.out.println("Wartość pierwszej komórki w tym wierszu: " + firstCellValue);
                    }
                }catch(Exception ex){}
            }
        };

        tableModel.addTableModelListener(tableModelListener);

        // Wyśrodkowanie tekstu w tabeli
        // DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        // centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);  // Ustawienie wyśrodkowania

        // for (int i = 0; i < table.getColumnCount(); i++) {
        //     table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        // }

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

        // Dodawanie głównego panelu do okna
        add(mainPanel);
    }

    // Funkcja show(), aby wyświetlić okno
    public void showWindow() {
        setVisible(true);
    }
}