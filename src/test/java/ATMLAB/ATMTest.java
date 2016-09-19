package ATMLAB;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;
import java.util.ArrayList;

/**
 * Created by evanhitchings on 9/17/16.
 */
public class ATMTest {
    User u1 = new User("Ruby", "rose");
    User u2 = new User("Weiss", "ice");
    User u3 = new User("Black", "shadow");
    User u4 = new User("Yang", "fire");
    ArrayList<User> userlist = new ArrayList<User>();
    ATM atm;

    @Before
    public void setup(){
        userlist.add(u1);
        userlist.add(u2);
        userlist.add(u3);
        userlist.add(u4);
        atm = new ATM(userlist);

    }

    @Test
    public void authenticateTest(){
        assertTrue("Authenticate did not return true", atm.authenticate("Ruby", "rose"));
    }

    @Test
    public void createUserAccountSuccessfullyCreatesAccountAndAddIt(){
        assertEquals("Did not create account", "Created " + Type.CHECKING.name() + " account for User", atm.createAccount("Ruby", "rose", Type.CHECKING));
        assertEquals("Did not add account to user's account list", Type.CHECKING, u1.getAccounts().get(0).getType());


    }

    @Test
    public void duplicateAccountTest(){
        atm.createAccount("Ruby", "rose", Type.CHECKING);
        assertEquals("Method added duplicate account", "User already has an account of that type", atm.createAccount("Ruby", "rose", Type.CHECKING));
    }

    @Test
    public void userDoesNotExistTest(){
        assertEquals("Create Account worked despite user not existing", "User has not been created.", atm.createAccount("Nora", "valkyrie", Type.CHECKING));
    }

    @Test
    public void accountIsEmptyTest(){
        Account account = new Account(Type.CHECKING);
        assertTrue(atm.accountIsEmpty(account));
        account.setBalance(50.00);
        assertFalse(atm.accountIsEmpty(account));
    }

    @Test
    public void closeEmptyAccountTest(){
        Account account = new Account(Type.CHECKING);
        u1.getAccounts().add(account);
        assertNotNull(u1.getAccount(Type.CHECKING));
        assertEquals("Empty account did not close", "Closed " + Type.CHECKING.name() + " account", atm.closeAccount("Ruby", "rose", Type.CHECKING));
        assertNull(u1.getAccount(Type.CHECKING));
    }

    @Test
    public void closeNonEmptyAccountTest(){
        Account account = new Account(Type.CHECKING);
        account.setBalance(50.00);
        u1.getAccounts().add(account);
        assertEquals("Account was not empty, yet closed", "Account must be empty to be closed", atm.closeAccount("Ruby", "rose", Type.CHECKING));
        assertNotNull(u1.getAccount(Type.CHECKING));
    }

    @Test
    public void createUserTestWithNewUser(){
        assertEquals("Failed to create new user", "Successfully created new user. Welcome Nora!", atm.createUser("Nora", "valkyrie"));
        assertEquals("User was not added to the userlist", "A user with that name and password already exists.", atm.createUser("Nora", "valkyrie"));

    }

    @Test
    public void showBalanceTest(){
        Account accountToCheck = new Account(Type.CHECKING);
        accountToCheck.setBalance(50.00);
        Account accountToNot = new Account(Type.INVESTMENT);
        u1.getAccounts().add(accountToCheck);
        u1.getAccounts().add(accountToNot);
        assertEquals("Did not show the current amount", "Current balance is 50.0.", atm.showBalance("Ruby", "rose", Type.CHECKING));
    }

    @Test
    public void showAccountHistoryTest(){
        Account account = new Account(Type.CHECKING);
        account.addToHistory("Test 1");
        account.addToHistory("Test 2");
        account.addToHistory("Test 3");
        u1.getAccounts().add(account);
        System.out.println(atm.showHistory("Ruby", "rose", Type.CHECKING));

    }

    @Test
    public void withdrawSuccessTest(){
        Account account = new Account(Type.CHECKING);
        account.setBalance(50.00);
        u1.getAccounts().add(account);
        assertEquals("Transaction was not successful", "Withdrew $25.0. New Balance: $25.0.", atm.withdraw("Ruby", "rose", Type.CHECKING, 25.00));
        assertEquals(25.00, account.getBalance(), 0.00);
        assertEquals("Transaction was not logged", "Withdrew $25.0. New Balance: $25.0.", account.getAccountHistory().get(0));
    }

    @Test
    public void withdrawFailTest(){
        Account account = new Account(Type.CHECKING);
        account.setBalance(50.00);
        u1.getAccounts().add(account);
        assertEquals("Transaction was successful when it should have failed", "Insufficient Funds", atm.withdraw("Ruby", "rose", Type.CHECKING, 100.00));
    }

    @Test
    public void depositTest(){
        Account account = new Account(Type.CHECKING);
        u1.getAccounts().add(account);
        assertEquals("Deposit was not successful", "Deposited $25.0. New Balance: $25.0.", atm.deposit("Ruby", "rose", Type.CHECKING, 25.00));
        assertEquals("Transaction was not logged", "Deposited $25.0. New Balance: $25.0.", account.getAccountHistory().get(0));

    }

    @Test
    public void transferTest(){
        Account accountFrom = new Account(Type.CHECKING);
        u1.getAccounts().add(accountFrom);
        Account accountTo = new Account(Type.CHECKING);
        u2.getAccounts().add(accountTo);
        accountFrom.setBalance(50.00);
        assertEquals("Transfer was not successful", "Withdrew $25.0. New Balance: $25.0.\nDeposited $25.0. New Balance: $25.0.", atm.transfer("Ruby", "rose", Type.CHECKING, 25.00, "Weiss", "ice", Type.CHECKING));
    }











}
