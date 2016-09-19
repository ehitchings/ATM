package ATMLAB;
import java.util.ArrayList;

/**
 * Created by evanhitchings on 9/17/16.
 */
public class ATM {
    private ArrayList<User> users;

    public ATM(){
        this.users = new ArrayList<User>();

    }

    public ATM(ArrayList<User> userlist){
        this.users = userlist;
    }

    public ArrayList getUserAccountList(){
        return this.users;
    }

    protected boolean authenticate(String username, String password){
        for (User user : this.users){
            if (user.getName().equalsIgnoreCase(username)){
                if (user.getPassword().equalsIgnoreCase(password)){
                    return true;
                }
            }
        }
        return false;
    }

    protected String createAccount(String username, String pwd, Type type){

        //In retrospect, I think these lines are superfluous, as I won't ever call this method unless I've already
        //verified a user exists.
        User currentUser = this.getUserFromList(username, pwd);
        if(currentUser == null){
            return "User has not been created.";
        }
        if(this.duplicateAccountCheck(currentUser, type)){
            return "User already has an account of that type";
        }
        Account account = new Account(type);
        currentUser.getAccounts().add(account);
        return "Created " + type.name() + " account for User";
    }

    private User getUserFromList(String username, String pwd){
        for(User user : this.users){
            if (user.getName().equalsIgnoreCase(username) && user.getPassword().equalsIgnoreCase(pwd)){
                return user;
            }
        }
        return null;
    }

    private boolean duplicateAccountCheck(User user, Type type){
        if(user.getAccount(type) == null){
                return false;
            }
        return true;
    }

    protected boolean duplicateAccountCheck(String username, String pwd, Type type){
        User user = this.getUserFromList(username, pwd);
        if(user.getAccount(type) == null){
            return false;
        }
        return true;
    }

    protected String closeAccount(String username, String pwd, Type type){
        User currentUser = this.getUserFromList(username, pwd);
        Account currentAccount = currentUser.getAccount(type);
        if (currentAccount == null){
            return "Account does not exit.";
        }
        if(this.accountIsEmpty(currentAccount)){
            currentUser.getAccounts().remove(currentAccount);
            return "Closed " + type.name() + " account";

        }
        return "Account must be empty to be closed";

    }

    //change to private after testing is complete
    protected boolean accountIsEmpty(Account account){
        return (account.getBalance() == 0.00);
    }

    protected String createUser(String name, String password){
        if(this.duplicateUserCheck(name, password)){
            return "A user with that name and password already exists.";
        }
        User user = new User(name, password);
        this.getUserAccountList().add(user);
        return "Successfully created new user. Welcome " + user.getName() + "!";

    }
    //currently, many of these methods will work incorrectly if 2 users have the same name, but different passwords. Consider solutions.
    //fixed, but I still feel the solution is not elegant. Refactor after MVP
    private boolean duplicateUserCheck(String username, String password){
        for(User user : this.users){
            if(user.getName().equalsIgnoreCase(username) && user.getPassword().equalsIgnoreCase(password)){
                return true;
            }
        }
        return false;
    }

    protected String showBalance(String username, String pwd, Type type){
        User user = this.getUserFromList(username, pwd);
        return "Current balance is " + user.getAccount(type).getBalance() + ".";
    }

    protected String showHistory(String username, String pwd, Type type){
        User user = this.getUserFromList(username, pwd);
        return user.getAccount(type).toString();
    }

    protected ArrayList<Account> getUsersAccounts(String username, String pwd){
        User user = this.getUserFromList(username, pwd);
        return user.getAccounts();
    }

    protected String withdraw(String username, String pwd, Type type, Double money){
        User user = this.getUserFromList(username, pwd);
        Account account = user.getAccount(type);
        Double currentValue = account.getBalance();
        if(money > currentValue){
            return "Insufficient Funds";
        }
        Double updatedValue = currentValue - money;
        account.setBalance(updatedValue);
        String result = "Withdrew $" + money + ". New Balance: $" + updatedValue + ".";
        account.addToHistory(result);
        return result;
    }

    protected String deposit(String username, String pwd, Type type, Double money){
        User user = this.getUserFromList(username, pwd);
        Account account = user.getAccount(type);
        Double currentValue = account.getBalance();
        Double updatedValue = currentValue + money;
        account.setBalance(updatedValue);
        String result = "Deposited $" + money + ". New Balance: $" + updatedValue + ".";
        account.addToHistory(result);
        return result;
    }

    protected String transfer(String usernameFrom, String pwdFrom, Type typeFrom, Double money, String usernameTo, String pwdTo, Type typeTo){
        User userFrom = this.getUserFromList(usernameFrom, pwdFrom);
        Account accountFrom = userFrom.getAccount(typeFrom);
        if(accountFrom.getBalance() < money){
            return "Insufficient funds";
        }
        User userTo = this.getUserFromList(usernameTo, pwdTo);
        Account accountTo = userTo.getAccount(typeTo);
        if(accountTo == null || userTo == null){
            return "User or Account does not exist";
        }
        String resultFrom = this.withdraw(usernameFrom,pwdFrom,typeFrom,money);
        String resultTo = this.deposit(usernameTo, pwdTo, typeTo, money);
        String result = resultFrom + "\n" + resultTo;
        return result;


    }

    protected ArrayList<String> getAvailableAccounts(String username, String password){
        ArrayList<String> availableAccounts = new ArrayList<String>();
        User user = this.getUserFromList(username, password);
        for(Account account : user.getAccounts()){
            availableAccounts.add(account.getType().name());

        }
        return availableAccounts;
    }


}
