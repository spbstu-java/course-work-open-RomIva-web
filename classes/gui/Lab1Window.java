package classes.gui;

import classes.lab1.Hero.Hero;
import classes.lab1.Movement.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Lab1Window extends JFrame {
    private Hero hero;
    private JTextArea outputArea;
    private JTextField fromField;
    private JTextField toField;
    
    public Lab1Window() {
        this.hero = new Hero();
        initializeUI();
    }
    
    private void initializeUI() {
        setTitle("Компьютерная игра");
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
        
        JLabel titleLabel = new JLabel("Компьютерная игра", JLabel.CENTER);
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
                "Результаты перемещения"
            ),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        scrollPane.setPreferredSize(new Dimension(960, 400));
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        JPanel buttonPanel = createBottomButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);
        appendOutput("Добро пожаловать в компьютерную игру!\n");
        appendOutput("Выберите способ перемещения и укажите точки маршрута.\n");
    }
    
    private JPanel createControlPanel() {
        JPanel controlPanel = new JPanel(new BorderLayout(10, 15));
        controlPanel.setBackground(Color.WHITE);
        controlPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)), 
                "Управление перемещением героя"
            ),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        JPanel pointsPanel = createPointsPanel();
        controlPanel.add(pointsPanel, BorderLayout.NORTH);
        
        JPanel movementPanel = createMovementPanel();
        controlPanel.add(movementPanel, BorderLayout.CENTER);
        return controlPanel;
    }
    
    private JPanel createPointsPanel() {
        JPanel pointsPanel = new JPanel(new GridBagLayout());
        pointsPanel.setBackground(Color.WHITE);
        pointsPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 10, 8, 10);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        JLabel fromLabel = new JLabel("Точка отправления:");
        fromLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
        pointsPanel.add(fromLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        fromField = new JTextField();
        fromField.setFont(new Font("Times New Roman", Font.BOLD, 16));
        fromField.setPreferredSize(new Dimension(300, 35));
        fromField.setMinimumSize(new Dimension(300, 35));
        pointsPanel.add(fromField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        JLabel toLabel = new JLabel("Точка назначения:");
        toLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
        pointsPanel.add(toLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        toField = new JTextField();
        toField.setFont(new Font("Times New Roman", Font.BOLD, 16));
        toField.setPreferredSize(new Dimension(300, 35));
        toField.setMinimumSize(new Dimension(300, 35));
        pointsPanel.add(toField, gbc);
        return pointsPanel;
    }
    
    private JPanel createMovementPanel() {
        JPanel movementPanel = new JPanel(new GridLayout(1, 4, 20, 0));
        movementPanel.setBackground(Color.WHITE);
        movementPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JButton walkButton = createMovementButton("Пешком", new WalkMovement());
        JButton transportButton = createMovementButton("Транспорт", new TransportMovement());
        JButton flyButton = createMovementButton("Полет", new FlyMovement());
        JButton teleportButton = createMovementButton("Телепортация", new TeleportMovement());
        
        Dimension buttonSize = new Dimension(200, 80);
        walkButton.setPreferredSize(buttonSize);
        transportButton.setPreferredSize(buttonSize);
        flyButton.setPreferredSize(buttonSize);
        teleportButton.setPreferredSize(buttonSize);
        movementPanel.add(walkButton);
        movementPanel.add(transportButton);
        movementPanel.add(flyButton);
        movementPanel.add(teleportButton);
        return movementPanel;
    }
    
    private JButton createMovementButton(String text, MoveMovement movement) {
        JButton button = new JButton("<html><center>" + text + "</center></html>");
        button.setFont(new Font("Times New Roman", Font.BOLD, 16));
        button.setBackground(new Color(20, 20, 80));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(50, 100, 150), 2),
            BorderFactory.createEmptyBorder(20, 10, 20, 10)
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performMovement(movement, text);
            }
        });
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
    
    private void performMovement(MoveMovement movement, String movementType) {
        String from = fromField.getText().trim();
        String to = toField.getText().trim();
        if (from.isEmpty() || to.isEmpty()) {
            appendOutput("ОШИБКА: Необходимо указать обе точки маршрута!\n");
            return;
        }
        hero.setMovement(movement);
        String result = hero.move(from, to);
        appendOutput("=== " + movementType.toUpperCase() + " ===");
        appendOutput(result);
        appendOutput("");
    }
    
    private void appendOutput(String text) {
        outputArea.append(text + "\n");
        outputArea.setCaretPosition(outputArea.getDocument().getLength());
    }
    
    private void clearOutput() {
        outputArea.setText("");
        appendOutput("Лог очищен.\n");
        appendOutput("Добро пожаловать в компьютерную игру!\n");
        appendOutput("Выберите способ перемещения и укажите точки маршрута.\n");
    }
    
    private void returnToMainMenu() {
        this.dispose();
        MainMenu mainMenu = new MainMenu();
        mainMenu.setVisible(true);
    }
}