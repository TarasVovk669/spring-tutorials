package com.demo.mvc.dao;

import com.demo.mvc.domain.Account;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AccountDaoImpl implements AccountDao {

  public void addAccount() {
    System.out.println(getClass() + ": DOING MY DB WORK: ADDING ACCOUNT");
  }

  @Override
  public void addAccount(Account account) {
    System.out.println(getClass() + ": DOING MY DB WORK: ADDING ACCOUNT with Account: " + account);
  }

  @Override
  public List<Account> getAccounts() {
    return List.of(
        new Account(1L, "name1"),
        new Account(2L, "name2"),
        new Account(3L, "name3"),
        new Account(4L, "name4"));
  }

  @Override
  public void getExceptionAccount() {
    System.out.println("Before exception method");

    throw new RuntimeException("My custom message");
  }

  @Override
  public String getProcessDataAccounts() throws InterruptedException {
    System.out.println("Enter method pda");

    Thread.sleep(1500);

    return "data";
  }

  @Override
  public String getProcessDataAccountsException() throws InterruptedException {
    System.out.println("Enter method pda with exception");

    Thread.sleep(1500);

    throw new RuntimeException("Error in method");
  }
}
