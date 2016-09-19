package ATMLAB;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by evanhitchings on 9/17/16.
 */
public class AccountTest {

    Account account;
    @Before
    public void setup(){
        account = new Account(Type.CHECKING);
    }

    @Test
    public void addToHistoryTest(){
        account.addToHistory("Deposited 5");
        assertEquals("String was not added to account history", "Deposited 5", account.getAccountHistory().get(0));
    }


}
