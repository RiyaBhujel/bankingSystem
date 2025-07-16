package Part1;

public class Main {
    public static void main(String[] args) {
        
        Account account1 = new Account("Jeffrey", "Ting", 1, 2000);
        
        Account account2 = new Account("Hiran", "Patel", 2, 1000);

        System.out.println("Account 1 balance: " + account1.getBalance());
        System.out.println("Account 2 balance: " + account2.getBalance());

        account1.deposit(250);
        System.out.println("Account 1 balance after deposit: " + account1.getBalance());

        account2.withdraw(500);
        System.out.println("Account 2 balance after withdraw: " + account2.getBalance());

        Transaction t = new Transaction();
        t.transfer(account1, account2, 250);

        System.out.println("Account 1 final balance: " + account1.getBalance());
        System.out.println("Account 2 final balance: " + account2.getBalance());
    }
}
