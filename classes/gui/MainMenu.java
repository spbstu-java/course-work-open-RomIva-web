package classes.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JFrame {
    private JPanel mainPanel;
    
    public MainMenu() {
        initializeUI();
    }
    
    private void initializeUI() {
        setTitle("Курсовая работа");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 1000);
        setLocationRelativeTo(null);
        setResizable(false);
        createMainMenu();
        add(mainPanel);
    }
    
    private void createMainMenu() {
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(50, 80, 50, 80));
        mainPanel.setBackground(new Color(245, 245, 245));
        
        JLabel titleLabel = new JLabel("Главное меню", JLabel.CENTER);
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 32));
        titleLabel.setForeground(new Color(60, 63, 65));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 40, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(new Color(245, 245, 245));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(40, 0, 40, 0));
        
        String[] labMenuItems = {
            "Компьютерная игра",
            "Аннотации и рефлексия", 
            "Переводчик текста",
            "Stream API"
        };
        for (int i = 0; i < labMenuItems.length; i++) {
            JButton button = createMenuButton(labMenuItems[i]);
            
            final int labNumber = i + 1;
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    openLabWindow(labNumber);
                }
            });
            
            buttonPanel.add(button);
            buttonPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        }
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 80)));
        
        JButton exitButton = createExitButton("Выход из приложения");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(
                    MainMenu.this,
                    "Вы уверены, что хотите выйти?",
                    "Подтверждение выхода",
                    JOptionPane.YES_NO_OPTION
                );
                if (confirm == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
        buttonPanel.add(exitButton);
        
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(new Color(245, 245, 245));
        centerPanel.add(buttonPanel);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
    }
    
    private JButton createMenuButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Times New Roman", Font.BOLD, 18));
        button.setBackground(new Color(20, 20, 80));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(50, 100, 150), 1),
            BorderFactory.createEmptyBorder(20, 40, 20, 40)
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(600, 80));
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
    
    private JButton createExitButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Times New Roman", Font.BOLD, 18));
        button.setBackground(new Color(180, 70, 70));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(150, 50, 50), 1),
            BorderFactory.createEmptyBorder(20, 40, 20, 40)
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(600, 80));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(200, 80, 80));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(180, 70, 70));
            }
        });
        return button;
    }
    
    private void openLabWindow(int labNumber) {
        JFrame labWindow;
        switch (labNumber) {
            case 1:
                labWindow = new Lab1Window();
                break;
            case 2:
                labWindow = new Lab2Window();
                break;
            case 3:
                labWindow = new Lab3Window();
                break;
            case 4:
                labWindow = new Lab4Window();
                break;
            default:
                labWindow = new LabBaseWindow(labNumber);
                break;
        }
        labWindow.setVisible(true);
        this.setVisible(false);
    }
    
    public void returnToMainMenu() {
        this.setVisible(true);
    }
}