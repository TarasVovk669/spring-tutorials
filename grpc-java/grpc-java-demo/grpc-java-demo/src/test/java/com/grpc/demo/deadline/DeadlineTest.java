package com.grpc.demo.deadline;

import com.grpc.demo.gen.Balance;
import com.grpc.demo.gen.BalanceRequest;
import com.grpc.demo.gen.BankServiceGrpc;
import com.grpc.demo.gen.Operation;
import io.grpc.Deadline;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.concurrent.TimeUnit;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DeadlineTest {

    private BankServiceGrpc.BankServiceBlockingStub bankServiceBlockingStub;

    @BeforeAll
    public void setup(){
        ManagedChannel localhost = ManagedChannelBuilder.forAddress("localhost", 8081)
                .intercept(new DeadlineInterceptor())
                .usePlaintext()
                .build();

        this.bankServiceBlockingStub = BankServiceGrpc.newBlockingStub(localhost);
    }

    @Test
    public void balanceTest1(){
        Balance balance = this.bankServiceBlockingStub
                .getBalance(BalanceRequest.newBuilder()
                        .setAccountNumber(5321L).build());
        System.out.println(balance);
    }

    @Test
    public void balanceTest2(){
        Balance balance = this.bankServiceBlockingStub
                .withDeadline(Deadline.after(4, TimeUnit.SECONDS))
                .getBalance(BalanceRequest.newBuilder()
                        .setAccountNumber(5321L).build());
        System.out.println(balance);
    }


}
