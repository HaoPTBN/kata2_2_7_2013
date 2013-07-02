package org.qsoft.tdd.entity;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: haopt
 * Date: 7/2/13
 * Time: 1:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class Transaction {
    private String accountNumber;
    private Long timeStamp;
    private Double amount;
    private String description;
//    private Calendar calendar = Calendar.getInstance() ;
//    private Date date;

//    public Date getDate() {
//        return date;
//    }
//
//    public void setDate(Date date) {
//        this.date = date;
//    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

//    public Calendar getCalendar() {
//        return calendar;
//    }
//
//    public void setCalendar(Calendar calendar) {
//        this.calendar = calendar;
//    }
}
