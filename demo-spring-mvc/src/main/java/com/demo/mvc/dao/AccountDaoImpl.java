package com.demo.mvc.dao;

import org.springframework.stereotype.Repository;

@Repository
public class AccountDaoImpl implements AccountDao {

    public void addAccount(){

    System.out.println(getClass()+ ": DOING MY DB WORK: ADDING ACCOUNT");
    }
}
