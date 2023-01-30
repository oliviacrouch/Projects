public class BankAccount
{
    private String clientName;
    private int clientID;
    private double accountBalance;
    private boolean closed = false;

    public BankAccount(String clientName, int clientID, double accountBalance)
    {
        //implement the constructor
        this.clientName = clientName;
        this.clientID = clientID;
        this.accountBalance = accountBalance;
    }

    public String getName()
    {
        //implement getter
        return clientName;
    }
    public int getID()
    {
        //implement getter
        return clientID;
    }

    public double getBalance()
    {
        //implement getter
        return accountBalance;
    }

    public boolean getClosed()
    {
        //implement getter
        return closed;
    }

    public void deposit(double depositAmount)
    {
        //implement this method
        accountBalance = accountBalance + depositAmount;
    }

    public void withdraw(double withdrawalAmount)
    {
        accountBalance = accountBalance - withdrawalAmount;
    }

    public void closeAccount()
    {
        //implement this method
        closed = true;
        accountBalance = 0;
    }

    public void transferTo(BankAccount toAccount, double amount)
    {
        //implement this method
       this.withdraw(amount);
       toAccount.deposit(amount);
    }
}
