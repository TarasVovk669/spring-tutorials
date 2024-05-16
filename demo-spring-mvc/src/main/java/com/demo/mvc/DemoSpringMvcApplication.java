package com.demo.mvc;

import com.demo.mvc.dao.AccountDao;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoSpringMvcApplication {

  public static void main(String[] args) {
    SpringApplication.run(DemoSpringMvcApplication.class, args);
  }

  @Bean
  public CommandLineRunner commandLineRunner(AccountDao accountDao) {
    return runner -> {
      demoTheBeforeAdvice(accountDao);
    };
  }

  private void demoTheBeforeAdvice(AccountDao accountDao) {

    accountDao.addAccount();
  }
}
