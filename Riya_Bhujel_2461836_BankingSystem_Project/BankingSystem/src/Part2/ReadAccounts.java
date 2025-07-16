package Part2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedList;

public class ReadAccounts {
    private String filePath; 

    public ReadAccounts(String URL) throws Exception {
        this.filePath = URL;
    }

    public LinkedList<String> getFirstNames() throws Exception {
        LinkedList<String> firstNames = new LinkedList<>();
        try (BufferedReader tempReader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = tempReader.readLine()) != null) {
                String[] data = line.split(",");
                firstNames.add(data[0].trim());
            }
        }
        return firstNames;
    }

    public LinkedList<String> getLastNames() throws Exception {
        LinkedList<String> lastNames = new LinkedList<>();
        try (BufferedReader tempReader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = tempReader.readLine()) != null) {
                String[] data = line.split(",");
                lastNames.add(data[1].trim());
            }
        }
        return lastNames;
    }

    public LinkedList<Integer> getAccounts() throws Exception {
        LinkedList<Integer> accountList = new LinkedList<>();
        try (BufferedReader tempReader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = tempReader.readLine()) != null) {
                String[] data = line.split(",");
                accountList.add(Integer.parseInt(data[2].trim()));
            }
        }
        return accountList;
    }

    public LinkedList<Integer> getBalances() throws Exception {
        LinkedList<Integer> balanceList = new LinkedList<>();
        try (BufferedReader tempReader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = tempReader.readLine()) != null) {
                String[] data = line.split(",");
                balanceList.add(Integer.parseInt(data[3].trim()));
            }
        }
        return balanceList;
    }
}