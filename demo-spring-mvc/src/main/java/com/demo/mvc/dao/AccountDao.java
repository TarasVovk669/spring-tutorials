package com.demo.mvc.dao;

import com.demo.mvc.domain.Account;

import java.util.List;

public interface AccountDao {

    void addAccount();
    void addAccount(Account account);

    List<Account> getAccounts();

    void getExceptionAccount();

    String getProcessDataAccounts() throws InterruptedException;

    String getProcessDataAccountsException() throws InterruptedException;
}
