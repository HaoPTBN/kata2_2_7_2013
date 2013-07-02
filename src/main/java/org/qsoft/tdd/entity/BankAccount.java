package org.qsoft.tdd.entity;

/**
 * Created by IntelliJ IDEA.
 * User: haopt
 * Date: 7/2/13
 * Time: 11:52 AM
 * To change this template use File | Settings | File Templates.
 */
public class BankAccount {

    private String accountNumber;
    private double balance;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
