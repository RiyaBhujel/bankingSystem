package Part1;

public class Transaction {
    
    public void transfer(Account acc1, Account acc2, int amount) {
        acc1.withdraw(amount); 
        acc2.deposit(amount);  
    }
}