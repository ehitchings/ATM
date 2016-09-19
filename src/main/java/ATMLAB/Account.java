package ATMLAB;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by evanhitchings on 9/17/16.
 */
public class Account {
    private double balance;
    //private int accountNumber;
    private ArrayList<String> accountHistory;
    private Type type;

    public Account(Type type) {
        this.balance = 0.00;
        //this.accountNumber = accountNumber;
        this.accountHistory = new ArrayList<String>();
        this.type = type;
    }

    protected void addToHistory(String toAdd){
        this.accountHistory.add(toAdd);


    }

    protected ArrayList<String> getAccountHistory(){
        return accountHistory;
    }

    public Type getType() {
        return type;
    }

    public double getBalance() {
        return balance;
    }


    protected void setBalance(Double money){
        this.balance = money;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(this.getType().name());
        sb.append("\n");
        for(int i = this.accountHistory.size() - 1; i >= 0; i--){
            sb.append(this.accountHistory.get(i));
            sb.append("\n");

        }
        return "" +sb;
    }
}



enum Type{CHECKING, SAVINGS, INVESTMENT}
