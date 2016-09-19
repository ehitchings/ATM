package ATMLAB;
import java.util.List;
import java.util.ArrayList;

/**
 * Created by evanhitchings on 9/17/16.
 */
public class User {

    private String name;
    private String password;
    private ArrayList<Account> accounts;


    public User(String name, String password) {
        this.name = name;
        this.password = password;
        this.accounts = new ArrayList<Account>();

    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public Account getAccount(Type type){
        for(Account account : this.getAccounts()){
            if (account.getType().equals(type)){
                return account;
            }
        }
        return null;
    }

    @Override
    public String toString(){
        return this.getName();
    }


}


