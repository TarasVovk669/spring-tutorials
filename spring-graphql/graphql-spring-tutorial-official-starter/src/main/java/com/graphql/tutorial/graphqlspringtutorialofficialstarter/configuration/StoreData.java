package com.graphql.tutorial.graphqlspringtutorialofficialstarter.configuration;

import com.graphql.tutorial.graphqlspringtutorialofficialstarter.domain.bank.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class StoreData {

    public static Map<Long, BankAccount> BANK_ACCOUNTS = new ConcurrentHashMap<>();
    public static Map<Long, List<Assets>> ASSETS_ACCOUNTS = new ConcurrentHashMap<>();
    public static Map<Long, Client> CLIENT_ACCOUNTS = new ConcurrentHashMap<>();
    public static Map<Long, Balance> BALANCE_ACCOUNTS = new ConcurrentHashMap<>();
    public static Map<String, User> USERS = new ConcurrentHashMap<>();


}
