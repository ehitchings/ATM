package ATMLAB;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * Created by evanhitchings on 9/17/16.
 */
public class UI {
    private String currentUser;
    private String currentPassword;
    private Type currentAccount;
    private double money;
    private Scanner reader;
    private ATM atm;
    private boolean power;

    public UI(){
        this.currentUser = null;
        this.currentPassword = null;
        this.currentAccount = null;
        this.money = 0.00;
        this.reader = new Scanner(System.in);
        this.atm = new ATM();
        this.power = true;
    }

    public void start(){
        //persistence stuff to go here
        while(power){
            this.displayLoginMenu();
            this.displayAccountMenu();
            this.displayActionMenu();

        }

    }

    private void clearFields(){
        this.currentUser = null;
        this.currentAccount = null;
        this.currentPassword = null;
        this.money = 0.00;
    }

    public void displayLoginMenu(){
        this.clearFields();

        System.out.println("1: Log In\n2: Create New User\n3: Shut Down");
        String command = reader.nextLine();
        label1:
        switch(command){
            case "1":
                this.login();
                break;
            case "2":
                this.displayCreateUserMenu();
                break;
            case "3":
                this.shutDown();
                break;
            default:
                System.out.println("Bad Command");
                //I feel like I shouldn't be doing this...
                break label1;
        }


    }

    public void shutDown(){
        this.power = false;
    }

    public void login(){
        String username = this.inputUsername();
        String password = this.inputPassword();
        if(atm.authenticate(username, password)){
            this.currentUser = username;
            this.currentPassword = password;
            return;
        }
        System.out.println("User does not exist or Password is incorrect");
        displayLoginMenu();

    }


    public String inputUsername(){
        System.out.println("Input Username");
        String username = reader.nextLine();
        return username;
    }

    public String inputPassword(){
        System.out.println("Input Password");
        String password = reader.nextLine();
        return password;
    }

    public boolean userHasNOAccounts() {
        if(atm.getAvailableAccounts(this.currentUser, this.currentPassword).isEmpty()){
            return true;
        }
        return false;
    }

    public void displayAccountMenu(){
        if(userHasNOAccounts()){
            System.out.println("You must create an account first.");
            this.createAccount();
        }
        System.out.println("1: Select Account\n2: Create Account");
        String command = reader.nextLine();
        switch(command){
            case "1":
                this.currentAccount = inputAccountType();
                displayActionMenu();
                break;
            case "2":
                createAccount();
                break;
            default:
                System.out.println("Invalid input");
                displayAccountMenu();
                break;

        }



    }

    public void createAccount(){
        this.currentAccount =  null;
        String command = "";
        while(this.currentAccount == null){

            System.out.println("1: Checking\n2: Savings\n3: Investment");
            command = reader.nextLine();

            switch(command){
                case "1":
                    this.currentAccount = Type.CHECKING;
                    break;
                case "2":
                    this.currentAccount = Type.SAVINGS;
                    break;
                case "3":
                    this.currentAccount = Type.INVESTMENT;
                    break;
                default:
                    System.out.println("Invalid Input");
                    this.createAccount();
                    break;
            }
            if(atm.duplicateAccountCheck(this.currentUser, this.currentPassword, this.currentAccount)){
                System.out.println("User already has an account of that type");
                this.currentAccount = null;
            } else{
                atm.createAccount(this.currentUser, this.currentPassword, this.currentAccount);

            }
        }
    }



    public Type inputAccountType(){

        System.out.println("1: Checking\n2: Savings\n3: Investment");
        String command = reader.nextLine();
        while(this.currentAccount == null){

            switch(command) {
                case "1":
                    if (atm.getAvailableAccounts(this.currentUser, this.currentPassword).contains(Type.CHECKING)) {
                        this.currentAccount = Type.CHECKING;
                        return Type.CHECKING;

                    } else {
                        System.out.println("Invalid input");
                        //inputAccountType();
                        break;
                    }

                case "2":
                    if (atm.getAvailableAccounts(this.currentUser, this.currentPassword).contains(Type.SAVINGS)) {
                        this.currentAccount = Type.SAVINGS;
                        return Type.SAVINGS;

                    } else {
                        System.out.println("Invalid input");
                        //inputAccountType();
                        break;
                    }

                case "3":
                    if (atm.getAvailableAccounts(this.currentUser, this.currentPassword).contains(Type.INVESTMENT)) {
                        this.currentAccount = Type.INVESTMENT;
                        return Type.INVESTMENT;

                    } else {
                        System.out.println("Invalid input");
                        //inputAccountType();
                        break;
                    }

                default:
                    System.out.println("Invalid input");
                    inputAccountType();
                    break;
            }

        }
        return this.currentAccount;
    }

    public void displayCreateUserMenu(){
        String username = this.inputUsername();
        String password = this.inputPassword();
        atm.createUser(username, password);
        this.currentUser = username;
        this.currentPassword = password;


    }

    public void displayActionMenu(){
        System.out.println("1: Withdraw\n2: Deposit\n3: Transfer\n4: Show History\n5: Logout");
        String command = reader.nextLine();
        Double amount;
        switch(command){
            case "1":
                this.money = this.getAmount();
                System.out.println(atm.withdraw(this.currentUser, this.currentPassword, this.currentAccount, this.money));
                break;
            case "2":
                this.money = this.getAmount();
                System.out.println(atm.deposit(this.currentUser, this.currentPassword, this.currentAccount, this.money));
                break;
            case "3":
                this.money = this.getAmount();
                Type secondAccount = null;
                while(secondAccount == null){
                    secondAccount = this.inputAccountType();
                }

                System.out.println(atm.transfer(this.currentUser, this.currentPassword, this.currentAccount, this.money, this.currentUser, this.currentPassword, secondAccount));
                break;
            case "4":
                System.out.println(atm.showHistory(this.currentUser, this.currentPassword, this.currentAccount));
                break;
            case "5":
                this.displayLoginMenu();
                break;
            default:
                System.out.println("Invalid input");
                this.displayActionMenu();
                break;

        }
    }

    public Double getAmount(){
        System.out.println("Input amount");
        int money = reader.nextInt();
        return money * 1.0;

    }


}
