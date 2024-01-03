package com.grpc.demo.loadbalancing;

import com.grpc.demo.gen.Balance;
import com.grpc.demo.gen.BalanceRequest;
import com.grpc.demo.gen.BankServiceGrpc;
import com.grpc.demo.loadbalancing.additional.ServiceRegistry;
import com.grpc.demo.loadbalancing.additional.TempNameResolverProvider;
import io.grpc.Deadline;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.NameResolverRegistry;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ClientSideLBTest {

    private BankServiceGrpc.BankServiceBlockingStub blockingStub;

    @BeforeAll
    public void setup() {
        List<String> instances = new ArrayList<>();
        instances.add("localhost:8081");
        instances.add("localhost:8082");
        ServiceRegistry.register("bank-service", instances);
        NameResolverRegistry.getDefaultRegistry().register(new TempNameResolverProvider());

        System.out.println(ServiceRegistry.getInstances("bank-service"));
        ManagedChannel managedChannel = ManagedChannelBuilder
                //.forAddress("localhost", 8585)
                .forTarget("http://bank-service")
                .defaultLoadBalancingPolicy("round_robin")
                .usePlaintext()
                .build();
        this.blockingStub = BankServiceGrpc.newBlockingStub(managedChannel);
    }

    @Test
    public void balanceTest() {
        for (int i = 0; i < 100; i++) {
            BalanceRequest balanceCheckRequest = BalanceRequest.newBuilder()
                    .setAccountNumber(5321)
                    .build();
            Balance balance = this.blockingStub
                    .withDeadlineAfter(1, TimeUnit.SECONDS)
                    .getBalance(balanceCheckRequest);
            System.out.println("Balance : " + balance);
        }
    }
}
