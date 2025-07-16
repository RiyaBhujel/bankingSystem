package Part2;

import java.util.LinkedList;

import Part1.Account;
import Part3.GUI;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Working Directory: " + System.getProperty("user.dir"));

        LinkedList<Account> accounts = new LinkedList<>();
        ReadAccounts readAccounts = new ReadAccounts("Accounts.csv");

        LinkedList<String> firstNames = readAccounts.getFirstNames();
        LinkedList<String> lastNames = readAccounts.getLastNames();
        LinkedList<Integer> accountList = readAccounts.getAccounts();
        LinkedList<Integer> balanceList = readAccounts.getBalances();

        int size = firstNames.size();
        if (lastNames.size() != size || accountList.size() != size || balanceList.size() != size) {
            throw new Exception("CSV data is inconsistent: Lists are not the same size.");
        }

        for (int i = 0; i < size; i++) {
            accounts.add(new Account(firstNames.get(i), lastNames.get(i), accountList.get(i), balanceList.get(i)));
        }

        new GUI(accounts);
    }

    private static Account findAccount(LinkedList<Account> accounts, int accNum) {
        for (Account acc : accounts) {
            if (acc.getAccountNum() == accNum) {
                return acc;
            }
        }
        return null;
    }
}