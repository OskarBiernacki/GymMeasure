import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GuiWindow extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private TrainingHistory trainingHistory;
    private TableModelListener tableModelListener;
    private JComboBox<String> comboBox;
    private JLabel weekText;

    public GuiWindow(ExerciseCategory[] exerciseCategories, TrainingHistory trainingHistory) {
        this.trainingHistory = trainingHistory;

        setTitle("GymMeasure");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Ciemny motyw
        //setDarkTheme();

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

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

        comboBox.addActionListener(e -> {
            onCategoryChange(exerciseCategories, columnNames);
        });

        topPanel.add(comboBox, BorderLayout.WEST);

        JButton leftArrowButton = new JButton("←");
        JButton rightArrowButton = new JButton("→");
        weekText = new JLabel("Active Week: 0");
        
        leftArrowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                trainingHistory.previousTraining();
                onCategoryChange(exerciseCategories, columnNames);
                weekText.setText("Active Week: "+trainingHistory.getHistoryIndex());
                trainingHistory.exportToJson();
            }
        });

        rightArrowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                trainingHistory.nextTraining();
                onCategoryChange(exerciseCategories, columnNames);
                weekText.setText("Active Week: "+trainingHistory.getHistoryIndex());
            }
            
        });

        buttonPanel.add(leftArrowButton);
        buttonPanel.add(rightArrowButton);
        buttonPanel.add(weekText);

        String[] items = {"Adrian", "Oskar", "Michał", "Obama"};
        JList<String> list = new JList<>(items);
        JScrollPane listScrollPane = new JScrollPane(list);

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

        table.getTableHeader().setReorderingAllowed(false);

        TableColumn exerciseColumn = table.getColumnModel().getColumn(0);
        exerciseColumn.setPreferredWidth(200);

        for (int i = 1; i < table.getColumnCount(); i++) {
            TableColumn otherColumn = table.getColumnModel().getColumn(i);
            otherColumn.setPreferredWidth(50);
        }

        JScrollPane tableScrollPane = new JScrollPane(table);

        topPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(listScrollPane, BorderLayout.WEST);
        mainPanel.add(tableScrollPane, BorderLayout.CENTER);

        onCategoryChange(exerciseCategories, columnNames);
        add(mainPanel);
        this.setBackground(Color.BLACK);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void showWindow() {
        setVisible(true);
    }

    private void onCategoryChange(ExerciseCategory[] exerciseCategories, String[] columnNames) {
        String selectedExercise = (String) comboBox.getSelectedItem();
        for (ExerciseCategory category : exerciseCategories) {
            if (category.exerciseCategoryName.compareTo(selectedExercise) == 0) {
                Object[][] newData = new Object[category.exercisesNames.length][9];
                for (int i = 0; i < category.exercisesNames.length; i++) {
                    newData[i][0] = category.exercisesNames[i];

                    Object[] seriesData = trainingHistory.getCurrentTraining().getSeriesOf(category.exercisesNames[i], category.exerciseCategoryName);
                    for (int x = 1; x < 9; x++) newData[i][x] = seriesData[x - 1];
                }

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