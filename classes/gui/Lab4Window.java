package classes.gui;

import classes.lab4.AverageCalculator;
import classes.lab4.StringTransformer;
import classes.lab4.UniqueSquaresCalculator;
import classes.lab4.CollectionHelper;
import classes.lab4.EvenSumCalculator;
import classes.lab4.StringToMapConverter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;
import java.util.List;

public class Lab4Window extends JFrame {
    private JTextArea outputArea;
    private JTextField inputField1;
    private JTextField inputField2;
    private JComboBox<String> methodComboBox;
    private JPanel controlPanel;
    private JLabel inputLabel2;
    
    public Lab4Window() {
        initializeUI();
    }
    
    private void initializeUI() {
        setTitle("Stream API");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(1000, 1000);
        setLocationRelativeTo(null);
        setResizable(true);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                returnToMainMenu();
            }
        });
        createLabInterface();
    }
    
    private void createLabInterface() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 245, 245));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Stream API", JLabel.CENTER);
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 28));
        titleLabel.setForeground(new Color(60, 63, 65));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        
        JPanel contentPanel = new JPanel(new BorderLayout(15, 15));
        contentPanel.setBackground(new Color(245, 245, 245));
        
        JPanel controlPanel = createControlPanel();
        contentPanel.add(controlPanel, BorderLayout.NORTH);
        
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        outputArea.setFont(new Font("Times New Roman", Font.BOLD, 14));
        outputArea.setBackground(new Color(253, 253, 253));
        outputArea.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        
        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)), 
                "Результаты выполнения"
            ),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        scrollPane.setPreferredSize(new Dimension(960, 500));
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        JPanel buttonPanel = createBottomButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);
        appendOutput("Добро пожаловать в лабораторную работу по Stream API!\n");
        appendOutput("Выберите метод и введите входные данные.\n");
        appendOutput("Формат ввода:\n");
        appendOutput("- Числа: 1,2,3,4,5\n");
        appendOutput("- Строки: apple,banana,cherry\n");
        appendOutput("- Массив: 1 2 3 4 5");
    }
    
    private JPanel createControlPanel() {
        controlPanel = new JPanel(new GridBagLayout());
        controlPanel.setBackground(Color.WHITE);
        controlPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)), 
                "Управление операциями Stream API"
            ),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        JLabel methodLabel = new JLabel("Выберите метод:");
        methodLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
        controlPanel.add(methodLabel, gbc);
    
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        String[] methods = {
            "Среднее значение списка чисел",
            "Трансформация строк (+префикс, верхний регистр)",
            "Квадраты уникальных элементов",
            "Последний элемент коллекции",
            "Сумма четных чисел массива",
            "Преобразование строк в Map"
        };
        methodComboBox = new JComboBox<>(methods);
        methodComboBox.setFont(new Font("Times New Roman", Font.BOLD, 14));
        methodComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateInputFields();
            }
        });
        controlPanel.add(methodComboBox, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        JLabel inputLabel1 = new JLabel("Входные данные:");
        inputLabel1.setFont(new Font("Times New Roman", Font.BOLD, 14));
        controlPanel.add(inputLabel1, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        inputField1 = new JTextField();
        inputField1.setFont(new Font("Times New Roman", Font.BOLD, 14));
        inputField1.setToolTipText("Введите данные через запятую или пробел");
        controlPanel.add(inputField1, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        inputLabel2 = new JLabel("Доп. параметры:");
        inputLabel2.setFont(new Font("Times New Roman", Font.BOLD, 14));
        inputLabel2.setVisible(false);
        controlPanel.add(inputLabel2, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        inputField2 = new JTextField();
        inputField2.setFont(new Font("Times New Roman", Font.BOLD, 14));
        inputField2.setVisible(false);
        controlPanel.add(inputField2, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.weightx = 0.5;
        JButton executeButton = createExecuteButton();
        executeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                executeSelectedMethod();
            }
        });
        controlPanel.add(executeButton, gbc);
        
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.weightx = 0.5;
        JButton clearInputButton = createClearInputButton();
        clearInputButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inputField1.setText("");
                inputField2.setText("");
            }
        });
        controlPanel.add(clearInputButton, gbc);
        return controlPanel;
    }
    
    private void updateInputFields() {
        int selectedIndex = methodComboBox.getSelectedIndex();
        inputField2.setVisible(false);
        inputLabel2.setVisible(false);
        switch (selectedIndex) {
            case 0:
                inputField1.setToolTipText("Введите числа через запятую: 1,2,3,4,5");
                break;
            case 1:
                inputField1.setToolTipText("Введите строки через запятую: apple,banana,cherry");
                break;
            case 2:
                inputField1.setToolTipText("Введите числа через запятую: 1,2,2,3,4,4,5");
                break;
            case 3:
                inputField1.setToolTipText("Введите элементы через запятую: first,second,last");
                break;
            case 4:
                inputField1.setToolTipText("Введите числа через пробел: 1 2 3 4 5 6");
                break;
            case 5:
                inputField1.setToolTipText("Введите строки через запятую: apple,banana,avocado");
                break;
        }
    }
    
    private JButton createExecuteButton() {
        JButton button = new JButton("Выполнить метод");
        button.setFont(new Font("Times New Roman", Font.BOLD, 14));
        button.setBackground(new Color(20, 20, 80));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(50, 100, 150), 2),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(65, 105, 225));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(20, 20, 80));
            }
        });
        
        return button;
    }
    
    private JButton createClearInputButton() {
        JButton button = new JButton("Очистить ввод");
        button.setFont(new Font("Times New Roman", Font.BOLD, 14));
        button.setBackground(new Color(20, 20, 80));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(50, 50, 50), 2),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(65, 105, 225));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(20, 20, 80));
            }
        });
        
        return button;
    }
    
    private JPanel createBottomButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setBackground(new Color(245, 245, 245));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        
        JButton clearButton = createClearButton();
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearOutput();
            }
        });
        
        JButton backButton = createBackButton();
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                returnToMainMenu();
            }
        });
        buttonPanel.add(clearButton);
        buttonPanel.add(backButton);
        return buttonPanel;
    }
    
    private JButton createClearButton() {
        JButton button = new JButton("Очистка лога");
        button.setFont(new Font("Times New Roman", Font.BOLD, 16));
        button.setBackground(new Color(255, 150, 0));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 100, 0), 1),
            BorderFactory.createEmptyBorder(12, 25, 12, 25)
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(255, 100, 0));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(255, 150, 0));
            }
        });
        return button;
    }
    
    private JButton createBackButton() {
        JButton button = new JButton("Назад в главное меню");
        button.setFont(new Font("Times New Roman", Font.BOLD, 16));
        button.setBackground(new Color(30, 30, 30));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(50, 50, 50), 1),
            BorderFactory.createEmptyBorder(12, 25, 12, 25)
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(50, 50, 50));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(30, 30, 30));
            }
        });
        return button;
    }
    
    private void executeSelectedMethod() {
        String input = inputField1.getText().trim();
        if (input.isEmpty()) {
            showWarning("Пустой ввод", "Введите данные для обработки");
            return;
        }
        int selectedIndex = methodComboBox.getSelectedIndex();
        appendOutput("\nМЕТОД: " + methodComboBox.getSelectedItem());
        appendOutput("Входные данные: " + input);
        appendOutput("Результат:");
        try {
            switch (selectedIndex) {
                case 0:
                    executeAverageCalculation(input);
                    break;
                case 1:
                    executeStringTransformation(input);
                    break;
                case 2:
                    executeUniqueSquares(input);
                    break;
                case 3:
                    executeLastElement(input);
                    break;
                case 4:
                    executeEvenSum(input);
                    break;
                case 5:
                    executeStringToMap(input);
                    break;
            }
        } catch (Exception e) {
            appendOutput("ОШИБКА: " + e.getMessage());
        }
    }
    
    private void executeAverageCalculation(String input) {
        List<Integer> numbers = parseIntegerList(input);
        double result = AverageCalculator.calculateAverage(numbers);
        appendOutput("Среднее значение: " + result);
        appendOutput("Обработанные числа: " + numbers);
    }
    
    private void executeStringTransformation(String input) {
        List<String> strings = parseStringList(input);
        List<String> result = StringTransformer.transformStrings(strings);
        appendOutput("Исходные строки: " + strings);
        appendOutput("Преобразованные строки: " + result);
    }
    
    private void executeUniqueSquares(String input) {
        List<Integer> numbers = parseIntegerList(input);
        List<Integer> result = UniqueSquaresCalculator.getUniqueSquares(numbers);
        appendOutput("Исходный список: " + numbers);
        appendOutput("Квадраты уникальных элементов: " + result);
    }
    
    private void executeLastElement(String input) {
        List<String> collection = parseStringList(input);
        String result = CollectionHelper.getLastElement(collection);
        appendOutput("Коллекция: " + collection);
        appendOutput("Последний элемент: " + result);
    }
    
    private void executeEvenSum(String input) {
        int[] numbers = parseIntArray(input);
        int result = EvenSumCalculator.calculateEvenSum(numbers);
        appendOutput("Массив: " + Arrays.toString(numbers));
        appendOutput("Сумма четных чисел: " + result);
    }
    
    private void executeStringToMap(String input) {
        List<String> strings = parseStringList(input);
        Map<Character, String> result = StringToMapConverter.convertToMap(strings);
        appendOutput("Исходные строки: " + strings);
        appendOutput("Результирующая Map: " + result);
    }
    
    private List<Integer> parseIntegerList(String input) {
        String[] parts = input.split("[,\\s]+");
        List<Integer> numbers = new ArrayList<>();
        for (String part : parts) {
            try {
                numbers.add(Integer.parseInt(part.trim()));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Неверный формат числа: " + part);
            }
        }
        return numbers;
    }
    
    private List<String> parseStringList(String input) {
        String[] parts = input.split(",");
        List<String> strings = new ArrayList<>();
        for (String part : parts) {
            strings.add(part.trim());
        }
        return strings;
    }
    
    private int[] parseIntArray(String input) {
        String[] parts = input.split("\\s+");
        int[] numbers = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
            try {
                numbers[i] = Integer.parseInt(parts[i].trim());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Неверный формат числа: " + parts[i]);
            }
        }
        return numbers;
    }
    
    private void appendOutput(String text) {
        outputArea.append(text + "\n");
        outputArea.setCaretPosition(outputArea.getDocument().getLength());
    }
    
    private void clearOutput() {
        outputArea.setText("");
        appendOutput("Лог очищен.\n");
        appendOutput("Выберите метод и введите входные данные.\n");
        appendOutput("Формат ввода:\n");
        appendOutput("- Числа: 1,2,3,4,5\n");
        appendOutput("- Строки: apple,banana,cherry\n");
        appendOutput("- Массив: 1 2 3 4 5");
    }
    
    private void showWarning(String title, String message) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.WARNING_MESSAGE);
    }
    
    private void returnToMainMenu() {
        this.dispose();
        MainMenu mainMenu = new MainMenu();
        mainMenu.setVisible(true);
    }
}