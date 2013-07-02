package org.qsoft.tdd.test;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.qsoft.tdd.dao.BankAccountDao;
import org.qsoft.tdd.dao.TransactionDao;
import org.qsoft.tdd.entity.BankAccount;
import org.qsoft.tdd.entity.Transaction;
import org.qsoft.tdd.services.BankAccountService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

/**
 * Created by IntelliJ IDEA.
 * User: haopt
 * Date: 7/2/13
 * Time: 11:50 AM
 * To change this template use File | Settings | File Templates.
 */
public class TestBankAccount {

    String accountNubmer = "123";
    BankAccountDao mockAccountDao = mock(BankAccountDao.class);
    TransactionDao mockTransactionDao = mock(TransactionDao.class);
    Date dateTimes = mock(Date.class);
    BankAccountService bankAccountService = new BankAccountService();
//    private BankAccount bankAccount;

    @Before
    public void setUp() {
        reset(mockAccountDao);
        reset(mockTransactionDao);
        reset(dateTimes);

        bankAccountService.setBankAccountDao(mockAccountDao);
        bankAccountService.setTransactionDao(mockTransactionDao);
        bankAccountService.setDateTime(dateTimes);
    }

    @Test
    public void openNewAccount() {
        bankAccountService.openBankAccount("123");

        ArgumentCaptor<BankAccount> savedAccountRecords = ArgumentCaptor.forClass(BankAccount.class);
        verify(mockAccountDao).save(savedAccountRecords.capture());

        assertEquals(savedAccountRecords.getValue().getBalance(), 0.0, 0.01);
        assertEquals(savedAccountRecords.getValue().getAccountNumber(), "123");
    }

    @Test
    public void testGetAccountByAccountNumber() {
        String stringAccountNumber = "123";
        bankAccountService.getBankAccountByNumberId(stringAccountNumber);
        ArgumentCaptor<String> accountNumberArgument = ArgumentCaptor.forClass(String.class);
        verify(mockAccountDao).getBankAccountByAccountNumber(accountNumberArgument.capture());

        assertEquals(stringAccountNumber, accountNumberArgument.getValue());
    }

    @Test
    public void testAccountDeposit() {
        BankAccount bankAccount = bankAccountService.openBankAccount(accountNubmer);
        bankAccountService.deposit(bankAccount, 1000.0);

        ArgumentCaptor<BankAccount> accountDeposite = ArgumentCaptor.forClass(BankAccount.class);
        verify(mockAccountDao, times(2)).save(accountDeposite.capture());
        assertEquals(1000.0, accountDeposite.getValue().getBalance());
    }

    @Test
    public void testAddLogDetailDeposit() {
        when(dateTimes.getTime()).thenReturn(1000L);

        //open account with 5000
        BankAccount bankAccount = bankAccountService.openBankAccountWithMoney(accountNubmer, 5000);

        //put 1000 for account
        bankAccountService.deposit(bankAccount, 1000.0);

        ArgumentCaptor<Transaction> transactionAC = ArgumentCaptor.forClass(Transaction.class);

        verify(mockTransactionDao, times(1)).save(transactionAC.capture());

        assertEquals(Long.valueOf(1000), transactionAC.getAllValues().get(0).getTimeStamp());

        assertEquals(6000.0, transactionAC.getAllValues().get(0).getAmount());
    }

    @Test
    public void testAccountWithdraw() {
        BankAccount bankAccount = bankAccountService.openBankAccountWithMoney(accountNubmer, 5000);
        bankAccountService.withDraw(bankAccount, 1000.0);

        ArgumentCaptor<BankAccount> accountDraw = ArgumentCaptor.forClass(BankAccount.class);
        verify(mockAccountDao, times(2)).save(accountDraw.capture());
        assertEquals(4000.0, accountDraw.getValue().getBalance());
    }

    @Test
    public void testAddLogWithDraw() {
        when(dateTimes.getTime()).thenReturn(1000L);

        //open account with 5000
        BankAccount bankAccount = bankAccountService.openBankAccountWithMoney(accountNubmer, 5000);

        //get 1000 for account
        bankAccountService.withDraw(bankAccount, 1000.0);

        ArgumentCaptor<Transaction> transactionAC = ArgumentCaptor.forClass(Transaction.class);

        verify(mockTransactionDao, times(1)).save(transactionAC.capture());

        assertEquals(Long.valueOf(1000), transactionAC.getAllValues().get(0).getTimeStamp());

        assertEquals(4000.0, transactionAC.getAllValues().get(0).getAmount());
    }

    @Test
    public void testThrowExceptionWhenDraw() {
        when(dateTimes.getTime()).thenReturn(1000L).thenReturn(2000L).thenReturn(3000L).thenReturn(4000L).thenReturn(5000L);
        //open account with 5000
        BankAccount bankAccount = bankAccountService.openBankAccountWithMoney(accountNubmer, 5000);
        //get 1000 for account
        try {
            bankAccountService.withDraw(bankAccount, 1000.0);

            ArgumentCaptor<BankAccount> accountDraw = ArgumentCaptor.forClass(BankAccount.class);
            verify(mockAccountDao, times(2)).save(accountDraw.capture());
            assertEquals(4000.0, accountDraw.getValue().getBalance());

            bankAccountService.withDraw(bankAccount, 6000.0);

        } catch (Exception e) {
            Assert.assertEquals("Fail", e.getMessage());
        }
    }

    @Test
    public void testGetListTransactionOccurred() {
        when(dateTimes.getTime()).thenReturn(1000L).thenReturn(2000L).thenReturn(3000L).thenReturn(4000L).thenReturn(5000L);
        bankAccountService.getAllTransactionAccurred(accountNubmer);
        verify(mockTransactionDao).getAllTransactionAccurred(accountNubmer);
    }

    @Test
    public void testGetListTransactionOccurredFromTimeToTime() {
        when(dateTimes.getTime()).thenReturn(1000L).thenReturn(2000L).thenReturn(3000L).thenReturn(4000L).thenReturn(5000L);
        bankAccountService.getAllTransactionAccurredFromTimeToTime(accountNubmer, dateTimes.getTime(), dateTimes.getTime());
        verify(mockTransactionDao).getAllTransactionAccurredFromTimeToTime(accountNubmer, dateTimes.getTime(), dateTimes.getTime());
    }

}
