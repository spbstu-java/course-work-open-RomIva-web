package classes.gui;

import classes.lab2.Invoker;
import classes.lab2.TestClass;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class Lab2Window extends JFrame {
    private TestClass testObject;
    private JTextArea outputArea;
    
    public Lab2Window() {
        this.testObject = new TestClass();
        initializeUI();
    }
    
    private void initializeUI() {
        setTitle("Аннотации и рефлексия");
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
        
        JLabel titleLabel = new JLabel("Аннотации и рефлексия", JLabel.CENTER);
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
        scrollPane.setPreferredSize(new Dimension(960, 450));
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        JPanel buttonPanel = createBottomButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);
        appendOutput("Добро пожаловать в лабораторную работу по аннотациям и рефлексии!\n");
        appendOutput("Используйте кнопки для анализа класса и вызова методов.\n");
    }
    
    private JPanel createControlPanel() {
        JPanel controlPanel = new JPanel(new GridLayout(2, 3, 15, 15));
        controlPanel.setBackground(Color.WHITE);
        controlPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)), 
                "Управление"
            ),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        JButton infoButton = createActionButton("Информация о классе", new Color(20, 20, 80));
        infoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showClassInfo();
            }
        });
        
        JButton publicButton = createActionButton("Вызвать публичные методы", new Color(20, 20, 80));
        publicButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                callPublicMethods();
            }
        });
        
        JButton annotatedButton = createActionButton("Вызвать аннотированные методы", new Color(20, 20, 80));
        annotatedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                callAnnotatedMethods();
            }
        });
        
        JButton privateButton = createActionButton("Вызвать приватные методы", new Color(20, 20, 80));
        privateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                callPrivateMethods();
            }
        });
        
        JButton protectedButton = createActionButton("Вызвать защищённые методы", new Color(20, 20, 80));
        protectedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                callProtectedMethods();
            }
        });
        
        JButton allButton = createActionButton("Вызвать все методы", new Color(20, 20, 80));
        allButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                callAllMethods();
            }
        });
        controlPanel.add(infoButton);
        controlPanel.add(publicButton);
        controlPanel.add(annotatedButton);
        controlPanel.add(privateButton);
        controlPanel.add(protectedButton);
        controlPanel.add(allButton);
        return controlPanel;
    }
    
    private JButton createActionButton(String text, Color color) {
        JButton button = new JButton("<html><center>" + text + "</center></html>");
        button.setFont(new Font("Times New Roman", Font.BOLD, 14));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color.darker(), 2),
            BorderFactory.createEmptyBorder(15, 5, 15, 5)
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
    
    private void showClassInfo() {
        List<String> classInfo = Invoker.getClassInfo(testObject);
        for (String line : classInfo) {
            appendOutput(line);
        }
    }
    
    private void callPublicMethods() {
        appendOutput("=== ПРЯМОЙ ВЫЗОВ ПУБЛИЧНЫХ МЕТОДОВ ===\n");
        appendOutput("publicMethod1(\"тест\"): ");
        testObject.publicMethod1("тест");
        appendOutput("publicMethod2(5, 3): ");
        int result = testObject.publicMethod2(5, 3);
        appendOutput("Результат: " + result);
    }
    
    private void callAnnotatedMethods() {
        List<String> results = Invoker.invokeProtectedAndPrivateMethods(testObject);
        for (String line : results) {
            appendOutput(line);
        }
    }
    
    private void callPrivateMethods() {
        List<String> results = Invoker.invokePrivateMethods(testObject);
        for (String line : results) {
            appendOutput(line);
        }
    }
    
    private void callProtectedMethods() {
        List<String> results = Invoker.invokeProtectedMethods(testObject);
        for (String line : results) {
            appendOutput(line);
        }
    }
    
    private void callAllMethods() {
        appendOutput("ПОЛНЫЙ ВЫЗОВ ВСЕХ МЕТОДОВ:\n");
        callPublicMethods();
        List<String> privateResults = Invoker.invokePrivateMethods(testObject);
        for (String line : privateResults) {
            if (!line.equals("=== ВЫЗОВ ПРИВАТНЫХ МЕТОДОВ ===")) {
                appendOutput(line);
            }
        }
        List<String> protectedResults = Invoker.invokeProtectedMethods(testObject);
        for (String line : protectedResults) {
            if (!line.equals("=== ВЫЗОВ ЗАЩИЩЕННЫХ МЕТОДОВ ===")) {
                appendOutput(line);
            }
        }
    }
    
    private void appendOutput(String text) {
        outputArea.append(text + "\n");
        outputArea.setCaretPosition(outputArea.getDocument().getLength());
    }
    
    private void clearOutput() {
        outputArea.setText("");
        appendOutput("Лог очищен.\n");
        appendOutput("Добро пожаловать в лабораторную работу по аннотациям и рефлексии!\n");
        appendOutput("Используйте кнопки для анализа класса и вызова методов.\n");
    }
    
    private void returnToMainMenu() {
        this.dispose();
        MainMenu mainMenu = new MainMenu();
        mainMenu.setVisible(true);
    }
}