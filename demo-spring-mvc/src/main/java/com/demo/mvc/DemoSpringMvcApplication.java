package com.demo.mvc;

import com.demo.mvc.dao.AccountDao;
import com.demo.mvc.dao.MembershipDao;
import com.demo.mvc.domain.Account;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class DemoSpringMvcApplication {

  public static void main(String[] args) {
    SpringApplication.run(DemoSpringMvcApplication.class, args);
  }

  @Bean
  public CommandLineRunner commandLineRunner(AccountDao accountDao, MembershipDao membershipDao) {
    return runner -> {
      // demoTheBeforeAdvice(accountDao, membershipDao);
      // demoAfterAdvice(accountDao);
      // demoAfterThrowing(accountDao);
      // demoAround(accountDao);
      demoAroundSwallowException(accountDao);
    };
  }

  private void demoAroundSwallowException(AccountDao accountDao) throws InterruptedException {
    String processDataAccountsException = accountDao.getProcessDataAccountsException();
    System.out.println(processDataAccountsException);
  }

  private void demoAround(AccountDao accountDao) throws InterruptedException {
    accountDao.getProcessDataAccounts();
  }

  private void demoAfterThrowing(AccountDao accountDao) {
    accountDao.getExceptionAccount();
  }

  private void demoAfterAdvice(AccountDao accountDao) {
    List<Account> accounts = accountDao.getAccounts();
    System.out.println("main method: " + accounts);
  }

  private void demoTheBeforeAdvice(AccountDao accountDao, MembershipDao membershipDao) {

    var acc1 = new Account(1L, "First");
    var acc2 = new Account(1L, "Second");
    accountDao.addAccount(acc1);
    membershipDao.addAccount(acc2);
  }
}
