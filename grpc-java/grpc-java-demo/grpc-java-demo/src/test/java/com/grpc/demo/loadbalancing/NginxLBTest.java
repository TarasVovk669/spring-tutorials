package com.grpc.demo.loadbalancing;

import com.grpc.demo.gen.Balance;
import com.grpc.demo.gen.BalanceRequest;
import com.grpc.demo.gen.BankServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class NginxLBTest {


    private BankServiceGrpc.BankServiceBlockingStub bankServiceBlockingStub;

    @BeforeAll
    public void setup() {
        ManagedChannel localhost = ManagedChannelBuilder.forAddress("localhost", 8585)
                .usePlaintext().build();

        this.bankServiceBlockingStub = BankServiceGrpc.newBlockingStub(localhost);

    }

    @Test
    public void balanceTest() {
        for (int i = 0; i < 10; i++) {
            Balance balance = this.bankServiceBlockingStub.getBalance(BalanceRequest.newBuilder().setAccountNumber(5321L).build());
            System.out.println(balance);
        }
    }
}
