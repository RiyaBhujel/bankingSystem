package Part1;

public class Account extends Customer {
   
    private int balance;
    private int accountNumber;

    public Account(String FName, String LName, int accNum, int accBal) {
        
        setFirstName(FName);
        setLastName(LName);
        
        accountNumber = accNum;
        balance = accBal;
    }

    public int getBalance() {
        return balance;
    }

    public int getAccountNum() {
        return accountNumber;
    }

    public void deposit(int amount) {
        balance += amount;
    }

    public void withdraw(int amount) {
        balance -= amount;
    }

    public void giveToken(String token){
        System.out.println("your token is "+ token);
    }

    giveToken();
}
