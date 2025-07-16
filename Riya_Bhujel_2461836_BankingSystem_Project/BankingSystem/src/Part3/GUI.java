package Part3;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;

import Part1.Account;
import Part1.Transaction;

public class GUI extends JFrame {
   
    private Transaction transferObject = new Transaction();
    private StringBuilder sbAllData = new StringBuilder();
    private LinkedList<Account> globalAccounts;
    private JTextArea accountDisplay; // Declared here
    private JLabel statusLabel;
    private JButton showAllButton, depositButton, withdrawButton, transferButton;
    private JTextField depositAccountField, depositAmountField;
    private JTextField withdrawAccountField, withdrawAmountField;
    private JTextField transferFromField, transferToField, transferAmountField;

    public GUI(LinkedList<Account> accounts) {
        super("Banking System");
        globalAccounts = accounts;

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        accountDisplay = new JTextArea(10, 50);
        accountDisplay.setEditable(false);
        accountDisplay.setFont(new Font("Arial", Font.PLAIN, 14));
        accountDisplay.setBackground(Color.WHITE);
        updateAccountData(); // Now safe to call

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setMinimumSize(new Dimension(600, 400));
        setLayout(new BorderLayout(10, 10));

        JPanel northPanel = new JPanel(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(accountDisplay);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Account Details"));
        showAllButton = new JButton("Refresh Accounts");
        showAllButton.setToolTipText("Click to refresh the list of all accounts");
        northPanel.add(showAllButton, BorderLayout.NORTH);
        northPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Consistent padding
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JPanel depositPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        depositPanel.setBorder(BorderFactory.createTitledBorder("Deposit"));
        depositAccountField = new JTextField(10);
        addPlaceholder(depositAccountField, "Account Number");
        depositAmountField = new JTextField(10);
        addPlaceholder(depositAmountField, "Amount");
        depositButton = new JButton("Deposit");
        depositButton.setToolTipText("Deposit money into the specified account");
        depositPanel.add(new JLabel("Account:"));
        depositPanel.add(depositAccountField);
        depositPanel.add(new JLabel("Amount:"));
        depositPanel.add(depositAmountField);
        depositPanel.add(depositButton);
        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(depositPanel, gbc);

        JPanel withdrawPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        withdrawPanel.setBorder(BorderFactory.createTitledBorder("Withdraw"));
        withdrawAccountField = new JTextField(10);
        addPlaceholder(withdrawAccountField, "Account Number");
        withdrawAmountField = new JTextField(10);
        addPlaceholder(withdrawAmountField, "Amount");
        withdrawButton = new JButton("Withdraw");
        withdrawButton.setToolTipText("Withdraw money from the specified account");
        withdrawPanel.add(new JLabel("Account:"));
        withdrawPanel.add(withdrawAccountField);
        withdrawPanel.add(new JLabel("Amount:"));
        withdrawPanel.add(withdrawAmountField);
        withdrawPanel.add(withdrawButton);
        gbc.gridy = 1;
        centerPanel.add(withdrawPanel, gbc);

        JPanel transferPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        transferPanel.setBorder(BorderFactory.createTitledBorder("Transfer"));
        transferFromField = new JTextField(10);
        addPlaceholder(transferFromField, "From Account");
        transferToField = new JTextField(10);
        addPlaceholder(transferToField, "To Account");
        transferAmountField = new JTextField(10);
        addPlaceholder(transferAmountField, "Amount");
        transferButton = new JButton("Transfer");
        transferButton.setToolTipText("Transfer money between accounts");
        transferPanel.add(new JLabel("From:"));
        transferPanel.add(transferFromField);
        transferPanel.add(new JLabel("To:"));
        transferPanel.add(transferToField);
        transferPanel.add(new JLabel("Amount:"));
        transferPanel.add(transferAmountField);
        transferPanel.add(transferButton);
        gbc.gridy = 2;
        centerPanel.add(transferPanel, gbc);

        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusLabel = new JLabel("Ready");
        statusLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        statusLabel.setForeground(Color.GRAY);
        southPanel.add(statusLabel);

        add(northPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);

        HandlerClass handler = new HandlerClass();
        showAllButton.addActionListener(handler);
        depositButton.addActionListener(handler);
        withdrawButton.addActionListener(handler);
        transferButton.addActionListener(handler);

        setVisible(true);
    }

    private void updateAccountData() {
        sbAllData.setLength(0);
        for (Account acc : globalAccounts) {
            sbAllData.append(String.format("Name: %-20s Account: %-10d Balance: $%-10d%n",
                    acc.getFirstName() + " " + acc.getLastName(),
                    acc.getAccountNum(),
                    acc.getBalance()));
        }
        if (accountDisplay != null) { 
            accountDisplay.setText(sbAllData.toString());
        }
    }

    private void addPlaceholder(JTextField field, String placeholder) {
        field.setText(placeholder);
        field.setForeground(Color.GRAY);
        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setText(placeholder);
                    field.setForeground(Color.GRAY);
                }
            }
        });
    }

    private Account findAccount(int accNum) {
        for (Account acc : globalAccounts) {
            if (acc.getAccountNum() == accNum) {
                return acc;
            }
        }
        return null;
    }

    private class HandlerClass implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            updateAccountData();

            if (e.getSource() == showAllButton) {
                statusLabel.setText("Accounts refreshed.");
            } else if (e.getSource() == depositButton) {
                try {
                    int accNum = Integer.parseInt(depositAccountField.getText().trim());
                    int amount = Integer.parseInt(depositAmountField.getText().trim());
                    if (amount <= 0) {
                        statusLabel.setText("Error: Amount must be positive.");
                        return;
                    }
                    Account acc = findAccount(accNum);
                    if (acc != null) {
                        acc.deposit(amount);
                        updateAccountData();
                        statusLabel.setText("Deposited $" + amount + " to account " + accNum + " successfully.");
                    } else {
                        statusLabel.setText("Error: Account " + accNum + " not found.");
                    }
                } catch (NumberFormatException ex) {
                    statusLabel.setText("Error: Please enter valid numbers.");
                }
            } else if (e.getSource() == withdrawButton) {
                try {
                    int accNum = Integer.parseInt(withdrawAccountField.getText().trim());
                    int amount = Integer.parseInt(withdrawAmountField.getText().trim());
                    if (amount <= 0) {
                        statusLabel.setText("Error: Amount must be positive.");
                        return;
                    }
                    Account acc = findAccount(accNum);
                    if (acc != null) {
                        if (acc.getBalance() >= amount) {
                            acc.withdraw(amount);
                            updateAccountData();
                            statusLabel.setText("Withdrawn $" + amount + " from account " + accNum + " successfully.");
                        } else {
                            statusLabel.setText("Error: Insufficient funds in account " + accNum + ".");
                        }
                    } else {
                        statusLabel.setText("Error: Account " + accNum + " not found.");
                    }
                } catch (NumberFormatException ex) {
                    statusLabel.setText("Error: Please enter valid numbers.");
                }
            } else if (e.getSource() == transferButton) {
                try {
                    int fromAccNum = Integer.parseInt(transferFromField.getText().trim());
                    int toAccNum = Integer.parseInt(transferToField.getText().trim());
                    int amount = Integer.parseInt(transferAmountField.getText().trim());
                    if (amount <= 0) {
                        statusLabel.setText("Error: Amount must be positive.");
                        return;
                    }
                    Account fromAcc = findAccount(fromAccNum);
                    Account toAcc = findAccount(toAccNum);
                    if (fromAcc != null && toAcc != null) {
                        if (fromAcc.getBalance() >= amount) {
                            transferObject.transfer(fromAcc, toAcc, amount);
                            updateAccountData();
                            statusLabel.setText("Transferred $" + amount + " from account " + fromAccNum + " to " + toAccNum + " successfully.");
                        } else {
                            statusLabel.setText("Error: Insufficient funds in account " + fromAccNum + ".");
                        }
                    } else {
                        statusLabel.setText("Error: One or both accounts not found.");
                    }
                } catch (NumberFormatException ex) {
                    statusLabel.setText("Error: Please enter valid numbers.");
                }
            }
        }
    }
}