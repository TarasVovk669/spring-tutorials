package com.grpc.demo;

import com.grpc.demo.gen.Balance;
import com.grpc.demo.gen.BalanceRequest;
import com.grpc.demo.gen.BankServiceGrpc;
import com.grpc.demo.gen.Operation;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BankClientTest {

    private BankServiceGrpc.BankServiceBlockingStub bankServiceBlockingStub;

    @BeforeAll
    public void setup(){
        ManagedChannel localhost = ManagedChannelBuilder.forAddress("localhost", 8081)
                .usePlaintext().build();

        this.bankServiceBlockingStub = BankServiceGrpc.newBlockingStub(localhost);
    }

    @Test
    public void balanceTest(){
        Balance balance = this.bankServiceBlockingStub.getBalance(BalanceRequest.newBuilder().setAccountNumber(53212L).build());
        System.out.println(balance);
    }

    @Test
    public void balanceTestStreaming(){
        this.bankServiceBlockingStub.manageBalanceStream(
                BalanceRequest.newBuilder()
                        .setAccountNumber(5321L)
                        .setAmount(1.00)
                        .setOperation(Operation.ADD)
                        .build()
        ).forEachRemaining(System.out::println);
    }


}
