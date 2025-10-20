package classes.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;

public class LabBaseWindow extends JFrame {
    private int labNumber;
    private JTextArea outputArea;
    
    public LabBaseWindow(int labNumber) {
        this.labNumber = labNumber;
        initializeUI();
    }
    
    private void initializeUI() {
        String[] titles = {
            "Компьютерная игра",
            "Аннотации и рефлексия", 
            "Переводчик текста",
            "Stream API"
        };
        setTitle(titles[labNumber - 1]);
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
        
        JLabel titleLabel = new JLabel(getTitle(), JLabel.CENTER);
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 28));
        titleLabel.setForeground(new Color(60, 63, 65));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        
        JPanel contentPanel = new JPanel(new BorderLayout(15, 15));
        contentPanel.setBackground(new Color(245, 245, 245));
        
        JPanel labSpecificPanel = new JPanel();
        labSpecificPanel.setBackground(Color.WHITE);
        labSpecificPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)), 
                "Управление"
            ),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        JLabel placeholderLabel = new JLabel("Функциональность для '" + getTitle() + "' будет реализована здесь");
        placeholderLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
        placeholderLabel.setForeground(new Color(150, 150, 150));
        labSpecificPanel.add(placeholderLabel);
        contentPanel.add(labSpecificPanel, BorderLayout.NORTH);
        
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
                "Результаты"
            ),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        scrollPane.setPreferredSize(new Dimension(960, 600));
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(245, 245, 245));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        
        JButton backButton = createBackButton();
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                returnToMainMenu();
            }
        });
        buttonPanel.add(backButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);
        outputArea.setText("Добро пожаловать в " + getTitle() + "!\n\n" +
                          "Здесь будут отображаться результаты работы программы.\n" +
                          "Интерфейс готов к реализации функциональности.\n\n" +
                          "Размер окна: 1000x1000 пикселей");
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
    
    private void returnToMainMenu() {
        this.dispose();
        MainMenu mainMenu = new MainMenu();
        mainMenu.setVisible(true);
    }
    
    public void appendOutput(String text) {
        outputArea.append(text + "\n");
    }
    
    public void clearOutput() {
        outputArea.setText("");
    }
}