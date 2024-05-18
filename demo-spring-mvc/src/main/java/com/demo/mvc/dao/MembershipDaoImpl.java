package com.demo.mvc.dao;

import com.demo.mvc.domain.Account;
import org.springframework.stereotype.Repository;

@Repository
public class MembershipDaoImpl implements MembershipDao {

    public void addAccount(){
    System.out.println(getClass()+ ": DOING MY DB WORK: ADDING ACCOUNT");
    }

    @Override
    public void addAccount(Account account) {
        System.out.println(getClass()+ ": DOING MY DB WORK: ADDING ACCOUNT with Account: "+ account);
    }
}
