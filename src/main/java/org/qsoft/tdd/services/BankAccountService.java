package org.qsoft.tdd.services;

import org.qsoft.tdd.dao.BankAccountDao;
import org.qsoft.tdd.dao.TransactionDao;
import org.qsoft.tdd.entity.BankAccount;
import org.qsoft.tdd.entity.Transaction;

import javax.print.attribute.standard.DateTimeAtCompleted;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: haopt
 * Date: 7/2/13
 * Time: 11:48 AM
 * To change this template use File | Settings | File Templates.
 */
public class BankAccountService {

    private BankAccountDao bankAccountDao;
    private TransactionDao transactionDao;
    private Date dateTime;

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public TransactionDao getTransactionDao() {
        return transactionDao;
    }

    public void setTransactionDao(TransactionDao transactionDao) {
        this.transactionDao = transactionDao;
    }

    public BankAccountDao getBankAccountDao() {
        return bankAccountDao;
    }

    public void setBankAccountDao(BankAccountDao mockAccountDao) {
        this.bankAccountDao = mockAccountDao;
    }

    public BankAccount openBankAccountWithMoney(String accountNumber, double amount) {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setAccountNumber(accountNumber);
        bankAccount.setBalance(amount);
        this.bankAccountDao.save(bankAccount);
        return bankAccount;
    }

    public BankAccount openBankAccount(String accountNumber) {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setAccountNumber(accountNumber);
        bankAccount.setBalance(0);
        this.bankAccountDao.save(bankAccount);
        return bankAccount;
    }

    public BankAccount getBankAccountByNumberId(String accountNumber) {
        return this.bankAccountDao.getBankAccountByAccountNumber(accountNumber);
    }

    public void deposit(BankAccount bankAccount, double amount) {
        bankAccount.setBalance(bankAccount.getBalance() + amount);
        this.bankAccountDao.save(bankAccount);

        Transaction t = new Transaction();
        t.setAccountNumber(bankAccount.getAccountNumber());
        t.setAmount(bankAccount.getBalance());
        t.setDescription("deposit");
        t.setTimeStamp(dateTime.getTime());
        this.transactionDao.save(t);
    }

    public void withDraw(BankAccount bankAccount, double amount) {
        if (bankAccount.getBalance() < amount) {
            throw new RuntimeException("Fail");
        }

        bankAccount.setBalance(bankAccount.getBalance() - amount);
        this.bankAccountDao.save(bankAccount);

        Transaction t = new Transaction();
        t.setAccountNumber(bankAccount.getAccountNumber());
        t.setAmount(bankAccount.getBalance());
        t.setDescription("withdraw");
        t.setTimeStamp(dateTime.getTime());
        this.transactionDao.save(t);
    }


    public List<Transaction> getAllTransactionAccurred(String numberAccount) {
        return transactionDao.getAllTransactionAccurred(numberAccount);
    }

    public List<Transaction> getAllTransactionAccurredFromTimeToTime(String numberAccount, long time1, long time2) {
        return transactionDao.getAllTransactionAccurredFromTimeToTime(numberAccount, time1, time2);
    }

}

