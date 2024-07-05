package ATM;

import javax.swing.*;
import java.awt.*;

public class ATM_Main_GUI {
    private Banks banks;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ATM_Main_GUI().createAndShowGUI());
    }

    public ATM_Main_GUI() {
        banks = new Banks();
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("ATM Machine");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(730, 380);
        frame.setResizable(false);

        showLoginScreen(frame);
    }

    private void showLoginScreen(JFrame frame) {
        frame.getContentPane().removeAll();

        JPanel loginPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel bankLabel = new JLabel("Choose your bank:");
        JComboBox<String> bankComboBox = new JComboBox<>(new String[]{"HBL", "Askari", "Alfalah", "Allied"});

        JLabel accountLabel = new JLabel("Enter your account number:");
        JTextField accountField = new JTextField(15);

        JLabel pinLabel = new JLabel("Enter your PIN:");
        JPasswordField pinField = new JPasswordField(15);

        JButton loginButton = new JButton("Enter");
        JButton shutdownButton = new JButton("Shutdown ATM");

        // Adding components to the layout
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        loginPanel.add(bankLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        loginPanel.add(bankComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        loginPanel.add(accountLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        loginPanel.add(accountField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        loginPanel.add(pinLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.LINE_START;
        loginPanel.add(pinField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.LAST_LINE_START;
        loginPanel.add(loginButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.LAST_LINE_START;
        loginPanel.add(shutdownButton, gbc);

        // Add an image to the right side
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridheight = 5;
        gbc.anchor = GridBagConstraints.CENTER;

        ImageIcon originalIcon = new ImageIcon("D:\\C Drive Data\\Dell\\IdeaProjects\\OOP-1\\src\\ATM\\logo.png");
        Image originalImage = originalIcon.getImage();
        Image resizedImage = originalImage.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);

        JLabel imageLabel = new JLabel(resizedIcon);
        loginPanel.add(imageLabel, gbc);

        frame.getContentPane().add(loginPanel);
        frame.setVisible(true);

        loginButton.addActionListener(e -> {
            String bank = (String) bankComboBox.getSelectedItem();
            String accountNumber = accountField.getText();
            String pin = new String(pinField.getPassword());

            if (bank != null) {
                String bankFile = "D:\\C Drive Data\\Dell\\IdeaProjects\\OOP-1\\src\\ATM\\" + bank + ".txt";
                banks.loadBankFile(bankFile);

                if (banks.authenticate(accountNumber, pin)) {
                    double balance = banks.getBalance(accountNumber);
                    Accounts acc = new Accounts(accountNumber, balance);
                    showAccountGUI(frame, acc);
                } else {
                    showMessageDialog("Authentication failed", "Invalid account number or PIN.");
                }
            } else {
                showMessageDialog("Bank not chosen", "Please choose a bank.");
            }
        });

        shutdownButton.addActionListener(e -> System.exit(0));
    }

    private void showAccountGUI(JFrame frame, Accounts acc) {
        frame.getContentPane().removeAll();

        JPanel accountPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JButton depositButton = new JButton("Deposit Money");
        JButton withdrawButton = new JButton("Withdraw Money");
        JButton balanceButton = new JButton("Check Balance");
        JButton historyButton = new JButton("View Transaction History");
        JButton exitButton = new JButton("Exit");

        // Set button colors
        depositButton.setBackground(new Color(255,145,0));
        withdrawButton.setBackground(new Color(255,145,0));
        balanceButton.setBackground(new Color(255,145,0));
        historyButton.setBackground(new Color(255,145,0));
        exitButton.setBackground(new Color(255,145,0));

        ImageIcon originalIcon = new ImageIcon("D:\\C Drive Data\\Dell\\IdeaProjects\\OOP-1\\src\\ATM\\money.png");
        Image originalImage = originalIcon.getImage();
        Image resizedImage = originalImage.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);
        JLabel imageLabel = new JLabel(resizedIcon);

        // Add the image label to the left
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 5;
        gbc.anchor = GridBagConstraints.CENTER;
        accountPanel.add(imageLabel, gbc);

        // Add buttons to the right
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        accountPanel.add(depositButton, gbc);

        gbc.gridy = 1;
        accountPanel.add(withdrawButton, gbc);

        gbc.gridy = 2;
        accountPanel.add(balanceButton, gbc);

        gbc.gridy = 3;
        accountPanel.add(historyButton, gbc);

        gbc.gridy = 4;
        accountPanel.add(exitButton, gbc);

        frame.getContentPane().add(accountPanel);
        frame.revalidate();
        frame.repaint();

        depositButton.addActionListener(e -> {
            String amountStr = JOptionPane.showInputDialog("Enter amount to deposit:");
            if (amountStr != null) {
                try {
                    double depositAmount = Double.parseDouble(amountStr);
                    acc.deposit(depositAmount);
                    showMessageDialog("Success", "Amount deposited successfully.");
                } catch (NumberFormatException ex) {
                    showMessageDialog("Invalid Input", "Please enter a valid amount.");
                }
            }
        });

        withdrawButton.addActionListener(e -> {
            String amountStr = JOptionPane.showInputDialog("Enter amount to withdraw:");
            if (amountStr != null) {
                try {
                    double withdrawAmount = Double.parseDouble(amountStr);
                    if (acc.withdraw(withdrawAmount)) {
                        showMessageDialog("Success", "Amount withdrawn successfully.");
                    } else {
                        showMessageDialog("Failure", "Insufficient balance.");
                    }
                } catch (NumberFormatException ex) {
                    showMessageDialog("Invalid Input", "Please enter a valid amount.");
                }
            }
        });

        balanceButton.addActionListener(e -> {
            showMessageDialog("Balance", "Your balance is: " + acc.getBalance());
        });

        historyButton.addActionListener(e -> {
            acc.printTransactionHistory();
        });

        exitButton.addActionListener(e -> {
            banks.clearData();
            showMessageDialog("Good Bye", "Thanks for Visiting");
            showLoginScreen(frame);
        });
    }


    private void showMessageDialog(String title, String message) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
    }
}
