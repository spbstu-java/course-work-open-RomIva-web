package classes.gui;

import classes.lab3.Model.Translator;
import classes.lab3.Exceptions.FileReadException;
import classes.lab3.Exceptions.InvalidFileFormatException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Lab3Window extends JFrame {
    private Translator translator;
    private JTextArea outputArea;
    private JTextArea inputArea;
    private JLabel statusLabel;
    private JButton translateButton;
    private JButton loadDictionaryButton;
    private JButton loadTextFileButton;
    
    public Lab3Window() {
        this.translator = new Translator();
        initializeUI();
    }
    
    private void initializeUI() {
        setTitle("Переводчик текста");
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
        
        JLabel titleLabel = new JLabel("Переводчик текста", JLabel.CENTER);
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 28));
        titleLabel.setForeground(new Color(60, 63, 65));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        
        JPanel contentPanel = new JPanel(new BorderLayout(15, 15));
        contentPanel.setBackground(new Color(245, 245, 245));
        
        JPanel controlPanel = createControlPanel();
        contentPanel.add(controlPanel, BorderLayout.NORTH);
        
        JPanel ioPanel = createIOPanel();
        contentPanel.add(ioPanel, BorderLayout.CENTER);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        
        JPanel buttonPanel = createBottomButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);
        appendOutput("Добро пожаловать в Переводчик текста!\n");
        appendOutput("Для начала работы загрузите файл словаря.\n");
        updateStatus("Словарь не загружен");
    }
    
    private JPanel createControlPanel() {
        JPanel controlPanel = new JPanel(new GridLayout(2, 2, 15, 15));
        controlPanel.setBackground(Color.WHITE);
        controlPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)), 
                "Управление"
            ),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        loadDictionaryButton = createActionButton("Загрузить файл словаря", new Color(20, 20, 80));
        loadDictionaryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadDictionaryFile();
            }
        });
        
        loadTextFileButton = createActionButton("Загрузить файл для перевода", new Color(20, 20, 80));
        loadTextFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadTextFile();
            }
        });
        loadTextFileButton.setEnabled(false);
        
        translateButton = createActionButton("Выполнить перевод", new Color(20, 20, 80));
        translateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performTranslation();
            }
        });
        translateButton.setEnabled(false);
        
        JButton clearInputButton = createActionButton("Очистить поле ввода", new Color(20, 20, 80));
        clearInputButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inputArea.setText("");
            }
        });
        controlPanel.add(loadDictionaryButton);
        controlPanel.add(loadTextFileButton);
        controlPanel.add(translateButton);
        controlPanel.add(clearInputButton);
        return controlPanel;
    }
    
    private JPanel createIOPanel() {
        JPanel ioPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        ioPanel.setBackground(new Color(245, 245, 245));
        
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setBackground(Color.WHITE);
        inputPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)), 
                "Ввод текста для перевода (можно вводить вручную или загрузить из файла)"
            ),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        inputArea = new JTextArea();
        inputArea.setLineWrap(true);
        inputArea.setWrapStyleWord(true);
        inputArea.setFont(new Font("Times New Roman", Font.BOLD, 14));
        inputArea.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        
        JScrollPane inputScroll = new JScrollPane(inputArea);
        inputScroll.setPreferredSize(new Dimension(960, 200));
        inputPanel.add(inputScroll, BorderLayout.CENTER);
        
        JPanel outputPanel = new JPanel(new BorderLayout());
        outputPanel.setBackground(Color.WHITE);
        outputPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)), 
                "Результат перевода"
            ),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        outputArea.setFont(new Font("Times New Roman", Font.BOLD, 14));
        outputArea.setBackground(new Color(253, 253, 253));
        outputArea.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        
        JScrollPane outputScroll = new JScrollPane(outputArea);
        outputScroll.setPreferredSize(new Dimension(960, 200));
        outputPanel.add(outputScroll, BorderLayout.CENTER);
        
        statusLabel = new JLabel("Готов к работе");
        statusLabel.setFont(new Font("Times New Roman", Font.BOLD, 12));
        statusLabel.setForeground(new Color(50, 50, 50));
        statusLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        outputPanel.add(statusLabel, BorderLayout.SOUTH);
        ioPanel.add(inputPanel);
        ioPanel.add(outputPanel);
        return ioPanel;
    }
    
    private JButton createActionButton(String text, Color color) {
        JButton button = new JButton("<html><center>" + text + "</center></html>");
        button.setFont(new Font("Times New Roman", Font.BOLD, 14));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color.darker(), 2),
            BorderFactory.createEmptyBorder(12, 8, 12, 8)
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(color.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(color);
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
    
    private void loadDictionaryFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Выберите файл словаря");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                translator.loadDictionary(selectedFile.getAbsolutePath());
                updateStatus("Словарь загружен успешно. Записей: " + translator.getDictionary().size());
                translateButton.setEnabled(true);
                loadTextFileButton.setEnabled(true);
                appendOutput("Словарь загружен из файла: " + selectedFile.getName() + "\n");
                appendOutput("Количество записей в словаре: " + translator.getDictionary().size() + "\n");
            } catch (FileReadException e) {
                showError("Ошибка чтения файла", e.getMessage());
                updateStatus("Ошибка загрузки словаря");
            } catch (InvalidFileFormatException e) {
                showError("Ошибка формата файла", e.getMessage());
                updateStatus("Ошибка формата словаря");
            }
        }
    }
    
    private void loadTextFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Выберите файл с текстом для перевода");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                String fileContent = readTextFile(selectedFile);
                inputArea.setText(fileContent);
                appendOutput("Текст загружен из файла: " + selectedFile.getName() + "\n");
                appendOutput("Размер текста: " + fileContent.length() + " символов\n");
                updateStatus("Текст загружен из файла");
            } catch (IOException e) {
                showError("Ошибка чтения файла", e.getMessage());
                updateStatus("Ошибка загрузки текста");
            }
        }
    }
    
    private String readTextFile(File file) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString().trim();
    }
    
    private void performTranslation() {
        String inputText = inputArea.getText().trim();
        if (inputText.isEmpty()) {
            showWarning("Пустой ввод", "Введите текст для перевода");
            return;
        }
        try {
            String translation = translator.translate(inputText);
            outputArea.setText("");
            appendOutput("Перевод выполнен:\n");
            appendOutput("Исходный текст: " + inputText + "\n");
            appendOutput("Результат перевода: " + translation + "\n");
            appendOutput("");
        } catch (Exception e) {
            showError("Ошибка перевода", e.getMessage());
        }
    }
    
    private void updateStatus(String message) {
        statusLabel.setText("Статус: " + message);
    }
    
    private void appendOutput(String text) {
        outputArea.append(text + "\n");
    }
    
    private void clearOutput() {
        outputArea.setText("");
        appendOutput("Лог очищен.\n");
        appendOutput("Добро пожаловать в Переводчик текста!\n");
        appendOutput("Для начала работы загрузите файл словаря.\n");
        updateStatus("Готов к работе");
    }
    
    private void showError(String title, String message) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
        appendOutput("ОШИБКА: " + title + " - " + message + "\n");
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