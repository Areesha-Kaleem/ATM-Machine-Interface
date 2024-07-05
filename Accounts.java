package ATM;

import javax.swing.*;
import java.io.*;

public class Accounts {
    private String accountNumber;
    private double balance;

    public Accounts(String accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public void deposit(double amount) {
        balance += amount;
        updateTransactionHistory("Deposit," + amount);
    }

    public boolean withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            updateTransactionHistory("Withdraw," + amount);
            return true;
        } else {
            return false;
        }
    }

    public double getBalance() {
        return balance;
    }

//    public void loadTransactionHistory(String filename) {
//        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
//            String line;
//            while ((line = br.readLine()) != null) {
//                String[] parts = line.split(",");
//                String type = parts[0];
//                double amount = Double.parseDouble(parts[1]);
//                if (type.equals("Deposit")) {
//                    balance += amount;
//                } else if (type.equals("Withdraw")) {
//                    balance -= amount;
//                }
//            }
//        } catch (IOException e) {
//            // File may not exist yet, which is okay
//        }
//    }

    public void printTransactionHistory() {
        try (BufferedReader br = new BufferedReader(new FileReader("D:\\C Drive Data\\Dell\\IdeaProjects\\OOP-1\\src\\ATM\\"
                + accountNumber + ".txt"))) {
            System.out.println("Account file opened successfully");
            String line;
            String  Msg = "";
            while ((line = br.readLine()) != null) {
                Msg = Msg + "\n" + line;
            }
            showMessageDialog("Transaction History", Msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateTransactionHistory(String transaction) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("D:\\C Drive Data\\Dell\\IdeaProjects\\OOP-1\\src\\ATM\\" + accountNumber + ".txt", true))) {
            bw.write(transaction);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void showMessageDialog(String title, String message) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
    }
}

