package com.ezpay.bank.model;

import java.util.*;

public class BankAccount {
    int Bankid;
    String BankName;
    String accountNumber;
    boolean isVerified;

    public BankAccount(int bankid, String bankName, String accountNumber, boolean isVerified) {
        super();
        Bankid = bankid;
        BankName = bankName;
        this.accountNumber = accountNumber;
        this.isVerified = isVerified;
    }
    //getters
    public int getBankid() {
        return Bankid;
    }
    public String getBankName() {
        return BankName;
    }
    public String getAccountNumber() {
        return accountNumber;
    }
    public boolean isVerified() {
        return isVerified;
    }
    //setters
    public void setBankid(int bankid) {
        Bankid = bankid;
    }
    public void setBankName(String bankName) {
        BankName = bankName;
    }
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
    public void setVerified(boolean isVerified) {
        this.isVerified = isVerified;
    }
}
