package ATM;

import java.io.*;
import java.util.*;

public class Banks {
    private List<String> accountNumbers;
    private List<String> pins;
    private List<Double> balances;

    public Banks() {
        accountNumbers = new ArrayList<>();
        pins = new ArrayList<>();
        balances = new ArrayList<>();
    }

    public void loadBankFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            System.out.println("Bank File opened successfully");
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 3) {
                    continue;
                }

                accountNumbers.add(parts[0]);
                pins.add(parts[1]);
                balances.add(Double.parseDouble(parts[2]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public boolean authenticate(String accountNumber, String pin) {
        for (int i = 0; i < accountNumbers.size(); i++) {
            if (accountNumbers.get(i).equals(accountNumber) && pins.get(i).equals(pin)) {
                return true;
            }
        }
        return false;
    }

    public double getBalance(String accountNumber) {
        int index = accountNumbers.indexOf(accountNumber);
        if (index != -1) {
            return balances.get(index);
        }
        return 0.0;
    }
    public void clearData() {
        accountNumbers.clear();
        pins.clear();
        balances.clear();
    }
}
